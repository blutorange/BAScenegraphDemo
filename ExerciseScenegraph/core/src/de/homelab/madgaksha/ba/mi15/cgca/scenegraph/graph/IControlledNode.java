package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph;

import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.Controller;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.GraphicsContext;

public interface IControlledNode {
	public Controller getController();
	default void controllerAction(final GraphicsContext context) {
		if (getController() != null) getController().update(context.getTime(), context.getDeltaTime());
	}
}
