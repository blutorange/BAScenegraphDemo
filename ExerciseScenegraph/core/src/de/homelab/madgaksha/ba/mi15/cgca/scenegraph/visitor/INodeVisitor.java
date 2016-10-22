package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.visitor;

import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeCamera;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeColor;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeFilter;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeGroup;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeSprite;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeText;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeTransform;

public interface INodeVisitor<R,T,E extends Throwable> {
	public R visit(NodeTransform node, T data) throws E;
	public R visit(NodeSprite node, T data) throws E;
	public R visit(NodeText node, T data) throws E;
	public R visit(NodeColor node, T data) throws E;
	public R visit(NodeGroup node, T data) throws E;
	public R visit(NodeFilter node, T data) throws E;
	public R visit(NodeCamera node, T data) throws E;
}
