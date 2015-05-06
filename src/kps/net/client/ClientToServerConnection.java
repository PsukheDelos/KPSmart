package kps.net.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import kps.net.event.Event;
import kps.net.server.Server;

public class ClientToServerConnection extends Thread{
	
	private Socket socket;
	private LinkedBlockingQueue<Event> events = new LinkedBlockingQueue<Event>();

	private ObjectOutputStream output;
	private ObjectInputStream input;
	
	public ClientToServerConnection(String ip) {
		try{
			connectToServer(ip);
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}
	
	private void connectToServer(String addr) throws IOException{
		InetAddress ip = InetAddress.getByName(addr);
		this.socket = new Socket(ip, Server.PORT_NUMBER);
		output = new ObjectOutputStream(socket.getOutputStream());
		input = new ObjectInputStream(socket.getInputStream());
	}
	
	public void send(Event event){
		try {
			output.writeObject(event);
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<Event> getUpdatesToBeActioned(){
		int items = events.size();
		List<Event> ret = new ArrayList<Event>(items);
		events.drainTo(ret, items);
		return ret;
	}
	
	public void run(){
		while (true){
			Event currentEvent = nextEvent();
			if(currentEvent == null) continue;
			events.offer(currentEvent);
		}			
	}
		
	public Event nextEvent(){
		Object in = null;
		try {
			in = input.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// If the connection is lost, we should return null. If we have no connection whatsoever, we should shut down the connection.
			return null;
		}
		
		if(!(in instanceof Event)){
			System.out.println(this + "[ERROR] Client Recieved Error that is not an event!");
			return null;
		}
		return (Event) in;
	}
	
	
	
	

}
