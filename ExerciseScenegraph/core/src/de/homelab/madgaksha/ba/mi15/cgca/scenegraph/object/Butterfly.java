package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.object;

import com.badlogic.gdx.math.Vector3;

import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.Controller;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.GraphicsContext;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.Resource;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.ANodeDrawable;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeController;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeSprite;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeTransform;

public class Butterfly extends NodeController {
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

	@Override
	protected void make() {
		body = new NodeSprite(Resource.sprite("butterfly/bodyG.png"));
		head = new NodeSprite(Resource.sprite("butterfly/headG.png"));
		mouth = new NodeSprite(Resource.sprite("butterfly/mouthG.png"));
		antennae = new NodeSprite(Resource.sprite("butterfly/antennaeG.png"));
		eyeLeft = new NodeSprite(Resource.sprite("butterfly/eyeleftG.png"));
		eyeRight = new NodeSprite(Resource.sprite("butterfly/eyerightG.png"));
		wingTopLeft = new NodeSprite(Resource.sprite("butterfly/wingtopleftG.png"));
		wingTopRight = new NodeSprite(Resource.sprite("butterfly/wingtoprightG.png"));
		wingBottomLeft = new NodeSprite(Resource.sprite("butterfly/wingbottomleftG.png"));
		wingBottomRight = new NodeSprite(Resource.sprite("butterfly/wingbottomrightG.png"));

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

		mAntennae = new NodeTransform();
		mBody = new NodeTransform();
		mEyeLeft = new NodeTransform();
		mEyeRight = new NodeTransform();
		mHead = new NodeTransform();
		mMouth = new NodeTransform();
		mWingTopLeft = new NodeTransform();
		mWingTopRight= new NodeTransform();
		mWingBottomLeft = new NodeTransform();
		mWingBottomRight= new NodeTransform();

		tBody = new NodeTransform();
		tHead = new NodeTransform(-18f, 111f);
		tUpperWings = new NodeTransform(-5f, 2f);
		tLowerWings= new NodeTransform(2f, -34f);

		tMouth = new NodeTransform(9f, -43f);
		tAntennae = new NodeTransform(-13f, 65f);
		tEyeLeft = new NodeTransform(-46f, 3f);
		tEyeRight = new NodeTransform(37f, 9f);

		this.addChild(tBody);

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

		tMouth.addChild(mMouth, 5);
		tAntennae.addChild(mAntennae, 3);
		tEyeLeft.addChild(mEyeLeft, 6);
		tEyeRight.addChild(mEyeRight, 6);

		mAntennae.addChild(antennae);
		mBody.addChild(body);
		mEyeLeft.addChild(eyeLeft);
		mEyeRight.addChild(eyeRight);
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
	}

	@Override
	public void renderAction(final GraphicsContext context) {
		final Vector3 v = inWorldCoordinates();
		if (v.x>=-2000f && v.x<=2000f) {
			super.renderAction(context);
		}
	}

	@Override
	public Controller getController() {
		return controller;
	}

}
