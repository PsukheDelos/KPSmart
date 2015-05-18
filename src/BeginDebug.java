import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import com.alee.laf.WebLookAndFeel;

import kps.frontend.gui.ClientFrame;
import kps.net.server.Server;


public class BeginDebug {
	
	public static void main(String[] args){
		WebLookAndFeel.install();
		
		// Server
		Server server = new Server();
		server.start();
		
		ClientFrame client = new ClientFrame();
		
	}
	
}
