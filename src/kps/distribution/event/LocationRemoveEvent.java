package kps.distribution.event;

public class LocationRemoveEvent extends LocationEvent{
	private static final long serialVersionUID = 1L;

	public LocationRemoveEvent(String location) {
		super(location, 0, 0, "remove");
	}

}
