package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.object;

import com.badlogic.gdx.math.MathUtils;

import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.Controller;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeAngle;

public class ChainController implements Controller {

	private final float gravity;
	private final float friction;
	private final Chain chain;

	public ChainController(final Chain chain) {
		this(chain, 200f, 0.5f);
	}

	public ChainController(final Chain chain, final float gravity, final float friction) {
		this.chain = chain;
		this.gravity = gravity;
		this.friction = friction;
	}

	@Override
	public void update(final float time, final float deltaTime) {
		simulate(time, deltaTime);
	}

	/**
	 * @param time
	 */
	private void simulate(final float time, final float deltaTime) {
		final float s = chain.nodes.size();
		for (int i = 0; i < chain.nodes.size(); ++i) {
			final NodeAngle node = chain.nodes.get(i);
			node.angularAcceleration = -(s - i) * gravity * MathUtils.sinDeg(node.angle)
					- friction * node.angularVelocity;
			node.reset();
			node.fromAcceleration(deltaTime);
		}
	}
}
