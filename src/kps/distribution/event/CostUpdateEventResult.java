package kps.distribution.event;

import kps.distribution.network.Route;

public class CostUpdateEventResult extends EventResult {

	public final Route route;

	public CostUpdateEventResult(Route route) {
		this.route = route;
	}

}
