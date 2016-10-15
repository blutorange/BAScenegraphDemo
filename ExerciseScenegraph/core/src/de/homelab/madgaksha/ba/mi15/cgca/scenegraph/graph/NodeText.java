package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Align;

public class NodeText extends ANode {

	private String string;
	private final BitmapFont font;

	public NodeText(final float size, final String string) {
		setString(string);
		font = new BitmapFont();
		scale(size/15f);
	}

	public final NodeText setString(final String string) {
		this.string = string == null ? "" : string;
		return this;
	}

	@Override
	public void draw(final Batch batch, final Color color) {
		font.setColor(color);
		font.draw(batch, string, 0, 0, 999999, Align.center, false);
		font.draw(batch, string, 0, 0);
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

}
