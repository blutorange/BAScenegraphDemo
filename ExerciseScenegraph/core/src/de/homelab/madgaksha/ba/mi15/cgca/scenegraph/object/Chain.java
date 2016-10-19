//package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.object;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.badlogic.gdx.graphics.g2d.Sprite;
//
//import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.Controller;
//import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.Resource;
//import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.ANode;
//import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeAngle;
//import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeController;
//import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeTransform;
//
//public class Chain extends NodeController {
//
//	private ChainController controller;
//	List<NodeAngle> nodes;
//
//	@Override
//	protected Controller getController() {
//		return controller;
//	}
//
//	@Override
//	protected void make() {
//		final Sprite sprite = Resource.sprite("chainlink.png");
//		controller = new ChainController(this);
//		nodes = new ArrayList<>();
//		ANode node = new NodeTransform();
//		addChild("mChain", node);
//		for (int i = 0; i < 10; ++i) {
//			final NodeTransform t = new NodeTransform(0f, i == 0 ? 0f : -38f);
//			final NodeAngle o = new NodeAngle(sprite);
//			o.setOrigin(0.50f, 0.82f);
//			o.rotate(10f);
//			o.angle = 10f;
//			nodes.add(o);
//			node.addChild("tChain" + i, t);
//			t.addChild("oChain" + i, o);
//			node = o;
//		}
//		setSmoothingFactorForThisAndChildren(1f);
//	}
//
//	@Override
//	public float getLeftWidth() {
//		return 11f;
//	}
//
//	@Override
//	public float getRightWidth() {
//		return 11f;
//	}
//
//	@Override
//	public float getTopHeight() {
//		return 11f;
//	}
//
//	@Override
//	public float getBottomHeight() {
//		return 49f;
//	}
//
//}
