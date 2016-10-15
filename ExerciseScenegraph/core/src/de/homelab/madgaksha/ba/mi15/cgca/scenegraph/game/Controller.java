package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph.ANode;

public class Controller {
	public static enum Direction {
		RIGHT,
		LEFT,
	}
	public static enum MovementState {
		STANDING,
		WALKING,
		JUMPING,
		DASHING,
		CROUCHING;
	}
	private  final int left, right, down, up, jump, modifier, crouch;
	private float speedX;
	private float speedY;
	private final float gravityY;
	private float jumpY, beginY;
	private MovementState state = MovementState.STANDING;
	private Direction direction = Direction.RIGHT;
	private Controller(final float gravityY, final float speedX, final float speedY, final int left, final int right, final int down, final int up, final int jump, final int modifier, final int crouch) {
		this.left = left;
		this.right = right;
		this.down = down;
		this.up = up;
		this.speedX = speedX;
		this.speedY = speedY;
		this.jump = jump;
		this.gravityY = gravityY;
		this.modifier = modifier;
		this.crouch = crouch;
	}

	public MovementState getMovementState() {
		return state;
	}
	public void control(final ANode node) {
		final float dx = Gdx.input.isKeyPressed(left) ? -1f : Gdx.input.isKeyPressed(right) ? 1f : 0f;
		final boolean mod = Gdx.input.isKeyPressed(modifier);
		final boolean isCrouched = Gdx.input.isKeyPressed(crouch);
		if (Gdx.input.isKeyJustPressed(jump) && state != MovementState.JUMPING) {
			state = MovementState.JUMPING;
			jumpY = speedY;
			beginY = 0f;
		}
		if (state == MovementState.JUMPING) {
			jumpY -= gravityY;
			beginY += jumpY;
			node.translate(speedX*dx, jumpY);
			if (beginY <= -50f) {
				state = dx != 0f ? (mod ? MovementState.DASHING : MovementState.WALKING) : (isCrouched ? MovementState.CROUCHING : MovementState.STANDING);
				node.translate(dx, -beginY);
			}
		}
		else {
			state = dx != 0f ? (mod ? MovementState.DASHING : MovementState.WALKING) : (isCrouched ? MovementState.CROUCHING : MovementState.STANDING);
		}
		node.translate(speedX*dx*(mod ? 5f : 1f), 0);
		direction = dx < 0f ? Direction.LEFT : dx > 0f ? Direction.RIGHT : direction;

	}

	public Controller speed(final float speedX, final float speedY) {
		this.speedX = speedX;
		this.speedY = speedY;
		return this;
	}

	public final static class Builder {
		int left = Keys.LEFT, right = Keys.RIGHT, down = Keys.DOWN, up = Keys.UP, jump = Keys.ENTER, modifier = Keys.SHIFT_RIGHT, crouch = Keys.DOWN;
		float speedX;
		float speedY;
		float gravityY = 0.2f;

		public Builder() {}
		public Builder speed(final float speedX, final float speedY) {
			this.speedX = speedX;
			this.speedY = speedY;
			return this;
		}
		public Builder left(final int left) {
			this.left = left;
			return this;
		}
		public Builder right(final int right) {
			this.right = right;
			return this;
		}
		public Builder up(final int up) {
			this.up = up;
			return this;
		}
		public Builder down(final int down) {
			this.down = down;
			return this;
		}
		public Builder jump(final int jump) {
			this.jump = jump;
			return this;
		}
		public Builder gravity(final float gravity) {
			this.gravityY = gravity;
			return this;
		}
		public Builder modifier(final int modifier) {
			this.modifier = modifier;
			return this;
		}
		public Builder crouch(final int crouch) {
			this.crouch = crouch;
			return this;
		}
		public Controller build() {
			return new Controller(gravityY, speedX, speedY, left, right, down, up, jump, modifier, crouch);
		}
	}

	public Direction getDirection() {
		return direction;
	}
}
