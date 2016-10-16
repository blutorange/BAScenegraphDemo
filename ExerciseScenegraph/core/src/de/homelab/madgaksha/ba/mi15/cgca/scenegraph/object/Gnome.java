package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.object;

import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.Resource;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.ANode;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeObject;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeText;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeTransform;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeUnit;

public class Gnome extends NodeUnit {
	private Animation<Gnome> mode = GnomeAnimationPack.STANDING;
	private int score;

	@Override
	public void update(final float time, final float deltaTime) {
		final Animation<Gnome> oldMode = mode;
		switch (getController().getDirection()) {
		case LEFT:
			getByName("tAll").reset().mirrorX();
			break;
		case RIGHT:
			getByName("tAll").reset();
			break;
		default:
			break;
		}
		switch (getController().getMovementState()) {
		case JUMPING:
			mode = GnomeAnimationPack.JUMPING;
			break;
		case STANDING:
			mode = GnomeAnimationPack.STANDING;
			break;
		case WALKING:
			mode = GnomeAnimationPack.WALKING;
			break;
		case DASHING:
			mode = GnomeAnimationPack.DASHING;
			break;
		case CROUCHING:
			mode = GnomeAnimationPack.CROUCHING;
			break;
		default:
			break;
		}
		if (mode != oldMode) {
			oldMode.end(this, time, deltaTime);
			mode.begin(this, time, deltaTime);
		}
		mode.animate(this, time, deltaTime);
	}

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
		return 100f;
	}

	@Override
	public float getBottomHeight() {
		return 100f;
	}

	public int getScore() {
		return score;
	}

	public void incrementScore() {
		score++;
		((NodeText)getByName("score")).setString(Integer.toString(score, 10));
	}
}
