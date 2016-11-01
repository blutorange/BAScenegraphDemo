package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.object;

import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.CmnCnst;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.CmnCnst.Files;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.ApplicationContext;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.Controller;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.ResourceManager;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.ANode;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.ANodeDrawable;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.ANodeGroup;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeCamera;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeColor;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeController;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeSprite;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeText;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeTransform;

public class Gnome extends NodeController {
	public Gnome(final ApplicationContext ac) {
		super(ac);
	}

	private int scoreNumber;
	private GnomeController controller;

	NodeText score;

	NodeColor color;

	ANodeDrawable torso;
	ANodeDrawable head;
	ANodeDrawable leftArm;
	ANodeDrawable rightArm;
	ANodeDrawable leftLeg;
	ANodeDrawable rightLeg;

	NodeTransform tAll;
	NodeTransform tHead;
	NodeTransform tScore;
	NodeTransform tLeftArm;
	NodeTransform tRightArm;
	NodeTransform tLeftLeg;
	NodeTransform tRightLeg;
	NodeTransform tHeadBall;
	NodeTransform tCamera;

	NodeTransform mHead;
	NodeTransform mTorso;
	NodeTransform mScore;
	NodeTransform mLeftArm;
	NodeTransform mRightArm;
	NodeTransform mLeftLeg;
	NodeTransform mRightLeg;

	NodeCamera camera;

	/**
	 * {@link NodeSprite}
	 */
	@Override
	protected void make() {
		final ResourceManager rm = ac().getResourceManager();

		// Hauptknoten, der alle Teile (Kinderknoten) des Gnomes einfärben kann.
		color = new NodeColor(ac());

		// Ein Kameraknoten. Wir können die Kamera zwischen beiden Gnomes wechseln.
		camera = new NodeCamera(1600, 1066, ac());

		// Ein Textknoten mit einer Zahl, die anzeigt, wieviele Schmetterlinge der
		// Gnome bereits fand.
		score = new NodeText(60, Integer.toString(0, 10), ac());

		// Bilder der Körperteile als Knoten erzeugen.
		makeBodyPartDrawable(rm);

		// Ursprung der Körpterteilbilder für Rotation etc.
		setBodyPartOrigin(torso, head, leftArm, rightArm, leftLeg, rightLeg);

		// Transformationsknoten für die Verschiebung der einzelnen Körpterteil
		// (relative Position) zueinander erzeugen.
		tAll = new NodeTransform(ac());
		tCamera = new NodeTransform(0f, +160f, ac());
		makeBodyPartTranslate();

		// Neue Transformationsknoten für andere
		// Transformationen wie Rotation, Skalierung etc.
		mHead = new NodeTransform(ac());
		mScore = new NodeTransform(ac());
		mLeftArm = new NodeTransform(ac());
		mRightArm = new NodeTransform(ac());
		mLeftLeg = new NodeTransform(ac());
		mRightLeg = new NodeTransform(ac());
		mTorso = new NodeTransform(ac());

		// Baumstruktur mit den Knoten erstellen.
		addChild(color);
		addChild(tCamera);
		
		color.addChild(tAll);
		tCamera.addChild(camera);

		makeTreeStructure(tAll, tHead, tHeadBall, tLeftLeg, tRightLeg,
				tLeftArm, tRightArm, tScore, mTorso, mHead, mLeftLeg,
				mRightLeg, mLeftArm, mRightArm, mScore, torso, head, leftLeg,
				rightLeg, leftArm, rightArm, score);

		// Flüssiger Übergang zwischen Animationen etc.
		setSmoothingFactorForThisAndChildren(0.05f);
	}

	@Override
	public float getLeftWidth() {
		return 120f;
	}

	@Override
	public float getRightWidth() {
		return 120f;
	}

	@Override
	public float getTopHeight() {
		return 260f;
	}

	@Override
	public float getBottomHeight() {
		return 260f;
	}

	public int getScore() {
		return scoreNumber;
	}

	public void incrementScore() {
		scoreNumber++;
		score.setString(Integer.toString(scoreNumber, 10));
	}

	public Gnome setController(final GnomeController controller) {
		this.controller = controller;
		return this;
	}

	@Override
	public Controller getController() {
		return controller;
	}

	public Gnome setHeadBallAccessory(final ANode accessory) {
		tHeadBall.addChild(accessory);
		return this;
	}
	
