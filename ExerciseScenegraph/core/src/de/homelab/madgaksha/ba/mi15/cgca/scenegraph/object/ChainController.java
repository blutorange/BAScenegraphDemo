package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.object;

import com.badlogic.gdx.math.MathUtils;

import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.Controller;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.object.Chain.ChainLinkModel;

public class ChainController implements Controller {

	private final float gravity;
	private final float friction;
	private final Chain chain;

	public ChainController(final Chain chain) {
		this(chain, 10f, 0.2f);
	}

	public ChainController(final Chain chain, final float gravity, final float friction) {
		this.chain = chain;
		this.gravity = gravity;
		this.friction = friction;
	}

	@Override
	public void update(final float time, final float deltaTime) {
		simulate(deltaTime);
	}

	private void simulate(final float deltaTime) {
		final float s = chain.nodes.size();
		final int len = chain.nodes.size();
		for (int i = 0; i < len; ++i) {
			final ChainLinkModel model = chain.nodes.get(i);
			model.acceleration = -(s - i) * gravity * MathUtils.sinDeg(model.angle)
					- friction * model.velocity;
			model.simulate(deltaTime);
		}
	}
}
