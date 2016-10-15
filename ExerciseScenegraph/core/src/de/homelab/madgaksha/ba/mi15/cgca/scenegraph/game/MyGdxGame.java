package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game;

import java.io.IOException;
import java.util.Iterator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;

import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.WorldNode;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.ANode;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeUnit;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.PrioritizedNode;

public class MyGdxGame extends ApplicationAdapter {
	private static final String OBJECT_PACKAGE = "de.homelab.madgaksha.ba.mi15.cgca.scenegraph.object";
	private final Matrix4 oldTransform = new Matrix4();
	private SpriteBatch batch;
	private ParticleEffect stars;
	private NodeUnit rootNode;
	private float time;
	private Camera camera;

	@Override
	public void create () {
		camera = new OrthographicCamera(1600, 1066);
		batch = new SpriteBatch();
		Resource.init();
		stars = Resource.particleEffect("stars.eff");
		stars.setPosition(-800, 500);
		setRootNode();
		DisplayMode best = null;
		int bestPix = 0;
		for (final DisplayMode dm : Gdx.graphics.getDisplayModes()) {
			if (dm.width*dm.height > bestPix) {
				bestPix = dm.width*dm.height;
				best = dm;
			}
		}
		//if (best != null) Gdx.graphics.setFullscreenMode(best);
	}

	private void setRootNode() {
		try {
			Class<?> clazz = null;
			for (final ClassInfo info : ClassPath.from(getClass().getClassLoader()).getTopLevelClasses(OBJECT_PACKAGE)) {
				final Class<?> tmp = info.load();
				if (tmp.getDeclaredAnnotation(WorldNode.class) != null) {
					if (clazz != null) throw new GdxRuntimeException(String.format("Es kann nur einen Weltenknoten geben, aber sowohl %s als auch %s sind welche.", clazz.getSimpleName(), tmp.getSimpleName()));
					clazz = tmp;
				}
			}
			if (clazz == null) throw new GdxRuntimeException("Keinen Weltknoten gefunden.");
			if (!NodeUnit.class.isAssignableFrom(clazz)) throw new GdxRuntimeException("Weltknoten muss vom Typ NodeUnit sein.");
			rootNode = (NodeUnit)clazz.newInstance();
		}
		catch (final IOException e) {
			e.printStackTrace();
			throw new GdxRuntimeException("Fehler beim Laden des Welknoten.");
		}
		catch (final InstantiationException e) {
			e.printStackTrace();
			throw new GdxRuntimeException("Fehler beim Laden des Welknoten.");
		}
		catch (final IllegalAccessException e) {
			e.printStackTrace();
			throw new GdxRuntimeException("Fehler beim Laden des Welknoten.");
		}
	}

	@Override
	public void render () {
		final float dt = Gdx.graphics.getRawDeltaTime();
		time += dt;
		rootNode.updateThisAndChildren(time, dt);
		rootNode.cascadeTransform(new Matrix4().idt());

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		oldTransform.set(batch.getTransformMatrix());
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		renderNode(rootNode, Color.WHITE);
		batch.setTransformMatrix(oldTransform);
		stars.draw(batch, dt);
		if (stars.isComplete()) {
			stars.reset();
			stars.start();
		}
		batch.end();

		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) Gdx.app.exit();
	}

	@Override
	public void dispose () {
		batch.dispose();
		Resource.dispose();
	}

	private void renderNode(final ANode node, final Color color) {
		batch.setTransformMatrix(node.getCascadedTransform());
		final Color newColor = new Color(color);
		if (node.getColor() != null) newColor.mul(node.getColor());
		node.draw(batch, newColor);
		for (final Iterator<PrioritizedNode> it = node.sortedIterator(); it.hasNext();) {
			renderNode(it.next().node, newColor);
		}
	}
}
