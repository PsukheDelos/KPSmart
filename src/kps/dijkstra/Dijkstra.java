package kps.dijkstra;

import java.util.ArrayList;
import java.util.List;

import kps.distributionNetwork.Mail;
import kps.distributionNetwork.MailDelivery;
import kps.distributionNetwork.Route;

public class Dijkstra {
	private Mail mail;
	
	public Dijkstra(Mail mail){
		this.mail = mail;
	}
	
	public MailDelivery getPath(){
		float cost = 0;
		List<Route> path = new ArrayList<Route>();
		
		
		
		return new MailDelivery(mail, cost, path);
	}
}
