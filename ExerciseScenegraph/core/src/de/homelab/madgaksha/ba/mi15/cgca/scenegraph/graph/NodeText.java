package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Align;

import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.ApplicationContext;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.visitor.INodeVisitor;

public class NodeText extends ANodeDrawable {

	private String string;
	private final BitmapFont font;

	public NodeText(final float size, final String string) {
		super(Type.TEXT);
		setString(string);
		font = new BitmapFont();
		scale(size/15f);
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
	public void updateAction(final ApplicationContext context) {
	}

	@Override
	public void renderAction(final ApplicationContext context) {
		applyBatch(context);
		font.setColor(getCascadedColor());
		font.draw(context.getBatch(), string, 0, 0, 999999, Align.center, false);
	}
}
