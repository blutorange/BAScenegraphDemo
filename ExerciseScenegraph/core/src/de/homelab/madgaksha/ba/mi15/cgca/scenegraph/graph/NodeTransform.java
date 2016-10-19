package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.GraphicsContext;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.visitor.INodeVisitor;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.visitor.VisitorSmoothingFactor;

public class NodeTransform extends ANode {
	/** @see #cascadeTransform(Matrix4) */
	private final Matrix4 transform = new Matrix4();
	private final Matrix4 cascadedTransform = new Matrix4();
	private final Matrix4 smoothTransform = new Matrix4();
	private float smoothingFactor = 1f;

	public NodeTransform(final Matrix4 transform) {
		super(Type.TRANSFORM);
		if (transform == null)
			this.transform.idt();
		else
			this.transform.set(transform);
	}

	public NodeTransform() {
		this(new Matrix4().idt());
	}

	public NodeTransform(final Vector2 origin) {
		this(origin.x, origin.y);
	}

	public NodeTransform(final float x, final float y) {
		this(new Matrix4().translate(x, y, 0));
	}

	@Override
	public <R, T, E extends Throwable> R accept(final INodeVisitor<R, T, E> visitor, final T data) throws E {
		return visitor.visit(this, data);
	}

	public ANode setSmoothToIsTransform() {
		this.smoothTransform.set(transform);
		return this;
	}

	public ANode setSmoothingFactor(final float smoothingFactor) {
		this.smoothingFactor = MathUtils.clamp(smoothingFactor, 0f, 1f);
		return this;
	}

	public ANode setSmoothingFactorForThisAndChildren(final float smoothingFactor) {
		GraphicsContext.getInstance().getNodeActionQueue()
		.addAction(new NodeActionSetSmoothingFactorForThisAndChildren(this, smoothingFactor));
		return this;
	}

	public float getSmoothingFactor() {
		return smoothingFactor;
	}

	@Override
	public Matrix4 getCascadedTransform() {
		return cascadedTransform;
	}

	@Override
	public Matrix4 getTransform() {
		return transform;
	}

	@Override
	public void updateAction(final GraphicsContext context) {
		if (smoothingFactor != 1f) smoothTransform.lerp(transform, smoothingFactor);
		else smoothTransform.set(transform);
		cascadedTransform.set(parent != null ? parent.getCascadedTransform() : IDENTITY).mul(smoothTransform);
	}

	@Override
	public void renderAction(final GraphicsContext context) {
	}

	@Override
	public String toString() {
		return super.toString() + "@" + smoothingFactor;
	}

	private static class NodeActionSetSmoothingFactorForThisAndChildren implements INodeAction {
		private final NodeTransform node;
		private final float smoothingFactor;
		public NodeActionSetSmoothingFactorForThisAndChildren(final NodeTransform node, final float smoothingFactor) {
			this.node = node;
			this.smoothingFactor = smoothingFactor;
		}
		@Override
		public void perform() {
			node.accept(VisitorSmoothingFactor.INSTANCE, smoothingFactor);
		}
	}

}