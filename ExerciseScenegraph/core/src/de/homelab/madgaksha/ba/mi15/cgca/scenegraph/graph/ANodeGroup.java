package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.NoSuchElementException;

import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.ApplicationContext;

public abstract class ANodeGroup extends ANode {
	protected final List<PrioritizedNode> children = new ArrayList<>();
	private boolean dirty = true;

	public ANodeGroup(final Type type, final ApplicationContext ac) {
		super(type, ac);
	}

	@Override
	public INodeIterator iterator() {
		if (dirty)
			rebuildSortedChildren();
		return new IteratorNodeGroup(this);
	}

	public final ANode addChild(final ANode node) {
		return addChild(node, 0);
	}

	public final ANode addChild(final ANode node, final int traversalPriority) {
		return addChild(node, traversalPriority, children.size());
	}

	public final ANode addChild(final ANode node, final int traversalPriority, final int position) {
		ac().getNodeActionQueue()
		.addAction(new NodeActionAddChild(this, node, traversalPriority, position));
		return this;
	}

	public void addChildImmediately(final ANode node, final int traversalPriority, final int position) {
		children.add(position, new PrioritizedNode(node, traversalPriority));
		node.parent = this;
		dirty = true;
	}

	private void rebuildSortedChildren() {
		Collections.sort(children);
		dirty = false;
	}

	public final ANode removeChild(final int position) {
		ac().getNodeActionQueue().addAction(new NodeActionRemoveChild(this, position));
		return this;
	}

	public final void removeChildImmediately(final int index) {
		if (index >= children.size())
			return;
		children.get(index).node.parent = null;
		children.remove(index);
	}

	public final ANode setTraversalPriority(final int childIndex, final int traversalPriority) {
		final PrioritizedNode node = children.get(childIndex);
		if (node != null) {
			children.set(childIndex, new PrioritizedNode(node.node, traversalPriority));
			dirty = true;
		}
		return this;
	}

	public static class NodeActionAddChild implements INodeAction {
		private final ANodeGroup parent;
		private final ANode child;
		private final int traversalPriority;
		private final int position;

		public NodeActionAddChild(final ANodeGroup parent, final ANode child, final int traversalPriority,
				final int position) {
			this.parent = parent;
			this.child = child;
			this.traversalPriority = traversalPriority;
			this.position = position;
		}

		@Override
		public void perform() {
			parent.addChildImmediately(child, traversalPriority, position);
		}
	}

	public static class NodeActionRemoveChild implements INodeAction {
		private final ANodeGroup parent;
		private final int position;

		public NodeActionRemoveChild(final ANodeGroup parent, final int position) {
			this.parent = parent;
			this.position = position;
		}

		@Override
		public void perform() {
			parent.removeChildImmediately(position);
		}
	}

	private static class IteratorNodeGroup implements INodeIterator {
		private final ANodeGroup node;
		private int pos = 0;
		private final int len;

		public IteratorNodeGroup(final ANodeGroup node) {
			this.node = node;
			this.len = node.children.size();
		}

		@Override
		public boolean hasNext() {
			return pos < len;
		}

		@Override
		public PrioritizedNode next() {
			if (len != node.children.size())
				throw new ConcurrentModificationException(
						String.format("Number of children was %s, but is now %s.", len, node.children.size()));
			if (pos >= len)
				throw new NoSuchElementException(String.format(
						"There are only %s children, cannot get children at index %s.", node.children.size(), pos));
			return node.children.get(pos++);
		}

		@Override
		public void remove() {
			assertSanity();
			node.removeChild(pos-1);
		}

		@Override
		public void removeImmediately() {
			assertSanity();
			node.removeChild(--pos);
		}

		@Override
		public void setTraversalPriority(final int traversalPriority) {
			assertSanity();
			node.children.set(pos-1, new PrioritizedNode(node.children.get(pos-1).node, traversalPriority));
		}

		private void assertSanity() {
			if (len != node.children.size())
				throw new ConcurrentModificationException(
						String.format("Number of children was %s, but is now %s.", len, node.children.size()));
			if (pos == 0 || pos >= len)
				throw new IllegalStateException(
						String.format("Cannot get child at %s, there are %s children.", pos, len));
		}
	}
}
