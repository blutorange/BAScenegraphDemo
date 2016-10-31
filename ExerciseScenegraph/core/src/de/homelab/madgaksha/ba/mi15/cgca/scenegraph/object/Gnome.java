package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.object;

import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.CmnCnst;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.ApplicationContext;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.Controller;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.ResourceManager;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.ANode;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.ANodeDrawable;
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

	@Override
	protected void make() {
		final ResourceManager rm = ac().getResourceManager();

		color = new NodeColor(ac());

		camera = new NodeCamera(1600, 1066, ac());
		torso = new NodeSprite(rm.sprite(CmnCnst.Files.GNOME_TORSO), ac());
		head = new NodeSprite(rm.sprite(CmnCnst.Files.GNOME_HEAD), ac());
		score = new NodeText(60, Integer.toString(0, 10), ac());
		leftArm = new NodeSprite(rm.sprite(CmnCnst.Files.GNOME_LEFTARM), ac());
		rightArm = new NodeSprite(rm.sprite(CmnCnst.Files.GNOME_RIGHTARM), ac());
		leftLeg = new NodeSprite(rm.sprite(CmnCnst.Files.GNOME_LEFTLEG), ac());
		rightLeg = new NodeSprite(rm.sprite(CmnCnst.Files.GNOME_RIGHTLEG), ac());

		head.setOrigin(0.7f, 0.3f);
		leftArm.setOrigin(0.875f,0.95f);
		rightArm.setOrigin(0.125f,0.95f);
		leftLeg.setOrigin(0.218f,1);
		rightLeg.setOrigin(0.218f,1);

		tAll = new NodeTransform(ac());
		tHead = new NodeTransform(15, 150, ac());
		tScore = new NodeTransform(0f, 200f, ac());
		tLeftArm = new NodeTransform(-28, 70, ac());
		tRightArm = new NodeTransform(28, 70, ac());
		tLeftLeg = new NodeTransform(-18, -70, ac());
		tRightLeg = new NodeTransform(30, -70, ac());
		tHeadBall = new NodeTransform(-117f, 56f, ac());
		tCamera = new NodeTransform(0f, +160f, ac());

		mHead = new NodeTransform(ac());
		mScore = new NodeTransform(ac());
		mLeftArm = new NodeTransform(ac());
		mRightArm = new NodeTransform(ac());
		mLeftLeg = new NodeTransform(ac());
		mRightLeg = new NodeTransform(ac());
		mTorso = new NodeTransform(ac());

		addChild(color);
		addChild(tCamera);
		color.addChild(tAll);

		tAll.addChild(mTorso);
		tAll.addChild(tHead, 1);
		tAll.addChild(tLeftLeg, 1);
		tAll.addChild(tRightLeg, -1);
		tAll.addChild(tRightArm, -1);
		tAll.addChild(tLeftArm, 1);

		tHead.addChild(mHead, 1);
		mHead.addChild(tScore, 3);
		mHead.addChild(tHeadBall, -5);

		tScore.addChild(mScore);
		tLeftLeg.addChild(mLeftLeg);
		tRightLeg.addChild(mRightLeg);
		tRightArm.addChild(mRightArm);
		tLeftArm.addChild(mLeftArm);

		tCamera.addChild(camera);
		mHead.addChild(head);
		mLeftArm.addChild(leftArm);
		mLeftLeg.addChild(leftLeg);
		mRightArm.addChild(rightArm);
		mRightLeg.addChild(rightLeg);
		mScore.addChild(score);
		mTorso.addChild(torso);

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
}
