package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.object;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.WorldNode;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.Controller;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.Resource;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.ANode;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeObject;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeTransform;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeUnit;

@WorldNode
public class World extends NodeUnit {
	private Sprite sprite;
	private Matrix4 isTransform;
	private Matrix4 targetTransform;
	private Gnome playerA;
	private Gnome playerB;
	private NodeUnit cameraTarget;
	private List<Butterfly> butterflyList;

	@Override
	public void update(final float time, final float deltaTime) {
		// Kamera auf Spieler 1/2 setzen.
		if (Gdx.input.isKeyJustPressed(Keys.NUM_1)) cameraTarget = playerA;
		else if (Gdx.input.isKeyJustPressed(Keys.NUM_2)) cameraTarget = playerB;
		// Kamera langsam mit Spieler mitlaufen lassen.
		targetTransform.set(cameraTarget.getTransform()).inv();
		isTransform.lerp(targetTransform, 0.035f);
		getTransform().set(isTransform);
		// Hintergrund periodisch verschieben, sodass er endlos erscheint.
		final float x = -new Vector3().mul(isTransform).x;
		final float dx = sprite.getWidth()*((int)(x / sprite.getWidth()));
		getByName("tBackground").reset().translate(dx, 70f);
		// Boden nach unten.
		translate(0, -150f);
		// Nacht werden lassen, je weiter man nach rechts geht.
		final float darknessFactor = Math.max(0.02f,1f-x/(sprite.getWidth()*40f));
		setColor(darknessFactor, darknessFactor*0.7f, darknessFactor*0.4f);
		// Kollision Schmetterling-Spieler
		if (time > 2f) {
			processCollision(playerA);
			processCollision(playerB);
		}
	}

	private void processCollision(final Gnome player) {
		final Iterator<Butterfly> it = butterflyList.iterator();
		while(it.hasNext()) {
			final Butterfly butterfly = it.next();
			if (butterfly.collides(player)) {
				it.remove();
				player.incrementScore();
				butterfly.caught();
			}
		}
	}

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
		makeBackground();
		makeButterflies();

		playerA = new Gnome();
		playerB = new Gnome();

		playerA.setController(new Controller.Builder().speed(3f, 18f).gravity(0.3f).build());
		playerB.setController(new Controller.Builder().left(Keys.A).right(Keys.D).up(Keys.W).down(Keys.S)
				.speed(3f, 18f).gravity(0.3f).jump(Keys.Q).modifier(Keys.SHIFT_LEFT).crouch(Keys.X).build());
		playerB.setColor(new Color(1f, 0.5f, 0.5f, 1f));

		addChild(playerA, 1);
		addChild(playerB, 0);

		final Music m = Resource.music("bgm3.mp3");
		m.setLooping(true);
		m.setVolume(0.25f);
		m.play();

		cameraTarget = playerA;
		isTransform = new Matrix4();
		targetTransform = new Matrix4();
		playerA.translate(400f, 0f);
		playerB.translate(-400f, 0f);
	}

	private void makeButterflies() {
		butterflyList = new ArrayList<>();
		final int butterflyCount = 25;

		float x = 0f;
		final float rangeMin = 5f;
		final float rangeMax = 20f;
		for (int i = 0; i < butterflyCount; ++i, x += MathUtils.random(rangeMin*400f, rangeMax*400f)) {
			makeButterfly(x);
		}
		x = -MathUtils.random(rangeMin*400f, rangeMax*400f);
		for (int i = 0; i<butterflyCount; ++i, x -= MathUtils.random(rangeMin*400f, rangeMax*400f)) {
			makeButterfly(x);
		}

	}

	private void makeButterfly(final float x) {
		final Butterfly butterfly = new Butterfly();
		butterfly.translate(x, 800f);
		butterflyList.add(butterfly);
		addChild(butterfly, 2);
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
}