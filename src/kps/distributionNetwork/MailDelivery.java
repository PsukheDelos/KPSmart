package kps.distributionNetwork;

import java.util.List;

public class MailDelivery {
	public final Mail mail;
	public final float cost;
	public final List<Route> path;
	
	public MailDelivery(Mail mail, float cost, List<Route> path){
		this.mail = mail;
		this.cost = cost;
		this.path = path;
	}
	
	public Mail getMail(){
		return this.mail;
	}
}
