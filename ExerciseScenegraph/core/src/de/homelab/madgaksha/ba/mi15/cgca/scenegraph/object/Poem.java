package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.object;

import com.badlogic.gdx.math.MathUtils;

import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.Controller;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.PoemGenerator;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeText;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeTransform;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeUnit;

public class Poem extends NodeUnit {

	private PoemController controller;
	NodeTransform tText;
	NodeText text;

	@Override
	protected Controller getController() {
		return controller;
	}

	@Override
	protected void make() {
		text = new NodeText(MathUtils.random(30f,60f), PoemGenerator.lineFromRandomTheme());
		tText = new NodeTransform();
		addChild("tText", tText);
		tText.addChild("text", text);
		controller = new PoemController(this);
	}

	@Override
	public float getLeftWidth() {
		return text.getLeftWidth();
	}

	@Override
	public float getRightWidth() {
		return text.getRightWidth();
	}

	@Override
	public float getTopHeight() {
		return text.getTopHeight();
	}

	@Override
	public float getBottomHeight() {
		return text.getBottomHeight();
	}

}
