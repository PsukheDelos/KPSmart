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
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Properties;

import javafx.application.Platform;
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

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableModel;

import kps.backend.UserPermissions;
import kps.backend.database.CostRepository;
import kps.backend.database.LocationRepository;
import kps.backend.database.MailRepository;
import kps.backend.database.PriceRepository;
import kps.backend.database.UserRepository;
import kps.distribution.event.CustomerPriceRemoveEvent;
import kps.distribution.event.LocationRemoveEvent;
import kps.distribution.event.MailDeliveryEvent;
import kps.distribution.event.TransportCostRemoveEvent;
import kps.distribution.event.UpdateTableLocationsEvent;
import kps.distribution.event.UpdateTablePriceEvent;
import kps.distribution.event.UpdateTableRouteEvent;
import kps.distribution.event.UpdateTableUserEvent;
import kps.distribution.network.Location;
import kps.frontend.MailClient;
import kps.net.event.RemoveUserEvent;
import kps.net.event.XMLGetEvent;

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
	
	private JTabbedPane tabbedPane;

	private JTable routeTable = new JTable();
	private JTable priceTable = new JTable();
	private JTable userTable = new JTable();
	private JTable locationTable = new JTable();
	private JTable eventTable = new JTable();

	private JComboBox<String> fromDropDown;

	String[] days = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
	
	private ClientFrame parent = this;

	public MailClient client;

	public MailClient getMailClient(){
		return client;
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

		// We should check if a user is logged in
		if(client.getCurrentUser() == null){
			setEnabled(false);
			ClientLoginFrame frame = new ClientLoginFrame(client, this);
			frame.revalidate();
		}

		initialise();

		pack();
		setLocationRelativeTo(null);
		setVisible(false);
	}

	private void initialise() {
		createTabbedPane();
	}

	protected void createTabbedPane(){
		setTabbedPane(new JTabbedPane());
		createMailTab(getTabbedPane());
		new JFXPanel();
		Platform.runLater(new Runnable(){
			public void run(){
				createDashboardTab(getTabbedPane());
				return;
			}
		});
		

		createRouteTab(getTabbedPane());
		createPriceTab(getTabbedPane());
		createMapTab(getTabbedPane());
		createLocationsTab(getTabbedPane());
		createUserTab(getTabbedPane());

		this.add(getTabbedPane());
	}

	public void setUserPermissions(UserPermissions up){
		if(!(up==UserPermissions.MANAGER)){
			tabbedPane.removeTabAt(tabbedPane.getTabCount()-1);
			tabbedPane.removeTabAt(tabbedPane.getTabCount()-1);
			tabbedPane.removeTabAt(0);
		}
	}
	
	public void createDashboardTab(JTabbedPane tabbedPane) {

		JLabel label = new JLabel("Dashboard");
		label.setHorizontalTextPosition(JLabel.TRAILING); // Set the text position regarding its icon
		label.setIcon(createImageIcon("img/dash-icon.png"));
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		tabbedPane.addTab("Dashboard", null, panel,"View the current financial status of KPSmart");
		tabbedPane.setTabComponentAt(tabbedPane.getTabCount()-1, label);

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
		c.gridy = c.gridy + 1;

		JPanel revenuePanel = new JPanel();
		revenuePanel.setLayout(new GridLayout(2,1));
		revenuePanel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), new EtchedBorder()));		
		revenuePanel.setBackground(Color.WHITE);

		JLabel revenueLabel = new JLabel("Revenue: ", SwingConstants.LEFT);
		revenueLabel.setFont(new Font(revenueLabel.getFont().getFontName(), Font.PLAIN, 30));
		JLabel revenueDisp = new JLabel("$" + String.format("%.2f", MailRepository.getRevenue()), SwingConstants.LEFT);
		revenueDisp.setFont(new Font(revenueLabel.getFont().getFontName(), Font.BOLD, 40));
		revenueDisp.setForeground(Color.GREEN);

		revenuePanel.add(revenueLabel);
		revenuePanel.add(revenueDisp);

		panel.add(revenuePanel,c);

		//Expenditure
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;

		JPanel expPanel = new JPanel();
		expPanel.setLayout(new GridLayout(2,1));
		expPanel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), new EtchedBorder()));		
		expPanel.setBackground(Color.white);

		JLabel expLabel = new JLabel("Expenditure: ", SwingConstants.LEFT);
		expLabel.setFont(new Font(expLabel.getFont().getFontName(), Font.PLAIN, 30));
		JLabel expDisp = new JLabel("$" + String.format("%.2f", MailRepository.getExpenditure()), SwingConstants.LEFT);
		expDisp.setFont(new Font(expLabel.getFont().getFontName(), Font.BOLD, 40));
		expDisp.setForeground(Color.RED);

		expPanel.add(expLabel);
		expPanel.add(expDisp);

		panel.add(expPanel,c);

		//Events
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;

		JPanel profPanel = new JPanel();
		profPanel.setLayout(new GridLayout(2,1));
		profPanel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), new EtchedBorder()));		
		profPanel.setBackground(Color.white);
		JLabel profLabel = new JLabel("Events: ", SwingConstants.LEFT);
		profLabel.setFont(new Font(profLabel.getFont().getFontName(), Font.PLAIN, 30));
		JLabel profDisp = new JLabel(MailRepository.getEventCount().toString(), SwingConstants.LEFT);
		profDisp.setFont(new Font(profDisp.getFont().getFontName(), Font.BOLD, 40));
		profDisp.setForeground(Color.RED);

		profPanel.add(profLabel);
		profPanel.add(profDisp);

		panel.add(profPanel,c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = c.gridy + 1;
		c.gridwidth = 4;

		//Chart: Domestic
		final JFXPanel jFXPanel = new JFXPanel();
		Group root = new Group();
		Scene scene = new Scene(root);

		final PieChart revchart = new PieChart(MailRepository.getDomesticPieChart());
		revchart.setTitle("Domestic");
		revchart.setStyle("-fx-background-color: rgba(184,219,254,1);");		
		((Group) scene.getRoot()).getChildren().add(revchart);

		jFXPanel.setScene(scene);
		JPanel domPieChart = new JPanel();
		domPieChart.add(jFXPanel);

		//Chart: International
		final JFXPanel jFXPanel2 = new JFXPanel();
		Group root2 = new Group();
		Scene scene2 = new Scene(root2);

		final PieChart revchart2 = new PieChart(MailRepository.getInternationalPieChart());
		revchart2.setTitle("International");
		revchart2.setStyle("-fx-background-color: rgba(184,219,254,1);");
		revchart2.setTitle("International");

		((Group) scene2.getRoot()).getChildren().add(revchart2);

		jFXPanel2.setScene(scene2);
		JPanel intPieChart = new JPanel();
		intPieChart.add(jFXPanel2);


		//Chart: Trend
		final JFXPanel jFXPanel3 = new JFXPanel();
		Group root3 = new Group();
		Scene scene3 = new Scene(root3);
		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("Month");
		yAxis.setLabel("Dollars");

		//Creating the chart
		final LineChart<String,Number> lineChart = 
				new LineChart<String,Number>(xAxis,yAxis);

		lineChart.setTitle("KPS Revenue/Expense Trends");
		lineChart.setLegendVisible(true);
		MailRepository.setMonthlyineChartData(lineChart);		

		((Group) scene3.getRoot()).getChildren().add(lineChart);
		jFXPanel3.setScene(scene3);
		JPanel trendLineChart = new JPanel();
		trendLineChart.add(jFXPanel3);


		//Table: Amount of Mail
		JTable aomTable = new JTable(MailRepository.getAmountOfMailModel());
		aomTable.setPreferredScrollableViewportSize(new Dimension(700, 300));
		aomTable.setFillsViewportHeight(true);	
		
		//Table: Average Delivery Times
		JTable adtTable = new JTable(MailRepository.getAverageDeliveryTimeModel());
		adtTable.setPreferredScrollableViewportSize(new Dimension(700, 300));
		adtTable.setFillsViewportHeight(true);	
		
		//Table: Critical Routes
		JTable crTable = new JTable(MailRepository.getCriticalRoutesModel());
		crTable.setPreferredScrollableViewportSize(new Dimension(700, 300));
		crTable.setFillsViewportHeight(true);

		//Table: Events
		client.sendEvent(new XMLGetEvent());
		eventTable.setPreferredScrollableViewportSize(new Dimension(700, 300));
		eventTable.setFillsViewportHeight(true);
		
		JTabbedPane dashTab = new JTabbedPane();
		dashTab.addTab("Trends", null, trendLineChart,"View the current financial status of KPSmart");
		dashTab.addTab("Domestic", null,domPieChart,"View the current financial status of KPSmart");
		dashTab.addTab("International", null,intPieChart,"View the current financial status of KPSmart");
//		dashTab.addTab("Export", null, new JPanel(),"View the current financial status of KPSmart");
		dashTab.addTab("Amount of Mail", new JScrollPane(aomTable));
		dashTab.addTab("Average Delivery Times", new JScrollPane(adtTable));
		dashTab.addTab("Critical Routes", new JScrollPane(crTable));
		dashTab.addTab("Events", null, new JScrollPane(eventTable), "View a list of the latest mail events.");
		
		dashTab.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
			JTabbedPane pane = (JTabbedPane)e.getSource();
				if(pane.getSelectedIndex() == 4)
					client.sendEvent(new XMLGetEvent());
			}
		});

		panel.add(dashTab,c);

	}

	private void createMailTab(JTabbedPane tabbedPane) {
		JLabel label = new JLabel("+");
		label.setHorizontalTextPosition(JLabel.TRAILING); // Set the text position regarding its icon
		label.setIcon(createImageIcon("img/mail-icon.png"));
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		tabbedPane.addTab("Mail Delivery", null, panel,"New Mail Delivery");
		tabbedPane.setTabComponentAt(tabbedPane.getTabCount()-1, label);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		JLabel title = new JLabel("New Mail Event", SwingConstants.LEFT);
		title.setFont(new Font(title.getFont().getFontName(), Font.PLAIN, 30));
		title.setForeground(Color.decode("#fffe9a"));
		panel.add(title,c);	
		//
//		private JComboBox<String> type = new JComboBox<String>(types);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		JLabel source = new JLabel("Day: ",SwingConstants.RIGHT);
		panel.add(source,c);	
		JComboBox<String> dayDropDown = new JComboBox<String>(days);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		panel.add(dayDropDown,c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = c.gridy+1;
		c.gridwidth = 1;
		source = new JLabel("Source: ",SwingConstants.RIGHT);
		panel.add(source,c);	
		fromDropDown = new JComboBox<String>();
		for (String fromCity : PriceRepository.getOriginCities()){
			fromDropDown.addItem(fromCity);
		}
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		panel.add(fromDropDown,c);

		//To Cities Drop Down
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = c.gridy+1;
		JLabel dest = new JLabel("Destination: ",SwingConstants.RIGHT);
		panel.add(dest,c);	

		JComboBox<String> toDropDown = new JComboBox<String>();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		panel.add(toDropDown,c);

		fromDropDown.addActionListener(new ActionListener() {//add actionlistner to listen for change
			@Override
			public void actionPerformed(ActionEvent e) {
				String s = (String) fromDropDown.getSelectedItem();//get the selected item
				toDropDown.removeAllItems();
				for (String toCity : PriceRepository.getDestinationCities(s)){
					toDropDown.addItem(toCity);
				}
			}
		});

		fromDropDown.setSelectedItem(PriceRepository.getOriginCities().get(0));

		//Priority Drop Down
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = c.gridy+1;
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

				
				Double price_weight2 = PriceRepository.getWeightCost(fromDropDown.getSelectedItem().toString(), toDropDown.getSelectedItem().toString(), priorityDropDown.getSelectedItem().toString());
				Double price_volume2 = PriceRepository.getVolumeCost(fromDropDown.getSelectedItem().toString(), toDropDown.getSelectedItem().toString(), priorityDropDown.getSelectedItem().toString());
				total_price = (double)2;
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

				Double price_weight2 = PriceRepository.getWeightCost(fromDropDown.getSelectedItem().toString(), toDropDown.getSelectedItem().toString(), priorityDropDown.getSelectedItem().toString());
				Double price_volume2 = PriceRepository.getVolumeCost(fromDropDown.getSelectedItem().toString(), toDropDown.getSelectedItem().toString(), priorityDropDown.getSelectedItem().toString());
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
		c.gridy = c.gridy+1;
		JLabel weight = new JLabel("Weight: ",SwingConstants.RIGHT);
		panel.add(weight,c);	

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;

		panel.add(weightText,c);

		//Enter Volume
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = c.gridy+1;
		JLabel volume = new JLabel("Volume: ",SwingConstants.RIGHT);
		panel.add(volume,c);	

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		panel.add(volumeText,c);

		Font g = new Font(totalPrice.getFont().getFontName(), Font.BOLD, 18);

		//Display Price
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = c.gridy+1;
		JLabel priceLabel = new JLabel("Total Price: ",SwingConstants.RIGHT);
		panel.add(priceLabel,c);	

		priceLabel.setFont(g);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		panel.add(totalPrice,c);

		totalPrice.setFont(g);

		//Submit Button
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = c.gridy+1;
		c.gridwidth = 2;
		JButton submit = new JButton();
		submit.setText("Submit");
		submit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to submit?", "WARNING", JOptionPane.YES_NO_OPTION);
				if(reply == JOptionPane.YES_OPTION){
					client.sendEvent(new MailDeliveryEvent(dayDropDown.getSelectedItem().toString(), fromDropDown.getSelectedItem().toString(), toDropDown.getSelectedItem().toString(), entered_weight, entered_volume, total_price, priorityDropDown.getSelectedItem().toString()));
				}
			}

		});
		panel.add(submit,c);	

		//Enter Weight
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = c.gridy+1;
		c.gridwidth = 1;

	}

	private void createPriceTab(JTabbedPane tabbedPane) {
		JLabel label = new JLabel("Prices");
		label.setHorizontalTextPosition(JLabel.TRAILING); // Set the text position regarding its icon
		label.setIcon(createImageIcon("img/price-icon.png"));
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		tabbedPane.addTab("Prices", null, panel,"View and Edit the current prices of KPSmart");
		tabbedPane.setTabComponentAt(tabbedPane.getTabCount()-1, label);

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
						CustomerPriceRemoveEvent c = new CustomerPriceRemoveEvent(priceTable.getModel().getValueAt(sr, 0).toString(), priceTable.getModel().getValueAt(sr, 1).toString(), priceTable.getModel().getValueAt(sr, 2).toString());
						parent.client.sendEvent(c);
						//						PriceRepository.remove(priceTable.getModel().getValueAt(sr, 0).toString(), priceTable.getModel().getValueAt(sr, 1).toString(), priceTable.getModel().getValueAt(sr, 2).toString());
						//						priceTable.setModel(PriceRepository.getPricesModel());
						//						updateOrigin();
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
		client.sendEvent(new UpdateTablePriceEvent(priceTable));
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
		tabbedPane.setTabComponentAt(tabbedPane.getTabCount()-1, label);

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
				new CostFrame(parent, "Add");
			}
		});
		panel.add(addRoute,c);

		//Button: Edit Cost
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		JButton editRoute = new JButton();
		editRoute.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int sr = routeTable.getSelectedRow();
				if(sr!=-1){
					new CostFrame (parent, routeTable.getModel().getValueAt(sr, 0).toString(), routeTable.getModel().getValueAt(sr, 1).toString(), routeTable.getModel().getValueAt(sr, 2).toString(), routeTable.getModel().getValueAt(sr, 3).toString(),
							routeTable.getModel().getValueAt(sr, 4).toString(), routeTable.getModel().getValueAt(sr, 5).toString(), routeTable.getModel().getValueAt(sr, 6).toString(), routeTable.getModel().getValueAt(sr, 7).toString(),
							routeTable.getModel().getValueAt(sr, 8).toString(),routeTable.getModel().getValueAt(sr, 9).toString(), routeTable.getModel().getValueAt(sr, 10).toString());
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
				int sr = routeTable.getSelectedRow();
				if(sr!=-1){
					int dialogButton = JOptionPane.YES_NO_OPTION;
					int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure?","Warning",dialogButton);
					if(dialogResult == JOptionPane.YES_OPTION){
						client.sendEvent(new TransportCostRemoveEvent(routeTable.getModel().getValueAt(sr, 0).toString(), routeTable.getModel().getValueAt(sr, 1).toString(), routeTable.getModel().getValueAt(sr, 2).toString(),routeTable.getModel().getValueAt(sr, 3).toString()));
						updateRoutes();
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
//		routeTable.setModel(CostRepository.getRoutesModel());
		client.sendEvent(new UpdateTableRouteEvent(routeTable));
		routeTable.setPreferredScrollableViewportSize(new Dimension(700, 300));
		routeTable.setFillsViewportHeight(true);
		panel.add(new JScrollPane(routeTable),c);
	}

	private void createMapTab(JTabbedPane tabbedPane) {
		/*
		 * The BasicMapPanel automatically creates many default components,
		 * including the MapBean and the MapHandler. You can extend the
		 * BasicMapPanel class if you like to add different functionality or
		 * different types of objects.
		 */
		MapPanel mapPanel = new OverlayMapPanel();
		JLabel label = new JLabel("Map");
		label.setHorizontalTextPosition(JLabel.TRAILING);
		label.setIcon(createImageIcon("img/map-icon.png"));
		tabbedPane.addTab("Map", null, (Component) mapPanel,"Here you can view all the locations.");
		tabbedPane.setTabComponentAt(tabbedPane.getTabCount()-1, label);

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
			//OMLine line = new OMLine(wellingtonLocation.getLatitude(), wellingtonLocation.getLongitude(), city.lat, city.lon, OMGraphic.LINETYPE_GREATCIRCLE);


			cityList.add(basicLocation);
		}

		for(String origin: CostRepository.getOrigins()){

			Location start = LocationRepository.getCity(origin);

			for(String destination: CostRepository.getDestinations(origin)){

				Location end = LocationRepository.getCity(destination);

				OMLine line = new OMLine(start.lat,start.lon,end.lat,end.lon,OMGraphic.LINETYPE_GREATCIRCLE);

				line.setStroke(new BasicStroke(1f));
				line.setLinePaint(Color.red);

				routeList.add(line);
			}
		}
		omList.add(cityList);
		omList.add(routeList);
		basicLayer.setRenderPolicy(new BufferedImageRenderPolicy());
		basicLayer.setList(omList);
		mapHandler.add(basicLayer);

		// Create Map tab
	}

	public void createUserTab(JTabbedPane tabbedPane){
		JLabel label = new JLabel("Users");
		label.setHorizontalTextPosition(JLabel.TRAILING);
		label.setIcon(createImageIcon("img/users-icon.png"));
		JPanel panel = new JPanel();

		tabbedPane.addTab("Users", null, panel,"Here you can edit users");
		tabbedPane.setTabComponentAt(tabbedPane.getTabCount()-1, label);

		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		//Title: Users
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 0;

		JLabel title = new JLabel("Users", SwingConstants.LEFT);
		title.setFont(new Font(title.getFont().getFontName(), Font.PLAIN, 30));
		title.setForeground(Color.decode("#fffe9a"));

		panel.add(title,c);

		//Table: Users
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 3;

		client.sendEvent(new UpdateTableUserEvent(userTable));
		userTable.setPreferredScrollableViewportSize(new Dimension(700, 300));
		userTable.setFillsViewportHeight(true);
		panel.add(new JScrollPane(userTable), c);

		//Button: Add User
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;

		JButton button = new JButton("+");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				new UserFrame(client);
			}
		});
		panel.add(button, c);

		//Button: Remove User
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 1;

		button = new JButton("-");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				int rowIndex = userTable.getSelectedRow();
				String username = (String)userTable.getModel().getValueAt(rowIndex, 1);

				int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove " + username + " from the database?", "WARNING", JOptionPane.YES_NO_OPTION);
				if(reply == JOptionPane.YES_OPTION){
					client.sendEvent(new RemoveUserEvent(username));
				}
			}
		});
		panel.add(button, c);
	}

	public void createLocationsTab(JTabbedPane tabbedPane){
		JLabel label = new JLabel("Locations");
		label.setHorizontalTextPosition(JLabel.TRAILING);
		label.setIcon(createImageIcon("img/location-icon.png"));
		JPanel panel = new JPanel();

		tabbedPane.addTab("Locations", null, panel,"Here you can edit locations");
		tabbedPane.setTabComponentAt(tabbedPane.getTabCount()-1, label);

		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		//Title: Locations
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 0;

		JLabel title = new JLabel("Locations", SwingConstants.LEFT);
		title.setFont(new Font(title.getFont().getFontName(), Font.PLAIN, 30));
		title.setForeground(Color.decode("#fffe9a"));

		panel.add(title,c);

		//Table: Locations
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 3;

		client.sendEvent(new UpdateTableLocationsEvent(locationTable));
		userTable.setPreferredScrollableViewportSize(new Dimension(700, 300));
		userTable.setFillsViewportHeight(true);
		panel.add(new JScrollPane(locationTable), c);

		//Button: Add Location
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;

		JButton button = new JButton("+");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				new LocationFrame(parent, "Add");
			}
		});
		panel.add(button, c);

		//Button: Remove Location
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 1;

		button = new JButton("-");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				int rowIndex = locationTable.getSelectedRow();
				String location = (String)locationTable.getModel().getValueAt(rowIndex, 0);

				int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove " + location + " from the database?", "WARNING", JOptionPane.YES_NO_OPTION);
				if(reply == JOptionPane.YES_OPTION){
					client.sendEvent(new LocationRemoveEvent(location));
				}
			}
		});
		panel.add(button, c);

	}
	
	public void updateRoutes() {
		client.sendEvent(new UpdateTableRouteEvent(routeTable));
	}

	public void updatePrices(){
		client.sendEvent(new UpdateTablePriceEvent(priceTable));
	}
	
	public void updateLocations(){
		client.sendEvent(new UpdateTableLocationsEvent(locationTable));
	}

	public void updateUsers(){
		client.sendEvent(new UpdateTableUserEvent(userTable));
	}
	
	public void updateXML(TableModel tableModel){
		eventTable.setModel(tableModel);
	}

	public void updateOrigin(){
		fromDropDown.removeAllItems();
		for (String toCity : PriceRepository.getOriginCities()){
			fromDropDown.addItem(toCity);
		}
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

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public void setTabbedPane(JTabbedPane tabbedPane) {
		this.tabbedPane = tabbedPane;
	}

}
