package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.object;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.Controller;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeUnit;

public class WorldController implements Controller {

	private float timePoem;
	private final Matrix4 isTransform;
	private final Matrix4 targetTransform;
	private NodeUnit cameraTarget;
	private final World world;

	public WorldController(final World world) {
		if (world == null)
			throw new IllegalArgumentException("World cannot be null.");
		this.world = world;
		cameraTarget = world.playerA;
		isTransform = new Matrix4();
		targetTransform = new Matrix4();
	}

	@Override
	public void update(final float time, final float deltaTime) {
		// Kamera auf Spieler 1/2 setzen.
		if (Gdx.input.isKeyJustPressed(Keys.NUM_1))
			cameraTarget = world.playerA;
		else if (Gdx.input.isKeyJustPressed(Keys.NUM_2))
			cameraTarget = world.playerB;
		// Kamera langsam mit Spieler mitlaufen lassen.
		targetTransform.set(cameraTarget.getTransform()).inv();
		isTransform.lerp(targetTransform, 0.035f);
		world.getTransform().set(isTransform);
		// Hintergrund periodisch verschieben, sodass er endlos erscheint.
		final float x = -new Vector3().mul(isTransform).x;
		final float dx = world.sprite.getWidth() * ((int) (x / world.sprite.getWidth()));
		world.getByName("tBackground").reset().translate(dx, 70f);
		// Boden nach unten.
		world.translate(0, -150f);
		// Nacht werden lassen, je weiter man nach rechts geht.
		final float darknessFactor = Math.max(0.02f, 1f - x / (world.sprite.getWidth() * 40f));
		world.setColor(darknessFactor, darknessFactor * 0.7f, darknessFactor * 0.4f);
		// Kollision Schmetterling-Spieler
		if (time > 2f) {
			processCollision(world.playerA);
			processCollision(world.playerB);
		}
		// Spieler B im Farbspiel
		world.playerB.setHsb(time * 0.01f, 0.7f, 1f);
		// Poems fliegen lassen
		timePoem -= deltaTime;
		if (timePoem <= 0f) {
			timePoem = MathUtils.random(0.5f, 1.0f);
			makePoem();
		}
	}

	private void makePoem() {
		final Poem poem = new Poem();
		final float dx = MathUtils.random(world.butterflyLeft, world.butterflyRight);
		final float dy = MathUtils.random(100f, 400f);
		poem.translate(dx, dy);
		world.addChild(poem);
	}

	private void processCollision(final Gnome player) {
		final Iterator<Butterfly> it = world.butterflyList.iterator();
		while (it.hasNext()) {
			final Butterfly butterfly = it.next();
			if (butterfly.collides(player)) {
				it.remove();
				player.incrementScore();
				butterfly.caught();
			}
		}
		if (world.butterflyList.size() < 10) {
			for (int i = world.butterflyCount; i-- > 0;)
				world.addRandomButterfly();
		}
	}
}
