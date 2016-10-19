package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.object;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;

public enum GnomeAnimationPack implements Animation<Gnome>{
	WALKING {
		@Override
		public void animate(final Gnome gnome, float time, final float deltaTime) {
			time -= startTime;

			final float rotate1 = 25f*MathUtils.sinDeg(time*220);
			final float rotate2 = 25f*MathUtils.sinDeg(time*220+90f);

			gnome.mHead.reset().rotate(15f*MathUtils.sinDeg(time*100f)).scale(1f+0.05f*MathUtils.sinDeg(time*100f));

			gnome.mTorso.reset().translate(0,2f*MathUtils.sinDeg(time*220));

			gnome.mLeftArm.reset().rotate(30f*MathUtils.sinDeg(time*220));
			gnome.mRightArm.reset().rotate(30f*MathUtils.sinDeg(time*220+90.0f));

			gnome.mLeftLeg.reset().rotate(rotate1);
			gnome.mRightLeg.reset().rotate(rotate2);
		}

		@Override
		public void begin(final Gnome gnome, final float time, final float deltaTime) {
			startTime = time;
			final Sound s = gnome.ac().getResourceManager().sound("lowsteps.wav");
			final long soundId = s.play();
			s.setLooping(soundId, true);
		}

		@Override
		public void end(final Gnome gnome, final float time, final float deltaTime) {
			gnome.ac().getResourceManager().sound("lowsteps.wav").stop();
		}
	},

	STANDING {
		@Override
		public void animate(final Gnome gnome, float time, final float deltaTime) {
			time -= startTime;

			final float dy = 5f*MathUtils.sinDeg(time*100f)-10f;
			final float scale = 1f+0.035f*MathUtils.sinDeg(time*100f);
			final float scaleHead = 1f+0.015f*MathUtils.sinDeg(time*100f);

			gnome.mHead.reset().rotate(5f*MathUtils.sinDeg(time*100f)).scale(scaleHead).translate(0, dy);

			gnome.mTorso.reset().scale(scale).translate(0f, dy);

			gnome.mLeftArm.reset().translate(0f, dy);
			gnome.mRightArm.reset().translate(0f, dy);

			gnome.mLeftLeg.reset().scale(scale).rotate(4f*MathUtils.sinDeg(time*40f));
			gnome.mRightLeg.reset().scale(scale).rotate(4f*MathUtils.sinDeg(time*40+90.0f));
		}

		@Override
		public void begin(final Gnome gnome, final float time, final float deltaTime) {
			startTime = time;
		}

		@Override
		public void end(final Gnome gnome, final float time, final float deltaTime) {
		}
	},

	CROUCHING {
		@Override
		public void animate(final Gnome gnome, float time, final float deltaTime) {
			time -= startTime;

			final float dy = 5f*MathUtils.sinDeg(time*100f)-10f;
			final float scale = 1f+0.035f*MathUtils.sinDeg(time*100f);
			final float scaleHead = 1f+0.015f*MathUtils.sinDeg(time*100f);
			final float crouchY = 100f;

			gnome.tAll.translate(190f,-crouchY).rotate(-80f);

			gnome.mHead.reset().translate(0f,-50f).rotate(5f*MathUtils.sinDeg(time*100f)).scale(scaleHead-0.3f).translate(0, dy-crouchY);

			gnome.mTorso.reset().scale(scale).translate(0f, dy-crouchY);

			gnome.mLeftArm.reset().translate(0f, dy-crouchY).rotate(80f);
			gnome.mRightArm.reset().translate(0f, dy-crouchY).rotate(110f);

			gnome.mLeftLeg.reset().translate(0f, -crouchY).rotate(60f+4f*MathUtils.sinDeg(time*40f)).scale(1f,0.7f);
			gnome.mRightLeg.reset().translate(0f, -crouchY).rotate(100f+4f*MathUtils.sinDeg(time*40+90.0f)).scale(1f, 0.5f);
		}

		@Override
		public void begin(final Gnome gnome, final float time, final float deltaTime) {
			startTime = time;
		}

		@Override
		public void end(final Gnome gnome, final float time, final float deltaTime) {
			gnome.tAll.reset();
			gnome.mTorso.reset();
		}
	},

	JUMPING {

		@Override
		public void animate(final Gnome gnome, float time, final float deltaTime) {
			time -= startTime;

			final float rotateArm = 15f*MathUtils.sinDeg(360f*time);

			gnome.mHead.reset().rotate(-20f);
			gnome.tAll.rotate(-30f).scale(0.9f);

			gnome.mLeftArm.reset().rotate(-100f+rotateArm);
			gnome.mRightArm.reset().rotate(100f+rotateArm);

			gnome.mLeftLeg.reset().scale(1f, 0.5f).rotate(10f*MathUtils.sinDeg(time*80f));
			gnome.mRightLeg.reset().scale(1f, 0.5f).rotate(10f*MathUtils.sinDeg(time*80+90.0f));
		}

		@Override
		public void begin(final Gnome gnome, final float time, final float deltaTime) {
			startTime = time;
			gnome.ac().getResourceManager().sound("boing.wav").play();
		}

		@Override
		public void end(final Gnome gnome, final float time, final float deltaTime) {
		}

	},
	DASHING {
		private float oldSmoothing1,oldSmoothing2;
		@Override
		public void animate(final Gnome gnome, float time, final float deltaTime) {
			time -= startTime;
			final float rotateArm = 35f*MathUtils.sinDeg(260f*time);
			final float translate = 10f*MathUtils.sinDeg(800f*time);
			final float scale = 1f+0.05f*MathUtils.sinDeg(749*time+90f);

			gnome.tAll.rotate(-55f);

			gnome.mHead.reset().rotate(-20f).scale(scale).translate(0, translate);
			gnome.mTorso.reset().scale(scale).translate(0, translate);

			gnome.mLeftArm.reset().rotate(-50f+rotateArm);
			gnome.mRightArm.reset().rotate(-70f+rotateArm);


			gnome.mLeftLeg.rotate(-12f);
			gnome.mRightLeg.rotate(-12f);
		}

		@Override
		public void begin(final Gnome gnome, final float time, final float deltaTime) {
			startTime = time;
			oldSmoothing1 = gnome.mLeftLeg.getSmoothingFactor();
			oldSmoothing2 = gnome.mRightLeg.getSmoothingFactor();
			gnome.mLeftLeg.setSmoothingFactor(0.5f).reset();
			gnome.mRightLeg.setSmoothingFactor(0.5f).reset().rotate(120f);
			final Sound s = gnome.ac().getResourceManager().sound("steps.wav");
			final long soundId = s.play();
			s.setLooping(soundId, true);
		}

		@Override
		public void end(final Gnome gnome, final float time, final float deltaTime) {
			gnome.mLeftLeg.setSmoothingFactor(oldSmoothing1);
			gnome.mRightLeg.setSmoothingFactor(oldSmoothing2);
			gnome.ac().getResourceManager().sound("steps.wav").stop();
		}
	}
	;

	protected float startTime;
}
