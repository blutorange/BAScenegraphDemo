package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector3;

import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.ApplicationContext;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.visitor.INodeVisitor;

public class NodeCamera extends ANode {

	private final OrthographicCamera camera;
	private final Vector3 worldCoordinates = new Vector3();

	public NodeCamera(final int w, final int h, final ApplicationContext ac) {
		super(Type.CAMERA, ac);
		camera = new OrthographicCamera(w, h);
	}

	@Override
	public <R, T, E extends Throwable> R accept(final INodeVisitor<R, T, E> visitor, final T data) throws E {
		return visitor.visit(this, data);
	}

	@Override
	public void updateAction() {
		super.inWorldCoordinates(worldCoordinates, 0f, 0f);
		if (camera.position.x != worldCoordinates.x || camera.position.y != worldCoordinates.y || camera.position.z != worldCoordinates.z) {
			camera.position.lerp(worldCoordinates, 0.05f);
			camera.update();
		}
	}

	public void smoothSwitch(final NodeCamera from) {
		camera.position.set(from.camera.position);
	}

	@Override
	public void renderAction() {
	}

	@Override
	public Vector3 inWorldCoordinates(final Vector3 result, final float x, final float y) {
		return result.set(worldCoordinates);
	}

	public void updateBatch(final Batch batch) {
		batch.setProjectionMatrix(camera.combined);
	}

	public Camera getCamera() {
		return camera;
	}
}
