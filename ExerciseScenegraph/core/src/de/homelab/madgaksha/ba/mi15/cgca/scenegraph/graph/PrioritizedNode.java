package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph;

import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.CmnCnst;

/**
 * Enthält Knoten und Renderpriorität, nach dieser sortiert werden kann.
 * @author madgaksha
 *
 */
public class PrioritizedNode implements Comparable<PrioritizedNode> {
	public final ANode node;
	public final int priority;

	public PrioritizedNode(final ANode node, final int priority) {
		if (node == null)
			throw new IllegalArgumentException(CmnCnst.Error.NULL_NODE);
		this.node = node;
		this.priority = priority;
	}

	@Override
	public int compareTo(final PrioritizedNode o) {
		return priority-o.priority;
	}
}
