package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph;

import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.FileTextureData;

import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.CmnCnst;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.ApplicationContext;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.visitor.INodeVisitor;

public class NodeSprite extends ANodeDrawable {
	private Sprite sprite;

	/**
	 * Erzeugt einen neuen Knoten mit einem Bild.
	 * @param sprite Das Bild, welches gezeichnet werden soll.
	 * @param ac Der ApplicationContext.
	 */
	public NodeSprite(final Sprite sprite, final ApplicationContext ac) {
		super(Type.SPRITE, ac);
		setSprite(sprite);
	}

	/**
	 * Setzt den Sprite (Bild), das für diesen Knoten gezeichnet werden soll.
	 *
	 * @param sprite
	 *            Zu setzendes Sprite.
	 */
	public void setSprite(final Sprite sprite) {
		if (sprite == null)
			throw new IllegalArgumentException(CmnCnst.Error.NULL_SPRITE);
		this.sprite = sprite;
	}

	@Override
	public float getLeftWidth() {
		return sprite.getWidth() * getOriginX();
	}

	@Override
	public float getRightWidth() {
		return sprite.getWidth() * (1f - getOriginX());
	}

	@Override
	public float getTopHeight() {
		return sprite.getHeight() * (1f - getOriginY());
	}

	@Override
	public float getBottomHeight() {
		return sprite.getHeight() * getOriginY();
	}

	@Override
	public <R, T, E extends Throwable> R accept(final INodeVisitor<R, T, E> visitor, final T data) throws E {
		return visitor.visit(this, data);
	}

	@Override
	public void renderAction() {
		sprite.setColor(getCascadedColor());
		final float x = sprite.getX();
		final float y = sprite.getY();
		sprite.setOrigin(sprite.getWidth() * getOriginX(), sprite.getHeight() * (getOriginY()));
		sprite.translate(-sprite.getWidth() * getOriginX(), -sprite.getHeight() * (getOriginY()));
		sprite.draw(applyBatch());
		sprite.setPosition(x, y);
	}

	@Override
	public String toString() {
		final TextureData data = sprite.getTexture().getTextureData();
		if (data instanceof FileTextureData) {
			final FileTextureData fd = (FileTextureData)data;
			return super.toString() + "@" + fd.getFileHandle().name(); //$NON-NLS-1$
		}
		return super.toString();
	}
}
