package kps.distribution.event;

public class TransportCostAddEvent extends TransportCostEvent {
	private static final long serialVersionUID = 1L;

	public TransportCostAddEvent(String company, String from, String to,
			String type, double weightCost, double volumeCost,
			double maxWeight, double maxVolume, double duration,
			double frequency, String day) {
		
		super(company, from, to, type, weightCost, volumeCost, maxWeight, maxVolume,
				duration, frequency, day, "add");
	}
}
