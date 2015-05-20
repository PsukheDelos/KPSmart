package kps.distribution.event;

import kps.distribution.network.Route;

public class CostuUpdateEventResult extends EventResult {

	public final Route route;

	public CostuUpdateEventResult(Route route) {
		this.route = route;
	}

}
