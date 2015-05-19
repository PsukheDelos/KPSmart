package kps.distribution.network;

import java.util.List;

public class MailDelivery {
	public final Mail mail;
	public final double cost;
	public final double time;
	public final List<Route> path;

	public MailDelivery(Mail mail, double cost, double time, List<Route> path){
		this.mail = mail;
		this.cost = cost;
		this.time = time;
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
