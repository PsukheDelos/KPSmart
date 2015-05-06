package kps.net.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;

import kps.net.event.Event;

public class WorkerThread extends Thread{
	
	private volatile Map<Integer, ServerToClientConnection> connections;
	private Server server;
	
	public WorkerThread(Server server){
		connections = new HashMap<Integer, ServerToClientConnection>();
		this.server = server;
		start();
	}
	
	public void start(){
		System.out.println(this + "Starting Worker Thread");
		super.start();
	}
	
	public void run(){
		while(true){
			
			for(Map.Entry<Integer, ServerToClientConnection> entry : connections.entrySet()){
				ObjectInputStream in = null;
				try {
					in = new ObjectInputStream(entry.getValue().socket.getInputStream());
				} catch (IOException e) {
					e.printStackTrace();
				}
				Object obj = null;
				try{
					obj = in.readObject();
					if(!(obj instanceof Event)) continue;
					else server.addEvent(new Update(entry.getKey(), (Event) obj));
				}catch(ClassNotFoundException | IOException ex){
					ex.printStackTrace();
				}
			}
			
			
		}
	}
	
	public void addConnection(int id, ServerToClientConnection connection){
		System.out.println(this + "Connection Added to the Worker Thread: " + connection.socket.getInetAddress());
		connections.put(id, connection);
	}

	public String toString(){
		return "[WORKER THREAD] ";
	}
	
}
