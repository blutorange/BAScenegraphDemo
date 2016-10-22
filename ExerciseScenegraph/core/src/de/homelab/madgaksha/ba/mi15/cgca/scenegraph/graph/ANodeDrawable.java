package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.ApplicationContext;

public abstract class ANodeDrawable extends ANode implements IColoredNode, IOriginedNode {
	private final Vector2 origin = new Vector2(0.5f, 0.5f);
	private final Color color = new Color(Color.WHITE);
	private final Color cascadedColor = new Color(Color.WHITE);

	public ANodeDrawable(final Type type, final ApplicationContext ac) {
		super(type, ac);
	}

	@Override
	public Vector2 getOrigin() {
		return origin;
	}

	@Override
	public Color getCascadedColor() {
		return cascadedColor;
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public void updateAction() {
		cascadeColor();
	}

	protected final Batch applyBatch() {
		ac().getBatch().setTransformMatrix(getCascadedTransform());
		return ac().getBatch();
	}
}
