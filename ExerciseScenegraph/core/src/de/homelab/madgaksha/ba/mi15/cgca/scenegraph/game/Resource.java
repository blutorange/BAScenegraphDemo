package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Resource {
	private static AssetManager manager;
	static void init() {
		manager = new AssetManager();
	}
	public static Texture texture(final String name) {
		if (!manager.isLoaded(name)) {
			manager.load(name, Texture.class);
			manager.finishLoading();
		}
		return manager.get(name, Texture.class);
	}
	public static Music music(final String name) {
		if (!manager.isLoaded(name)) {
			manager.load(name, Music.class);
			manager.finishLoading();
		}
		return manager.get(name, Music.class);
	}

	public static ParticleEffect particleEffect(final String name) {
		if (!manager.isLoaded(name)) {
			manager.load(name, ParticleEffect.class);
			manager.finishLoading();
		}
		return manager.get(name, ParticleEffect.class);
	}

	public static Sprite sprite(final String name) {
		return new Sprite(texture(name));
	}

	public static void dispose() {
		if (manager != null) manager.dispose();
	}
	public static Sound sound(final String name) {
		if (!manager.isLoaded(name)) {
			manager.load(name, Sound.class);
			manager.finishLoading();
		}
		return manager.get(name, Sound.class);
	}
}
