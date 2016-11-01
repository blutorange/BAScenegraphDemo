package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph;

import com.badlogic.gdx.math.Vector2;

public interface IOriginedNode {
	public Vector2 getOrigin();

	/**
	 * Setzt den Koordinatenursprung, der z.B. für Rotation etc. verwendet wird.
	 * Erlaubte Werte liegen im Intervall <code>[0,1]</code>. Dabei entspricht
	 * <code>(x,y) = (0,0)</code> der linken unteren Ecke, <code>(x,y) = (1,1)</code>
	 * der rechten oberen Ecke.
	 * @param originX Koordinatenursprung x-Wert.
	 * @param originY Koordinatenursprung x-Wert.
	 * @return Diesen Knoten zum Verketten.
	 */
	default IOriginedNode setOrigin(final float originX, final float originY) {
		getOrigin().set(originX, originY);
		return this;
	}

	default float getOriginX() {
		return getOrigin().x;
	}

	default float getOriginY() {
		return getOrigin().y;
	}
}