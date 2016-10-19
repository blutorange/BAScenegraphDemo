package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph;

import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.GraphicsContext;

public abstract class NodeController extends NodeTransform implements IControlledNode {
	private final String name;

	public NodeController() {
		name = getClass().getSimpleName();
		make();
	}

	public String getName() {
		return name;
	}

	@Override
	public final void updateAction(final GraphicsContext context) {
		controllerAction(context);
		super.updateAction(context);
	}

	protected abstract void make();
}
