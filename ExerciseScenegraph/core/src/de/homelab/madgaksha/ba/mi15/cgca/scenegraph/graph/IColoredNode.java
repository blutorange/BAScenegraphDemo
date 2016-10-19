package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;

public interface IColoredNode {
	public Color getColor();
	public Color getCascadedColor();
	public ANode getParent();

	default IColoredNode randomHue() {
		return randomHue(MathUtils.random(0.5f, 1f), 1f);
	}

	default IColoredNode randomHue(final float saturation, final float brightness) {
		return setHsb(MathUtils.random(0f, 360f), saturation, brightness);
	}

	default IColoredNode setHsb(final float hue, final float saturation, final float brightness) {
		final int c = java.awt.Color.HSBtoRGB(hue, saturation, brightness);
		setColor(((c & 0xFF0000) >> 16) / 255f, ((c & 0x00FF00) >> 8) / 255f, (c & 0x0000FF) / 255f);
		return this;
	}

	default IColoredNode setColor(final Color color) {
		getColor().set(color);
		return this;
	}

	default IColoredNode setColor(final float r, final float g, final float b) {
		getColor().set(r,g,b,1.0f);
		return this;
	}

	default IColoredNode setAlpha(final float alpha) {
		getColor().a = alpha;
		return this;
	}

	default void cascadeColor() {
		getCascadedColor().set(getParent() != null ? getParent().getCascadedColor() : Color.WHITE).mul(getColor());
	}
}
