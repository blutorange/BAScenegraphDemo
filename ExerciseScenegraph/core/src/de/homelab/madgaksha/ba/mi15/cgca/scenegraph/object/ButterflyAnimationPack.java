package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.object;

import com.badlogic.gdx.math.MathUtils;

import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.visitor.VisitorSmoothingFactor;

public enum ButterflyAnimationPack implements Animation<Butterfly> {
	FLYING {
		@Override
		public void animate(final Butterfly bf, final float time, final float deltaTime) {
			final float rotateWing1 = 25f*MathUtils.sinDeg(80f*time);
			final float rotateWing2 = 10f*MathUtils.sinDeg(80f*time);
			final float rotateHead = 5f*MathUtils.sinDeg(51f*time);
			final float rotateMouth = 10f*MathUtils.sinDeg(67f*time);
			final float rotateBody = 20f*MathUtils.sinDeg(33f*time);
			final float rotateAntennae = 25f*MathUtils.sinDeg(23f*time);
			final float rotateEye = 5f*MathUtils.sinDeg(53f*time+90f);
			final float scaleHead = 1f+0.08f*MathUtils.sinDeg(47f*time);
			final float scaleEye = 1f+0.08f*MathUtils.sinDeg(51f*time+90f);

			bf.mWingTopLeft.reset().rotate(rotateWing1);
			bf.mWingTopRight.reset().rotate(-rotateWing1);
			bf.mWingBottomLeft.reset().rotate(rotateWing2);
			bf.mWingBottomRight.reset().rotate(-rotateWing2);
			bf.mHead.reset().scale(scaleHead).rotate(rotateHead);
			bf.mEyeLeft.reset().scale(scaleEye).rotate(rotateEye);
			bf.mEyeRight.reset().scale(scaleEye).rotate(rotateEye);
			bf.tBody.reset().rotate(rotateBody);
			bf.mMouth.reset().rotate(rotateMouth);
			bf.mAntennae.reset().rotate(rotateAntennae);
		}

		@Override
		public void begin(final Butterfly bf, final float time, final float deltaTime) {
		}

		@Override
		public void end(final Butterfly bf, final float time, final float deltaTime) {
		}
	},

	CATCH {
		final float wingTime = 0.2f;
		@Override
		public void animate(final Butterfly bf, final float time, final float deltaTime) {
			final float sawWing = 60f*(Math.abs((time%wingTime)-0.5f*wingTime)-0.25f*wingTime)/(0.25f*wingTime);
			final float translateBody = 200f*MathUtils.sinDeg(57f*time);
			final float scaleBody = MathUtils.sinDeg(201f*time);
			final float scaleBody2 = 1f+0.2f*MathUtils.sinDeg(30f*time);
			final float rotateBody = 60f*MathUtils.sinDeg(99f*time);
			final float scaleAntennae = 1f+0.5f*MathUtils.sinDeg(273f*time);
			final float scaleMouth = MathUtils.sinDeg(77f*time);
			bf.mWingTopLeft.reset().rotate(sawWing);
			bf.mWingTopRight.reset().rotate(-sawWing);
			bf.mWingBottomLeft.reset().rotate(sawWing);
			bf.mWingBottomRight.reset().rotate(-sawWing);
			bf.mEyeLeft.rotate(5f);
			bf.mEyeRight.rotate(5f);
			bf.tBody.reset().rotate(rotateBody).scale(scaleBody*scaleBody2,scaleBody2).translate(0f, translateBody);
			bf.mMouth.reset().scale(1f, scaleMouth);
			bf.mAntennae.reset().scale(1f,scaleAntennae);
		}

		@Override
		public void begin(final Butterfly bf, final float time, final float deltaTime) {
			bf.accept(VisitorSmoothingFactor.INSTANCE, 0.2f);
		}

		@Override
		public void end(final Butterfly object, final float time, final float deltaTime) {
		}
	}
	;
}
