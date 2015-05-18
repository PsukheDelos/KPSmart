package kps.frontend.gui;

import java.awt.Dimension;

import javax.swing.JPanel;


public class DashboardPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	public static final int CLIENT_WIDTH = 1200;
	public static final int CLIENT_HEIGHT = 700;
	
	public DashboardPanel(){
		setPreferredSize(new Dimension(CLIENT_WIDTH, CLIENT_HEIGHT));
		
		setVisible(true);

	}

	private void generateChart(){
	}
	

}
