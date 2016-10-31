package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.object;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.MathUtils;

import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.CmnCnst;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.Controller;

public class WorldController implements Controller {

	private float timePoem;
	private final World world;

	public WorldController(final World world) {
		if (world == null)
			throw new IllegalArgumentException(CmnCnst.Error.NULL_WORLD);
		this.world = world;
		world.ac().setCameraNode(world.playerA.camera);
	}

	@Override
	public void update(final float time, final float deltaTime) {
		// Switch camera between player A and B
		if (Gdx.input.isKeyJustPressed(Keys.NUM_1))
			world.ac().smoothSwitchCameraNode(world.playerA.camera);
		else if (Gdx.input.isKeyJustPressed(Keys.NUM_2))
			world.ac().smoothSwitchCameraNode(world.playerB.camera);

		// Hintergrund periodisch verschieben, sodass er endlos erscheint.
		final float x = world.ac().getCameraPosition().x;
		world.mBackground.reset().translate(world.sprite.getWidth() * ((int) (x / world.sprite.getWidth())), 0f);

		// Nacht werden lassen, je weiter man nach rechts geht.
		final float darknessFactor = Math.max(0.03f, 1f - x / (x < 0f ? -world.butterflyLeft : world.butterflyRight));
		world.cWorld.setColor(darknessFactor, darknessFactor * 0.7f, darknessFactor * 0.4f);

		// Kollision Schmetterling-Spieler
		if (time > 2f) {
			processCollision(world.playerA);
			processCollision(world.playerB);
		}
		// Spieler B im Farbspiel
		world.playerB.color.setHsb(time * 0.01f, 0.7f, 1f);

		// Poems fliegen lassen
		timePoem -= deltaTime;
		if (timePoem <= 0f) {
			timePoem = MathUtils.random(0.5f, 1.0f);
			world.makePoem();
		}
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
