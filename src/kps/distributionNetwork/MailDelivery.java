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
	
	public String toString(){
		if (path.isEmpty()) return "No delivery path";
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < path.size(); i++){
			Route r = path.get(i);
			sb.append(i + ". " + r.getOrigin() + " to " + r.getDestination()
					    + " via " + r.getType() + " with " + r.getCompany());
		}
		return sb.toString();
	}
}
