package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph;

import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.Controller;

public abstract class NodeUnit extends NodeTransform {
	private final String name;
	private Controller controller;

	public NodeUnit() {
		name = getClass().getSimpleName();
		make();
	}
	public String name() {
		return name;
	}
	public final void control() {
		if (controller != null) controller.control(this);
	}

	public NodeUnit setController(final Controller controller) {
		this.controller = controller;
		return this;
	}
	public Controller getController() {
		return controller;
	}

	@Override
	protected abstract void update(final float time, final float deltaTime);
	protected abstract void make();

	@Override
	public abstract float getLeftWidth();
	@Override
	public abstract float getRightWidth();
	@Override
	public abstract float getTopHeight();
	@Override
	public abstract float getBottomHeight();

}
