package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.ApplicationContext;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.visitor.INodeVisitor;

public class NodeText extends ANodeDrawable {

	private String string;
	private final BitmapFont font;
	private final float size;

	public NodeText(final float size, final String string, final ApplicationContext ac) {
		super(Type.TEXT, ac);
		this.size = size;
		setString(string);
		font = new BitmapFont();
	}

	public final NodeText setString(final String string) {
		this.string = string == null ? "" : string;
		return this;
	}


	@Override
	public float getLeftWidth() {
		// TODO Auto-generated method stub
		throw new RuntimeException("TODO - not yet implemented");
	}

	@Override
	public float getRightWidth() {
		// TODO Auto-generated method stub
		throw new RuntimeException("TODO - not yet implemented");
	}

	@Override
	public float getTopHeight() {
		// TODO Auto-generated method stub
		throw new RuntimeException("TODO - not yet implemented");
	}

	@Override
	public float getBottomHeight() {
		// TODO Auto-generated method stub
		throw new RuntimeException("TODO - not yet implemented");
	}

	@Override
	public <R, T, E extends Throwable> R accept(final INodeVisitor<R, T, E> visitor, final T data) throws E {
		return visitor.visit(this, data);
	}

	@Override
	public String toString() {
		return super.toString() + "(" + string + ")";
	}

	@Override
	public void renderAction() {
		font.setColor(getCascadedColor());
		font.getData().scaleX = font.getData().scaleY = size/15f;
		font.draw(applyBatch(), string, 0, 0);
	}
}
