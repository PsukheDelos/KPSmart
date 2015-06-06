package kps.distribution.event;

public class TransportCostUpdateEvent extends TransportCostEvent {
	private static final long serialVersionUID = 1L;

	public TransportCostUpdateEvent(String company, String to, String from,
			String type, double weightCost, double volumeCost,
			double maxWeight, double maxVolume, double duration,
			double frequency, String day, String action) {
		
		super(company, to, from, type, weightCost, volumeCost, maxWeight, maxVolume,
				duration, frequency, day, "add");
	}
}
