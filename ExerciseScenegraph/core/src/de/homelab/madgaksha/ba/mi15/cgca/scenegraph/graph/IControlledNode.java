package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph;

import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.ApplicationContext;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.Controller;

public interface IControlledNode {
	public Controller getController();
	default void controllerAction(final ApplicationContext context) {
		final Controller c = getController();
		if (c != null) c.update(context.getTime(), context.getDeltaTime());
	}
}
