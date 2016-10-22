package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;

import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.ANode;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.ActionQueue;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeController;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.object.World;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.visitor.ICommonNodeAction;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.visitor.TraversalVisitor;

public class ApplicationContext extends ApplicationAdapter {
	private final Matrix4 oldTransform = new Matrix4();
	private SpriteBatch batch;
	private ParticleEffect stars;
	private NodeController rootNode;
	private Camera camera;
	private float time, deltaTime;
	private TraversalVisitor<RuntimeException> traversalVisitor;
	private ICommonNodeAction<RuntimeException> renderAction;
	private ICommonNodeAction<RuntimeException> updateAction;
	private ActionQueue nodeActionQueue;
	private ResourceManager resourceManager;

	private static ApplicationContext currentInstance;

	public ApplicationContext() {
		currentInstance = this;
	}

	@Override
	public void create () {
		camera = new OrthographicCamera(1600, 1066);
		batch = new SpriteBatch();
		nodeActionQueue = new ActionQueue();
		resourceManager = new ResourceManager();
		stars = resourceManager.particleEffect("stars.eff");
		stars.setPosition(-800, 500);
		setRootNode();
		rootNode.printDebug();
		traversalVisitor = new TraversalVisitor<RuntimeException>();
		renderAction = makeRenderAction();
		updateAction = makeUpdateAction();
	}

	private void setRootNode() {
		rootNode = new World(this);
	}

	@Override
	public void render () {
		deltaTime = MathUtils.clamp(Gdx.graphics.getRawDeltaTime(), 0.001f, 0.2f);
		time += deltaTime;

		nodeActionQueue.perform();

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
		resourceManager.dispose();
	}

	private ICommonNodeAction<RuntimeException> makeUpdateAction() {
		return new ICommonNodeAction<RuntimeException>() {
			@Override
			public void visit(final ANode node) throws RuntimeException {
				node.updateAction(ApplicationContext.this);
			}
		};
	}

	private ICommonNodeAction<RuntimeException> makeRenderAction() {
		return new ICommonNodeAction<RuntimeException>() {
			@Override
			public void visit(final ANode node) throws RuntimeException {
				node.renderAction(ApplicationContext.this);
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

	public ActionQueue getNodeActionQueue() {
		return nodeActionQueue;
	}

	public ResourceManager getResourceManager() {
		return resourceManager;
	}

	public static ApplicationContext getInstance() {
		return currentInstance;
	}
}
