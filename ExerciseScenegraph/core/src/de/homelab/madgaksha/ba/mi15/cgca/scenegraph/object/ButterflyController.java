package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.object;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;

import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.CmnCnst;
import de.homelab.madgaksha.ba.mi15.cgca.scenegraph.game.Controller;

public class ButterflyController implements Controller{
	private final Butterfly butterfly;
	private ButterflyAnimationPack oldAnimationMode = null;
	private ButterflyAnimationPack animationMode = ButterflyAnimationPack.FLYING;
	private float catchTime, catchTime2, catchDy, catchY;
	private Sound flap;
	private long flapId;
	private boolean renderPriorityChanged = false;

	public ButterflyController(final Butterfly butterfly) {
		if (butterfly == null) throw new IllegalArgumentException(CmnCnst.Error.NULL_BUTTERFLY);
		this.butterfly = butterfly;
	}
	@Override
	public void update(final float time, final float deltaTime) {
		if (oldAnimationMode != animationMode) {
			if (oldAnimationMode != null) oldAnimationMode.end(butterfly, time, deltaTime);
			if (animationMode != null) animationMode.begin(butterfly, time, deltaTime);
			oldAnimationMode = animationMode;
		}

		switch (animationMode) {
		case FLYING:
			animationMode.animate(butterfly, time, deltaTime);
			break;
		case CATCH:
			animationMode.animate(butterfly, time, deltaTime);
			catchTime += deltaTime;
			catchTime2 += deltaTime;
			if (catchTime2>0.8f) {
				final float skew = 80f*(-800f-catchY)/800f;
				catchDy = MathUtils.random(-80f+skew,80f+skew);
				catchY += catchDy;
				butterfly.translate(MathUtils.randomBoolean() ? MathUtils.random(-200f,-50) : MathUtils.random(50f,200f), catchDy);
				catchTime2 = 0f;
			}
			if (catchTime > 10f) {
				if (!renderPriorityChanged) {
					renderPriorityChanged  = true;
					butterfly.setTraversalPriority(5);
				}
				butterfly.scale(0.996f);
			}
			if (flap != null && catchTime > 15f) {
				flap.stop(flapId);
			}
			if (catchTime > 35f) {
				butterfly.detach();
			}
			break;
		default:
			break;

		}
	}

	public void caught() {
		catchTime = 0f;
		catchTime2 = 0f;
		catchY = catchDy = 0f;
		animationMode = ButterflyAnimationPack.CATCH;
		butterfly.ac().getResourceManager().sound(String.format(CmnCnst.Files.SOUND_OBTAIN_BUTTERFLY, MathUtils.random(1, 6))).play();
		flap = butterfly.ac().getResourceManager().sound(CmnCnst.Files.SOUND_FLAP);
		flapId = flap.play();
		flap.setLooping(flapId, true);
	}
}
