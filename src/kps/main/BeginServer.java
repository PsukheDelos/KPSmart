package kps.main;

import kps.net.server.Server;

public class BeginServer {

	public static void main(String[] args){
		Server server = new Server();
		server.start();
		
	}
}
