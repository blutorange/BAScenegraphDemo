package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph;

import java.util.Iterator;
import java.util.function.Predicate;
import java.util.stream.Stream;

import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.ApplicationContext;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.visitor.INodeVisitor;

public class NodeFilter extends ANodeGroup {
	private Predicate<PrioritizedNode> predicate;

	public NodeFilter() {
		this(IdentityPredicate.INSTANCE);
	}

	public NodeFilter(final Predicate<PrioritizedNode> predicate) {
		super(Type.FILTER);
		this.predicate = predicate;
	}

	@Override
	public INodeIterator iterator() {
		return new IteratorNodeFilter(this);
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

	/**
	 * @param predicate Predicate to set. May be null to remove the predicate.
	 * @return
	 */
	public NodeFilter setPredicate(final Predicate<PrioritizedNode> predicate) {
		ApplicationContext.getInstance().getNodeActionQueue().addAction(new NodeActionSetPredicate(this, predicate));
		return this;
	}

	public NodeFilter setPredicateImmediately(final Predicate<PrioritizedNode> predicate) {
		this.predicate = predicate != null ? predicate : IdentityPredicate.INSTANCE;
		return this;
	}

	public Stream<PrioritizedNode> getFilteredStream() {
		return children.stream().filter(predicate);
	}

	public NodeFilter removeChild(final ANode node) {
		final int index = findIndex(node);
		if (index > -1) removeChild(index);
		return this;
	}

	public NodeFilter removeChildImmediately(final ANode node) {
		final int index = findIndex(node);
		if (index > -1) removeChildImmediately(index);
		return this;
	}

	public NodeFilter setTraversalPriority(final ANode node, final int traversalPriority) {
		final int index = findIndex(node);
		if (index > -1) setTraversalPriority(index, traversalPriority);
		return this;
	}

	private int findIndex(final ANode node) {
		for (int i = children.size(); i-->0;)
			if (children.get(i).node == node) return i;
		return -1;
	}

	private static class IteratorNodeFilter implements INodeIterator {
		private final NodeFilter node;
		private final Iterator<PrioritizedNode> iterator;
		private PrioritizedNode current;

		public IteratorNodeFilter(final NodeFilter node) {
			this.node= node;
			this.iterator = node.getFilteredStream().iterator();
		}

		@Override
		public void setTraversalPriority(final int traversalPriority) {
			assertSanity();
			node.setTraversalPriority(current.node, traversalPriority);
		}

		@Override
		public boolean hasNext() {
			return iterator.hasNext();
		}

		@Override
		public PrioritizedNode next() {
			current = iterator.next();
			return current;
		}

		@Override
		public void remove() {
			assertSanity();
			node.removeChild(current.node);
		}

		@Override
		public void removeImmediately() {
			assertSanity();
			node.removeChildImmediately(current.node);
		}

		private void assertSanity() {
			if (current == null) throw new IllegalStateException("next not called or remove called more than once.");
			current = null;
		}
	}

	private static class NodeActionSetPredicate implements INodeAction {
		private final NodeFilter node;
		private final Predicate<PrioritizedNode> predicate;
		public NodeActionSetPredicate(final NodeFilter node, final Predicate<PrioritizedNode> predicate) {
			this.node = node;
			this.predicate = predicate;
		}
		@Override
		public void perform() {
			node.setPredicateImmediately(predicate);
		}
	}

	private static enum IdentityPredicate implements Predicate<PrioritizedNode> {
		INSTANCE
		;
		@Override
		public boolean test(final PrioritizedNode t) {
			return true;
		}
	}

}
