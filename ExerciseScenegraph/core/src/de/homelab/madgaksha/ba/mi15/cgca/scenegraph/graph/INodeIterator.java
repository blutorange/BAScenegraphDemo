package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph;

import java.util.Iterator;

public interface INodeIterator extends Iterator<PrioritizedNode> {
	public void setTraversalPriority(final int traversalPriority);
	public void removeImmediately();
}
