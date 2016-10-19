package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph;

import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.ApplicationContext;

public abstract class NodeController extends NodeTransform implements IControlledNode {
	private final String name;

	public NodeController(final ApplicationContext ac) {
		super(ac);
		name = getClass().getSimpleName();
		make();
	}

	public String getName() {
		return name;
	}

	@Override
	public final void updateAction(final ApplicationContext context) {
		controllerAction(context);
		super.updateAction(context);
	}

	protected abstract void make();
}
