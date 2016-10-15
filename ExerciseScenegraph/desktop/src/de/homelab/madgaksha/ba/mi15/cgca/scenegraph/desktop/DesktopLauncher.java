package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.MyGdxGame;

public class DesktopLauncher {
	public static void main (final String[] arg) {
		final LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 720;
		config.height = 480;
		config.fullscreen=false;
		new LwjglApplication(new MyGdxGame(), config);
	}
}
