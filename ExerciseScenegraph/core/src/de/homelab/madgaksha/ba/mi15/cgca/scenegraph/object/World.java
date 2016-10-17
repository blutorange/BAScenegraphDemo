package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.object;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;

import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.WorldNode;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.Controller;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.Resource;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.ANode;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeObject;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeTransform;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeUnit;

@WorldNode
public class World extends NodeUnit {
	private WorldController controller;
	int butterflyCount;
	Sprite sprite;
	Gnome playerA;
	Gnome playerB;
	List<Butterfly> butterflyList;
	float butterflyRight;
	float butterflyLeft;

	private void makeBackground() {
		sprite = Resource.sprite("background.jpg");
		final ANode tBackground = new NodeTransform(0,0f);
		final ANode tBackground1 = new NodeTransform(0,300f);
		final ANode tBackground2 = new NodeTransform(sprite.getWidth(),300f);
		final ANode tBackground3 = new NodeTransform(-sprite.getWidth(),300f);
		final ANode tBackground4 = new NodeTransform(2*sprite.getWidth(),300f);
		final ANode tBackground5 = new NodeTransform(-2*sprite.getWidth(),300f);
		final ANode background1 = new NodeObject(sprite);
		final ANode background2 = new NodeObject(sprite);
		final ANode background3 = new NodeObject(sprite);
		final ANode background4 = new NodeObject(sprite);
		final ANode background5 = new NodeObject(sprite);

		addChild("tBackground", tBackground, -99);

		tBackground.addChild("tBackground1", tBackground1, -99);
		tBackground1.addChild("background1", background1);

		tBackground.addChild("tBackground2", tBackground2, -99);
		tBackground2.addChild("background2", background2);

		tBackground.addChild("tBackground3", tBackground3, -99);
		tBackground3.addChild("background3", background3);

		tBackground.addChild("tBackground4", tBackground4, -99);
		tBackground4.addChild("background4", background4);

		tBackground.addChild("tBackground5", tBackground5, -99);
		tBackground5.addChild("background5", background5);
	}

	@Override
	protected void make() {
		butterflyCount = 25;
		makeBackground();
		makeButterflies();

		playerA = new Gnome();
		playerB = new Gnome();

		playerA.setController(new GnomeController.Builder().speed(3f, 18f).gravity(0.3f).build(playerA));
		playerB.setController(new GnomeController.Builder().left(Keys.A).right(Keys.D).up(Keys.W).down(Keys.S)
				.speed(3f, 18f).gravity(0.3f).jump(Keys.Q).modifier(Keys.SHIFT_LEFT).crouch(Keys.X).build(playerB));

		addChild(playerA, 1);
		addChild(playerB, 0);

		final Music m = Resource.music("bgm3.mp3");
		m.setLooping(true);
		m.setVolume(0.25f);
		m.play();

		playerA.translate(400f, 0f);
		playerB.translate(-400f, 0f);

		controller = new WorldController(this);
	}

	private void makeButterflies() {
		butterflyList = new ArrayList<>();

		float x = 0f;
		final float rangeMin = 5f;
		final float rangeMax = 20f;
		for (int i = 0; i < butterflyCount; ++i, x += MathUtils.random(rangeMin*400f, rangeMax*400f)) {
			makeButterfly(x);
		}
		butterflyRight = x;
		x = -MathUtils.random(rangeMin*400f, rangeMax*400f);
		for (int i = 0; i<butterflyCount; ++i, x -= MathUtils.random(rangeMin*400f, rangeMax*400f)) {
			makeButterfly(x);
		}
		butterflyLeft = x;
	}

	private Butterfly makeButterfly(final float x) {
		final Butterfly butterfly = new Butterfly();
		butterfly.setController(new ButterflyController(butterfly));
		butterfly.translate(x, 800f);
		butterflyList.add(butterfly);
		addChild(butterfly, 2);
		return butterfly;
	}

	@Override
	public float getLeftWidth() {
		return Float.MAX_VALUE;
	}

	@Override
	public float getRightWidth() {
		return -Float.MAX_VALUE;
	}

	@Override
	public float getTopHeight() {
		return 0f;
	}

	@Override
	public float getBottomHeight() {
		return 0f;
	}

	@Override
	protected Controller getController() {
		return controller;
	}

	void addRandomButterfly() {
		final float x = MathUtils.random(butterflyLeft, butterflyRight);
		System.out.println(butterflyLeft + " " + butterflyRight + " " + x);
		final Butterfly bf = makeButterfly(x);
		bf.translate(0f, 1600f);
		bf.setSmoothTransform(bf.getTransform());
		bf.translate(0f, -1600f);
		bf.setSmoothingFactor(0.01f);
	}
}