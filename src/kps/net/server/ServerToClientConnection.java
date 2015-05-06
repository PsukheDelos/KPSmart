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

}
