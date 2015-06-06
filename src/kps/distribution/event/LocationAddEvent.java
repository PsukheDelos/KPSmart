package kps.distribution.event;

public class LocationAddEvent extends LocationEvent{
	private static final long serialVersionUID = 1L;

	public LocationAddEvent(String location, double longtitude,
			double latitude) {
		super(location, longtitude, latitude, "add");
	}

}
