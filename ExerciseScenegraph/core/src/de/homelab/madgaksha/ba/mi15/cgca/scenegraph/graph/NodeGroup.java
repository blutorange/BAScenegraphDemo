package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph;

import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.ApplicationContext;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.visitor.INodeVisitor;

public class NodeGroup extends ANodeGroup {
	public NodeGroup() {
		super(Type.GROUP);
	}

	@Override
	public <R, T, E extends Throwable> R accept(final INodeVisitor<R, T, E> visitor, final T data) throws E {
		return visitor.visit(this, data);
	}

	@Override
	public void updateAction(final ApplicationContext context) {
	}

	@Override
	public void renderAction(final ApplicationContext context) {
	}
}