	/** TODO TEIL 4 - Baumstruktur mit Knoten erzeugen.
	 * 
	 * <p>Hier werden die Knoten zu einer Baumstruktur (Szenengraph) kombiniert. Hierzu
	 * die Method {@link ANodeGroup#addChild(ANode)} bzw. {@link ANodeGroup#addChild(ANode, int)}
	 * verwenden. Zweitere Methode erlaubt es zusätzlich, eine Priorität mitzugeben, in welcher Reihenfolge
	 * die Kinder eines Knoten besucht werden. Je höher die Priorität, desto später wird der Knoten besucht und
	 * desto weiter vorne wird der Knoten gezeichnet. Z.B. sollte der Knoten der Gnome die Kinderknoten Kopf
	 * und Torso haben, der Kopf mit einer höheren Priorität, damit er den Torso überdeckt.
	 * </p>
	 * 
	 * <p>
	 * Der Knoten <code>root</code> ist der Wurzelknoten, hieran werden alle anderen gehängt. Für die anderen
	 * Knoten gilt:
	 * <ul>
	 *   <li>beginnend mit t: Translationsknoten aus Schritt 3 ({@link #makeBodyPartTranslate()}. Die Transformationsmatrizen dieser Knoten sollten bei der späteren Animation nicht verändert werden.</li>
	 *   <li>beginnend mit m: Knoten für andere Transformationen, die später bei der Animation gebraucht werden, z.B. um das Bein zu drehen. Überlegen, ob sie vor oder nach obigen Knoten kommen sollten?</li>
	 *   <li>alle anderen Knoten: Enthalten das Bild für das jeweilige Körpterteil.</li>
	 * </ul>
	 * </p>
	 * 
	 * @param root Wurzel.
	 * @param tHead Translation Kopf.
	 * @param tHeadBall Translation Puschel der Mütze.
	 * @param tLeftLeg Translation linkes Bein.
	 * @param tRightLeg Translation rechtes Bein.
	 * @param tLeftArm Translation linker Arm.
	 * @param tRightArm Translation rechter Arm.
	 * @param tScore Translation Text (Anzahl Schmetterlinge).
	 * @param mTorso Transformation Torso.
	 * @param mHead Transformation Kopf.
	 * @param mLeftLeg Transformation linkes Bein.
	 * @param mRightLeg Transformation rechtes Bein.
	 * @param mLeftArm Transformation linker Arm.
	 * @param mRightArm Transformation rechter Arm.
	 * @param mScore Transformation Text (Anzahl Schmetterlinge).
	 * @param torso Bild Torso.
	 * @param head Bild Kopf.
	 * @param leftLeg Bild linkes Fuß.
	 * @param rightLeg Bild rechtes Bein.
	 * @param leftArm Bild linker Arm.
	 * @param rightArm Bild rechter Arm.
	 * @param score Text (Anzahl Schmetterlinge).
	 */
	private static void makeTreeStructure(ANodeGroup root, ANodeGroup tHead,
			ANodeGroup tHeadBall, ANodeGroup tLeftLeg, ANodeGroup tRightLeg,
			ANodeGroup tLeftArm, ANodeGroup tRightArm, ANodeGroup tScore,
			ANodeGroup mTorso, ANodeGroup mHead, ANodeGroup mLeftLeg,
			ANodeGroup mRightLeg, ANodeGroup mLeftArm, ANodeGroup mRightArm,
			ANodeGroup mScore, ANode torso, ANode head, ANode leftLeg,
			ANode rightLeg, ANode leftArm, ANode rightArm, ANode score) {
		root.addChild(mTorso);
		root.addChild(tHead, 1);
		root.addChild(tLeftLeg, 1);
		root.addChild(tRightLeg, -1);
		root.addChild(tRightArm, -1);
		root.addChild(tLeftArm, 1);

		tHead.addChild(mHead, 1);
		mHead.addChild(tScore, 3);
		mHead.addChild(tHeadBall, -5);

		tScore.addChild(mScore);
		tLeftLeg.addChild(mLeftLeg);
		tRightLeg.addChild(mRightLeg);
		tRightArm.addChild(mRightArm);
		tLeftArm.addChild(mLeftArm);

		mHead.addChild(head);
		mLeftArm.addChild(leftArm);
		mLeftLeg.addChild(leftLeg);
		mRightArm.addChild(rightArm);
		mRightLeg.addChild(rightLeg);
		mScore.addChild(score);
		mTorso.addChild(torso);		
	}

