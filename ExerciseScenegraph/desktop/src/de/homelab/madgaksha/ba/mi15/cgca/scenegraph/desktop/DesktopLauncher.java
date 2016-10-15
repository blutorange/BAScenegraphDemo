package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.MyGdxGame;

public class DesktopLauncher {
	public static void main (final String[] args) {
		final LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		System.out.println("Fullsceen on: ./bascene.jar <width> <height> true");
		System.out.println("Fullsceen off: ./bascene.jar <width> <height> false");
		config.width = 720;
		config.height = 480;
		config.fullscreen = false;
		if (args.length>0) {
			try {
				config.width = Integer.parseInt(args[0], 10);
			}
			catch (final NumberFormatException e) {
				e.printStackTrace();
			}
		}
		if (args.length>1) {
			try {
				config.height = Integer.parseInt(args[1], 10);
			}
			catch (final NumberFormatException e) {
				e.printStackTrace();
			}
		}
		if (args.length>2) {
			config.fullscreen = Boolean.parseBoolean(args[2]);
		}
		new LwjglApplication(new MyGdxGame(), config);
	}
}
