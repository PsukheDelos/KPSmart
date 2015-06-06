package kps.distribution.event;

import kps.distribution.network.Route;

public class TransportCostEventResult extends EventResult {
	private static final long serialVersionUID = 1L;
	
	public final Route route;

	public TransportCostEventResult(Route route) {
		this.route = route;
	}

}
