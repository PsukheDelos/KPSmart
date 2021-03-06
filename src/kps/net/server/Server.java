package kps.net.server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import kps.backend.MailSystem;
import kps.distribution.event.MailDeliveryEvent;
import kps.interfaces.IMailSystem;
import kps.net.event.Event;

public class Server extends Thread{

	public static final int PORT_NUMBER = 45632;
	
	private ServerSocket serverSocket;
	private IMailSystem mailSystem;
	
	private UpdateThread updateThread;
	
	private boolean isRunning = true;
	
	private Map<Integer, ServerToClientConnection> connections;
	private int globalID = 0;
	
	private BlockingQueue<Update> updates = new LinkedBlockingQueue<Update>();
	
	public Server(IMailSystem mailSystem){
		
		// The Server holds the MailSystem as it's acting as the adapter the Client will see.
		this.mailSystem = mailSystem;
		try {
			serverSocket = new ServerSocket();
			serverSocket.bind(new InetSocketAddress(PORT_NUMBER));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		connections = new HashMap<Integer, ServerToClientConnection>();
	}
	
	public void start(){
		System.out.println(this + "Server Started!");
		super.start();
	}
		
	public void run(){
		
		try{
			int id = 0;
			updateThread = new UpdateThread(this, mailSystem);
			while(isRunning){
				Socket socket = serverSocket.accept();
				ServerToClientConnection newConnection = new ServerToClientConnection(socket, new ObjectOutputStream(socket.getOutputStream()));
				connections.put(id, newConnection);
				WorkerThread workerThread = new WorkerThread(this, id, socket);
				
				// You could setup initialisations here.
				// I don't think we should on this end, and instead we should probably put them on the Client Side
				// Such as a UserAuthenticationEvent should be sent through the WorkerThread
				// Talk to Jack if you have thoughts otherwise.
				
				System.out.println(this + "Client Added: [" + id + "] on address " + newConnection.socket.getInetAddress());
				id++;
			}
		}catch(IOException ex){
			ex.printStackTrace();
		}
		
		
	}
	
	public void shutdown() throws IOException{
		this.isRunning = false;
		serverSocket.close();
		// TODO: Handle the Shutdown HERE;
	}
	
	private ServerToClientConnection getServerConnection(Socket client){
		for(ServerToClientConnection sc : connections.values()){
			if(sc.socket.equals(client)) return sc;
		}
		return null;
	}
	
	public ServerToClientConnection getConnection(int id){
		return connections.get(id);
	}
	
	private boolean isConnected(Socket socket){
		for(ServerToClientConnection sc : connections.values()){
			if(sc.socket.equals(socket)) return true;
		}
		return false;
	}
	
	public void addEvent(Update event){
		updates.add(event);
	}
	
	public Update popEvent(){
		try { return updates.take(); } catch (InterruptedException e) { e.printStackTrace();}
		// Shouldn't reach here.
		return null;
	}
	
	public Event processEvent(Event event){
		return mailSystem.processEvent(event);
	}
	
	
	public String toString(){
		return "[Server: " + serverSocket.getInetAddress().getHostAddress() + ":"+ serverSocket.getLocalPort() + "] "; 
	}

	public Map<Integer, ServerToClientConnection> getClients() {
		return connections;
	}

	public void removeConnection(ServerToClientConnection player) {
		for(Map.Entry<Integer, ServerToClientConnection> e : connections.entrySet()){
			if(player.equals(e.getValue()))
				connections.remove(e.getKey());
		}
	}
	
	
	
}
