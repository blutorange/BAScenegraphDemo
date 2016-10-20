package de.homelab.madgaksha.ba.mi15.cgca.scenegraph.graph;

import java.util.ArrayList;
import java.util.List;

public class ActionQueue {
	private final static List<INodeAction> queue = new ArrayList<>();
	public void addAction(final INodeAction action) {
		queue.add(action);
	}
	public void perform() {
		for (final INodeAction action : queue)
			action.perform();
		queue.clear();
	}
}
