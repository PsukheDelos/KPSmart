package kps.main;

import kps.backend.MailSystem;
import kps.net.server.Server;

public class BeginServer {

	public static void main(String[] args){
		Server server = new Server(new MailSystem());
		server.start();
	}
}
