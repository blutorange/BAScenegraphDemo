package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.object;

import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.ApplicationContext;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.Controller;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.ResourceManager;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.ANodeDrawable;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeController;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeFilter;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeSprite;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeTransform;

public class Butterfly extends NodeController {

	public Butterfly(final ApplicationContext ac) {
		super(ac);
	}

	ANodeDrawable body;
	ANodeDrawable head;
	ANodeDrawable mouth;
	ANodeDrawable antennae;
	ANodeDrawable eyeLeft;
	ANodeDrawable eyeRight;
	ANodeDrawable wingTopLeft;
	ANodeDrawable wingTopRight;
	ANodeDrawable wingBottomLeft;
	ANodeDrawable wingBottomRight;
	NodeTransform tBody;
	NodeTransform tHead;
	NodeTransform tUpperWings;
	NodeTransform tLowerWings;
	NodeTransform tMouth;
	NodeTransform tAntennae;
	NodeTransform tEyeLeft;
	NodeTransform tEyeRight;

	NodeTransform mBody;
	NodeTransform mHead;
	NodeTransform mWingTopLeft;
	NodeTransform mWingTopRight;
	NodeTransform mWingBottomLeft;
	NodeTransform mWingBottomRight;
	NodeTransform mMouth;
	NodeTransform mAntennae;
	NodeTransform mEyeLeft;
	NodeTransform mEyeRight;

	private ButterflyController controller;
	private boolean isCaught;

	@Override
	protected void make() {
		final ResourceManager rm = ac().getResourceManager();
		body = new NodeSprite(rm.sprite("butterfly/bodyG.png"), ac());
		head = new NodeSprite(rm.sprite("butterfly/headG.png"), ac());
		mouth = new NodeSprite(rm.sprite("butterfly/mouthG.png"), ac());
		antennae = new NodeSprite(rm.sprite("butterfly/antennaeG.png"), ac());
		eyeLeft = new NodeSprite(rm.sprite("butterfly/eyeleftG.png"), ac());
		eyeRight = new NodeSprite(rm.sprite("butterfly/eyerightG.png"), ac());
		wingTopLeft = new NodeSprite(rm.sprite("butterfly/wingtopleftG.png"), ac());
		wingTopRight = new NodeSprite(rm.sprite("butterfly/wingtoprightG.png"), ac());
		wingBottomLeft = new NodeSprite(rm.sprite("butterfly/wingbottomleftG.png"), ac());
		wingBottomRight = new NodeSprite(rm.sprite("butterfly/wingbottomrightG.png"), ac());

		antennae.setOrigin(0.49f, 0.05f);
		antennae.randomHue();
		body.setOrigin(0.53f, 0.75f);
		body.randomHue();
		eyeLeft.setOrigin(0.5f, 0.5f);
		eyeLeft.randomHue();
		eyeRight.setOrigin(0.5f, 0.5f);
		eyeRight.randomHue();
		head.setOrigin(0.50f, 0.50f);
		head.randomHue();
		mouth.setOrigin(0.60f, 0.11f);
		mouth.randomHue();
		wingTopLeft.setOrigin(0.96f, 0.20f);
		wingTopLeft.randomHue();
		wingTopRight.setOrigin(0.04f, 0.11f);
		wingTopRight.randomHue();
		wingBottomLeft.setOrigin(0.97f, 0.94f);
		wingBottomLeft.randomHue();
		wingBottomRight.setOrigin(0.03f, 0.92f);
		wingBottomRight.randomHue();

		mAntennae = new NodeTransform(ac());
		mBody = new NodeTransform(ac());
		mEyeLeft = new NodeTransform(ac());
		mEyeRight = new NodeTransform(ac());
		mHead = new NodeTransform(ac());
		mMouth = new NodeTransform(ac());
		mWingTopLeft = new NodeTransform(ac());
		mWingTopRight= new NodeTransform(ac());
		mWingBottomLeft = new NodeTransform(ac());
		mWingBottomRight= new NodeTransform(ac());

		tBody = new NodeTransform(ac());
		tHead = new NodeTransform(-18f, 111f, ac());
		tUpperWings = new NodeTransform(-5f, 2f, ac());
		tLowerWings= new NodeTransform(2f, -34f, ac());

		tMouth = new NodeTransform(9f, -43f, ac());
		tAntennae = new NodeTransform(-13f, 65f, ac());
		tEyeLeft = new NodeTransform(-46f, 3f, ac());
		tEyeRight = new NodeTransform(37f, 9f, ac());

		// Do not process uncaught butterflies that are off-screen.
		final NodeFilter nodeFilter = new NodeFilter(ac());
		nodeFilter.setPredicate((t) -> isCaught || Math.abs(inWorldCoordinates().x-ac().cameraInWorldCoordinates().x) <= 2000f);

		this.addChild(nodeFilter);
		nodeFilter.addChild(tBody);

		tBody.addChild(mBody, 2);
		tBody.addChild(tHead, 4);
		tBody.addChild(tUpperWings, 1);
		tBody.addChild(tLowerWings, 1);

		tHead.addChild(mHead);
		mHead.addChild(head, 4);
		mHead.addChild(tMouth, 5);
		mHead.addChild(tAntennae, 3);
		mHead.addChild(tEyeLeft, 6);
		mHead.addChild(tEyeRight, 6);

		tUpperWings.addChild(mWingTopLeft, 1);
		tUpperWings.addChild(mWingTopRight, 1);

		tLowerWings.addChild(mWingBottomLeft, 0);
		tLowerWings.addChild(mWingBottomRight, 0);

		tMouth.addChild(mMouth);
		tAntennae.addChild(mAntennae, 3);
		tEyeLeft.addChild(mEyeLeft, 6);
		tEyeRight.addChild(mEyeRight, 6);

		mAntennae.addChild(antennae);
		mBody.addChild(body);
		mEyeLeft.addChild(eyeLeft);
		mEyeRight.addChild(eyeRight);
		mMouth.addChild(mouth);
		mWingTopLeft.addChild(wingTopLeft);
		mWingTopRight.addChild(wingTopRight);
		mWingBottomLeft.addChild(wingBottomLeft);
		mWingBottomRight.addChild(wingBottomRight);
	}

	@Override
	public float getLeftWidth() {
		return 260f;
	}

	@Override
	public float getRightWidth() {
		return 260f;
	}

	@Override
	public float getTopHeight() {
		return 260f;
	}

	@Override
	public float getBottomHeight() {
		return 260f;
	}

	public Butterfly setController(final ButterflyController controller) {
		this.controller = controller;
		return this;
	}

	public void caught() {
		controller.caught();
		isCaught = true;
	}

	@Override
	public Controller getController() {
		return controller;
	}

}
