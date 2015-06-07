package kps.distribution.event;

import java.io.Serializable;
import java.util.UUID;

import kps.distribution.network.MailDelivery;

public class DeliveryEventResult extends EventResult implements Serializable{

	public final MailDelivery mailDelivery;
	
	public DeliveryEventResult(MailDelivery mailDelivery) {
		this.mailDelivery = mailDelivery;
	}

}
