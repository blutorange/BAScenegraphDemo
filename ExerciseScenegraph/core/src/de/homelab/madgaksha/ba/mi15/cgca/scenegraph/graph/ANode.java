package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicLong;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.ApplicationContext;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.visitor.INodeVisitor;

public abstract class ANode implements Iterable<PrioritizedNode> {
	public static enum Type {
		/**
		 * A node with a color that cascades to all its children. For example, this
		 * may be used to change the color of an entire scene, or actor.
		 */
		COLOR,
		/**
		 * A drawable object that draws a 2D image.
		 */
		SPRITE,
		/**
		 * A drawable object that draws text.
		 */
		TEXT,
		/**
		 * A node with a transform that cascades to all its children. For example,
		 * this may be used to rotate an entire scene, or translate an actor.
		 */
		TRANSFORM,
		/**
		 * A basic node with children.
		 */
		GROUP,
		/**
		 * A node with only a single child that may change dynamically depending on some condition.
		 */
		FILTER;
	}

	protected final static Matrix4 IDENTITY = new Matrix4();
	private final static AtomicLong idProvider = new AtomicLong();

	private ApplicationContext applicationContext;
	private final long id;
	public final Type type;
	protected ANode parent = null;

	public ANode(final Type type, final ApplicationContext ac) {
		this.type = type;
		this.applicationContext = ac;
		id = idProvider.incrementAndGet();
	}

	public abstract <R, T, E extends Throwable> R accept(INodeVisitor<R, T, E> visitor, T data) throws E;

	/**
	 * Some common action a node needs to do on each update. Should not recurse.
	 * Called in the correct traversal order.
	 */
	public abstract void updateAction(ApplicationContext context);

	/**
	 * Some common action a node needs to each time it is rendered. Should not
	 * recurse. Called in the correct traversal order.
	 */
	public abstract void renderAction(ApplicationContext context);

	/**
	 * Prüft, ob dieser Knoten mit einem anderen in Berührung steht. Dabei
	 * werden die räumlichen Ausdehnungen genommen, wie sie von
	 * {@link #getTopHeight()} etc. zurückgeliefert werden. Diese sind relativ
	 * zum Koordinatenursprung im Koordinatensystem dieses Knotens.
	 *
	 * @param other
	 *            Knoten, mit dem Kollision geprüft werden soll.
	 * @return Ob die Knoten kollidieren.
	 */
	public final boolean collides(final ANode other) {
		final Vector3 thisPos = this.inWorldCoordinates();
		final Vector3 otherPos = other.inWorldCoordinates();
		return rangeOverlap(thisPos.x - this.getLeftWidth(), thisPos.x + this.getRightWidth(), otherPos.x - other.getLeftWidth(),
				otherPos.x + other.getRightWidth())
				&& rangeOverlap(thisPos.y - this.getBottomHeight(), thisPos.y + this.getTopHeight(),
						otherPos.y - other.getBottomHeight(), otherPos.y + other.getTopHeight());
	}

	public final ANode detach() {
		final INodeIterator it = iteratorForParentAtThisPosition();
		if (it != null)
			it.remove();
		return this;
	}

	public final ANode detachImmediately() {
		final INodeIterator it = iteratorForParentAtThisPosition();
		if (it != null)
			it.removeImmediately();
		return this;
	}

	public final ApplicationContext ac() {
		if (applicationContext == null) if (parent != null) applicationContext = parent.ac();
		return applicationContext;
	}

	public float getBottomHeight() {
		return parent != null ? parent.getBottomHeight() : 0f;
	}
	public Color getCascadedColor() {
		return (parent != null) ? parent.getCascadedColor() : Color.WHITE;
	}
	public Matrix4 getCascadedTransform() {
		return (parent != null) ? parent.getCascadedTransform() : new Matrix4().idt();
	}
	public Color getColor() {
		return parent != null ? parent.getColor() : Color.WHITE;
	}
	public final long getId() {
		return id;
	}
	public float getLeftWidth() {
		return parent != null ? parent.getLeftWidth() : 0f;
	}
	public final ANode getParent() {
		return parent;
	}

	public float getRightWidth() {
		return parent != null ? parent.getRightWidth() : 0f;
	}

	public float getTopHeight() {
		return parent != null ? parent.getTopHeight() : 0f;
	}

	public Matrix4 getTransform() {
		return (parent != null) ? parent.getTransform() : new Matrix4().idt();
	}

	public final Vector3 inWorldCoordinates() {
		return inWorldCoordinates(0f, 0f);
	}

	public final Vector3 inWorldCoordinates(final float x, final float y) {
		return new Vector3(x, y, 0f).mul(getCascadedTransform());
	}

	/**
	 * @return An iterator that supports {@link Iterator#remove()()},
	 *         {@link Iterator#hasNext()}, and {@link Iterator#next()}.
	 */
	@Override
	public INodeIterator iterator() {
		return IteratorNodeEmpty.INSTANCE;
	}

	protected final INodeIterator iteratorForParentAtThisPosition() {
		if (parent == null) return null;
		final INodeIterator it = iterator();
		while (it.hasNext()) {
			if (this == it.next().node) {
				return it;
			}
		}
		return null;
	}

	public final ANode mirrorX() {
		getTransform().scale(-1f, 1f, 1f);
		return this;
	}

	public final ANode mirrorY() {
		getTransform().scale(1f, -1f, 1f);
		return this;
	}

	public ANode printDebug() {
		return printDebug("");
	}

	private ANode printDebug(final String prefix) {
		System.out.println(prefix + this);
		for (final PrioritizedNode child : this) child.node.printDebug(prefix + " ");
		return this;
	}

	private boolean rangeOverlap(final float range1From, final float range1To, final float range2From,
			final float range2To) {
		return range1From <= range2To && range2From <= range1To;
	}

	public final ANode reset() {
		getTransform().idt();
		return this;
	}

	public final ANode rotate(final float degrees) {
		getTransform().rotate(0, 0, 1, degrees);
		return this;
	}

	public final ANode scale(final float scaleXY) {
		return scale(scaleXY, scaleXY);
	}

	public final ANode scale(final float scaleX, final float scaleY) {
		getTransform().scale(scaleX, scaleY, 1f);
		return this;
	}

	/**
	 * @param renderPriority Setzt die Priorität dieses Knotens.
	 * @return Diesen Knoten zum Verketten.
	 */
	public final ANode setTraversalPriority(final int traversalPriority) {
		final INodeIterator it = iteratorForParentAtThisPosition();
		if (it != null)
			it.setTraversalPriority(traversalPriority);
		return this;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "@" + id;
	}

	public final ANode translate(final float dx, final float dy) {
		getTransform().translate(dx, dy, 0);
		return this;
	}

	private static enum IteratorNodeEmpty implements INodeIterator {
		INSTANCE;

		@Override
		public void setTraversalPriority(final int traversalPriority) {
			throw new IllegalStateException("Empty iterator does not have children.");
		}

		@Override
		public boolean hasNext() {
			return false;
		}

		@Override
		public PrioritizedNode next() {
			throw new NoSuchElementException("Empty iterator does not have children.");
		}

		@Override
		public void removeImmediately() {
			throw new NoSuchElementException("Empty iterator does not have children.");
		}
	}

	/**
	 * @param parent Parent that was set.
	 */
	protected void onParentSet(final ANode parent) {}

	/**
	 * @param parent The parent from which this node was removed.
	 */
	public void onParentRemoved(final ANodeGroup parent) {}
}
