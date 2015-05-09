package kps.frontend.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;

import javax.swing.*;

import kps.frontend.MailClient;

public class ClientFrame extends JFrame{
	
	public static final int CLIENT_WIDTH = 1200;
	public static final int CLIENT_HEIGHT = 700;
	
	private MailClient client;
	
	private ClientListener listener = new ClientListener();
	
	public ClientFrame(){
		super("--// KPSmart Mail System (Version 0.1) ");
		setPreferredSize(new Dimension(CLIENT_WIDTH, CLIENT_HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		client = new MailClient();
		
		initialise();
		
		pack();
		setLocationRelativeTo(null);
		
		
		setVisible(true);
		
		
	}
	
	
	
	private void initialise() {
		// TODO Auto-generated method stub
		createTabbedPane();
	}



	public static void main(String[] args){
		new ClientFrame();
	}
	
	protected void createTabbedPane(){
		JTabbedPane tabbedPane = new JTabbedPane();
		ImageIcon icon = createImageIcon("img/tab-128.png");
		
		JComponent panel1 = makeTextPanel("Panel #1");
		tabbedPane.addTab("Monthly Overview", icon, panel1,
		                  "Does nothing");
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

		JComponent panel2 = makeTextPanel("Panel #2");
		tabbedPane.addTab("Revenue & Expenditures", icon, panel2,
		                  "Does twice as much nothing");
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

		JComponent panel3 = makeTextPanel("Panel #3");
		tabbedPane.addTab("Events", icon, panel3,
		                  "Still does nothing");
		tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);

		JComponent panel4 = makeTextPanel(
		        "Panel #4 (has a preferred size of 410 x 50).");
		panel4.setPreferredSize(new Dimension(410, 50));
		tabbedPane.addTab("Routes", icon, panel4,
		                      "Does nothing at all");
		tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);
		
		this.add(tabbedPane);
	}
	
    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = ClientFrame.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
    
    protected JComponent makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        return panel;
    }

}
