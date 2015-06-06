package kps.distribution.event;

public class TransportCostRemoveEvent extends TransportCostEvent {
	private static final long serialVersionUID = 1L;

	public TransportCostRemoveEvent(String company, String from, String to, String type) {
		
		super(company, from, to, type, 0, 0, 0, 0, 0, 0, "", "remove");
		
	}
}
