package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.object;

import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.ApplicationContext;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.Controller;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.ANodeDrawable;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeColor;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeController;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeSprite;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeText;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeTransform;

public class Gnome extends NodeController {
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

	NodeTransform mHead;
	NodeTransform mTorso;
	NodeTransform mScore;
	NodeTransform mLeftArm;
	NodeTransform mRightArm;
	NodeTransform mLeftLeg;
	NodeTransform mRightLeg;

	@Override
	protected void make() {
		color = new NodeColor();

		torso = new NodeSprite(ApplicationContext.getInstance().getResourceManager().sprite("gnome/torso.png"));
		head = new NodeSprite(ApplicationContext.getInstance().getResourceManager().sprite("gnome/head.png"));
		score = new NodeText(60,"0");
		leftArm = new NodeSprite(ApplicationContext.getInstance().getResourceManager().sprite("gnome/leftarm.png"));
		rightArm = new NodeSprite(ApplicationContext.getInstance().getResourceManager().sprite("gnome/rightarm.png"));
		leftLeg = new NodeSprite(ApplicationContext.getInstance().getResourceManager().sprite("gnome/leftleg.png"));
		rightLeg = new NodeSprite(ApplicationContext.getInstance().getResourceManager().sprite("gnome/rightleg.png"));

		head.setOrigin(0.7f, 0.3f);
		leftArm.setOrigin(0.875f,0.95f);
		rightArm.setOrigin(0.125f,0.95f);
		leftLeg.setOrigin(0.218f,1);
		rightLeg.setOrigin(0.218f,1);

		tAll = new NodeTransform();
		tHead = new NodeTransform(15, 150);
		tScore = new NodeTransform(0f, 200f);
		tLeftArm = new NodeTransform(-28, 70);
		tRightArm = new NodeTransform(28, 70);
		tLeftLeg = new NodeTransform(-18, -70);
		tRightLeg = new NodeTransform(30, -70);

		mHead = new NodeTransform();
		mScore = new NodeTransform();
		mLeftArm = new NodeTransform();
		mRightArm = new NodeTransform();
		mLeftLeg = new NodeTransform();
		mRightLeg = new NodeTransform();
		mTorso = new NodeTransform();

		addChild(color);
		color.addChild(tAll);

		tAll.addChild(mTorso);
		tAll.addChild(tHead, 1);
		tAll.addChild(tLeftLeg, 1);
		tAll.addChild(tRightLeg, -1);
		tAll.addChild(tRightArm, -1);
		tAll.addChild(tLeftArm, 1);

		tHead.addChild(mHead, 1);
		mHead.addChild(tScore, 2);

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

		tAll.setSmoothingFactorForThisAndChildren(0.05f);
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
}
