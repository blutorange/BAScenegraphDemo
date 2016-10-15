package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.object;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;

import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.Resource;

public enum GnomeAnimationPack implements Animation<Gnome>{
	WALKING {
		@Override
		public void animate(final Gnome gnome, float time, final float deltaTime) {
			time -= startTime;

			final float rotate1 = 25f*MathUtils.sinDeg(time*220);
			final float rotate2 = 25f*MathUtils.sinDeg(time*220+90f);

			gnome.control();

			gnome.getByName("head").reset().rotate(15f*MathUtils.sinDeg(time*100f)).scale(1f+0.05f*MathUtils.sinDeg(time*100f));

			gnome.getByName("torso").reset().translate(0,2f*MathUtils.sinDeg(time*220));

			gnome.getByName("leftArm").reset().rotate(30f*MathUtils.sinDeg(time*220));
			gnome.getByName("rightArm").reset().rotate(30f*MathUtils.sinDeg(time*220+90.0f));

			gnome.getByName("leftLeg").reset().rotate(rotate1);
			gnome.getByName("rightLeg").reset().rotate(rotate2);
		}

		@Override
		public void begin(final Gnome gnome, final float time, final float deltaTime) {
			startTime = time;
			final Sound s = Resource.sound("lowsteps.wav");
			final long soundId = s.play();
			s.setLooping(soundId, true);
		}

		@Override
		public void end(final Gnome gnome, final float time, final float deltaTime) {
			Resource.sound("lowsteps.wav").stop();
		}
	},

	STANDING {
		@Override
		public void animate(final Gnome gnome, float time, final float deltaTime) {
			time -= startTime;

			final float dy = 5f*MathUtils.sinDeg(time*100f)-10f;
			final float scale = 1f+0.035f*MathUtils.sinDeg(time*100f);
			final float scaleHead = 1f+0.015f*MathUtils.sinDeg(time*100f);

			gnome.control();

			gnome.getByName("head").reset().rotate(5f*MathUtils.sinDeg(time*100f)).scale(scaleHead).translate(0, dy);

			gnome.getByName("torso").reset().scale(scale).translate(0f, dy);

			gnome.getByName("leftArm").reset().translate(0f, dy);
			gnome.getByName("rightArm").reset().translate(0f, dy);

			gnome.getByName("leftLeg").reset().scale(scale).rotate(4f*MathUtils.sinDeg(time*40f));
			gnome.getByName("rightLeg").reset().scale(scale).rotate(4f*MathUtils.sinDeg(time*40+90.0f));
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

			gnome.control();

			gnome.getByName("tAll").translate(190f,-crouchY).rotate(-80f);

			gnome.getByName("head").reset().translate(0f,-50f).rotate(5f*MathUtils.sinDeg(time*100f)).scale(scaleHead-0.3f).translate(0, dy-crouchY);

			gnome.getByName("torso").reset().scale(scale).translate(0f, dy-crouchY);

			gnome.getByName("leftArm").reset().translate(0f, dy-crouchY).rotate(80f);
			gnome.getByName("rightArm").reset().translate(0f, dy-crouchY).rotate(110f);

			gnome.getByName("leftLeg").reset().translate(0f, -crouchY).rotate(60f+4f*MathUtils.sinDeg(time*40f)).scale(1f,0.7f);
			gnome.getByName("rightLeg").reset().translate(0f, -crouchY).rotate(100f+4f*MathUtils.sinDeg(time*40+90.0f)).scale(1f, 0.5f);
		}

		@Override
		public void begin(final Gnome gnome, final float time, final float deltaTime) {
			startTime = time;
		}

		@Override
		public void end(final Gnome gnome, final float time, final float deltaTime) {
			gnome.getByName("tAll").reset();
			gnome.getByName("torso").reset();
		}
	},

	JUMPING {

		@Override
		public void animate(final Gnome gnome, float time, final float deltaTime) {
			time -= startTime;
			gnome.control();

			final float rotateArm = 15f*MathUtils.sinDeg(360f*time);

			gnome.getByName("head").reset().rotate(-20f);
			gnome.getByName("tAll").rotate(-30f).scale(0.9f);

			gnome.getByName("leftArm").reset().rotate(-100f+rotateArm);
			gnome.getByName("rightArm").reset().rotate(100f+rotateArm);

			gnome.getByName("leftLeg").reset().scale(1f, 0.5f).rotate(10f*MathUtils.sinDeg(time*80f));
			gnome.getByName("rightLeg").reset().scale(1f, 0.5f).rotate(10f*MathUtils.sinDeg(time*80+90.0f));
		}

		@Override
		public void begin(final Gnome gnome, final float time, final float deltaTime) {
			startTime = time;
			Resource.sound("boing.wav").play();
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

			gnome.control();
			gnome.getByName("tAll").rotate(-55f);

			gnome.getByName("head").reset().rotate(-20f).scale(scale).translate(0, translate);
			gnome.getByName("torso").reset().scale(scale).translate(0, translate);

			gnome.getByName("leftArm").reset().rotate(-50f+rotateArm);
			gnome.getByName("rightArm").reset().rotate(-70f+rotateArm);


			gnome.getByName("leftLeg").rotate(-12f);
			gnome.getByName("rightLeg").rotate(-12f);
		}

		@Override
		public void begin(final Gnome gnome, final float time, final float deltaTime) {
			startTime = time;
			oldSmoothing1 = gnome.getByName("leftLeg").getSmoothingFactor();
			oldSmoothing2 = gnome.getByName("rightLeg").getSmoothingFactor();
			gnome.getByName("leftLeg").setSmoothingFactor(0.5f).reset();
			gnome.getByName("rightLeg").setSmoothingFactor(0.5f).reset().rotate(120f);
			final Sound s = Resource.sound("steps.wav");
			final long soundId = s.play();
			s.setLooping(soundId, true);
		}

		@Override
		public void end(final Gnome gnome, final float time, final float deltaTime) {
			gnome.getByName("leftLeg").setSmoothingFactor(oldSmoothing1);
			gnome.getByName("rightLeg").setSmoothingFactor(oldSmoothing2);
			Resource.sound("steps.wav").stop();
		}

	}
	;

	protected float startTime;
}
