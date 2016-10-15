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
		ROOT,
		CAMERA,
		OBJECT,
		TRANSFORM;
	}
	protected final static Matrix4 IDENTITY = new Matrix4();

	private final Matrix4 smoothTransform = new Matrix4();
	private final Matrix4 cascadedTransform = new Matrix4();
	public final Matrix4 transform = new Matrix4();
	private float smoothingFactor = 1f;

	protected ANode parent = null;
	private final Map<String, PrioritizedNode> children = new HashMap<>();

	private final List<PrioritizedNode> sortedChildren = new ArrayList<>();
	private boolean dirty = true;

	private final Color color = new Color(Color.WHITE);
	private float originX = 0.5f;
	private float originY = 0.5f;

	public ANode(final Matrix4 transform) {
		if (transform == null)
			this.transform.idt();
		else
			this.transform.set(transform);
	}

	public ANode() {
		this(new Matrix4());
	}

	public ANode(final Vector2 origin) {
		this(origin.x, origin.y);
	}

	public ANode(final float x, final float y) {
		this(new Matrix4().translate(x, y, 0));
	}

	@Override
	public final Iterator<Entry<String, PrioritizedNode>> iterator() {
		return children.entrySet().iterator();
	}

	public Matrix4 updateAndGetSmoothTransform() {
		return smoothingFactor == 1f ? smoothTransform.set(getTransform()) : smoothTransform.lerp(getTransform(), smoothingFactor);
	}

	public Matrix4 getTransform() {
		return transform;
	}

	public abstract void draw(Batch batch, Color color);

	public ANode addChild(final String name, final ANode node) {
		return addChild(name, node, 0);
	}

	public ANode addChild(final String name, final ANode node, final int renderPriority) {
		removeChild(name);
		children.put(name, new PrioritizedNode(node, renderPriority));
		node.parent = this;
		dirty = true;
		return this;
	}

	public final ANode cascadeTransform(final Matrix4 matrix) {
		cascadedTransform.set(matrix).mul(updateAndGetSmoothTransform());
		for (final PrioritizedNode child : children.values()) child.node.cascadeTransform(cascadedTransform);
		return this;
	}

	public final Matrix4 getCascadedTransform() {
		return cascadedTransform;
	}

	private void rebuildSortedChildren() {
		sortedChildren.clear();
		sortedChildren.addAll(children.values());
		Collections.sort(sortedChildren);
		dirty = false;
	}

	public Iterator<PrioritizedNode> sortedIterator() {
		if (dirty) rebuildSortedChildren();
		return sortedChildren.iterator();
	}

	public ANode addChild(final NodeUnit unit) {
		return addChild(unit, 0);
	}

	public ANode setColor(final Color color) {
		this.color.set(color);
		return this;
	}

	public ANode setColor(final float r, final float g, final float b) {
		this.color.set(r,g,b,1f);
		return this;
	}

	public Color getColor() {
		return color;
	}

	public ANode addChild(final NodeUnit unit, final int renderPriority) {
		int i = 0;
		final String name = unit.name();
		String newName;
		do {
			newName = name + ++i;
		} while (children.get(newName) != null);
		return addChild(newName, unit, renderPriority);
	}

	public ANode removeChild(final String name) {
		final PrioritizedNode node = children.get(name);
		if (node != null) node.node.parent = null;
		children.remove(name);
		dirty = true;
		return this;
	}

	/**
	 * Entfernt diesen Knoten aus dem Graphen.
	 * @return Diesen Knoten.
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

	public ANode getChild(final String name) {
		return children.get(name).node;
	}

	public ANode translate(final float dx, final float dy) {
		getTransform().translate(dx, dy, 0);
		return this;
	}

	public ANode rotate(final float degrees) {
		getTransform().rotate(0, 0, 1, degrees);
		return this;
	}

	private ANode getAnyChildByName(final String name) {
		// Search first level children first, most common case?
		for (final Entry<String, PrioritizedNode> child : children.entrySet()) {
			if (child.getKey().equals(name)) return child.getValue().node;
		}
		for (final Entry<String, PrioritizedNode> child : children.entrySet()) {
			final ANode tmp = child.getValue().node.getAnyChildByName(name);
			if (tmp != null) return tmp;
		}
		return null;
	}

	/**
	 * @param name Name eines Knoten eines Unterknoten dieses Knotens.
	 * @return Den ersten gefunden Knoten mit diesem Namen. <code>null</code>, wenn kein solcher Knoten existiert.
	 */
	public ANode getByName(final String name) {
		// Search children
		final ANode node = this;
		//		while (node.parent != null) node = node.parent;
		return node.getAnyChildByName(name);
	}

	/**
	 * @param time Zeit, die ingesamt seit Spielstart vergangen ist.
	 * @param deltaTime Zeit, die seit dem letzten Frame vergangen ist.
	 */
	public final void updateThisAndChildren(final float time, final float deltaTime) {
		update(time, deltaTime);
		for (final PrioritizedNode child : children.values().toArray(new PrioritizedNode[children.values().size()]))
			child.node.update(time, deltaTime);
	}

	public ANode setSmoothingFactor(final float smoothingFactor) {
		this.smoothingFactor = MathUtils.clamp(smoothingFactor, 0f, 1f);
		return this;
	}

	public ANode setSmoothingFactorForThisAndChildren(final float smoothingFactor) {
		setSmoothingFactor(smoothingFactor);
		for (final PrioritizedNode child : children.values()) child.node.setSmoothingFactorForThisAndChildren(smoothingFactor);
		return this;
	}

	/**
	 * @param time Zeit, die ingesamt seit Spielstart vergangen ist.
	 * @param deltaTime Zeit, die seit dem letzten Frame vergangen ist.
	 */
	protected void update(final float time, final float deltaTime){}

	public float getOriginX() {
		return originX ;
	}
	public float getOriginY() {
		return originY;
	}
	public ANode setOrigin(final float originX, final float originY) {
		this.originX = originX;
		this.originY = originY;
		return this;
	}

	public ANode reset() {
		getTransform().idt();
		return this;
	}

	public ANode mirrorX() {
		getTransform().scale(-1f, 1f, 1f);
		return this;
	}

	public ANode scale(final float scaleXY) {
		getTransform().scale(scaleXY, scaleXY, 1f);
		return this;
	}

	public float getSmoothingFactor() {
		return smoothingFactor;
	}

	public ANode scale(final float scaleX, final float scaleY) {
		getTransform().scale(scaleX, scaleY, 1f);
		return this;
	}

	public Vector3 inWorldCoordinates() {
		return inWorldCoordinates(0f,0f);
	}
	public Vector3 inWorldCoordinates(final float x, final float y) {
		return new Vector3(x,y,0f).mul(getCascadedTransform());
	}

	public abstract float getLeftWidth();
	public abstract float getRightWidth();
	public abstract float getTopHeight();
	public abstract float getBottomHeight();

	public boolean collides(final ANode node) {
		final Vector3 thiz = this.inWorldCoordinates();
		final Vector3 other = node.inWorldCoordinates();
		return rangeOverlap(thiz.x-this.getLeftWidth(), thiz.x + this.getRightWidth(), other.x-node.getLeftWidth(), other.x+node.getRightWidth())
				&& rangeOverlap(thiz.y-this.getBottomHeight(), thiz.y + this.getTopHeight(), other.x-node.getBottomHeight(), other.x+node.getTopHeight());
	}

	private boolean rangeOverlap(final float range1From, final float range1To, final float range2From, final float range2To) {
		return range1From <= range2To && range2From <= range1To;
	}

	public ANode randomHue() {
		return randomHue(1f,1f);
	}

	public ANode randomHue(final float saturation, final float brightness) {
		final int c = java.awt.Color.HSBtoRGB(MathUtils.random(0f,360f), saturation, brightness);
		setColor((c&0xFF0000)>>16, (c&0x00FF00)>>8, c&0x0000FF);
		return this;
	}


}
