package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.List;

import com.badlogic.gdx.utils.Pool.Poolable;

import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.CmnCnst;
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
		node.onParentSet(this);
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
		final ANode c = children.get(index).node;
		if (c.parent != null) c.onParentRemoved(this);
		c.parent = null;
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

	public static class IteratorNodeGroup implements INodeIterator, Poolable {
		private ANodeGroup node;
		private int pos;
		private int len;

		public IteratorNodeGroup(final ANodeGroup node) {
			setNode(node);
		}

		public IteratorNodeGroup() {
		}

		public void setNode(final ANodeGroup node) {
			this.node = node;
			this.len = node.children.size();
		}

		@Override
		public boolean hasNext() {
			return pos < len;
		}

		@Override
		public PrioritizedNode next() {
			assertSanity();
			return node.children.get(pos++);
		}

		@Override
		public void remove() {
			assertSanityPrev();
			node.removeChild(pos-1);
		}

		@Override
		public void removeImmediately() {
			assertSanityPrev();
			node.removeChildImmediately(--pos);
		}

		@Override
		public void setTraversalPriority(final int traversalPriority) {
			assertSanityPrev();
			node.children.set(pos-1, new PrioritizedNode(node.children.get(pos-1).node, traversalPriority));
			node.dirty = true;
		}

		private void assertSanityPrev() {
			if (len != node.children.size())
				throw new ConcurrentModificationException(
						String.format(CmnCnst.Error.ITERATOR_CONCURRENT_CHILD, len, node.children.size()));
			if (pos == 0 || pos >= len + 1)
				throw new IllegalStateException(
						String.format(CmnCnst.Error.ITERATOR_ILLEGAL_CHILD, pos-1, len));
		}

		private void assertSanity() {
			if (len != node.children.size())
				throw new ConcurrentModificationException(
						String.format(CmnCnst.Error.ITERATOR_CONCURRENT_CHILD, len, node.children.size()));
			if (pos >= len)
				throw new IllegalStateException(
						String.format(CmnCnst.Error.ITERATOR_ILLEGAL_CHILD, pos, len));
		}

		@Override
		public void reset() {
			pos = 0;
			node = null;
			len = 0;
		}
	}
}
