package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.object;

import com.badlogic.gdx.audio.Sound;

import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.CmnCnst;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.ANode;

/**
 * <p>Animationen für verschiedene Zustände (Laufen, Rennen, Stehen etc.)</p>
 * 
 * <p>Den Methoden {@link Animation#animate(de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeController, float, float)}
 * wird immer ein {@link Gnome}-Objekt mitgegeben. Auf die entsprechenden Knoten kann hiermit direkt zugegriffen werden,
 * z.B. <code>gnome.mTorso</code> für den Transformationsknoten des Körpers. Hierbei die Knoten beginnend mit "m" nehmen,
 * nicht die mit "t" (Position Körperteile zueinander). Der Knoten <code>tAll</code> kann für globale Transformationen
 * genutzt werden, wie etwa, um den ganzen Gnome zu kipppen.</p>
 * 
 * <p>Zur Transformation selber können die Methoden {@link ANode#translate(float, float)}, 
 * {@link ANode#rotate(float)}, {@link ANode#scale(float)} etc. genutzt werden. Beachten, dass diese
 * inkrementelle Verschiebungen sind, d.h. zwei Aufrufe von <code>gnome.transform(5)</code> resultieren in
 * einer Gesamtrotation von 10 Grad. Zum Zurücksetzten der Transformation kann {@link ANode#reset()} genutzt
 * werden.</p>
 * 
 * @author madgaksha
 *
 */
public enum GnomeAnimationPack implements Animation<Gnome>{
	WALKING {
		/** TODO TEIL 5.2 - Gehanimation
		 * Vorschläge: Die die beiden Beine langsam hin- und her drehen. Arme auch.
		 * Den Torso vielleicht langsam auf- und ab bewegen lassen. Den Kopf langsam
		 * drehen und immer leicht größer und kleiner werden lassen.
		 */
		@Override
		public void animate(final Gnome gnome, float time, final float deltaTime) {
			throw new RuntimeException(CmnCnst.Error.TODO);
		}

		@Override
		public void begin(final Gnome gnome, final float time, final float deltaTime) {
			final Sound s = gnome.ac().getResourceManager().sound(CmnCnst.Files.SOUND_WALK);
			final long soundId = s.play();
			s.setLooping(soundId, true);
		}

		@Override
		public void end(final Gnome gnome, final float time, final float deltaTime) {
			gnome.ac().getResourceManager().sound(CmnCnst.Files.SOUND_WALK).stop();
		}
	},

	STANDING {
		/** TODO TEIL 5.1 - Stehanimation
		 * Vorschläge: Kopf langsam drehen und skalieren. Torso langsam
		 * auf und abwärts bewegen lassen. Beine mit geringer Amplitude drehen.
		 */
		@Override
		public void animate(final Gnome gnome, float time, final float deltaTime) {
			throw new RuntimeException(CmnCnst.Error.TODO);
		}

		@Override
		public void begin(final Gnome gnome, final float time, final float deltaTime) {
		}

		@Override
		public void end(final Gnome gnome, final float time, final float deltaTime) {
		}
	},

	CROUCHING {
		/** TODO TEIL 5.4 Duckanimation 
		 * Vorschläge: Gesamten Gnome um etwa -70..-110 Grad kippen. Beine entsprechend um +70..+120 Graden kippen.
		 */
		@Override
		public void animate(final Gnome gnome, float time, final float deltaTime) {
			throw new RuntimeException(CmnCnst.Error.TODO);
		}

		@Override
		public void begin(final Gnome gnome, final float time, final float deltaTime) {
		}

		@Override
		public void end(final Gnome gnome, final float time, final float deltaTime) {
			gnome.tAll.reset();
			gnome.mTorso.reset();
		}
	},

	JUMPING {
		/** TODO TEIL 5.3 Sprunganimation 
		 * Vorschläge: Beine leicht baumeln lassen. Arme etwas nach oben
		 * nehmen und auch baumeln lassen. Kopf nach unten neigen (drehen).
		 */
		@Override
		public void animate(final Gnome gnome, float time, final float deltaTime) {
			throw new RuntimeException(CmnCnst.Error.TODO);
		}

		@Override
		public void begin(final Gnome gnome, final float time, final float deltaTime) {
			gnome.ac().getResourceManager().sound(CmnCnst.Files.SOUND_JUMP).play();
		}

		@Override
		public void end(final Gnome gnome, final float time, final float deltaTime) {
		}

	},
	DASHING {
		private float oldSmoothing1,oldSmoothing2;
		/** TODO TEIL 5.5 Rennanimation 
		 * Vorschläge: Beine im Kreis drehen lassen, phasenversetzt. Arme
		 * nach hinten nehmen und baumel lassen. Torso (Körper) nach vorne
		 * neigen.
		 */
		@Override
		public void animate(final Gnome gnome, float time, final float deltaTime) {
			throw new RuntimeException(CmnCnst.Error.TODO);
		}

		@Override
		public void begin(final Gnome gnome, final float time, final float deltaTime) {
			oldSmoothing1 = gnome.mLeftLeg.getSmoothingFactor();
			oldSmoothing2 = gnome.mRightLeg.getSmoothingFactor();
			gnome.mLeftLeg.setSmoothingFactor(0.5f).reset();
			gnome.mRightLeg.setSmoothingFactor(0.5f).reset().rotate(120f);
			final Sound s = gnome.ac().getResourceManager().sound(CmnCnst.Files.SOUND_RUN);
			final long soundId = s.play();
			s.setLooping(soundId, true);
		}

		@Override
		public void end(final Gnome gnome, final float time, final float deltaTime) {
			gnome.mLeftLeg.setSmoothingFactor(oldSmoothing1);
			gnome.mRightLeg.setSmoothingFactor(oldSmoothing2);
			gnome.ac().getResourceManager().sound(CmnCnst.Files.SOUND_RUN).stop();
		}
	}
	;
}
