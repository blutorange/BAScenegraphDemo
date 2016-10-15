package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class NodeObject extends NodeTransform {
	private Sprite sprite;
	public NodeObject(final Sprite sprite) {
		setSprite(sprite);
	}
	public void setSprite(final Sprite sprite) {
		if (sprite == null) throw new IllegalArgumentException("Sprite cannot be null.");
		this.sprite = sprite;
	}

	public Sprite getSprite() {
		return sprite;
	}

	@Override
	public void draw(final Batch batch, final Color color) {
		sprite.setColor(color);
		final float x = sprite.getX();
		final float y = sprite.getY();
		sprite.setOrigin(sprite.getWidth()*getOriginX(), sprite.getHeight()*(getOriginY()));
		sprite.translate(-sprite.getWidth()*getOriginX(), -sprite.getHeight()*(getOriginY()));
		sprite.draw(batch);
		sprite.setPosition(x,y);
	}

	@Override
	public float getLeftWidth() {
		return sprite.getWidth()*getOriginX();
	}
	@Override
	public float getRightWidth() {
		return sprite.getWidth()*(1f-getOriginX());
	}
	@Override
	public float getTopHeight() {
		return sprite.getHeight()*(1f-getOriginY());
	}
	@Override
	public float getBottomHeight() {
		return sprite.getHeight()*getOriginY();
	}
}
