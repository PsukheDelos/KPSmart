package kps.net.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import kps.backend.Event;
import kps.backend.MailSystem;

public class Server extends Thread{

	public static final int PORT_NUMBER = 45632;
	
	private ServerSocket serverSocket;
	private MailSystem mailSystem;
	
	private Map<Integer, ServerConnection> connections;
	private int globalID = 0;
	
	private BlockingQueue<Event> updates = new LinkedBlockingQueue<Event>();
	
	public Server(){
		
		// The Server holds the MailSystem as it's acting as the adapter the Client will see.
		mailSystem = new MailSystem();
		try {
			serverSocket = new ServerSocket();
			serverSocket.bind(new InetSocketAddress(PORT_NUMBER));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		connections = new HashMap<Integer, ServerConnection>();
	}
	
	public void start(){
		System.out.println(this + "Server Started!");
		super.start();
	}
	
	public void run(){
		int id = 0;		
		while(true){
			try {			
				Socket client = serverSocket.accept();
				if(isConnected(client))
					processEvent(client);
				else
					addNewConnection(client);
			} catch (IOException | ClassNotFoundException e) { e.printStackTrace(); }
		}
	}
	
	private void processEvent(Socket client) throws IOException, ClassNotFoundException{
		ServerConnection clientConnection = getServerConnection(client);
		ObjectInputStream in = new ObjectInputStream(clientConnection.socket.getInputStream());
		Object obj = in.readObject();
		if(!(obj instanceof Event)) return;
		else mailSystem.processEvent((Event) obj);
	}
	
	private void addNewConnection(Socket client) throws IOException{
		connections.put(globalID++, new ServerConnection(client, new ObjectOutputStream(client.getOutputStream())));
	}
	
	private ServerConnection getServerConnection(Socket client){
		for(ServerConnection sc : connections.values()){
			if(sc.socket.equals(client)) return sc;
		}
		return null;
	}
	
	private boolean isConnected(Socket socket){
		for(ServerConnection sc : connections.values()){
			if(sc.socket.equals(socket)) return true;
		}
		return false;
	}
	
	
	public String toString(){
		return "[Server: " + serverSocket.getLocalPort() + "] "; 
	}
	
	
	
}
