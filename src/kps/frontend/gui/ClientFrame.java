package kps.frontend.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Properties;

import javax.swing.*;

import kps.backend.database.LocationRepository;
import kps.frontend.MailClient;

import com.bbn.openmap.LayerHandler;
import com.bbn.openmap.MapBean;
import com.bbn.openmap.MapHandler;
import com.bbn.openmap.MouseDelegator;
import com.bbn.openmap.event.OMMouseMode;
import com.bbn.openmap.gui.EmbeddedNavPanel;
import com.bbn.openmap.gui.EmbeddedScaleDisplayPanel;
import com.bbn.openmap.gui.MapPanel;
import com.bbn.openmap.gui.OverlayMapPanel;
import com.bbn.openmap.gui.ToolPanel;
import com.bbn.openmap.layer.shape.ShapeLayer;

public class ClientFrame extends JFrame{
	
	private static final long serialVersionUID = 1L;
	public static final int CLIENT_WIDTH = 1200;
	public static final int CLIENT_HEIGHT = 700;

	private MailClient client;

//	private ClientListener listener = new ClientListener();

	public ClientFrame(){
		super("--// KPSmart Mail System (Version 0.1) ");
		setPreferredSize(new Dimension(CLIENT_WIDTH, CLIENT_HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		client = new MailClient();
		initialise();

		pack();
		setLocationRelativeTo(null);

		setVisible(true);

//		 We should check if a user is logged in (Most likely not, but a check is gooood.
		if(client.getCurrentUser() == null){
			setEnabled(false);
			new ClientLoginFrame(client, this);
		}
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

		//Don't think we need carriers, leaving just in case we want later
		//		icon = createImageIcon("img/carrier-icon.png");
		//		JComponent panel4 = makeTextPanel("View a list of KPSmart carriers—add, update or delete them to your hearts content!");
		//		tabbedPane.addTab("Carriers", icon, panel4,
		//				"View a list of KPSmart carriers—add, update or delete them to your hearts content!");
		//		tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);

		icon = createImageIcon("img/price-icon.png");
		JComponent panel5 = makeTextPanel("Charge the customers exorbitant amounts using our friendly UI.");
		tabbedPane.addTab("Prices", icon, panel5,
				"Charge the customers exorbitant amounts using our friendly UI.");
		tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);

		// Create a MapBean
        MapPanel mapPanel = new OverlayMapPanel();

        // Get the default MapHandler the BasicMapPanel created.
        MapHandler mapHandler = mapPanel.getMapHandler();
        mapHandler.add(new LayerHandler());
        // Add navigation tools over the map
        mapHandler.add(new EmbeddedNavPanel());
        // Add scale display widget over the map
        mapHandler.add(new EmbeddedScaleDisplayPanel());
        // Add MouseDelegator, which handles mouse modes (managing mouse events)
        mapHandler.add(new MouseDelegator());
        // Add OMMouseMode, which handles how the map reacts to mouse movements
        mapHandler.add(new OMMouseMode());
        // Add a ToolPanel for widgets on the north side of the map.
        mapHandler.add(new ToolPanel());
        
        // Get the default MapBean that the BasicMapPanel created.
        MapBean mapBean = mapPanel.getMapBean();

		mapBean.setCenter(LocationRepository.getCity("Wellington").lat, LocationRepository.getCity("Wellington").lon);
//        mapBean.setScale(10000000f);  //good level for just NZ

        // Create a ShapeLayer to show world political boundaries.
		ShapeLayer shapeLayer = new ShapeLayer();
		Properties shapeLayerProps = new Properties();
		shapeLayerProps.put("prettyName", "Political Solid");
		shapeLayerProps.put("lineColor", "000000");
		shapeLayerProps.put("fillColor", "BDDE83");
		shapeLayerProps.put("shapeFile", "data"+File.separator+"shape"+File.separator+"dcwpo-browse.shp");
		shapeLayerProps.put("spatialIndex", "data"+File.separator+"shape"+File.separator+"dcwpo-browse.ssx");
		shapeLayer.setProperties(shapeLayerProps);		
		
		// Add the political layer to the map
		mapBean.add(shapeLayer);
				
		icon = createImageIcon("img/map-icon.png");
		tabbedPane.addTab("Locations", icon, (Component) mapPanel,
				"View a map of all our locations!");
		tabbedPane.setMnemonicAt(4, KeyEvent.VK_5);

		this.add(tabbedPane);
	}

	/** Returns an ImageIcon, or null if the path was invalid. */
	protected static ImageIcon createImageIcon(String path) {
		path.replace("/", File.separator);
		java.net.URL imgURL = ClientFrame.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(((new ImageIcon(imgURL)).getImage()).getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH));
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
