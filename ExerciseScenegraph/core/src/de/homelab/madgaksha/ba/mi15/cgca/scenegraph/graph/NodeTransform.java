package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

public class NodeTransform extends ANode {
	public NodeTransform(final Matrix4 transform) {
		super(transform);
	}
	public NodeTransform() {
		super();
	}
	public NodeTransform(final Vector2 origin) {
		super(origin.x, origin.y);
	}
	public NodeTransform(final float x, final float y) {
		super(new Matrix4().translate(x, y, 0));
	}
	@Override
	public void draw(final Batch batch, final Color color) {
	}
	@Override
	public float getLeftWidth() {
		return 0f;
	}
	@Override
	public float getRightWidth() {
		return 0f;
	}
	@Override
	public float getTopHeight() {
		return 0f;
	}
	@Override
	public float getBottomHeight() {
		return 0f;
	}
}