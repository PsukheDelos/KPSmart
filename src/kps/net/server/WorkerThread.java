package kps.net.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Map;

import kps.net.event.Event;

public class WorkerThread extends Thread{
	
	private int id;
	private Socket socket;
	private Server server;
	
	public WorkerThread(Server server, int id, Socket socket){
		this.socket = socket;
		this.id = id;
		this.server = server;
		System.out.println(this + " Client Worker ["+ id + "] on ip " + socket.getInetAddress());
		// Then we should start the thread
		// We'll also set this as Daemon so we don't have any messy leftovers
		setDaemon(true);
		start();
	}
	
	public void start(){
		System.out.println(this + "Starting Worker Thread");
		super.start();
	}
	
	public void run(){
			Object obj = null;
			ObjectInputStream in = null;
			try{
				in = new ObjectInputStream(socket.getInputStream());
			}catch(IOException ex){
				ex.printStackTrace();
			}
			while(true){
				try {
					obj = in.readObject();
				} catch (ClassNotFoundException | IOException e) {
					//e.printStackTrace();
					System.exit(1);
				}
				
				if(!(obj instanceof Event)) continue;
				else server.addEvent(new Update(id, (Event) obj));
		}
	}

	public String toString(){
		return "[WORKER THREAD] ";
	}
	
}
