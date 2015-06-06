package kps.distribution.event;

import java.util.UUID;

import kps.distribution.network.MailDelivery;

public class DeliveryEventResult extends EventResult{
	private static final long serialVersionUID = 1L;

	public final MailDelivery mailDelivery;

	public UUID id;
	
	public DeliveryEventResult(MailDelivery mailDelivery) {
		this.mailDelivery = mailDelivery;
	}

}
