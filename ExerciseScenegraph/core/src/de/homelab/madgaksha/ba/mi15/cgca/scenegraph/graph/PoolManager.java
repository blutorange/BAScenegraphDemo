package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph;

import com.badlogic.gdx.utils.Pool;

import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.CmnCnst;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.ANodeGroup.IteratorNodeGroup;

public final class PoolManager {
	private PoolManager() {}
	public final static IteratorNodeGroupPool ITERATOR_NODE_GROUP = new IteratorNodeGroupPool();
	public final static class IteratorNodeGroupPool extends Pool<IteratorNodeGroup> {
		public IteratorNodeGroupPool() {
			super(50,5000);
		}
		@Override
		protected IteratorNodeGroup newObject() {
			return new IteratorNodeGroup();
		}
		/** @throws UnsupportedOperationException Use {@link #obtain(ANodeGroup)}. */
		@Override
		public IteratorNodeGroup obtain () {
			throw new UnsupportedOperationException(CmnCnst.Error.ITERATOR_NEEDS_NODE);
		}
		public IteratorNodeGroup obtain (final ANodeGroup node) {
			final IteratorNodeGroup it = super.obtain();
			it.setNode(node);
			return it;
		}
	}
}