	/** TODO TEIL 3 - Erzeugen der Translationsknoten für Körperteil
	 * <p>Hier werden die Knoten erzeugt, mit denen die einzelnen
	 * Körperteile relativ zueinander positioniert werden. Hierzu
	 * eine neue Instanz der Klasse {@link NodeTransform} erstellen.</p>
	 * 
	 * <p>Zudem soll gleich die Translation festgelegt werden. Hierzu den Konstruktor
	 * {@link NodeTransform#NodeTransform(float, float, ApplicationContext)} nutzen.
	 * Die ersten beiden Parameter sind die Verschiebungen in x-Richtung (positive Werte nach rechts) und
	 * y-Richtung (positive Werte nach oben) in Pixel. Zur Ermittelung der Wert kann ein Zeichenprogramm
	 * wie Photoshop oder Gimp genutzt werden.</p>
	 * 
	 *  <p>Dritter Parameter ist wie in Teil1 {@link #makeBodyPartDrawable(ResourceManager)}, einfach
	 *  die Methode {@link #ac()} nutzen.</p>
	 */
	private void makeBodyPartTranslate() {
		tHead = new NodeTransform(15, 150, ac());
		tScore = new NodeTransform(0, 200, ac());
		tLeftArm = new NodeTransform(-28, 70, ac());
		tRightArm = new NodeTransform(28, 70, ac());
		tLeftLeg = new NodeTransform(-18, -70, ac());
		tRightLeg = new NodeTransform(30, -70, ac());
		tHeadBall = new NodeTransform(-117, 56, ac());		
	}

	/** TODO TEIL 2 - Ursprung der Körperteilbilder
	 *
	 * <p>Hier muss der Ursprung des lokalen Koordinatensystems
	 * der einzelnen Körperteilbilder gesetzt werden. Dieser wird
	 * dann z.B. für die Rotation etc. genutzt.</p>
	 * 
	 * <p>Zum Setzen wird die Method {@link ANodeDrawable#setOrigin(float, float)} verwendet.</p>
	 * 
	 * <p>Der Knoten <code>torso</code> soll seinen Ursprung genau in der Mitte haben.</p>
	 */
	private static void setBodyPartOrigin(ANodeDrawable torso, ANodeDrawable head,
			ANodeDrawable leftArm, ANodeDrawable rightArm,
			ANodeDrawable leftLeg, ANodeDrawable rightLeg) {
		torso.setOrigin(0.5f, 0.5f);
		head.setOrigin(0.7f, 0.3f);
		leftArm.setOrigin(0.875f,0.95f);
		rightArm.setOrigin(0.125f,0.95f);
		leftLeg.setOrigin(0.218f,1);
		rightLeg.setOrigin(0.218f,1);
	}

	/** TODO TEIL 1 - Erzeugen Knoten für Körperteilbilder
	 * <p>Die unterstehenden Variablen müssen mit den Bildern der einzelnen
	 * Körperteile versehen werden.</p>
	 * 
	 * <p>Hierzu neue Instanzen der Klasse {@link NodeSprite} erstellen.
	 * Der Konstruktor erwartet als zweites Argument eines {@link ApplicationContext},
	 * hierzu einfach die Method {@link Gnome#ac()} verwenden. Also z.B:</p>
	 * 
	 * <p><code>new NodeSprite(..., ac());</code></p>
	 * 
	 * <p>
	 * Zum Laden von Bildern die Method {@link ResourceManager#sprite(String)}
	 * nutzen. Die Dateinamen der Bilder befinden sich in {@link Files}.
	 * </p>
	 */
	private void makeBodyPartDrawable(ResourceManager rm) {
		torso = new NodeSprite(rm.sprite(CmnCnst.Files.GNOME_TORSO), ac());
		head = new NodeSprite(rm.sprite(CmnCnst.Files.GNOME_HEAD), ac());
		leftArm = new NodeSprite(rm.sprite(CmnCnst.Files.GNOME_LEFTARM), ac());
		rightArm = new NodeSprite(rm.sprite(CmnCnst.Files.GNOME_RIGHTARM), ac());
		leftLeg = new NodeSprite(rm.sprite(CmnCnst.Files.GNOME_LEFTLEG), ac());
		rightLeg = new NodeSprite(rm.sprite(CmnCnst.Files.GNOME_RIGHTLEG), ac());		
	}
}
