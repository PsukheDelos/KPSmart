package kps.frontend;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.swing.JOptionPane;

import kps.backend.UserPermissions;
import kps.backend.users.User;
import kps.distribution.event.CustomerPriceEvent;
import kps.distribution.event.CustomerPriceEventResult;
import kps.distribution.event.DeliveryEventResult;
import kps.distribution.event.MailDeliveryEvent;
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

			System.out.println(this + "Response Recieved for " + evt.user.username);
			setCurrentUser(evt.user);
			
			clientFrame.setUserPermissions(evt.user.permissions);
			
//			if(evt.user.permissions == UserPermissions.CLERK){
//				System.out.println(evt.user.permissions);
//				
//				clientFrame.createManagerTab(clientFrame.getTabbedPane());
//				clientFrame.createDashboardTab(clientFrame.getTabbedPane());
//			}
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
		else if(e instanceof DeliveryEventResult){
			System.out.println("Received Return for DeliveryEventResult");
			if(awaitingResponse.containsKey(((DeliveryEventResult) e).id)){
				System.out.println("DeliveryEventResult Success!");
			}
			// Then you know the key is in there, and since it is stored with the corresponding Event, you know what you sent.
			// Maybe pass this to a method somewhere, and make sure that you remove it from the map once done. Just to avoid collisions.
			System.err.println(this + "" + ((DeliveryEventResult)e).mailDelivery.cost);
		}else if(e instanceof CustomerPriceEventResult){
			//			System.out.println("Recieved Return for PriceUpdateEvent" + ((PriceUpdateEventResult)e).id);
			//			if(awaitingResponse.containsKey(((CustomerPriceEventResult)e).id)){
			clientFrame.updateOrigin();
			clientFrame.updatePrices();
			//			}
		}else if(e instanceof TransportCostEventResult){
			System.out.println("mailclient: processevent: transportcosteventresult");
			clientFrame.updateRoutes();
		}

	}

	public void sendEvent(Event e) {
		// Here we will add an UUID to any Event you want
		// For now, I'll add it to the Mail Delivery Event
		// Look in MailSystem for the response method.
		if(e instanceof MailDeliveryEvent){
			System.out.println("Adding Awaiting Delivery for id: " + ((MailDeliveryEvent)e).id);
			awaitingResponse.put(((MailDeliveryEvent) e).id, (MailDeliveryEvent)e);
		}else if(e instanceof CustomerPriceEvent){
			System.out.println("Adding Price Update Event awaiting return: " + ((CustomerPriceEvent)e).id);
			awaitingResponse.put(((CustomerPriceEvent) e).id, (CustomerPriceEvent)e);
		}
		else if(e instanceof TransportCostEvent){
			System.out.println("Adding Transport Update Event awaiting return: " + ((TransportCostEvent)e).id);
			awaitingResponse.put(((TransportCostEvent) e).id, (TransportCostEvent)e);
		}
		System.out.println("MailClient: sendEvent: ");

		client.sendEvent(e);
	}

	public String toString(){
		return "[MAIL CLIENT] ";
	}

}
