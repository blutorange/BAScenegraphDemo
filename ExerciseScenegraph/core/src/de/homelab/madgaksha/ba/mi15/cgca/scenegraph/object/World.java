package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.object;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;

import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.ApplicationContext;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.Controller;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeColor;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeController;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeSprite;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeTransform;

public class World extends NodeController {
	public World(final ApplicationContext ac) {
		super(ac);
	}

	private WorldController controller;
	int butterflyCount;
	Sprite sprite;
	Gnome playerA;
	Gnome playerB;
	List<Butterfly> butterflyList;
	float butterflyRight;
	float butterflyLeft;

	NodeColor cWorld;
	NodeTransform tBackground;
	NodeTransform mBackground;
	NodeTransform tBackground1;
	NodeTransform tBackground2;
	NodeTransform tBackground3;
	NodeTransform tBackground4;
	NodeTransform tBackground5;
	NodeSprite background1;
	NodeSprite background2;
	NodeSprite background3;
	NodeSprite background4;
	NodeSprite background5;

	private void makeBackground() {
		sprite = ac().getResourceManager().sprite("background.jpg");

		tBackground = new NodeTransform(0, 80f, ac());
		mBackground = new NodeTransform(ac());

		tBackground1 = new NodeTransform(0, 300f, ac());
		tBackground2 = new NodeTransform(sprite.getWidth(), 300f, ac());
		tBackground3 = new NodeTransform(-sprite.getWidth(), 300f, ac());
		tBackground4 = new NodeTransform(2 * sprite.getWidth(), 300f, ac());
		tBackground5 = new NodeTransform(-2 * sprite.getWidth(), 300f, ac());
		background1 = new NodeSprite(sprite, ac());
		background2 = new NodeSprite(sprite, ac());
		background3 = new NodeSprite(sprite, ac());
		background4 = new NodeSprite(sprite, ac());
		background5 = new NodeSprite(sprite, ac());

		cWorld.addChild(tBackground, -99);
		tBackground.addChild(mBackground);

		mBackground.addChild(tBackground1);
		mBackground.addChild(tBackground2);
		mBackground.addChild(tBackground3);
		mBackground.addChild(tBackground4);
		mBackground.addChild(tBackground5);

		tBackground1.addChild(background1);
		tBackground2.addChild(background2);
		tBackground3.addChild(background3);
		tBackground4.addChild(background4);
		tBackground5.addChild(background5);
	}

	@Override
	protected void make() {
		butterflyCount = 25;

		cWorld = new NodeColor(ac());
		addChild(cWorld);

		makeBackground();
		makePlayers();
		makeButterflies();
		makeMusic();

		controller = new WorldController(this);
	}

	private void makeMusic() {
		final Music m = ac().getResourceManager().music("bgm3.mp3");
		m.setLooping(true);
		m.setVolume(0.20f);
		m.play();
	}

	private void makePlayers() {
		playerA = new Gnome(ac());
		playerB = new Gnome(ac());

		playerA.setController(new GnomeController.Builder().speed(3f, 18f).gravity(0.3f).build(playerA));
		playerB.setController(new GnomeController.Builder().left(Keys.A).right(Keys.D).up(Keys.W).down(Keys.S)
				.speed(3f, 18f).gravity(0.3f).jump(Keys.Q).modifier(Keys.SHIFT_LEFT).crouch(Keys.X).build(playerB));

		cWorld.addChild(playerA, 11);
		cWorld.addChild(playerB, 10);

		playerA.setHeadBallAccessory(new Chain(ac()));
		playerB.setHeadBallAccessory(new Chain(ac()));

		playerA.translate(400f, 0f);
		playerB.translate(-400f, 0f);
	}

	private void makeButterflies() {
		butterflyList = new ArrayList<>();

		float x = 0f;
		final float rangeMin = 10f;
		final float rangeMax = 30f;
		for (int i = 0; i < butterflyCount; ++i, x += MathUtils.random(rangeMin * 400f, rangeMax * 400f)) {
			makeButterfly(x);
		}
		butterflyRight = x;
		x = -MathUtils.random(rangeMin * 400f, rangeMax * 400f);
		for (int i = 0; i < butterflyCount; ++i, x -= MathUtils.random(rangeMin * 400f, rangeMax * 400f)) {
			makeButterfly(x);
		}
		butterflyLeft = x;
	}

	private Butterfly makeButterfly(final float x) {
		final Butterfly butterfly = new Butterfly(ac());
		butterfly.setController(new ButterflyController(butterfly));
		butterfly.translate(x, 800f);
		butterflyList.add(butterfly);
		cWorld.addChild(butterfly, 20);
		return butterfly;
	}

	void makePoem() {
		final Poem poem = new Poem(ac());
		final float dx = MathUtils.random(butterflyLeft, butterflyRight);
		final float dy = MathUtils.random(100f, 400f);
		poem.translate(dx, dy);
		cWorld.addChild(poem);
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
		return 800f;
	}

	@Override
	public float getBottomHeight() {
		return 200f;
	}

	@Override
	public Controller getController() {
		return controller;
	}

	void addRandomButterfly() {
		final float x = MathUtils.random(butterflyLeft, butterflyRight);
		System.out.println(butterflyLeft + " " + butterflyRight + " " + x);
		final Butterfly bf = makeButterfly(x);
		bf.translate(0f, 1600f);
		bf.setSmoothToIsTransform();
		bf.translate(0f, -1600f);
		bf.setSmoothingFactor(0.01f);
	}
}