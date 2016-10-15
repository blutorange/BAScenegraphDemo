package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.object;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.Resource;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.ANode;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeObject;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeTransform;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeUnit;

public class Butterfly extends NodeUnit {


	private ButterflyAnimationPack oldMode = null;
	private ButterflyAnimationPack mode = ButterflyAnimationPack.FLYING;
	private float catchTime, catchTime2, catchDy, catchY;

	@Override
	protected void update(final float time, final float deltaTime) {
		if (oldMode != mode) {
			if (oldMode != null) oldMode.end(this, time, deltaTime);
			if (mode != null) mode.begin(this, time, deltaTime);
			oldMode = mode;
		}
		switch (mode) {
		case FLYING:
			mode.animate(this, time, deltaTime);
			break;
		case CATCH1:
			mode.animate(this, time, deltaTime);
			catchTime += deltaTime;
			catchTime2 += deltaTime;
			if (catchTime2>0.8f) {
				final float skew = 80f*(-800f-catchY)/800f;
				catchDy = MathUtils.random(-80f+skew,80f+skew);
				catchY += catchDy;
				translate(MathUtils.randomBoolean() ? MathUtils.random(-200f,-50) : MathUtils.random(50f,200f), catchDy);
				catchTime2 = 0f;
			}
			if (catchTime > 10f) {
				scale(0.996f);
			}
			if (catchTime > 35f) {
				detach();
			}
			break;
		default:
			break;

		}
	}

	@Override
	protected void make() {
		final ANode body = new NodeObject(Resource.sprite("butterfly/bodyG.png")).setOrigin(0.53f, 0.75f).randomHue();
		final ANode head = new NodeObject(Resource.sprite("butterfly/headG.png")).setOrigin(0.50f, 0.50f).randomHue();
		final ANode mouth = new NodeObject(Resource.sprite("butterfly/mouthG.png")).setOrigin(0.60f, 0.11f).randomHue();
		final ANode antennae = new NodeObject(Resource.sprite("butterfly/antennaeG.png")).setOrigin(0.49f, 0.05f).randomHue();
		final ANode eyeLeft = new NodeObject(Resource.sprite("butterfly/eyeleftG.png")).setOrigin(0.5f, 0.5f).randomHue();
		final ANode eyeRight = new NodeObject(Resource.sprite("butterfly/eyerightG.png")).setOrigin(0.5f, 0.5f).randomHue();
		final ANode wingTopLeft = new NodeObject(Resource.sprite("butterfly/wingtopleftG.png")).setOrigin(0.96f, 0.20f).randomHue();
		final ANode wingTopRight = new NodeObject(Resource.sprite("butterfly/wingtoprightG.png")).setOrigin(0.04f, 0.11f).randomHue();
		final ANode wingBottomLeft = new NodeObject(Resource.sprite("butterfly/wingbottomleftG.png")).setOrigin(0.97f, 0.94f).randomHue();
		final ANode wingBottomRight = new NodeObject(Resource.sprite("butterfly/wingbottomrightG.png")).setOrigin(0.03f, 0.92f).randomHue();

		final ANode tBody = new NodeTransform();

		final ANode cHead = new NodeTransform();

		final ANode tHead = new NodeTransform(-18f, 111f);
		final ANode tUpperWings = new NodeTransform(-5f, 2f);
		final ANode tLowerWings= new NodeTransform(2f, -34f);

		final ANode tMouth = new NodeTransform(9f, -43f);
		final ANode tAntennae = new NodeTransform(-13f, 65f);
		final ANode tEyeLeft = new NodeTransform(-46f, 3f);
		final ANode tEyeRight = new NodeTransform(37f, 9f);

		addChild("tBody", tBody);

		tBody.addChild("body", body, 2);
		tBody.addChild("tHead", tHead, 4);
		tBody.addChild("tUpperWings", tUpperWings, 1);
		tBody.addChild("tLowerWings", tLowerWings, 1);

		tHead.addChild("cHead", cHead, 4);
		cHead.addChild("tMouth", tMouth, 5);
		cHead.addChild("tAntennae", tAntennae, 3);
		cHead.addChild("tEyeLeft", tEyeLeft, 6);
		cHead.addChild("tEyeRight", tEyeRight, 6);
		cHead.addChild("head", head, 4);

		tUpperWings.addChild("wingTopLeft", wingTopLeft, 1);
		tUpperWings.addChild("wingTopRight", wingTopRight, 1);

		tLowerWings.addChild("wingBottomLeft", wingBottomLeft, 0);
		tLowerWings.addChild("wingBottomRight", wingBottomRight, 0);

		tMouth.addChild("mouth", mouth, 5);
		tAntennae.addChild("antennae", antennae, 3);
		tEyeLeft.addChild("eyeLeft", eyeLeft, 6);
		tEyeRight.addChild("eyeRight", eyeRight, 6);

		setSmoothingFactorForThisAndChildren(1f);
	}

	@Override
	public float getLeftWidth() {
		return 270f;
	}

	@Override
	public float getRightWidth() {
		return 270f;
	}

	@Override
	public float getTopHeight() {
		return 270f;
	}

	@Override
	public float getBottomHeight() {
		return 270f;
	}

	public void caught() {
		catchTime = 0f;
		catchTime2 = 0f;
		catchY = catchDy = 0f;
		mode = ButterflyAnimationPack.CATCH1;
	}

	@Override
	public void draw(final Batch batch, final Color color) {
		final Vector3 v = inWorldCoordinates();
		if (v.x>=-2000f && v.x<=2000f) {
			super.draw(batch, color);
		}
	}

}
