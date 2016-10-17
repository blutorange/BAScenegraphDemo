package de.homelab.madgaksha.ba.mi15.cgca.scenegraph;

public interface ITemporalFunction {
	public float apply(float x, float dxdt, float d2xdt2, float t);
}
