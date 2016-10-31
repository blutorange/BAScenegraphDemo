package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.ApplicationContext;

public class DesktopLauncher {
	public static void main (final String[] args) {
		final LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		System.out.println("Fullsceen on: ./bascene.jar <width> <height> <fps> true"); //$NON-NLS-1$
		System.out.println("Fullsceen off: ./bascene.jar <width> <height> <fps> false"); //$NON-NLS-1$
		config.width = 720;
		config.height = 480;
		config.foregroundFPS = 60;
		config.fullscreen = false;
		if (args.length>0) {
			try {
				config.width = Integer.parseInt(args[0], 10);
			}
			catch (final NumberFormatException e) {
				System.out.println("invalid width"); //$NON-NLS-1$
				e.printStackTrace();
			}
		}
		if (args.length>1) {
			try {
				config.height = Integer.parseInt(args[1], 10);
			}
			catch (final NumberFormatException e) {
				System.out.println("invalid height"); //$NON-NLS-1$
				e.printStackTrace();
			}
		}
		if (args.length>2) {
			try {
				config.foregroundFPS = Integer.parseInt(args[2], 10);
			}
			catch (final NumberFormatException e) {
				System.out.println("invalid fps"); //$NON-NLS-1$
				e.printStackTrace();
			}
		}
		if (args.length>3) {
			config.fullscreen = Boolean.parseBoolean(args[3]);
		}
		final LwjglApplication app = new LwjglApplication(new ApplicationContext(), config);
		System.out.println(String.format("Started app %s.", app)); //$NON-NLS-1$
	}
}
