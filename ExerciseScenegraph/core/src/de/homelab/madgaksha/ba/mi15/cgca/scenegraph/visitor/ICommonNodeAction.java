package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.visitor;

import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.ANode;

public interface ICommonNodeAction<E extends Throwable> {
	public void visit(ANode node) throws E;
}
