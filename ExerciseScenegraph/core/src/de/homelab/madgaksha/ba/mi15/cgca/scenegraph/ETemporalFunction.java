package de.homelab.madgaksha.ba.mi15.cgca.scenegraph;

public enum ETemporalFunction implements ITemporalFunction {
	IDENTITY {
		@Override
		public float apply(final float x, final float dxdt, final float d2xdt2, final float t) {
			return x;
		}
	};
}
