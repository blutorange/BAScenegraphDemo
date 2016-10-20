package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.object;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;

import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.ApplicationContext;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.Controller;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.ResourceManager;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.ANode;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.ANodeDrawable;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.ANodeGroup;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeController;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeSprite;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeTransform;

public class Chain extends NodeController {

	private ChainController controller;
	private final int chainLinkCount = 500;
	/** Maximum absolute value of the angle of any chain relative to the previous one. */
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
		final Sprite sprite = rm.sprite("gnome/chainline.png");
		controller = new ChainController(this);
		nodes = new ArrayList<>();
		final ANodeGroup node = new NodeTransform(ac());
		makeChainLinks(node, sprite);
		addChild(node);
	}

	private void makeChainLinks(ANodeGroup node, final Sprite sprite) {
		float hue = MathUtils.random(0f,1f);
		for (int i = 0; i < chainLinkCount; ++i) {
			final NodeTransform t = new NodeTransform(0f, i == 0 ? 0f : -1f, ac());
			final NodeTransform r = new NodeTransform(ac());
			final ANodeDrawable d = new NodeSprite(sprite, ac());
			final ChainLinkModel m = new ChainLinkModel(r, i*0f/chainLinkCount);

			d.setOrigin(0.50f, 0.50f);
			d.setHsb(hue, 0.8f, 0.8f);
			hue += MathUtils.random(0.001f,0.005f);

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

	@Override
	public void onParentSet(final ANode parent) {
		controller.connectToSuspensionNode(parent);
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
		public void simulate(final float deltaTime, final float offset) {
			velocity += deltaTime * acceleration;
			angle = angle + deltaTime * velocity;
			node.reset().rotate(angle-offset);
		}
	}
}
