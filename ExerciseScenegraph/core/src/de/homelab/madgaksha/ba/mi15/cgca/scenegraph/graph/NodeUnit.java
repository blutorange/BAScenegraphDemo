package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph;

import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.Controller;

public abstract class NodeUnit extends NodeTransform {
	private final String name;

	/** Neuen Knoten mit Namen der Unterklasse. */
	public NodeUnit() {
		name = getClass().getSimpleName();
		make();
	}

	/** @return Name dieses Knotens. */
	public String name() {
		return name;
	}

	@Override
	public final void update(final float time, final float deltaTime) {
		if (getController() != null) getController().update(time, deltaTime);
	}

	protected abstract Controller getController();

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
