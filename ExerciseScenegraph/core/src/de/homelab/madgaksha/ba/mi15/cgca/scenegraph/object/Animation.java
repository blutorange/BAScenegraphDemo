package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.object;

import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeUnit;

public interface Animation<T extends NodeUnit> {
	/** Wird während des Update aufgerufen. */
	public void animate(T object, final float time, final float deltaTime);

	/** Wird bei Beginn der Animation aufgerufen. */
	public void begin(T object, float time, float deltaTime);

	/** Wird bei Ende der Animation aufgerufen. */
	public void end(T object, float time, float deltaTime);
}
