import kps.frontend.gui.ClientFrame;
import kps.net.server.Server;


public class BeginDebug {

	public static void main(String[] args){
		Server server = new Server();
		server.start();
		
		new ClientFrame();
		
		
	}
	
}
