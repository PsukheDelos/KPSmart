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
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Properties;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

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
import kps.distribution.event.MailDeliveryEvent;
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

	public JTable priceTable = new JTable(PriceRepository.getPricesModel());
	private JComboBox<String> fromDropDown;

	private ClientFrame parent = this;

	public MailClient client;

	private ClientListener listener = new ClientListener(); //What is this for?

	public MailClient getMailClient(){
		return client;
	}

	public void updatePrice(){
		priceTable.setModel(PriceRepository.getPricesModel());
	}

	public void updateFrom(){
		fromDropDown.removeAllItems();
		for (String toCity : PriceRepository.getFromCities()){
			fromDropDown.addItem(toCity);
		}
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

		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
		}

		client = new MailClient(this);
		initialise();

		pack();
		setLocationRelativeTo(null);
		setVisible(false);

		// We should check if a user is logged in
		if(client.getCurrentUser() == null){
			setEnabled(false);
			ClientLoginFrame frame = new ClientLoginFrame(client, this);
			frame.revalidate();
		}
	}

	private void initialise() {
		client.sendEvent(new MailDeliveryEvent("Monday", "Auckland", "Wellington", 1, 2, "International Air"));
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
		LatLonPoint wellingtonLocation = new LatLonPoint.Double(-41.21039581,175.1449432);

		// Get the default MapHandler the BasicMapPanel created.
		MapHandler mapHandler = mapPanel.getMapHandler();
		// Get the default MapBean that the BasicMapPanel created.
		MapBean mapBean = mapPanel.getMapBean();
		// Set the map's center to Wellington
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

		mapBean.setBackgroundColor(Color.decode("#b8dbfe"));

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

			line.setStroke(new BasicStroke(1f));
			line.setLinePaint(Color.red);

			routeList.add(line);
			cityList.add(basicLocation);
		}
		omList.add(cityList);
		//		omList.add(routeList);
		basicLayer.setRenderPolicy(new BufferedImageRenderPolicy());
		basicLayer.setList(omList);
		mapHandler.add(basicLayer);

		// Create Map tab

		JLabel label = new JLabel("Locations");
		label.setHorizontalTextPosition(JLabel.TRAILING);
		label.setIcon(createImageIcon("img/map-icon.png"));
		tabbedPane.addTab("Locations", null, (Component) mapPanel,"Here you can view all the locations.");
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
		addPrice.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				new PriceFrame(parent,"Add");
				//						PriceRepository.removePrice(jt.getModel().getValueAt(sr, 0).toString(), jt.getModel().getValueAt(sr, 1).toString(), jt.getModel().getValueAt(sr, 2).toString());
				//						jt.setModel(PriceRepository.getPricesModel());

			}
		});
		addPrice.setText("+");
		panel.add(addPrice,c);

		//Button: Edit Price
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		JButton editPrice = new JButton();
		editPrice.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int sr = priceTable.getSelectedRow();
				if(sr!=-1){
					new PriceFrame(parent,priceTable.getModel().getValueAt(sr, 0).toString(), priceTable.getModel().getValueAt(sr, 1).toString(), priceTable.getModel().getValueAt(sr, 2).toString(),priceTable.getModel().getValueAt(sr, 3).toString(),priceTable.getModel().getValueAt(sr, 4).toString());
					//						PriceRepository.removePrice(jt.getModel().getValueAt(sr, 0).toString(), jt.getModel().getValueAt(sr, 1).toString(), jt.getModel().getValueAt(sr, 2).toString());
					//						jt.setModel(PriceRepository.getPricesModel());
				}

			}
		});
		editPrice.setText("Edit");
		panel.add(editPrice,c);

		//Button: Remove Price
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 1;
		c.gridwidth = 1;
		JButton removePrice = new JButton();
		removePrice.setText("-");
		removePrice.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int sr = priceTable.getSelectedRow();
				if(sr!=-1){
					int dialogButton = JOptionPane.YES_NO_OPTION;
					int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure?","Warning",dialogButton);
					if(dialogResult == JOptionPane.YES_OPTION){
						PriceRepository.removePrice(priceTable.getModel().getValueAt(sr, 0).toString(), priceTable.getModel().getValueAt(sr, 1).toString(), priceTable.getModel().getValueAt(sr, 2).toString());
						priceTable.setModel(PriceRepository.getPricesModel());
						updateFrom();
					}
				}
			}
		});

		panel.add(removePrice,c);

		//Table: Price Table
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 4;
		priceTable.setPreferredScrollableViewportSize(new Dimension(700, 300));
		priceTable.setFillsViewportHeight(true);
		panel.add(new JScrollPane(priceTable),c);
	}

	/**
	 * @param tabbedPane
	 */
	private void createRouteTab(JTabbedPane tabbedPane) {
		JLabel label = new JLabel("Routes");
		label.setHorizontalTextPosition(JLabel.TRAILING); // Set the text position regarding its icon
		label.setIcon(createImageIcon("img/route-icon.png"));
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		tabbedPane.addTab("Routes", null, panel,"View and Edit the current Routes of KPSmart");
		tabbedPane.setTabComponentAt(2, label);
		tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
		JTable jt = new JTable();

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
		JButton addRoute = new JButton();
		addRoute.setText("+");
		addRoute.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				new RouteFrame(null,"Add");
				//						PriceRepository.removePrice(jt.getModel().getValueAt(sr, 0).toString(), jt.getModel().getValueAt(sr, 1).toString(), jt.getModel().getValueAt(sr, 2).toString());
				//						jt.setModel(PriceRepository.getPricesModel());

			}
		});
		panel.add(addRoute,c);

		//Button: Edit Route
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		JButton editRoute = new JButton();
		editRoute.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int sr = jt.getSelectedRow();
				if(sr!=-1){
					new RouteFrame(null,jt.getModel().getValueAt(sr, 0).toString(), jt.getModel().getValueAt(sr, 1).toString(), jt.getModel().getValueAt(sr, 2).toString(),jt.getModel().getValueAt(sr, 3).toString() );
					//						PriceRepository.removePrice(jt.getModel().getValueAt(sr, 0).toString(), jt.getModel().getValueAt(sr, 1).toString(), jt.getModel().getValueAt(sr, 2).toString());
					//						jt.setModel(PriceRepository.getPricesModel());
				}

			}
		});
		editRoute.setText("Edit");
		panel.add(editRoute,c);

		//Button: Remove Route
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 1;
		c.gridwidth = 1;
		JButton removeRoute = new JButton();
		removeRoute.setText("-");
		removeRoute.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int sr = jt.getSelectedRow();
				if(sr!=-1){
					int dialogButton = JOptionPane.YES_NO_OPTION;
					int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure?","Warning",dialogButton);
					if(dialogResult == JOptionPane.YES_OPTION){
						RouteRepository.removeRoute(jt.getModel().getValueAt(sr, 0).toString(), jt.getModel().getValueAt(sr, 1).toString(), jt.getModel().getValueAt(sr, 3).toString());
						jt.setModel(RouteRepository.getRoutesModel());
					}
				}

			}
		});
		panel.add(removeRoute,c);

		//Table: Price Table
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 4;
		jt.setModel(RouteRepository.getRoutesModel());
		jt.setPreferredScrollableViewportSize(new Dimension(700, 300));
		jt.setFillsViewportHeight(true);
		panel.add(new JScrollPane(jt),c);
	}

	/**
	 * @param tabbedPane
	 */
	private void createMailTab(JTabbedPane tabbedPane) {
		JLabel label = new JLabel("+");
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
		fromDropDown = new JComboBox<String>();
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
				}
			}
		});

		priorityDropDown.setSelectedItem(PriceRepository.getPriorities(f,t).get(0));

		JLabel	 totalPrice = new JLabel("$0.00",SwingConstants.LEFT);

		JFormattedTextField	 weightText = new JFormattedTextField(NumberFormat.getNumberInstance());
		weightText.setValue(0);
		weightText.addKeyListener(new KeyAdapter() {

			public void keyTyped(KeyEvent e){
				char c = e.getKeyChar();
				if( ( (c < '0') || (c > '9') ) && (c != KeyEvent.VK_BACK_SPACE) && (c != KeyEvent.VK_PERIOD || weightText.getText().contains(".") ) ) e.consume();
			}

			public void keyReleased(KeyEvent e){
				if(weightText.getText().equals("")){
					entered_weight = 0.00;
				}else{
					try{
						entered_weight = Double.valueOf(weightText.getText());
					} catch (NullPointerException exception) {
						entered_weight = 0.00;
					} catch (NumberFormatException exception) {
						entered_weight = 0.00;
					}
				}

				Double price_weight2 = PriceRepository.getPriceWeight(fromDropDown.getSelectedItem().toString(), toDropDown.getSelectedItem().toString(), priorityDropDown.getSelectedItem().toString());
				Double price_volume2 = PriceRepository.getPriceVolume(fromDropDown.getSelectedItem().toString(), toDropDown.getSelectedItem().toString(), priorityDropDown.getSelectedItem().toString());

				total_price = (entered_weight * price_weight2) + (entered_volume * price_volume2);

				totalPrice.setText("$" + new BigDecimal(total_price).setScale(2, BigDecimal.ROUND_HALF_UP));
			}

		}

				);
		weightText.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e){
				if(weightText.getText().equals("")) weightText.setText("0");
			}
			public void focusGained(FocusEvent e){
				if(weightText.getText().equals("0"))weightText.setText("");
			}
		});
		weightText.setBackground(Color.decode("#fffe9a"));
		JFormattedTextField	 volumeText = new JFormattedTextField(NumberFormat.getNumberInstance());
		volumeText.setValue(0);
		volumeText.setBackground(Color.decode("#fffe9a"));
		volumeText.addKeyListener(new KeyAdapter() {

			public void keyTyped(KeyEvent e){
				char c = e.getKeyChar();
				if( ( (c < '0') || (c > '9') ) && (c != KeyEvent.VK_BACK_SPACE) && (c != KeyEvent.VK_PERIOD || weightText.getText().contains(".") ) ) e.consume();
			}

			public void keyReleased(KeyEvent e){
				if(volumeText.getText().equals("")){
					entered_volume = 0.00;
				}else{
					try{
						entered_volume = Double.valueOf(volumeText.getText());
					} catch (NullPointerException exception) {
						entered_volume = 0.00;
					} catch (NumberFormatException exception) {
						entered_volume = 0.00;
					}

				}

				Double price_weight2 = PriceRepository.getPriceWeight(fromDropDown.getSelectedItem().toString(), toDropDown.getSelectedItem().toString(), priorityDropDown.getSelectedItem().toString());
				Double price_volume2 = PriceRepository.getPriceVolume(fromDropDown.getSelectedItem().toString(), toDropDown.getSelectedItem().toString(), priorityDropDown.getSelectedItem().toString());
				Double total_price = (entered_weight * price_weight2) + (entered_volume * price_volume2);
				totalPrice.setText("$" + new BigDecimal(total_price).setScale(2, BigDecimal.ROUND_HALF_UP));
			}

		}

				);
		volumeText.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e){
				if(volumeText.getText().equals(""))volumeText.setText("0");
			}

			public void focusGained(FocusEvent e){
				if(volumeText.getText().equals("0"))volumeText.setText("");
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
		panel.add(volumeText,c);

		Font g = new Font(totalPrice.getFont().getFontName(), Font.BOLD, 18);

		//Display Price
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 6;
		JLabel priceLabel = new JLabel("Total Price: ",SwingConstants.RIGHT);
		panel.add(priceLabel,c);	

		priceLabel.setFont(g);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 6;
		panel.add(totalPrice,c);

		totalPrice.setFont(g);

		//Submit Button
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 7;
		c.gridwidth = 2;
		JButton submit = new JButton();
		submit.setText("Submit");
		submit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				//				client.
				client.sendEvent(new MailDeliveryEvent("Monday", toDropDown.getSelectedItem().toString(), fromDropDown.getSelectedItem().toString(), entered_weight, entered_volume, priorityDropDown.getSelectedItem().toString()));
				//				
			}

		});
		panel.add(submit,c);	
	}

	/**
	 * @param tabbedPane
	 */
	@SuppressWarnings("unchecked")
	private void createDashboardTab(JTabbedPane tabbedPane) {
		JLabel label = new JLabel("Dashboard");
		label.setHorizontalTextPosition(JLabel.TRAILING); // Set the text position regarding its icon
		label.setIcon(createImageIcon("img/dash-icon.png"));
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		tabbedPane.addTab("Dashboard", null, panel,"View the current financial status of KPSmart");
		tabbedPane.setTabComponentAt(tabbedPane.getTabCount()-1, label);
		tabbedPane.setMnemonicAt(tabbedPane.getTabCount()-1, KeyEvent.VK_1);

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

		//Chart: Domestic
		final JFXPanel jFXPanel = new JFXPanel();
		Group root = new Group();
		Scene scene = new Scene(root);

		ObservableList<PieChart.Data> pieChartData =
				FXCollections.observableArrayList(
						new PieChart.Data("Revenue", 134),
						new PieChart.Data("Expense", 27));
		final PieChart revchart = new PieChart(pieChartData);
		revchart.setTitle("Domestic");
		revchart.setStyle("-fx-background-color: rgba(184,219,254,1);");		
		((Group) scene.getRoot()).getChildren().add(revchart);

		jFXPanel.setScene(scene);
		JPanel j = new JPanel();
		j.add(jFXPanel);

		//Chart: International
		final JFXPanel jFXPanel2 = new JFXPanel();
		Group root2 = new Group();
		Scene scene2 = new Scene(root2);

		ObservableList<PieChart.Data> pieChartData2 =
				FXCollections.observableArrayList(
						new PieChart.Data("Revenue", 2),
						new PieChart.Data("Expense", 300));
		final PieChart revchart2 = new PieChart(pieChartData2);
		revchart2.setTitle("International");
		revchart2.setStyle("-fx-background-color: rgba(184,219,254,1);");
		revchart2.setTitle("International");

		((Group) scene2.getRoot()).getChildren().add(revchart2);

		jFXPanel2.setScene(scene2);
		JPanel k = new JPanel();
		k.add(jFXPanel2);


		//Chart: Trend
		final JFXPanel jFXPanel3 = new JFXPanel();
		Group root3 = new Group();
		Scene scene3 = new Scene(root3);
		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("Month");
		yAxis.setLabel("Profit ($100)");

		//Creating the chart
		final LineChart<String,Number> lineChart = 
				new LineChart<String,Number>(xAxis,yAxis);

		lineChart.setTitle("KPS Monthly Profit, 2015");
		lineChart.setLegendVisible(false);
		//defining a series
		XYChart.Series<String,Number> series1 = new XYChart.Series<String,Number>();
		series1.setName("Profit");

		series1.getData().add(new XYChart.Data<String,Number>("Jan", 23));
		series1.getData().add(new XYChart.Data<String,Number>("Feb", 14));
		series1.getData().add(new XYChart.Data<String,Number>("Mar", 15));
		series1.getData().add(new XYChart.Data<String,Number>("Apr", 24));
		series1.getData().add(new XYChart.Data<String,Number>("May", 34));
		series1.getData().add(new XYChart.Data<String,Number>("Jul", 22));
		series1.getData().add(new XYChart.Data<String,Number>("Aug", 45));
		series1.getData().add(new XYChart.Data<String,Number>("Sep", 43));
		series1.getData().add(new XYChart.Data<String,Number>("Oct", 17));
		series1.getData().add(new XYChart.Data<String,Number>("Nov", 29));
		series1.getData().add(new XYChart.Data<String,Number>("Dec", 25));

		//	        XYChart.Series series2 = new XYChart.Series();
		//	        series2.setName("Expenses");
		//	        series2.getData().add(new XYChart.Data("Jan", 33));
		//	        series2.getData().add(new XYChart.Data("Feb", 34));
		//	        series2.getData().add(new XYChart.Data("Mar", 25));
		//	        series2.getData().add(new XYChart.Data("Apr", 44));
		//	        series2.getData().add(new XYChart.Data("May", 39));
		//	        series2.getData().add(new XYChart.Data("Jun", 16));
		//	        series2.getData().add(new XYChart.Data("Jul", 55));
		//	        series2.getData().add(new XYChart.Data("Aug", 54));
		//	        series2.getData().add(new XYChart.Data("Sep", 48));
		//	        series2.getData().add(new XYChart.Data("Oct", 27));
		//	        series2.getData().add(new XYChart.Data("Nov", 37));
		//	        series2.getData().add(new XYChart.Data("Dec", 29));

		lineChart.getData().addAll(series1);

		((Group) scene3.getRoot()).getChildren().add(lineChart);
		jFXPanel3.setScene(scene3);
		JPanel i = new JPanel();
		i.add(jFXPanel3);



		JTabbedPane dashTab = new JTabbedPane();
		dashTab.addTab("Trends", null, i,"View the current financial status of KPSmart");
		dashTab.addTab("Domestic", null,j,"View the current financial status of KPSmart");
		dashTab.addTab("International", null,k,"View the current financial status of KPSmart");
		dashTab.addTab("Export", null, new JPanel(),"View the current financial status of KPSmart");

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

}
