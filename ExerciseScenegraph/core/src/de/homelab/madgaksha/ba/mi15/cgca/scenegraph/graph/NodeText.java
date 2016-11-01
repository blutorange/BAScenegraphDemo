package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph;

import org.apache.commons.lang3.StringUtils;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.CmnCnst;
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
		this.string = string == null ? StringUtils.EMPTY : string;
		return this;
	}


	@Override
	public float getLeftWidth() {
		throw new RuntimeException(CmnCnst.Error.TODO);
	}

	@Override
	public float getRightWidth() {
		throw new RuntimeException(CmnCnst.Error.TODO);
	}

	@Override
	public float getTopHeight() {
		throw new RuntimeException(CmnCnst.Error.TODO);
	}

	@Override
	public float getBottomHeight() {
		throw new RuntimeException(CmnCnst.Error.TODO);
	}

	@Override
	public <R, T, E extends Throwable> R accept(final INodeVisitor<R, T, E> visitor, final T data) throws E {
		return visitor.visit(this, data);
	}

	@Override
	public String toString() {
		return super.toString() + "(" + string + ")";  //$NON-NLS-1$//$NON-NLS-2$
	}

	@Override
	public void renderAction() {
		font.setColor(getCascadedColor());
		font.getData().scaleX = font.getData().scaleY = size/15f;
		font.draw(applyBatch(), string, 0, 0);
	}
}
