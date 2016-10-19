package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph;

import com.badlogic.gdx.math.Vector2;

public interface IOriginedNode {
	public Vector2 getOrigin();

	default IOriginedNode setOrigin(final float originX, final float originY) {
		getOrigin().set(originX, originY);
		return this;
	}

	default float getOriginX() {
		return getOrigin().x;
	}

	default float getOriginY() {
		return getOrigin().y;
	}
}
