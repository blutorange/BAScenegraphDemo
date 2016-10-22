package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph;

import com.badlogic.gdx.graphics.Color;

import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.ApplicationContext;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.visitor.INodeVisitor;

public class NodeColor extends ANodeGroup implements IColoredNode {

	private final Color color = new Color(Color.WHITE);
	private final Color cascadedColor = new Color(Color.WHITE);

	public NodeColor(final ApplicationContext ac) {
		super(Type.COLOR, ac);
	}

	@Override
	public <R, T, E extends Throwable> R accept(final INodeVisitor<R, T, E> visitor, final T data) throws E {
		return visitor.visit(this, data);
	}

	@Override
	public Color getCascadedColor() {
		return cascadedColor;
	}

	@Override
	public void updateAction() {
		cascadeColor();
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public void renderAction() {
	}
}
