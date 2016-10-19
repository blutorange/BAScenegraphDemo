package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.object;

import com.badlogic.gdx.math.MathUtils;

import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.Controller;

public class PoemController implements Controller {

	private final static float alphaTime = 3f;
	private final float maxAlpha;
	private final Poem poem;
	private final float lifeTime;
	private final float speedX;
	private final float amplitude;
	private final float frequency;
	private float relTime;

	public PoemController(final Poem poem) {
		this.poem = poem;
		this.relTime = 0f;
		lifeTime = alphaTime+MathUtils.random(10f, 40f);
		speedX = MathUtils.randomSign()*MathUtils.random(40f,100f);
		maxAlpha = MathUtils.random(0.1f,0.7f);
		amplitude = MathUtils.random(50f, 200f);
		frequency = MathUtils.random(10f, 60f);
	}

	@Override
	public void update(final float time, final float deltaTime) {
		relTime += deltaTime;

		float alpha = maxAlpha * (0.5f+0.25f*(1f+MathUtils.sinDeg(relTime*57f)));
		final float translate = amplitude*MathUtils.sinDeg(relTime*frequency);
		poem.tText.reset().translate(relTime*speedX, translate);

		if (relTime < alphaTime) {
			alpha *=  relTime/alphaTime;
		}
		else if (relTime < lifeTime) {

		}
		else if (relTime < lifeTime+alphaTime) {
			alpha *= (1f-(relTime-lifeTime)/alphaTime);
		}
		else poem.detach();

		poem.text.setAlpha(alpha);
	}

}
