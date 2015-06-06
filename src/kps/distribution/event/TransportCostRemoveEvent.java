package kps.distribution.event;

public class TransportCostRemoveEvent extends TransportCostEvent {
	private static final long serialVersionUID = 1L;

	public TransportCostRemoveEvent(String company, String to, String from, String type) {
		
		super(company, to, from, type, 0, 0, 0, 0, 0, 0, "", "remove");
	}
}
