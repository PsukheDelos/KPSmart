package kps.frontend;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.swing.JOptionPane;

import kps.backend.UserPermissions;
import kps.backend.database.MailRepository;
import kps.backend.users.User;
import kps.distribution.event.CustomerPriceEvent;
import kps.distribution.event.CustomerPriceEventResult;
import kps.distribution.event.DeliveryEventResult;
import kps.distribution.event.LocationEventResult;
import kps.distribution.event.MailDeliveryEvent;
import kps.distribution.event.MailDeliveryEventResult;
import kps.distribution.event.TransportCostEvent;
import kps.distribution.event.TransportCostEventResult;
import kps.frontend.gui.ClientFrame;
import kps.interfaces.IMailClient;
import kps.net.client.Client;
import kps.net.event.Event;
import kps.net.event.LoginResponseEvent;
import kps.net.event.NewUserResultEvent;
import kps.net.event.RemoveUserResultEvent;
import kps.net.event.UserAuthenticationEvent;
import kps.net.event.XMLReplyEvent;

public class MailClient implements IMailClient{

	private User currentUser;
	private Client client;
	private ClientFrame clientFrame;

	private Map<UUID, Event> awaitingResponse;

	public MailClient(ClientFrame clientFrame){
		client = new Client("127.0.0.1", this);
		this.clientFrame = clientFrame;

		awaitingResponse = new HashMap<UUID, Event>();

	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public void authenticateUser(String username, String password) {
		client.sendEvent(new UserAuthenticationEvent(username, password));
	}

	public void update(){
		client.update();
	}

	public void processEvent(Event e) {
		if(e instanceof LoginResponseEvent){
			LoginResponseEvent evt = (LoginResponseEvent)e;

			System.out.println(this + "Response Received for " + evt.user.username);
			setCurrentUser(evt.user);
			
			clientFrame.setUserPermissions(evt.user.permissions);

		}
		else if(e instanceof NewUserResultEvent){
			if(((NewUserResultEvent) e).successful)
				JOptionPane.showMessageDialog(clientFrame, "User Added Successfully");
			else
				JOptionPane.showMessageDialog(clientFrame, "Error Adding User");
			clientFrame.updateUsers();
		}
		else if(e instanceof RemoveUserResultEvent){
			JOptionPane.showMessageDialog(clientFrame, "User Removed Successfully");
			clientFrame.updateUsers();
		}
		else if(e instanceof XMLReplyEvent){
			clientFrame.updateXML(((XMLReplyEvent)e).tableModel);
		}
		else if(e instanceof MailDeliveryEventResult){
			if(awaitingResponse.containsKey(((MailDeliveryEventResult) e).id)){
				MailRepository.add(((MailDeliveryEventResult)e).day, ((MailDeliveryEventResult)e).origin, ((MailDeliveryEventResult)e).destination, ((MailDeliveryEventResult)e).weight, ((MailDeliveryEventResult)e).volume, ((MailDeliveryEventResult)e).priority, ((MailDeliveryEventResult)e).price, ((MailDeliveryEventResult)e).cost, ((MailDeliveryEventResult)e).time);
			}
		}
		else if(e instanceof CustomerPriceEventResult){
			clientFrame.updateOrigin();
			clientFrame.updatePrices();
		}else if(e instanceof TransportCostEventResult){
			clientFrame.updateRoutes();
		}
		else if(e instanceof  LocationEventResult){
			clientFrame.updateLocations();
		}

	}

	public void sendEvent(Event e) {
		// Here we will add an UUID to any Event you want
		// For now, I'll add it to the Mail Delivery Event
		// Look in MailSystem for the response method.
		if(e instanceof MailDeliveryEvent){
			awaitingResponse.put(((MailDeliveryEvent) e).id, (MailDeliveryEvent)e);
		}else if(e instanceof CustomerPriceEvent){
			awaitingResponse.put(((CustomerPriceEvent) e).id, (CustomerPriceEvent)e);
		}
		else if(e instanceof TransportCostEvent){
			awaitingResponse.put(((TransportCostEvent) e).id, (TransportCostEvent)e);
		}

		client.sendEvent(e);
	}

	public String toString(){
		return "[MAIL CLIENT] ";
	}

}
