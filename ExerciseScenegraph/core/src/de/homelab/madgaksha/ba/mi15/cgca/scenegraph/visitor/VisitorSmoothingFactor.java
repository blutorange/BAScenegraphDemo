package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.visitor;

import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.ANode;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeColor;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeFilter;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeGroup;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeSprite;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeText;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeTransform;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.PrioritizedNode;

public enum VisitorSmoothingFactor implements INodeVisitor<Void, Float, RuntimeException>{
	INSTANCE;

	private Void visitChildren(final ANode node, final Float smoothingFactor) throws RuntimeException {
		for (final PrioritizedNode child : node) child.node.accept(this, smoothingFactor);
		return null;
	}

	@Override
	public Void visit(final NodeTransform node, final Float smoothingFactor) throws RuntimeException {
		node.setSmoothingFactor(smoothingFactor.floatValue());
		return visitChildren(node, smoothingFactor);
	}

	@Override
	public Void visit(final NodeSprite node, final Float smoothingFactor) throws RuntimeException {
		return visitChildren(node, smoothingFactor);
	}

	@Override
	public Void visit(final NodeText node, final Float smoothingFactor) throws RuntimeException {
		return visitChildren(node, smoothingFactor);
	}

	@Override
	public Void visit(final NodeColor node, final Float smoothingFactor) throws RuntimeException {
		return visitChildren(node, smoothingFactor);
	}

	@Override
	public Void visit(final NodeGroup node, final Float smoothingFactor) throws RuntimeException {
		return visitChildren(node, smoothingFactor);
	}

	@Override
	public Void visit(final NodeFilter node, final Float smoothingFactor) throws RuntimeException {
		return visitChildren(node, smoothingFactor);
	}
}
