package de.homelab.madgaksha.ba.mi15.cgca.scenegraph;

public final class CmnCnst {
	public final static class Files {

		public static final String STARS = "stars.eff"; //$NON-NLS-1$
		public static final String BUTTERFLY_BODY = "butterfly/bodyG.png"; //$NON-NLS-1$
		public static final String BUTTERFLY_HEAD = "butterfly/headG.png"; //$NON-NLS-1$
		public static final String BUTTERFLY_MOUTH = "butterfly/mouthG.png"; //$NON-NLS-1$
		public static final String BUTTERFLY_ANTENNAE = "butterfly/antennaeG.png"; //$NON-NLS-1$
		public static final String BUTTERFLY_EYE_LEFT = "butterfly/eyeleftG.png"; //$NON-NLS-1$
		public static final String BUTTERFLY_RIGHT = "butterfly/eyerightG.png"; //$NON-NLS-1$
		public static final String BUTTERFLY_WING_TOP_LEFT = "butterfly/wingtopleftG.png"; //$NON-NLS-1$
		public static final String BUTTERFLY_WING_TOP_RIGHT = "butterfly/wingtoprightG.png"; //$NON-NLS-1$
		public static final String BUTTERFLY_WING_BOTTOM_LEFT = "butterfly/wingbottomleftG.png"; //$NON-NLS-1$
		public static final String BUTTERFLY_BOTTOM_RIGHT = "butterfly/wingbottomrightG.png"; //$NON-NLS-1$
		public static final String GNOME_TORSO = "gnome/torso.png"; //$NON-NLS-1$
		public static final String GNOME_HEAD = "gnome/head.png"; //$NON-NLS-1$
		public static final String GNOME_LEFTARM = "gnome/leftarm.png"; //$NON-NLS-1$
		public static final String GNOME_RIGHTARM = "gnome/rightarm.png"; //$NON-NLS-1$
		public static final String GNOME_LEFTLEG = "gnome/leftleg.png"; //$NON-NLS-1$
		public static final String GNOME_RIGHTLEG = "gnome/rightleg.png"; //$NON-NLS-1$
		public static final String CHAIN = "gnome/chainline.png"; //$NON-NLS-1$
		public static final String WORLD = "background.jpg"; //$NON-NLS-1$

		public static final String BGM_3 = "bgm3.mp3"; //$NON-NLS-1$

		public static final String SOUND_WALK = "lowsteps.wav"; //$NON-NLS-1$
		public static final String SOUND_JUMP = "boing.wav"; //$NON-NLS-1$
		public static final String SOUND_RUN = "steps.wav"; //$NON-NLS-1$
		public static final String SOUND_FLAP = "flap.wav"; //$NON-NLS-1$
		public static final String SOUND_OBTAIN_BUTTERFLY = "get%d.wav"; //$NON-NLS-1$

	}

	public final static class Error {

		public static final String ITERATOR_NEEDS_NODE = "You must provide a node."; //$NON-NLS-1$
		public static final String EMPTY_ITERATOR = "Empty iterator does not have children."; //$NON-NLS-1$
		public static final String INCONSISTENT_ITERATOR_STATE = "Next not called or remove called more than once."; //$NON-NLS-1$
		public static final String NULL_SPRITE = "Sprite cannot be null."; //$NON-NLS-1$
		public static final String TODO = "TODO - not yet implemented"; //$NON-NLS-1$
		public static final String NULL_NODE = "Node must not be null"; //$NON-NLS-1$
		public static final String NULL_WORLD = "World cannot be null."; //$NON-NLS-1$
		public static final String NULL_BUTTERFLY = "Butterfly cannot be null."; //$NON-NLS-1$
		public static final String ITERATOR_CONCURRENT_CHILD = "Number of children was %s, but is now %s."; //$NON-NLS-1$
		public static final String ITERATOR_ILLEGAL_CHILD = "Cannot get child at %s, there are %s children."; //$NON-NLS-1$
		public static final String NULL_GNOME = "Gnome cannot be null."; //$NON-NLS-1$

	}
}
