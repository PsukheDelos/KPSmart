package kps.net.server;

import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerToClientConnection {
	
	public final Socket socket;
	public final ObjectOutputStream out;
	
	public ServerToClientConnection(Socket socket, ObjectOutputStream out){
		this.socket = socket;
		this.out = out;
	}
	
	public boolean equals(Object o){
		if(!(o instanceof ServerToClientConnection)) return false;
		ServerToClientConnection other = (ServerToClientConnection)o;
		// Put our faith into the Java Overlords.
		return other.socket.equals(socket) && other.out.equals(out);
	}

}
