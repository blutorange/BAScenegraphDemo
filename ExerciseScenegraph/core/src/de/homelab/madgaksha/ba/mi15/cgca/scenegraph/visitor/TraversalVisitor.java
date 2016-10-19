package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.visitor;

import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.ANode;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeColor;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeFilter;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeGroup;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeSprite;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeText;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeTransform;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.PrioritizedNode;

public class TraversalVisitor<E extends Throwable> implements INodeVisitor <Void , ICommonNodeAction<E>, E>{

	private Void traverse(final ANode node, final ICommonNodeAction<E> data) throws E{
		for (final PrioritizedNode child : node) child.node.accept(this, data);
		return null;
	}

	@Override
	public Void visit(final NodeTransform node, final ICommonNodeAction<E> data) throws E {
		data.visit(node);
		return traverse(node, data);
	}

	@Override
	public Void visit(final NodeSprite node, final ICommonNodeAction<E> data) throws E {
		data.visit(node);
		return traverse(node, data);
	}

	@Override
	public Void visit(final NodeText node, final ICommonNodeAction<E> data) throws E {
		data.visit(node);
		return traverse(node, data);
	}

	@Override
	public Void visit(final NodeColor node, final ICommonNodeAction<E> data) throws E {
		data.visit(node);
		return traverse(node, data);
	}

	@Override
	public Void visit(final NodeGroup node, final ICommonNodeAction<E> data) throws E {
		data.visit(node);
		return traverse(node, data);
	}

	@Override
	public Void visit(final NodeFilter node, final ICommonNodeAction<E> data) throws E {
		data.visit(node);
		return traverse(node, data);
	}
}
