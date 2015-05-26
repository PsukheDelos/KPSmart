package kps.frontend.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Properties;

import javax.swing.UIManager.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import kps.backend.database.LocationRepository;
import kps.backend.database.PriceRepository;
import kps.backend.database.RouteRepository;
import kps.distribution.network.Location;
import kps.frontend.MailClient;
import kps.net.server.Server;

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
import com.bbn.openmap.layer.learn.BasicLayer;
import com.bbn.openmap.layer.location.BasicLocation;
import com.bbn.openmap.layer.policy.BufferedImageRenderPolicy;
import com.bbn.openmap.layer.shape.BufferedShapeLayer;
import com.bbn.openmap.layer.shape.ShapeLayer;
import com.bbn.openmap.omGraphics.OMGraphic;
import com.bbn.openmap.omGraphics.OMGraphicList;
import com.bbn.openmap.omGraphics.OMLine;
import com.bbn.openmap.omGraphics.OMPoint;
import com.bbn.openmap.proj.coords.LatLonPoint;

public class ClientFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	public static final int CLIENT_WIDTH = 1200;
	public static final int CLIENT_HEIGHT = 700;

	private Double entered_weight = (double) 0;
	private Double entered_volume = (double) 0;
	private Double total_price = (double) 0;

	private MailClient client;

	private ClientListener listener = new ClientListener(); //What is this for?

	public static void main(String[] args){
		//put for quicker launch
//		Server server = new Server();
//		server.start();
		new ClientFrame();
	}

	public ClientFrame() {
		super("--// KPSmart Mail System (Version 0.1) //--");
		setPreferredSize(new Dimension(CLIENT_WIDTH, CLIENT_HEIGHT));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				int answer = JOptionPane.showConfirmDialog(null, "You want to quit?", "Quit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (answer == JOptionPane.YES_OPTION)
					System.exit(0);
			}
		});

		UIManager.put("nimbusBase", Color.decode("#FFCC00"));
		UIManager.put("nimbusBlueGrey", Color.white);
		UIManager.put("control", Color.decode("#b8dbfe"));
		//this.setBackground(Color.decode("#b8dbfe"));
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			// If Nimbus is not available, you can set the GUI to another look and feel.
		}



		client = new MailClient();
		initialise();

		pack();
		setLocationRelativeTo(null);
		setVisible(true);

		// We should check if a user is logged in (Most likely not, but a check is gooood.
		if(client.getCurrentUser() == null){
			setEnabled(false);
			ClientLoginFrame frame = new ClientLoginFrame(client, this);
			frame.revalidate();
			// This is kept below as a backup, just in case
			//new ClientLoginPane(client, this);
		}
	}

	private void initialise() {
		createTabbedPane();
	}

	protected void createTabbedPane(){

		JTabbedPane tabbedPane = new JTabbedPane();

		createDashboardTab(tabbedPane);
		createMailTab(tabbedPane);
		createRouteTab(tabbedPane);
		createPriceTab(tabbedPane);
		createMapTab(tabbedPane);

		this.add(tabbedPane);
	}

	/**
	 * @param tabbedPane
	 */
	private void createMapTab(JTabbedPane tabbedPane) {
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

		mapBean.setBackgroundColor(new Color((float)0.255, (float)0.412, (float)0.882));

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
		shapeLayerProps.put("fillColor", "4ECD21");
		shapeLayerProps.put("shapeFile", "data/shape/cntry02/cntry02.shp");
		shapeLayerProps.put("lineWidth", 0);
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
			point.setStroke(new BasicStroke(0));
			point.setOval(true);
			BasicLocation basicLocation = new BasicLocation(city.lat, city.lon, city.name, point);
			basicLocation.setShowName(false);

			// Add an OMLine
			OMLine line = new OMLine(wellingtonLocation.getLatitude(), wellingtonLocation.getLongitude(), city.lat, city.lon, OMGraphic.LINETYPE_GREATCIRCLE);

			// line.addArrowHead(true);
			line.setStroke(new BasicStroke(0.5f));
			line.setLinePaint(Color.red);
			//			line.putAttribute(OMGraphicConstants.LABEL, new OMTextLabeler("Line Label"));

			routeList.add(line);
			cityList.add(basicLocation);
		}
		omList.add(cityList);
		omList.add(routeList);
		basicLayer.setRenderPolicy(new BufferedImageRenderPolicy());
		basicLayer.setList(omList);
		mapHandler.add(basicLayer);

		// Create Map tab

		JLabel label = new JLabel("Locations");
		label.setHorizontalTextPosition(JLabel.TRAILING); // Set the text position regarding its icon
		label.setIcon(createImageIcon("img/map-icon.png"));
		tabbedPane.addTab("Locations", null, (Component) mapPanel,"Here you can update and add new prices.");
		tabbedPane.setTabComponentAt(4, label);
		tabbedPane.setMnemonicAt(4, KeyEvent.VK_5);
	}

	/**
	 * @param tabbedPane
	 */
	private void createPriceTab(JTabbedPane tabbedPane) {
		JLabel label = new JLabel("Prices");
		label.setHorizontalTextPosition(JLabel.TRAILING); // Set the text position regarding its icon
		label.setIcon(createImageIcon("img/price-icon.png"));
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		tabbedPane.addTab("Prices", null, panel,"View and Edit the current prices of KPSmart");
		tabbedPane.setTabComponentAt(3, label);
		tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);

		//Title: Prices
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 0;

		JLabel priceTitle = new JLabel("Prices", SwingConstants.LEFT);
		priceTitle.setFont(new Font(priceTitle.getFont().getFontName(), Font.PLAIN, 30));
		priceTitle.setForeground(Color.decode("#fffe9a"));

		panel.add(priceTitle,c);

		//Button: Add Price
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		JButton addPrice = new JButton();
		addPrice.setText("+");
		panel.add(addPrice,c);

		//Button: Edit Price
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		JButton editPrice = new JButton();
		editPrice.setText("Edit");
		panel.add(editPrice,c);

		//Button: Add Price
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 1;
		c.gridwidth = 1;
		JButton removePrice = new JButton();
		removePrice.setText("-");
		panel.add(removePrice,c);

		//Table: Price Table
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 4;
		JTable jt = PriceRepository.getPricesTable();
		jt.setPreferredScrollableViewportSize(new Dimension(700, 300));
		jt.setFillsViewportHeight(true);
		panel.add(new JScrollPane(jt),c);
	}

	/**
	 * @param tabbedPane
	 */
	private void createRouteTab(JTabbedPane tabbedPane) {
//		JLabel label = new JLabel("Routes");
//		label.setHorizontalTextPosition(JLabel.TRAILING); // Set the text position regarding its icon
//		label.setIcon(createImageIcon("img/route-icon.png"));
//		JComponent panel2 = makeTextPanel("Here you can update and add new routes between ports.");
//		tabbedPane.addTab("Routes", null, panel2,"Here you can update and add new routes between ports.");
//		tabbedPane.setTabComponentAt(2, label);
//		tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
		JLabel label = new JLabel("Routes");
		label.setHorizontalTextPosition(JLabel.TRAILING); // Set the text position regarding its icon
		label.setIcon(createImageIcon("img/route-icon.png"));
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		tabbedPane.addTab("Routes", null, panel,"View and Edit the current Routes of KPSmart");
		tabbedPane.setTabComponentAt(2, label);
		tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);

		//Title: Routes
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 0;

		JLabel title = new JLabel("Routes", SwingConstants.LEFT);
		title.setFont(new Font(title.getFont().getFontName(), Font.PLAIN, 30));
		title.setForeground(Color.decode("#fffe9a"));

		panel.add(title,c);

		//Button: Add Route
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		JButton addPrice = new JButton();
		addPrice.setText("+");
		panel.add(addPrice,c);

		//Button: Edit Price
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		JButton editPrice = new JButton();
		editPrice.setText("Edit");
		panel.add(editPrice,c);

		//Button: Add Price
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 1;
		c.gridwidth = 1;
		JButton removePrice = new JButton();
		removePrice.setText("-");
		panel.add(removePrice,c);

		//Table: Price Table
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 4;
		JTable jt = RouteRepository.getRoutesTable();
		jt.setPreferredScrollableViewportSize(new Dimension(700, 300));
		jt.setFillsViewportHeight(true);
		panel.add(new JScrollPane(jt),c);
	}

	/**
	 * @param tabbedPane
	 */
	private void createMailTab(JTabbedPane tabbedPane) {
		JLabel label = new JLabel("Mail Delivery");
		label.setHorizontalTextPosition(JLabel.TRAILING); // Set the text position regarding its icon
		label.setIcon(createImageIcon("img/mail-icon.png"));
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		tabbedPane.addTab("Mail Delivery", null, panel,"New Mail Delivery");
		tabbedPane.setTabComponentAt(1, label);
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		JLabel title = new JLabel("New Mail Event", SwingConstants.LEFT);
		title.setFont(new Font(title.getFont().getFontName(), Font.PLAIN, 30));
		title.setForeground(Color.decode("#fffe9a"));
		panel.add(title,c);	
		//

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		JLabel source = new JLabel("Source: ",SwingConstants.RIGHT);
		panel.add(source,c);	

		JComboBox<String> fromDropDown = new JComboBox<String>();
		for (String fromCity : PriceRepository.getFromCities()){
			fromDropDown.addItem(fromCity);
		}
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 1;
		panel.add(fromDropDown,c);



		//To Cities Drop Down
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		JLabel dest = new JLabel("Destination: ",SwingConstants.RIGHT);
		panel.add(dest,c);	

		JComboBox<String> toDropDown = new JComboBox<String>();
		//		for (String toCity : PriceRepository.getToCities(PriceRepository.getFromCities().get(0))){
		//			toDropDown.addItem(toCity);
		//		}
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 2;
		panel.add(toDropDown,c);

		fromDropDown.addActionListener(new ActionListener() {//add actionlistner to listen for change
			@Override
			public void actionPerformed(ActionEvent e) {
				String s = (String) fromDropDown.getSelectedItem();//get the selected item
				toDropDown.removeAllItems();
				for (String toCity : PriceRepository.getToCities(s)){
					toDropDown.addItem(toCity);
				}
			}
		});

		fromDropDown.setSelectedItem(PriceRepository.getFromCities().get(0));

		//Priority Drop Down
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 3;
		JLabel priority = new JLabel("Priority: ",SwingConstants.RIGHT);
		panel.add(priority,c);	

		JComboBox<String> priorityDropDown = new JComboBox<String>();
		String f = (String) fromDropDown.getSelectedItem();
		String t = (String) toDropDown.getSelectedItem();
		priorityDropDown.removeAllItems();
		for (String p : PriceRepository.getPriorities(f,t)){
			priorityDropDown.addItem(p);
		}

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 3;
		panel.add(priorityDropDown,c);

		toDropDown.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(fromDropDown.getItemCount()!=0){
					String f = (String) fromDropDown.getSelectedItem();
					String t = (String) toDropDown.getSelectedItem();
					priorityDropDown.removeAllItems();
					for (String priority : PriceRepository.getPriorities(f,t)){
						priorityDropDown.addItem(priority);
					}
					priorityDropDown.setSelectedItem(PriceRepository.getPriorities(f,t).get(0));
				}
			}
		});

		priorityDropDown.setSelectedItem(PriceRepository.getPriorities(f,t).get(0));

		JLabel	 totalPrice = new JLabel("$0.00",SwingConstants.LEFT);

		JFormattedTextField	 weightText = new JFormattedTextField(NumberFormat.getNumberInstance());
		weightText.setValue(0);
		weightText.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void focusLost(FocusEvent e) {
				entered_weight = Double.valueOf(weightText.getText());

				Double price_weight2 = PriceRepository.getPriceWeight(fromDropDown.getSelectedItem().toString(), toDropDown.getSelectedItem().toString(), priorityDropDown.getSelectedItem().toString());
				Double price_volume2 = PriceRepository.getPriceVolume(fromDropDown.getSelectedItem().toString(), toDropDown.getSelectedItem().toString(), priorityDropDown.getSelectedItem().toString());

				total_price = (entered_weight * price_weight2) + (entered_volume * price_volume2);

				totalPrice.setText("$" + new BigDecimal(total_price).setScale(2, BigDecimal.ROUND_HALF_UP));
			}

		});
		weightText.setBackground(Color.decode("#fffe9a"));
		JFormattedTextField	 volumeText = new JFormattedTextField(NumberFormat.getNumberInstance());
		volumeText.setValue(0);
		volumeText.setBackground(Color.decode("#fffe9a"));
		volumeText.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void focusLost(FocusEvent e) {
				entered_volume = Double.valueOf(volumeText.getText());
				Double price_weight2 = PriceRepository.getPriceWeight(fromDropDown.getSelectedItem().toString(), toDropDown.getSelectedItem().toString(), priorityDropDown.getSelectedItem().toString());
				Double price_volume2 = PriceRepository.getPriceVolume(fromDropDown.getSelectedItem().toString(), toDropDown.getSelectedItem().toString(), priorityDropDown.getSelectedItem().toString());
				Double total_price = (entered_weight * price_weight2) + (entered_volume * price_volume2);
				totalPrice.setText("$" + new BigDecimal(total_price).setScale(2, BigDecimal.ROUND_HALF_UP));
			}

		});

		priorityDropDown.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(priorityDropDown.getItemCount()!=0){
					Double price_weight2 = PriceRepository.getPriceWeight(fromDropDown.getSelectedItem().toString(), toDropDown.getSelectedItem().toString(), priorityDropDown.getSelectedItem().toString());
					Double price_volume2 = PriceRepository.getPriceVolume(fromDropDown.getSelectedItem().toString(), toDropDown.getSelectedItem().toString(), priorityDropDown.getSelectedItem().toString());
					Double total_price = (entered_weight * price_weight2) + (entered_volume * price_volume2);
					totalPrice.setText("$" + new BigDecimal(total_price).setScale(2, BigDecimal.ROUND_HALF_UP));
				}
			}
		});

		//Enter Weight
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 4;
		JLabel weight = new JLabel("Weight: ",SwingConstants.RIGHT);
		panel.add(weight,c);	

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 4;

		panel.add(weightText,c);

		//Enter Volume
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 5;
		JLabel volume = new JLabel("Volume: ",SwingConstants.RIGHT);
		panel.add(volume,c);	

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 5;
		//		JFormattedTextField	 volumeText = new JFormattedTextField(NumberFormat.getNumberInstance());
		//		volumeText.setValue(0);
		panel.add(volumeText,c);

		//Display Price
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 6;
		JLabel priceLabel = new JLabel("Total Price: ",SwingConstants.RIGHT);
		panel.add(priceLabel,c);	

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 6;
		//		JLabel	 totalPrice = new JLabel("$0.00",SwingConstants.LEFT);
		panel.add(totalPrice,c);

		//Submit Button
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 7;
		c.gridwidth = 2;
		JButton submit = new JButton();
		submit.setText("Submit");
		panel.add(submit,c);	
	}

	/**
	 * @param tabbedPane
	 */
	private void createDashboardTab(JTabbedPane tabbedPane) {
		JLabel label = new JLabel("Dashboard");
		label.setHorizontalTextPosition(JLabel.TRAILING); // Set the text position regarding its icon
		label.setIcon(createImageIcon("img/dash-icon.png"));
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		tabbedPane.addTab("Dashboard", null, panel,"View the current financial status of KPSmart");
		tabbedPane.setTabComponentAt(0, label);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		JLabel title = new JLabel("Dashboard", SwingConstants.LEFT);
		title.setFont(new Font(title.getFont().getFontName(), Font.PLAIN, 30));
		title.setForeground(Color.decode("#fffe9a"));
		panel.add(title,c);
		
		//Revenue
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;

		JPanel revenuePanel = new JPanel();
		revenuePanel.setLayout(new GridLayout(2,1));
		revenuePanel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), new EtchedBorder()));		
		revenuePanel.setBackground(Color.WHITE);

		JLabel revenueLabel = new JLabel("Revenue: ", SwingConstants.LEFT);
		revenueLabel.setFont(new Font(revenueLabel.getFont().getFontName(), Font.PLAIN, 30));
		JLabel revenueDisp = new JLabel("$1000.00", SwingConstants.LEFT);
		revenueDisp.setFont(new Font(revenueLabel.getFont().getFontName(), Font.BOLD, 40));
		revenueDisp.setForeground(Color.GREEN);

		revenuePanel.add(revenueLabel);
		revenuePanel.add(revenueDisp);

		panel.add(revenuePanel,c);

		//Expenditure
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 1;

		JPanel expPanel = new JPanel();
		expPanel.setLayout(new GridLayout(2,1));
		expPanel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), new EtchedBorder()));		
		expPanel.setBackground(Color.white);

		JLabel expLabel = new JLabel("Expenditure: ", SwingConstants.LEFT);
		expLabel.setFont(new Font(expLabel.getFont().getFontName(), Font.PLAIN, 30));
		JLabel expDisp = new JLabel("$3000.00", SwingConstants.LEFT);
		expDisp.setFont(new Font(expLabel.getFont().getFontName(), Font.BOLD, 40));
		expDisp.setForeground(Color.RED);

		expPanel.add(expLabel);
		expPanel.add(expDisp);

		panel.add(expPanel,c);

		//Profit
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 1;

		JPanel profPanel = new JPanel();
		profPanel.setLayout(new GridLayout(2,1));
		profPanel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), new EtchedBorder()));		
		profPanel.setBackground(Color.white);
		JLabel profLabel = new JLabel("Profit: ", SwingConstants.LEFT);
		profLabel.setFont(new Font(profLabel.getFont().getFontName(), Font.PLAIN, 30));
		JLabel profDisp = new JLabel("-$2000.00", SwingConstants.LEFT);
		profDisp.setFont(new Font(profDisp.getFont().getFontName(), Font.BOLD, 40));
		profDisp.setForeground(Color.RED);

		profPanel.add(profLabel);
		profPanel.add(profDisp);

		panel.add(profPanel,c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 3;

		JTabbedPane dashTab = new JTabbedPane();
		dashTab.addTab("Critical routes", null, null,"View the current financial status of KPSmart");
		dashTab.addTab("Monthly overview", null, null,"View the current financial status of KPSmart");
		dashTab.addTab("Revenue & expenditure", null, null,"View the current financial status of KPSmart");
		dashTab.addTab("Number of events", null, null,"View the current financial status of KPSmart");
		dashTab.addTab("Export", null, null,"View the current financial status of KPSmart");

		panel.add(dashTab,c);

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

	/** Returns a TextPanel from the String 'text'. **/
	protected JComponent makeTextPanel(String text) {
		JPanel panel = new JPanel(false);
		JLabel filler = new JLabel(text);
		filler.setHorizontalAlignment(JLabel.CENTER);
		panel.setLayout(new GridLayout(1, 1));
		panel.add(filler);
		return panel;
	}



}
