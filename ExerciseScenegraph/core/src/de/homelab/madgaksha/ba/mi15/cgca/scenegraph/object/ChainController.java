package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.object;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.Controller;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.ANode;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.object.Chain.ChainLinkModel;

public class ChainController implements Controller {

	private final float gravity;
	private final float friction;
	private final float suspensionAcceleration;
	private final float angleClip = 0.75f;
	private final Chain chain;

	private final Vector2 tmp = new Vector2();
	private final Vector3 susNodeP= new Vector3();
	private final Vector3 susNodePP= new Vector3();

	public ChainController(final Chain chain) {
		this(chain, 2000f, 0.3f, 0.1f);
	}

	public ChainController(final Chain chain, final float gravity, final float friction, final float suspensionAcceleration) {
		this.chain = chain;
		this.gravity = gravity;
		this.friction = friction;
		this.suspensionAcceleration = suspensionAcceleration;
	}

	@Override
	public void update(final float time, final float deltaTime) {
		simulate(deltaTime);
	}

	void connectToSuspensionNode(final ANode node) {
		susNodeP.set(node.inWorldCoordinates());
		susNodePP.set(susNodePP);
	}

	private void simulate(final float deltaTime) {
		final int len = chain.nodes.size();
		float offset = 0f;
		float accel = 0f;
		float angle = 0f;
		final ANode suspensionNode = chain.getParent();
		if (suspensionNode != null) {
			final Vector3 ws = suspensionNode.inWorldCoordinates();
			final Vector3 down = suspensionNode.inWorldCoordinates(0, -1).sub(ws);
			final Vector3 right = suspensionNode.inWorldCoordinates(1, 0).sub(ws);
			final float signum = Math.signum(down.x*right.y-down.y*right.x);
			//
			angle = signum * (tmp.set(down.x,down.y).angle()+90f);
			// Acceleration
			final float ax = (ws.x-2f*susNodeP.x+susNodePP.x)/(deltaTime*deltaTime);
			accel = -suspensionAcceleration*ax;
			//
			susNodePP.set(susNodeP);
			susNodeP.set(ws.x,ws.y,ws.z);
		}
		final float invlen = accel/len;
		for (int i = 0; i < len; ++i) {
			final ChainLinkModel model = chain.nodes.get(i);
			model.acceleration =
					- gravity * MathUtils.sinDeg(model.angle+angle)
					- friction * model.velocity
					+ i*invlen*MathUtils.cosDeg(model.angle)
					;
			model.simulate(deltaTime, offset);
			offset = model.angle;
		}
		ChainLinkModel last = chain.nodes.get(0);
		for (int i = 1; i< len; ++i) {
			final ChainLinkModel model = chain.nodes.get(i);
			model.angle = MathUtils.clamp(model.angle, last.angle-angleClip, last.angle+angleClip);
			last = model;
		}
	}
}
