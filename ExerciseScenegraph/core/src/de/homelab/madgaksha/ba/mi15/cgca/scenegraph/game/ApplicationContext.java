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
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.ANode;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.ActionQueue;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeCamera;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.NodeController;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.object.World;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.visitor.ICommonNodeAction;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.visitor.TraversalVisitor;

public class ApplicationContext extends ApplicationAdapter {
	private SpriteBatch batch;
	private ModelBatch modelBatch;
	private ParticleEffect stars;
	private NodeController rootNode;
	private Camera defaultCamera;
	private float time, deltaTime;
	private TraversalVisitor<RuntimeException> traversalVisitor;
	private ICommonNodeAction<RuntimeException> renderAction;
	private ICommonNodeAction<RuntimeException> updateAction;
	private ActionQueue nodeActionQueue;
	private ResourceManager resourceManager;
	private NodeCamera cameraNode;
	private final Vector3 cameraInWorldCoordinates = new Vector3();

	private static ApplicationContext currentInstance;

	public ApplicationContext() {
		currentInstance = this;
	}

	@Override
	public void create () {
		defaultCamera = new OrthographicCamera(1600, 1066);
		batch = new SpriteBatch();
		modelBatch = new ModelBatch();
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

		if (cameraNode != null) cameraNode.inWorldCoordinates(cameraInWorldCoordinates);
		else cameraInWorldCoordinates.setZero();
		rootNode.accept(traversalVisitor, updateAction);
		stars.update(deltaTime);

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (cameraNode != null)
			cameraNode.updateBatch(batch);
		else {
			defaultCamera.update();
			batch.setProjectionMatrix(defaultCamera.combined);
		}
		batch.begin();
		rootNode.accept(traversalVisitor, renderAction);
		drawStars();
		batch.end();
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) Gdx.app.exit();
	}

	private void drawStars() {
		batch.setTransformMatrix(batch.getTransformMatrix().idt().translate(cameraNode.getCamera().position));
		stars.draw(batch);
		if (stars.isComplete()) {
			stars.reset();
			stars.start();
		}
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
				node.updateAction();
			}
		};
	}

	private ICommonNodeAction<RuntimeException> makeRenderAction() {
		return new ICommonNodeAction<RuntimeException>() {
			@Override
			public void visit(final ANode node) throws RuntimeException {
				node.renderAction();
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

	public ModelBatch getModelBatch() {
		return modelBatch;
	}

	public void setCameraNode(final NodeCamera cameraNode) {
		if (cameraNode != null)
			this.cameraNode = cameraNode;
	}

	public Vector3 cameraInWorldCoordinates() {
		return cameraInWorldCoordinates;
	}

	public Matrix4 getCameraView() {
		return (cameraNode != null ? cameraNode.getCamera() : defaultCamera).view;
	}

	public Vector3 getCameraPosition() {
		return (cameraNode != null ? cameraNode.getCamera() : defaultCamera).position;
	}

	public void smoothSwitchCameraNode(final NodeCamera to) {
		if (cameraNode != null && to != cameraNode) {
			to.smoothSwitch(cameraNode);
		}
		setCameraNode(to);
	}
}
