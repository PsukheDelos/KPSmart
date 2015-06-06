package kps.distribution.event;

public class LocationUpdateEvent extends LocationEvent{
	private static final long serialVersionUID = 1L;

	public LocationUpdateEvent(String location, double longtitude,
			double latitude) {
		super(location, longtitude, latitude, "update");
	}

}
