package kps.frontend.gui;

import java.awt.Dimension;

import javax.swing.JFrame;

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
		
		pack();
		setLocationRelativeTo(null);
		
		
		setVisible(true);
		
		
	}
	
	
	
	public static void main(String[] args){
		new ClientFrame();
	}
	

}
