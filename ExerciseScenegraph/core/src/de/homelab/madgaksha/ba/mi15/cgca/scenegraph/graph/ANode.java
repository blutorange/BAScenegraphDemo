package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public abstract class ANode implements Iterable<Entry<String, PrioritizedNode>> {
	public static enum Type {
		CAMERA,
		OBJECT,
		ROOT,
		TRANSFORM;
	}

	protected final static Matrix4 IDENTITY = new Matrix4();

	/** @see #cascadeTransform(Matrix4) */
	private final Matrix4 cascadedTransform = new Matrix4();

	/** Mappe mit Namen der Kinderknoten zu Kinderknoten. */
	private final Map<String, PrioritizedNode> children = new HashMap<>();

	/** Farbe dieses Knotens. */
	private final Color color = new Color(Color.WHITE);

	/**
	 * <code>true</code>, wenn Kinder neu sortiert werden müssen.
	 *
	 * @see #rebuildSortedChildren()
	 */
	private boolean dirty = true;

	/** @see #setOrigin(float, float)} */
	private float originX = 0.5f;

	/** @see #setOrigin(float, float)} */
	private float originY = 0.5f;

	/** Elternknoten, oder <code>null</code>, wenn es keinen gibt. */
	protected ANode parent = null;
	/** @see #setSmoothingFactor(float) */
	private float smoothingFactor = 1f;

	/** @see #setSmoothingFactor(float) */
	private final Matrix4 smoothTransform = new Matrix4();

	/** @see #rebuildSortedChildren() */
	private final List<PrioritizedNode> sortedChildren = new ArrayList<>();

	/** Momentane Transformation dieses Knotens. */
	public final Matrix4 transform = new Matrix4();

	/**
	 * Erstellt neuen Knoten und setzt die Transformationsmatrix auf die
	 * Einheitsmatrix.
	 */
	public ANode() {
		this(new Matrix4().idt());
	}

	/**
	 * Erstellt neuen Knoten und setzt die Transformationsmatrix auf die
	 * Verschiebung (x,y).
	 *
	 * @param x
	 *            Verschiebung x.
	 * @param y
	 *            Verschiebung y.
	 */
	public ANode(final float x, final float y) {
		this(new Matrix4().translate(x, y, 0));
	}

	/**
	 * Erstellt neuen Knoten und setzt die Transformationsmatrix auf die
	 * gegebene Matrix.
	 *
	 * @param transform
	 *            Zu setztende Transformationsmatrix.
	 */
	public ANode(final Matrix4 transform) {
		if (transform == null)
			this.transform.idt();
		else
			this.transform.set(transform);
	}

	/**
	 * Erstellt neuen Knoten und setzt die Transformationsmatrix auf die
	 * Verschiebung (origin.x,origin.y).
	 *
	 * @param x
	 *            Verschiebung x.
	 * @param y
	 *            Verschiebung y.
	 */
	public ANode(final Vector2 origin) {
		this(origin.x, origin.y);
	}

	/**
	 * Fügt Knoten mit Renderpriorität <code>0</code> hinzu.
	 *
	 * @return Diesen Knoten zum Verketten.
	 * @see #addChild(NodeUnit, int)
	 */
	public ANode addChild(final NodeUnit unit) {
		return addChild(unit, 0);
	}

	/**
	 * Fügt einen Knoten eines Spielobjekts hinzu. Ein Spielobjektknoten hat
	 * einen Namen, dieser muss daher nicht extra angegeben werden. Ist der Name
	 * bereits vorhanden, wird ein Index (1,2,3,4,...) angehängt.
	 *
	 * @param unit
	 *            Knoten zum Hinzufügen.
	 * @param renderPriority
	 *            Renderpriorität
	 * @return Diesen Knoten zum Verketten.
	 * @see #addChild(String, ANode, int)
	 */
	public ANode addChild(final NodeUnit unit, final int renderPriority) {
		int i = 0;
		final String name = unit.name();
		String newName;
		do {
			newName = name + ++i;
		}
		while (children.get(newName) != null);
		return addChild(newName, unit, renderPriority);
	}

	/**
	 * Fügt Knoten mit Renderpriorität <code>0</code> hinzu.
	 *
	 * @see ANode#addChild(String, ANode, int)
	 */
	public ANode addChild(final String name, final ANode node) {
		return addChild(name, node, 0);
	}

	/**
	 * Fügt einen Kindknoten mit gegebenen Namen hinzu. Später kann über den
	 * Namen einfach auf den hinzugefügten Knoten wieder zugegriffen werden.
	 * Zudem kann festgelegt werden, in welcher Reihenfolge später die
	 * Kindknoten gerendert werden. Je höher die Renderpriorität, desto später
	 * wird der Knoten gerendert und verdeckt damit Knoten geringerer
	 * Renderpriorität.
	 *
	 * @param name
	 *            Name des Knotens.
	 * @param node
	 *            Knoten zum Hinzufügen.
	 * @param renderPriority
	 *            Renderpriorität.
	 * @return Diesen Knoten zum Verketten.
	 */
	public ANode addChild(final String name, final ANode node, final int renderPriority) {
		removeChild(name);
		children.put(name, new PrioritizedNode(node, renderPriority));
		node.parent = this;
		dirty = true;
		return this;
	}

	/**
	 * Nimmt die gegebene Transformation und verbindet diese mit der
	 * Transformation dieses Knotens. Anschließend wird die so erhaltene
	 * Transformation an alle Kinder weitergegeben.
	 *
	 * @param matrix
	 *            Ausgangsmatrix.
	 * @return Diesen Knoten zum Verketten.
	 */
	public final ANode cascadeTransform(final Matrix4 matrix) {
		cascadedTransform.set(matrix).mul(updateAndGetSmoothTransform());
		for (final PrioritizedNode child : children.values())
			child.node.cascadeTransform(cascadedTransform);
		return this;
	}

	/**
	 * Prüft, ob dieser Knoten mit einem anderen in Berührung steht. Dabei
	 * werden die räumlichen Ausdehnungen genommen, wie sie von
	 * {@link #getTopHeight()} etc. zurückgeliefert werden. Diese sind relativ
	 * zum Koordinatenursprung im Koordinatensystem dieses Knotens.
	 *
	 * @param node
	 *            Knoten, mit dem Kollision geprüft werden soll.
	 * @return Ob die Knoten kollidieren.
	 */
	public boolean collides(final ANode node) {
		final Vector3 thiz = this.inWorldCoordinates();
		final Vector3 other = node.inWorldCoordinates();
		return rangeOverlap(thiz.x - this.getLeftWidth(), thiz.x + this.getRightWidth(), other.x - node.getLeftWidth(),
				other.x + node.getRightWidth())
				&& rangeOverlap(thiz.y - this.getBottomHeight(), thiz.y + this.getTopHeight(),
						other.x - node.getBottomHeight(), other.x + node.getTopHeight());
	}

	/**
	 * Entfernt diesen Knoten aus dem Graphen. Dabei muss der Verweis auf den
	 * Elternknoten auf <code>null</code> gesetzt werden und dieser Knoten aus
	 * den Kindknoten des Elternknoten gesetzt werden. Der Einfachkeit halber
	 * kann hierfür auch die Funktion {@link #removeChild(String)} genutzt
	 * werden.
	 *
	 * @return Diesen Knoten zum Verketten.
	 */
	public ANode detach() {
		if (parent != null) {
			for (final Entry<String, PrioritizedNode> entry : parent.children.entrySet()) {
				if (this == entry.getValue().node) {
					parent.removeChild(entry.getKey());
					break;
				}
			}
		}
		return this;
	}

	/**
	 * Methode für Unterklassen. Dafür verantwortlich, den Knoten zu zeichnen.
	 * Das kann über Sprites geschehen, Formen zeichen oder 3D-Modelle verwenden
	 * etc. Wenn nichts zum Zeichnen ist, braucht diese Methode auch nichts
	 * machen. Standardmäßig passiert nichts, kann Überschrieben werden.
	 *
	 * @param batch
	 *            Batch, mit dem gezeichnet werden soll.
	 * @param color
	 *            Die kaskadierte Farbe zum Zeichnen.
	 */
	public void draw(final Batch batch, final Color color) {
	}

	/**
	 * Sucht in allen Kinderknoten (also auch den Kindknoten der Kinderknoten
	 * etc.) nach einem Knoten mit gegebenen Namen. Ineffektiv, aber der
	 * Einfachheit halber kann man das so machen. Unser Programm ist nicht so
	 * rechenaufwändig.
	 *
	 * @param name
	 *            Name des Knoten.
	 * @return Der erste gefundene Knoten mit dem Namen, sonst <code>null</code>
	 *         , wenn kein solcher Knoten existiert.
	 */
	public ANode getByName(final String name) {
		// Search first level children first, most common case?
		for (final Entry<String, PrioritizedNode> child : children.entrySet()) {
			if (child.getKey().equals(name))
				return child.getValue().node;
		}
		for (final Entry<String, PrioritizedNode> child : children.entrySet()) {
			final ANode tmp = child.getValue().node.getByName(name);
			if (tmp != null)
				return tmp;
		}
		return null;
	}

	/**
	 * @return Abstand vom Ursprung dieses Knotens zur unteren Kante. Nur nötig
	 *         für Knoten, mit denen Kollision berechnet werden soll.
	 * @see #setOrigin(float, float)
	 */
	public abstract float getBottomHeight();

	/**
	 * @return Die kaskadierte Transformation. Führt kein Update durch, hierzu
	 *         muss {@link #cascadeTransform(Matrix4)} direkt aufgerufen werden.
	 */
	public final Matrix4 getCascadedTransform() {
		return cascadedTransform;
	}

	/***
	 * @param name
	 *            Name des Kindes-
	 * @return Kindknoten mit diesem Namen, <code>null</code> wenn kein solcher
	 *         existiert.
	 */
	public ANode getChild(final String name) {
		return children.get(name).node;
	}

	/**
	 * @return Farbe, die dieser Knoten haben soll.
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @return Abstand vom Ursprung dieses Knotens zur linken Kante. Nur nötig
	 *         für Knoten, mit denen Kollision berechnet werden soll.
	 * @see #setOrigin(float, float)
	 */
	public abstract float getLeftWidth();

	/**
	 * @return Koordinatenursprung des Sprites dieses Knotens. 0 ist die linke
	 *         Kante, 1 die rechte Kante.
	 * @see #setOrigin(float, float)
	 */
	public float getOriginX() {
		return originX;
	}

	/**
	 * @return Koordinatenursprung des Sprites dieses Knotens. 0 ist die untere
	 *         Kante, 1 die obere Kante.
	 * @see #setOrigin(float, float)
	 */
	public float getOriginY() {
		return originY;
	}
        
        public ANode getParent() {
            return parent;
        }

	/**
	 * @return Abstand vom Ursprung dieses Knotens zur rechten Kante. Nur nötig
	 *         für Knoten, mit denen Kollision berechnet werden soll.
	 * @see #setOrigin(float, float)
	 */
	public abstract float getRightWidth();

	/**
	 * Glättungsfaktor, der für einen glatten Übergang zwischen verschiedenen
	 * Animationen benutzt wird, sodass es z.B. keinen abrupten Übergang
	 * zwischen Stehanimation und Laufanimation gibt. Zwischen <code>0</code>
	 * und <code>1</code>, bei <code>0</code> gibt es keinen Übergang, bei
	 * <code>1</code> ist der Übergang abrupt.
	 *
	 * @return Der Glättungsfaktor.
	 */
	public float getSmoothingFactor() {
		return smoothingFactor;
	}

	/**
	 * @return Abstand vom Ursprung dieses Knotens zur oberen Kante. Nur nötig
	 *         für Knoten, mit denen Kollision berechnet werden soll.
	 * @see #setOrigin(float, float)
	 */
	public abstract float getTopHeight();

	/**
	 * @return Die derzeitige Transformation dieses Knotens.
	 */
	public Matrix4 getTransform() {
		return transform;
	}

	/**
	 * @return Der Punkt (0,0) in Knotenkoordinaten in Weltkoordinaten.
	 */
	public Vector3 inWorldCoordinates() {
		return inWorldCoordinates(0f, 0f);
	}

	/**
	 * @param x
	 *            Punkt x.
	 * @param y
	 *            Punkt y.
	 * @return Der gegebene Punkt in Knotenkoordinaten wird umgerechnet in
	 *         Weltkoordinaten zurückgegeben.
	 */
	public Vector3 inWorldCoordinates(final float x, final float y) {
		return new Vector3(x, y, 0f).mul(getCascadedTransform());
	}

	/**
	 * @return Ein Iterator zum Iterieren über die Kinder 1. Grades dieses
	 *         Knotens.
	 */
	@Override
	public final Iterator<Entry<String, PrioritizedNode>> iterator() {
		return children.entrySet().iterator();
	}

	/**
	 * Spiegelt diesen Knoten an der y-Achse, d.h. vertauscht link mit rechts.
	 *
	 * @return
	 */
	public ANode mirrorX() {
		getTransform().scale(-1f, 1f, 1f);
		return this;
	}

	/**
	 * @return Setzt die Farbe dieses Knoten auf eine Farbe mit
	 *         zufallsgenerierten Hue.
	 */
	public ANode randomHue() {
		return randomHue(MathUtils.random(0.5f, 1f), 1f);
	}

	/**
	 * @param saturation
	 *            Sättigung zwischen 0 und 1.
	 * @param brightness
	 *            Helligkeit zwischen 0 und 1.
	 * @return Setzt die Farbe dieses Knoten auf eine Farbe mit
	 *         zufallsgenerierten Hue bei gegebener Helligkeit und Sättigung.
	 */
	public ANode randomHue(final float saturation, final float brightness) {
		return setHsb(MathUtils.random(0f, 360f), saturation, brightness);
	}

	/**
	 * @param range1From
	 *            Intervall 1 Anfang.
	 * @param range1To
	 *            Intervall 2 Ende.
	 * @param range2From
	 *            Intervall 1 Anfang.
	 * @param range2To
	 *            Intervall 2 Ende.
	 * @return Ob sich die beiden Intervalle überlappen.
	 */
	private boolean rangeOverlap(final float range1From, final float range1To, final float range2From,
			final float range2To) {
		return range1From <= range2To && range2From <= range1To;
	}

	/**
	 * Sortiert die derzeitigen Kinder nach ihrer Renderpriorität und schreibt
	 * das Ergebnis in die Liste {@link #sortedChildren}. Wird automatisch bei
	 * Bedarf aufgerufen. {@link #dirty} muss auf <code>false</code> gesetzt
	 * werden.
	 *
	 * @see #sortedIterator()
	 */
	private void rebuildSortedChildren() {
		sortedChildren.clear();
		sortedChildren.addAll(children.values());
		Collections.sort(sortedChildren);
		dirty = false;
	}

	/**
	 * Entfernt das Kind mit gegebenen Namen, falls verhanden. Der Eintrag im
	 * Feld {@link #parent} des Kinderknoten muss auf <code>null</code> gesetzt
	 * werden, der Knoten aus der Kinderliste entfernt werden und die Liste
	 * {@link ANode#sortedChildren} neu erstellt werden, d.h. {@link #dirty} auf
	 * <code>true</code> gesetzt werden.
	 *
	 * @param name
	 *            Name des zu entfernenden Knoten.
	 * @return Diese Knoten zum Verketten.
	 */
	public ANode removeChild(final String name) {
		final PrioritizedNode node = children.get(name);
		if (node != null)
			node.node.parent = null;
		children.remove(name);
		dirty = true;
		return this;
	}

	/**
	 * Setzt die Transformationsmatrix auf die Einheitsmatrix.
	 *
	 * @return Diesen Knoten zum Verketten.
	 */
	public ANode reset() {
		getTransform().idt();
		return this;
	}

	/**
	 * Rotiert diesen Knoten um den gegebene Winkel.
	 *
	 * @param degrees
	 *            Winkel in Grad.
	 * @return Diesen Knoten zum Verketten.
	 */
	public ANode rotate(final float degrees) {
		getTransform().rotate(0, 0, 1, degrees);
		return this;
	}

	/**
	 * Skaliert diesen Knoten um den gegebenen Faktor.
	 *
	 * @param scaleXY
	 *            Skalierungsfaktor in X- und Y-Richtung.
	 * @return Diesen Knoten zum Verketten.
	 */
	public ANode scale(final float scaleXY) {
		getTransform().scale(scaleXY, scaleXY, 1f);
		return this;
	}

	/**
	 * Skaliert diesen Knoten um den gegebenen Faktor.
	 *
	 * @param scaleX
	 *            Skalierungsfaktor in X-Richtung.
	 * @param scaleY
	 *            Skalierungsfaktor in Y-Richtung.
	 * @return Diesen Knoten zum Verketten.
	 */
	public ANode scale(final float scaleX, final float scaleY) {
		getTransform().scale(scaleX, scaleY, 1f);
		return this;
	}

	/**
	 * Setzt den Alphawert der momentanen Farbe auf den gegebenen Wert.
	 * @param alpha Zu setztendes Alpha. 0 ist transparent, 1 opaque.
	 * @return Diesen Knoten zum Verketten.
	 */
	public ANode setAlpha(final float alpha) {
		color.a = alpha;
		return this;
	}

	/**
	 * Setzt die Farbe dieses Knotens.
	 *
	 * @param color
	 *            Zu setztende Farbe.
	 * @return Diesen Knoten zum Verketten.
	 */
	public ANode setColor(final Color color) {
		this.color.set(color);
		return this;
	}

	/**
	 * Setzt die Farbe dieses Knotens, ohne Transparenz.
	 *
	 * @param r
	 *            Rotanteil, zwischen 0 und 1.
	 * @param g
	 *            Rotanteil, zwischen 0 und 1.
	 * @param b
	 *            Rotanteil, zwischen 0 und 1.
	 * @return Diesen Knoten zum Verketten.
	 */
	public ANode setColor(final float r, final float g, final float b) {
		this.color.set(r, g, b, 1f);
		return this;
	}

	/**
	 * Setzt die Farbe dieses Knoten auf eine Farbe mit zufallsgenerierten Hue
	 * bei gegebener Helligkeit und Sättigung.
	 *
	 * @param hue
	 *            Hue zwischen 0 und 1.
	 * @param saturation
	 *            Sättigung zwischen 0 und 1.
	 * @param brightness
	 *            Helligkeit zwischen 0 und 1.
	 * @return Diesen Knoten zum Verketten.
	 */
	public ANode setHsb(final float hue, final float saturation, final float brightness) {
		final int c = java.awt.Color.HSBtoRGB(hue, saturation, brightness);
		setColor(((c & 0xFF0000) >> 16) / 255f, ((c & 0x00FF00) >> 8) / 255f, (c & 0x0000FF) / 255f);
		return this;
	}

        /**
         * Setzt die geglättete Transformationsmatrix. Sollte nur initial
         * verwendet werden, um den Startpunkt festzulegen.
         * @return Diesen Knoten zum Verketten.
         * @param transform Zu setztende Transformation.
         */
        public ANode setSmoothTransform(Matrix4 transform) {
            this.smoothTransform.set(transform);
            return this;
        }
        
	/**
	 * Nur relevant, wenn dieser Knoten gezeichnet wird. Setzt den Punkt des
	 * Sprites, der am Ursprungspunkt (0,0) dieses Knotens sein soll. (0,0)
	 * bedeutet dabei, dass die linke unteren Ecke des Sprites genommen wird,
	 * (1,1) die rechte obere Ecke.
	 *
	 * @param originX
	 *            Urspung X zwischen 0 und 1.
	 * @param originY
	 *            Urspung X zwischen 0 und 1.
	 * @return Diesen Knoten zum Verketten.
	 */
	public ANode setOrigin(final float originX, final float originY) {
		this.originX = originX;
		this.originY = originY;
		return this;
	}

	public ANode setRenderPriority(final int renderPriority) {
		if (parent != null) {
			for (final Entry<String, PrioritizedNode> entry : parent.children.entrySet()) {
				if (this == entry.getValue().node) {
					parent.setRenderPriority(entry.getKey(), renderPriority);
					break;
				}
			}
		}
		return this;
	}

	public ANode setRenderPriority(final String name, final int renderPriority) {
		final PrioritizedNode node = children.get(name);
		if (node != null) {
			children.put(name, new PrioritizedNode(node.node, renderPriority));
			dirty = true;
		}
		return this;
	}

	/**
	 * Glättungsfaktor, der für einen glatten Übergang zwischen verschiedenen
	 * Animationen benutzt wird, sodass es z.B. keinen abrupten Übergang
	 * zwischen Stehanimation und Laufanimation gibt. Zwischen <code>0</code>
	 * und <code>1</code>, bei <code>0</code> gibt es keinen Übergang, bei
	 * <code>1</code> ist der Übergang abrupt.
	 *
	 * @param smoothingFactor
	 *            Der zu setztende Glättungsfaktor.
	 * @return Diesen Knoten zum Verketten.
	 * @see #getSmoothingFactor()
	 */
	public ANode setSmoothingFactor(final float smoothingFactor) {
		this.smoothingFactor = MathUtils.clamp(smoothingFactor, 0f, 1f);
		return this;
	}

	/**
	 * Setzt den Glättungsfaktor für diesen Knoten und alle Unterknoten.
	 *
	 * @param smoothingFactor
	 *            Der zu setztende Glättungsfaktor.
	 * @return Diesen Knoten zum Verketten.
	 * @see #setSmoothingFactor(float)
	 * @see #getSmoothingFactor()
	 */
	public ANode setSmoothingFactorForThisAndChildren(final float smoothingFactor) {
		setSmoothingFactor(smoothingFactor);
		for (final PrioritizedNode child : children.values())
			child.node.setSmoothingFactorForThisAndChildren(smoothingFactor);
		return this;
	}

	/**
	 * @return Ein Iterator, der alle Kinderknoten 1. Grades in Reihenfolge der
	 *         Renderpriorität zurückliefert.
	 * @see #rebuildSortedChildren()
	 */
	public Iterator<PrioritizedNode> sortedIterator() {
		if (dirty)
			rebuildSortedChildren();
		return sortedChildren.iterator();
	}

	/**
	 * Verschiebt diesen Knoten um den gegebenen Wert.
	 *
	 * @param dx
	 *            Verschiebung X.
	 * @param dy
	 *            Verschiebung Y.
	 * @return Diesen Knoten zum Verketten.
	 */
	public ANode translate(final float dx, final float dy) {
		getTransform().translate(dx, dy, 0);
		return this;
	}

	/**
	 * Standardmäßig passiert nicht beim Update. Kann überschrieben werden.
	 *
	 * @param time
	 *            Zeit, die insgesamt seit Spielstart vergangen ist.
	 * @param deltaTime
	 *            Zeit, die seit dem letzten Frame vergangen ist.
	 */
	protected void update(final float time, final float deltaTime) {
	}

	/**
	 * Updated die geglättete Transformation und liefert diese zurück.
	 *
	 * @return Die geglättete Transformation.
	 * @see #getSmoothingFactor()
	 */
	public Matrix4 updateAndGetSmoothTransform() {
		return smoothingFactor == 1f ? smoothTransform.set(getTransform())
				: smoothTransform.lerp(getTransform(), smoothingFactor);
	}

	/**
	 * @param time
	 *            Zeit, die ingesamt seit Spielstart vergangen ist.
	 * @param deltaTime
	 *            Zeit, die seit dem letzten Frame vergangen ist.
	 */
	public final void updateThisAndChildren(final float time, final float deltaTime) {
		update(time, deltaTime);
		for (final PrioritizedNode child : children.values().toArray(new PrioritizedNode[children.values().size()]))
			child.node.update(time, deltaTime);
	}
}
