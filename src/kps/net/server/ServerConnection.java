package kps.net.server;

import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerConnection {
	
	public final Socket socket;
	public final ObjectOutputStream out;
	
	public ServerConnection(Socket socket, ObjectOutputStream out){
		this.socket = socket;
		this.out = out;
	}

}
