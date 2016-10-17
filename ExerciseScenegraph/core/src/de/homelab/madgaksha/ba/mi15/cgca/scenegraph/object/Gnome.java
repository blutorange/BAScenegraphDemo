package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.object;

import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.Controller;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.Resource;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.ANode;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeObject;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeText;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeTransform;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeUnit;

public class Gnome extends NodeUnit {
	private int score;
	private GnomeController controller;

	@Override
	protected void make() {
		final ANode torso = new NodeObject(Resource.sprite("gnome/torso.png"));
		final ANode head = new NodeObject(Resource.sprite("gnome/head.png")).setOrigin(0.7f, 0.3f);
		final ANode score = new NodeText(60,"0");
		final ANode leftArm = new NodeObject(Resource.sprite("gnome/leftarm.png")).setOrigin(0.875f,0.95f);
		final ANode rightArm = new NodeObject(Resource.sprite("gnome/rightarm.png")).setOrigin(0.125f,0.95f);
		final ANode leftLeg = new NodeObject(Resource.sprite("gnome/leftleg.png")).setOrigin(0.218f,1);
		final ANode rightLeg = new NodeObject(Resource.sprite("gnome/rightleg.png")).setOrigin(0.218f,1);

		final ANode tAll = new NodeTransform();
		final ANode tHead = new NodeTransform(15, 150);
		final ANode tScore = new NodeTransform(0f, 200f);
		final ANode tLeftArm = new NodeTransform(-28, 70);
		final ANode tRightArm = new NodeTransform(28, 70);
		final ANode tLeftLeg = new NodeTransform(-18, -70);
		final ANode tRightLeg = new NodeTransform(30, -70);

		addChild("tAll", tAll);

		tAll.addChild("torso", torso);

		tAll.addChild("tHead", tHead, 1);
		tHead.addChild("head", head);
		head.addChild("tScore", tScore, 2);
		tScore.addChild("score", score);

		tAll.addChild("tLeftLeg", tLeftLeg, 1);
		tLeftLeg.addChild("leftLeg", leftLeg);

		tAll.addChild("tRightLeg", tRightLeg, -1);
		tRightLeg.addChild("rightLeg", rightLeg);

		tAll.addChild("tRightArm", tRightArm, -1);
		tRightArm.addChild("rightArm", rightArm);

		tAll.addChild("tLeftArm", tLeftArm, 1);
		tLeftArm.addChild("leftArm", leftArm);

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
		return score;
	}

	public void incrementScore() {
		score++;
		((NodeText)getByName("score")).setString(Integer.toString(score, 10));
	}

	public Gnome setController(final GnomeController controller) {
		this.controller = controller;
		return this;
	}

	@Override
	protected Controller getController() {
		return controller;
	}
}
