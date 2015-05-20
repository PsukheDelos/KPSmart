package kps.distribution.event;

import kps.distribution.network.MailDelivery;

public class DeliveryEventResult extends EventResult{
	public final MailDelivery mailDelivery;

	public DeliveryEventResult(MailDelivery mailDelivery) {
		this.mailDelivery = mailDelivery;
	}

}
