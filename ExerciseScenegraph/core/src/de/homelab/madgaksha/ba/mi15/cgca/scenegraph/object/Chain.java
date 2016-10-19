package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.object;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;

import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.ApplicationContext;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.Controller;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.ResourceManager;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.ANodeDrawable;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.ANodeGroup;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeController;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeSprite;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeTransform;

public class Chain extends NodeController {

	private ChainController controller;
	private final int chainLinkCount = 20;
	List<ChainLinkModel> nodes;


	public Chain(final ApplicationContext ac) {
		super(ac);
	}

	@Override
	public Controller getController() {
		return controller;
	}

	@Override
	protected void make() {
		final ResourceManager rm = ac().getResourceManager();
		final Sprite sprite = rm.sprite("chainlink.png");
		controller = new ChainController(this);
		nodes = new ArrayList<>();
		final ANodeGroup node = new NodeTransform(ac());
		makeChainLinks(node, sprite);
		addChild(node);
	}

	private void makeChainLinks(ANodeGroup node, final Sprite sprite) {
		for (int i = 0; i < chainLinkCount; ++i) {
			final NodeTransform t = new NodeTransform(0f, i == 0 ? 0f : -38f, ac());
			final NodeTransform r = new NodeTransform(ac());
			final ANodeDrawable d = new NodeSprite(sprite, ac());
			final ChainLinkModel m = new ChainLinkModel(r, (float)Math.exp(5f*i/chainLinkCount));

			d.setOrigin(0.50f, 0.82f);
			d.randomHue();

			node.addChild(t, i);
			t.addChild(r);
			r.addChild(d, i);
			node = r;
			nodes.add(m);
		}
	}

	@Override
	public float getLeftWidth() {
		return 11f;
	}

	@Override
	public float getRightWidth() {
		return 11f;
	}

	@Override
	public float getTopHeight() {
		return 11f;
	}

	@Override
	public float getBottomHeight() {
		return 49f;
	}

	static class ChainLinkModel {
		public final NodeTransform node;
		public float angle;
		public float velocity;
		public float acceleration;
		public ChainLinkModel(final NodeTransform node, final float initialRotation) {
			this.node = node;
			this.angle = initialRotation;
			node.reset().rotate(angle);
		}
		public void simulate(final float deltaTime) {
			velocity += deltaTime * acceleration;
			angle += deltaTime * velocity;
			node.reset().rotate(angle);
		}
	}
}
