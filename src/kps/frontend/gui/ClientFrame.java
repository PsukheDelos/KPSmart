package kps.frontend.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Properties;

import javax.swing.*;

import kps.backend.database.LocationRepository;
import kps.distribution.network.Location;
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
import com.bbn.openmap.layer.daynight.DayNightLayer;
import com.bbn.openmap.layer.learn.BasicLayer;
import com.bbn.openmap.layer.location.BasicLocation;
import com.bbn.openmap.layer.shape.BufferedShapeLayer;
import com.bbn.openmap.layer.shape.ShapeLayer;
import com.bbn.openmap.omGraphics.OMGraphic;
import com.bbn.openmap.omGraphics.OMGraphicConstants;
import com.bbn.openmap.omGraphics.OMGraphicList;
import com.bbn.openmap.omGraphics.OMLine;
import com.bbn.openmap.omGraphics.OMPoint;
import com.bbn.openmap.omGraphics.OMTextLabeler;
import com.bbn.openmap.proj.coords.LatLonPoint;

public class ClientFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	public static final int CLIENT_WIDTH = 1200;
	public static final int CLIENT_HEIGHT = 700;

	private MailClient client;

	private ClientListener listener = new ClientListener();

	public ClientFrame(){
		super("--// KPSmart Mail System (Version 0.1) //--");
		setPreferredSize(new Dimension(CLIENT_WIDTH, CLIENT_HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		client = new MailClient();
		initialise();

		pack();
		setLocationRelativeTo(null);

		setVisible(true);

		//		We should check if a user is logged in (Most likely not, but a check is gooood.
		if(client.getCurrentUser() == null){
			setEnabled(false);
			new ClientLoginFrame(client, this);
		}
	}

	public static void main(String[] args){
		new ClientFrame();
	}

	private void initialise() {
		createTabbedPane();
	}

	protected void createTabbedPane(){
		JTabbedPane tabbedPane = new JTabbedPane();
		ImageIcon icon = createImageIcon("img/dash-icon.png");
		JComponent panel1 = makeTextPanel("Welcome to the dashboard, here you can view the current financial status of KPSmart.");
		tabbedPane.addTab("Dashboard", icon, new DashboardPanel(),
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

		icon = createImageIcon("img/price-icon.png");
		JComponent panel5 = makeTextPanel("Charge the customers exorbitant amounts using our friendly UI.");
		tabbedPane.addTab("Prices", icon, panel5,
				"Charge the customers exorbitant amounts using our friendly UI.");
		tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);

		/*
		 * The BasicMapPanel automatically creates many default components,
		 * including the MapBean and the MapHandler. You can extend the
		 * BasicMapPanel class if you like to add different functionality or
		 * different types of objects.
		 */
		MapPanel mapPanel = new OverlayMapPanel();

		LatLonPoint wellingtonLocation = new LatLonPoint.Double(LocationRepository.getCity("Wellington").lat, LocationRepository.getCity("Wellington").lon);

		// Get the default MapHandler the BasicMapPanel created.
		MapHandler mapHandler = mapPanel.getMapHandler();

		// Get the default MapBean that the BasicMapPanel created.
		MapBean mapBean = mapPanel.getMapBean();

		// Set the map's center
		mapBean.setCenter(wellingtonLocation);

		// Set the map's scale 1:120 million
		mapBean.setScale(120000000f);

		/*
		 * Create and add a LayerHandler to the MapHandler. The LayerHandler
		 * manages Layers, whether they are part of the map or not.
		 * layer.setVisible(true) will add it to the map. The LayerHandler
		 * has methods to do this, too. The LayerHandler will find the
		 * MapBean in the MapHandler.
		 */
		mapHandler.add(new LayerHandler());
		// Add navigation tools over the map
		mapHandler.add(new EmbeddedNavPanel());
		// Add scale display widget over the map
		mapHandler.add(new EmbeddedScaleDisplayPanel());
		// Add MouseDelegator, which handles mouse modes (managing mouse
		// events)
		mapHandler.add(new MouseDelegator());
		// Add OMMouseMode, which handles how the map reacts to mouse
		// movements
		mapHandler.add(new OMMouseMode());
		// Add a ToolPanel for widgets on the north side of the map.
		mapHandler.add(new ToolPanel());

		/*
		 * Create a ShapeLayer to show world political boundaries. Set the
		 * properties of the layer. This assumes that the datafile
		 * "cntry02.shp" is in a path specified in the CLASSPATH variable.
		 * These files are distributed with OpenMap and reside in the
		 * top level "share" sub-directory.
		 */
		ShapeLayer shapeLayer = new BufferedShapeLayer();

		// Since this Properties object is being used just for
		// this layer, the properties do not have to be scoped
		// with marker name.
		Properties shapeLayerProps = new Properties();
		shapeLayerProps.put("prettyName", "Political Solid");
		shapeLayerProps.put("lineColor", "000000");
		shapeLayerProps.put("fillColor", "BDDE83");
		shapeLayerProps.put("shapeFile", "data/shape/cntry02/cntry02.shp");
		shapeLayer.setProperties(shapeLayerProps);
		shapeLayer.setVisible(true);

		// Last on top.
		mapHandler.add(shapeLayer);
		BasicLayer basicLayer = new BasicLayer();

		OMGraphicList omList = new OMGraphicList();

		OMGraphicList cityList = new OMGraphicList();
		OMGraphicList routeList = new OMGraphicList();
		for(Location city: LocationRepository.getLocations()){
			OMPoint point = new OMPoint(city.lat, city.lon, 3);
			point.setFillPaint(Color.yellow);
			point.setOval(true);
			BasicLocation basicLocation = new BasicLocation(city.lat, city.lon, city.name, point);
<<<<<<< HEAD
=======
			if(mapBean.getScale()<120000000f){
				System.err.println("HEY");
			}
>>>>>>> master
			basicLocation.setShowName(false);

			// Add an OMLine
			OMLine line = new OMLine(wellingtonLocation.getLatitude(), wellingtonLocation.getLongitude(), city.lat, city.lon, OMGraphic.LINETYPE_GREATCIRCLE);
			// line.addArrowHead(true);
			line.setStroke(new BasicStroke(2));
			line.setLinePaint(Color.red);
			line.putAttribute(OMGraphicConstants.LABEL, new OMTextLabeler("Line Label"));

			routeList.add(line);
			cityList.add(basicLocation);
		}
		omList.add(cityList);
		omList.add(routeList);
		basicLayer.setList(omList);
		mapHandler.add(basicLayer);

		//Optional: Do we want this?
		//mapHandler.add(new DayNightLayer());

		// Create Map tab
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
