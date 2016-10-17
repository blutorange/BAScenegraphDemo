package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class NodeAngle extends NodeObject {
	public float angle;
	public float angularVelocity;
	public float angularAcceleration;

	public NodeAngle(final Sprite sprite) {
		super(sprite);
	}

	public NodeAngle fromAcceleration(final float deltaTime) {
		angularVelocity += deltaTime * angularAcceleration;
		angle += deltaTime * angularVelocity;
		rotate(angle);
		return this;
	}
}
