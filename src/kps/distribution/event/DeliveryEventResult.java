package kps.distribution.event;

import java.util.UUID;

import kps.distribution.network.MailDelivery;

public class DeliveryEventResult extends EventResult{
	public final MailDelivery mailDelivery;

	public UUID id;
	
	public DeliveryEventResult(MailDelivery mailDelivery) {
		this.mailDelivery = mailDelivery;
	}

}
