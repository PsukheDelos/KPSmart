package kps.frontend.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Properties;

import javafx.scene.transform.Scale;

import javax.swing.*;

import kps.frontend.MailClient;
import kps.frontend.gui.map.MapBean;
import kps.frontend.gui.map.layer.shape.ShapeLayer;

public class ClientFrame extends JFrame{
	
	private static final long serialVersionUID = 1L;
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
		createTabbedPane();
	}



	public static void main(String[] args){
		new ClientFrame();
	}
	
	protected void createTabbedPane(){
		JTabbedPane tabbedPane = new JTabbedPane();
		
		ImageIcon icon = createImageIcon("img/dash-icon.png");
		JComponent panel1 = makeTextPanel("Welcome to the dashboard, here you can view the current financial status of KPSmart.");
		tabbedPane.addTab("Dashboard", icon, panel1,
		                  "Does  here you can view the current financial status of KPSmart");
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

		icon = createImageIcon("img/mail-icon.png");
		JComponent panel2 = makeTextPanel("New Mail Delivery");
		tabbedPane.addTab("Mail Delivery", icon, panel2,
		                  "New Mail Delivery");
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

		icon = createImageIcon("img/route-icon.png");
		JComponent panel3 = makeTextPanel("Here you can update and add new routes between ports.");
		tabbedPane.addTab("Routes", icon, panel3,
		                  "Here you can update and add new routes between ports.");
		tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);

		icon = createImageIcon("img/carrier-icon.png");
		JComponent panel4 = makeTextPanel("View a list of KPSmart carriers—add, update or delete them to your hearts content!");
		tabbedPane.addTab("Carriers", icon, panel4,
		                  "View a list of KPSmart carriers—add, update or delete them to your hearts content!");
		tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);
		
		icon = createImageIcon("img/price-icon.png");
		JComponent panel5 = makeTextPanel("Charge the customers exorbitant amounts using our friendly UI.");
		tabbedPane.addTab("Prices", icon, panel5,
		                  "Charge the customers exorbitant amounts using our friendly UI.");
		tabbedPane.setMnemonicAt(4, KeyEvent.VK_5);
		

        // Create a Swing frame
        JFrame frame = new JFrame("Simple Map");

        // Size the frame appropriately
        frame.setSize(640, 480);

        // Create a MapBean
        MapBean mapBean = new MapBean();

        // Create a ShapeLayer to show world political boundaries.
        // Set the properties of the layer. This assumes that the
        // datafiles "dcwpo-browse.shp" and "dcwpo-browse.ssx" are in
        // a path specified in the CLASSPATH variable. These files
        // are distributed with OpenMap and reside in the toplevel
        // "share" subdirectory.
        ShapeLayer shapeLayer = new ShapeLayer();
        Properties shapeLayerProps = new Properties();
        shapeLayerProps.put("prettyName", "Political Solid");
        shapeLayerProps.put("lineColor", "000000");
        shapeLayerProps.put("fillColor", "BDDE83");
        shapeLayerProps.put("shapeFile", "data/shape/dcwpo-browse.shp");
        shapeLayerProps.put("spatialIndex", "data/shape/dcwpo-browse.ssx");
        shapeLayer.setProperties(shapeLayerProps);

        // Add the political layer to the map
        mapBean.add(shapeLayer);
		
        icon = createImageIcon("img/map-icon.png");
		JComponent panel6 = makeTextPanel("View a map of all our locations!");
		panel6.add(mapBean);
		tabbedPane.addTab("Locations", icon, mapBean,
		                  "View a map of all our locations!");
		tabbedPane.setMnemonicAt(5, KeyEvent.VK_6);
		
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
