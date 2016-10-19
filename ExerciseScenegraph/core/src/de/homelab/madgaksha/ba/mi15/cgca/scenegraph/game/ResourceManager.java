package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Disposable;

public class ResourceManager implements Disposable {
	private final AssetManager manager;
	public ResourceManager() {
		manager = new AssetManager();
	}
	@Override
	public void dispose() {
		if (manager != null) manager.dispose();
	}

	public Texture texture(final String name) {
		if (!manager.isLoaded(name)) {
			manager.load(name, Texture.class);
			manager.finishLoading();
		}
		return manager.get(name, Texture.class);
	}
	public Music music(final String name) {
		if (!manager.isLoaded(name)) {
			manager.load(name, Music.class);
			manager.finishLoading();
		}
		return manager.get(name, Music.class);
	}

	public ParticleEffect particleEffect(final String name) {
		if (!manager.isLoaded(name)) {
			manager.load(name, ParticleEffect.class);
			manager.finishLoading();
		}
		return manager.get(name, ParticleEffect.class);
	}

	public Sprite sprite(final String name) {
		return new Sprite(texture(name));
	}

	public Sound sound(final String name) {
		if (!manager.isLoaded(name)) {
			manager.load(name, Sound.class);
			manager.finishLoading();
		}
		return manager.get(name, Sound.class);
	}
}
