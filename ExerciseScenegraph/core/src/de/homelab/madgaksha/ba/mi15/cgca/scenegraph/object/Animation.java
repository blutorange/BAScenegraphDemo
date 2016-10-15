package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.object;

import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeUnit;

public interface Animation<T extends NodeUnit> {
	public void animate(T object, final float time, final float deltaTime);

	public void begin(T object, float time, float deltaTime);

	public void end(T object, float time, float deltaTime);
}
