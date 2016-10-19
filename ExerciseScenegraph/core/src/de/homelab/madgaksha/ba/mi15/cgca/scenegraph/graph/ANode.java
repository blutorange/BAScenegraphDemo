package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.GraphicsContext;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.visitor.INodeVisitor;

public abstract class ANode implements Iterable<PrioritizedNode> {
	public static enum Type {
		TRANSFORM, TEXT, SPRITE, COLOR;
	}

	private final static AtomicLong idProvider = new AtomicLong();

	public final Type type;

	protected final static Matrix4 IDENTITY = new Matrix4();

	private final long id;

	private final List<PrioritizedNode> children = new ArrayList<>();

	private boolean dirty = true;

	protected ANode parent = null;

	public ANode(final Type type) {
		this.type = type;
		id = idProvider.incrementAndGet();
	}

	public final ANode addChild(final ANode node) {
		return addChild(node, 0);
	}

	public final ANode addChild(final ANode node, final int traversalPriority) {
		return addChild(node, traversalPriority, children.size());
	}

	public final ANode addChild(final ANode node, final int traversalPriority, final int position) {
		GraphicsContext.getInstance().getNodeActionQueue().addAction(new NodeActionAddChild(this, node, traversalPriority, position));
		return this;
	}


	public void addChildInternal(final ANode node, final int traversalPriority, final int position) {
		children.add(position, new PrioritizedNode(node, traversalPriority));
		node.parent = this;
		dirty = true;
	}


	public abstract <R,T,E extends Throwable> R accept(INodeVisitor<R, T, E>  visitor, T data) throws E;

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
	public boolean collides(final ANode other) {
		final Vector3 thisPos = this.inWorldCoordinates();
		final Vector3 otherPos = other.inWorldCoordinates();
		return rangeOverlap(thisPos.x - this.getLeftWidth(), thisPos.x + this.getRightWidth(), otherPos.x - other.getLeftWidth(),
				otherPos.x + other.getRightWidth())
				&& rangeOverlap(thisPos.y - this.getBottomHeight(), thisPos.y + this.getTopHeight(),
						otherPos.y - other.getBottomHeight(), otherPos.y + other.getTopHeight());
	}

	public float getLeftWidth() {
		return parent != null ? parent.getLeftWidth() : 0f;
	}
	public float getRightWidth() {
		return parent != null ? parent.getRightWidth() : 0f;
	}
	public float getTopHeight() {
		return parent != null ? parent.getTopHeight() : 0f;
	}
	public float getBottomHeight() {
		return parent != null ? parent.getBottomHeight() : 0f;
	}
	public Matrix4 getCascadedTransform() {
		return (parent != null) ? parent.getCascadedTransform() : new Matrix4().idt();
	}
	public Color getCascadedColor() {
		return (parent != null) ? parent.getCascadedColor() : Color.WHITE;
	}
	public Color getColor() {
		return parent != null ? parent.getColor() : Color.WHITE;
	}
	public Matrix4 getTransform() {
		return (parent != null) ? parent.getTransform() : new Matrix4().idt();
	}

	public final ANode detach() {
		if (parent != null) {
			int i = 0;
			for (final PrioritizedNode child : parent.children) {
				if (this == child.node) {
					parent.removeChild(i);
					break;
				}
				++i;
			}
		}
		return this;
	}

	public final ANode getParent() {
		return parent;
	}

	public final Vector3 inWorldCoordinates() {
		return inWorldCoordinates(0f, 0f);
	}

	public final Vector3 inWorldCoordinates(final float x, final float y) {
		return new Vector3(x, y, 0f).mul(getCascadedTransform());
	}

	@Override
	public final Iterator<PrioritizedNode> iterator() {
		if (dirty) rebuildSortedChildren();
		return children.iterator();
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

	private void rebuildSortedChildren() {
		Collections.sort(children);
		dirty = false;
	}

	public final ANode removeChild(final int position) {
		GraphicsContext.getInstance().getNodeActionQueue().addAction(new NodeActionRemoveChild(this, position));
		return this;
	}

	public final void removeChildInternal(final int index) {
		if (index >= children.size()) return;
		children.get(index).node.parent = null;
		children.remove(index);
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
	 * @return
	 */
	public final ANode setTraversalPriority(final int traversalPriority) {
		final int childIndex = findIndexOfParent();
		if (childIndex >= 0)
			parent.setTraversalPriority(childIndex, traversalPriority);
		return this;
	}

	private int findIndexOfParent() {
		if (parent == null) return -1;
		int index = -1;
		for (final PrioritizedNode child : parent.children) {
			if (this == child.node) {
				return index;
			}
			++index;
		}
		return -1;
	}

	public final ANode setTraversalPriority(final int childIndex, final int traversalPriority) {
		final PrioritizedNode node = children.get(childIndex);
		if (node != null) {
			children.set(childIndex, new PrioritizedNode(node.node, traversalPriority));
			dirty = true;
		}
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

	public long getId() {
		return id;
	}

	/** Some common action a node needs to do on each update. Should not recurse. Called in the correct traversal order. */
	public abstract void updateAction(GraphicsContext context);
	public abstract void renderAction(GraphicsContext context);

	private static class NodeActionAddChild implements INodeAction {
		private final ANode parent;
		private final ANode child;
		private final int traversalPriority;
		private final int position;
		public NodeActionAddChild(final ANode parent, final ANode child, final int traversalPriority, final int position) {
			this.parent = parent;
			this.child = child;
			this.traversalPriority = traversalPriority;
			this.position = position;
		}
		@Override
		public void perform() {
			parent.addChildInternal(child, traversalPriority, position);
		}
	}
	private static class NodeActionRemoveChild implements INodeAction {
		private final ANode parent;
		private final int position;
		public NodeActionRemoveChild(final ANode parent, final int position) {
			this.parent = parent;
			this.position = position;
		}
		@Override
		public void perform() {
			parent.removeChildInternal(position);
		}
	}
}
