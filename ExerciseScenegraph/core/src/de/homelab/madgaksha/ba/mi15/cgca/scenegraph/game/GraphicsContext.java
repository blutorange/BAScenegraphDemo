package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game;

import java.io.IOException;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;

import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.WorldNode;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.ANode;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeController;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.visitor.ICommonNodeAction;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.visitor.TraversalVisitor;

public class GraphicsContext extends ApplicationAdapter {
	private static final String OBJECT_PACKAGE = "de.homelab.madgaksha.ba.mi15.cgca.scenegraph.object";
	private final Matrix4 oldTransform = new Matrix4();
	private SpriteBatch batch;
	private ParticleEffect stars;
	private NodeController rootNode;
	private Camera camera;
	private float time, deltaTime;
	private TraversalVisitor<RuntimeException> traversalVisitor;
	private ICommonNodeAction<RuntimeException> renderAction;
	private ICommonNodeAction<RuntimeException> updateAction;

	@Override
	public void create () {
		camera = new OrthographicCamera(1600, 1066);
		batch = new SpriteBatch();
		Resource.init();
		stars = Resource.particleEffect("stars.eff");
		stars.setPosition(-800, 500);
		setRootNode();
		rootNode.printDebug();
		traversalVisitor = new TraversalVisitor<RuntimeException>();
		renderAction = makeRenderAction();
		updateAction = makeUpdateAction();
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
			if (!NodeController.class.isAssignableFrom(clazz)) throw new GdxRuntimeException("Weltknoten muss vom Typ NodeUnit sein.");
			rootNode = (NodeController)clazz.newInstance();
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
		deltaTime = Gdx.graphics.getRawDeltaTime();
		time += deltaTime;

		rootNode.accept(traversalVisitor, updateAction);

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		oldTransform.set(batch.getTransformMatrix());
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		rootNode.accept(traversalVisitor, renderAction);
		batch.setTransformMatrix(oldTransform);
		stars.draw(batch, deltaTime);
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

	private ICommonNodeAction<RuntimeException> makeUpdateAction() {
		return new ICommonNodeAction<RuntimeException>() {
			@Override
			public void visit(final ANode node) throws RuntimeException {
				node.updateAction(GraphicsContext.this);
			}
		};
	}

	private ICommonNodeAction<RuntimeException> makeRenderAction() {
		return new ICommonNodeAction<RuntimeException>() {
			@Override
			public void visit(final ANode node) throws RuntimeException {
				node.renderAction(GraphicsContext.this);
			}
		};
	}

	public float getTime() {
		return time;
	}

	public float getDeltaTime() {
		return deltaTime;
	}

	public Batch getBatch() {
		return batch;
	}
}
