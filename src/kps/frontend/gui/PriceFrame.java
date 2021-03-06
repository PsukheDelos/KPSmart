package kps.frontend.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import kps.backend.database.LocationRepository;
import kps.backend.database.MailRepository;
import kps.backend.database.PriceRepository;
import kps.distribution.event.CustomerPriceAddEvent;
import kps.distribution.event.CustomerPriceUpdateEvent;

public class PriceFrame extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	protected JLabel userLabel;
	protected JTextField userText;
	protected JButton loginButton;


	private ClientFrame parent;
	private String action;
	
	String[] priorities = {"Domestic Standard", "Domestic Air", "International Standard", "International Air"};
	
	private JComboBox origin = new JComboBox(LocationRepository.getLocationNames());
	private JComboBox destination = new JComboBox(LocationRepository.getLocationNames());
	private JComboBox priority = new JComboBox(priorities);
	private JTextField weightcost = new JTextField(20);
	private JTextField volumecost = new JTextField(20);

	Boolean edit = false;

	public PriceFrame(ClientFrame parent, String action){
		super("--<< " + action + " Price >>--");
		this.parent = parent;
		this.action = action;
		this.weightcost.setText("0");
		this.volumecost.setText("0");
		
		JPanel panel = new JPanel();
		initialise(panel);
		add(panel);
	}

	public PriceFrame(ClientFrame parent, String fromText, String toText, String priorityText,String weightText,String volText){
		super("--<< Edit Price >>--");
		this.parent = parent;
		this.action = "Edit";
		this.edit = true;
		this.origin.setSelectedItem(fromText);
		this.destination.setSelectedItem(toText);
		this.priority.setSelectedItem(priorityText);
		this.weightcost.setText(weightText);
		this.volumecost.setText(volText);

		this.origin.setEditable(false);
		this.destination.setEditable(false);
		this.priority.setEditable(false);
		
		this.origin.setEnabled(false);
		this.destination.setEnabled(false);
		this.priority.setEnabled(false);
		
		this.origin.setBackground(Color.LIGHT_GRAY);
		this.destination.setBackground(Color.LIGHT_GRAY);
		this.priority.setBackground(Color.LIGHT_GRAY);

		JPanel panel = new JPanel();
		initialise(panel);
		add(panel);
	}

	private void initialise(JPanel panel){
		setMinimumSize(new Dimension(320, 210));
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Input Constraints
		
		//weightcost
		weightcost.addKeyListener(new KeyAdapter() {
			
			public void keyTyped(KeyEvent e){
				char c = e.getKeyChar();
				if( ( (c < '0') || (c > '9') ) 
						&& (c != KeyEvent.VK_BACK_SPACE) 
						&& (c != KeyEvent.VK_PERIOD || weightcost.getText().contains(".") ) )
					e.consume();
			}
		});
		
		weightcost.addFocusListener(new FocusAdapter() {
			
			public void focusGained(FocusEvent e){
				if(weightcost.getText().equals("0")) weightcost.setText("");
			}
			
			public void focusLost(FocusEvent e){
				if(weightcost.getText().equals("")) weightcost.setText("0");
			}
		});
		
		//volumecost
		volumecost.addKeyListener(new KeyAdapter() {
			
			public void keyTyped(KeyEvent e){
				char c = e.getKeyChar();
				if( ( (c < '0') || (c > '9') ) 
						&& (c != KeyEvent.VK_BACK_SPACE) 
						&& (c != KeyEvent.VK_PERIOD || volumecost.getText().contains(".") ) )
					e.consume();
			}
		});
		
		volumecost.addFocusListener(new FocusAdapter() {
			
			public void focusGained(FocusEvent e){
				if(volumecost.getText().equals("0")) volumecost.setText("");
			}
			
			public void focusLost(FocusEvent e){
				if(volumecost.getText().equals("")) volumecost.setText("0");
			}
		});
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				showExitDialog();
			}
		});

		JPanel priceForm = new JPanel();
		priceForm.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		//Title: Prices
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 1;

		JLabel priceTitle = new JLabel(action + " Price", SwingConstants.LEFT);
		priceTitle.setFont(new Font(priceTitle.getFont().getFontName(), Font.PLAIN, 30));
		priceTitle.setForeground(Color.decode("#fffe9a"));

		priceForm.add(priceTitle,c);

		//Enter Origin
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		priceForm.add(new JLabel("Origin: ",SwingConstants.RIGHT),c);	

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 1;
		priceForm.add(origin,c);

		//Enter Destination
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		priceForm.add(new JLabel("Destination: ",SwingConstants.RIGHT),c);	

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 2;
		priceForm.add(destination,c);

		//Enter priority
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		priceForm.add(new JLabel("Priority: ",SwingConstants.RIGHT),c);	

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 3;
		priceForm.add(priority,c);

		//Enter Weight Price
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 4;
		priceForm.add(new JLabel("Weight Price: ",SwingConstants.RIGHT),c);	

		weightcost.addKeyListener(new KeyAdapter() {

			public void keyTyped(KeyEvent e){
				char c = e.getKeyChar();
				if( ( (c < '0') || (c > '9') ) && (c != KeyEvent.VK_BACK_SPACE) && (c != KeyEvent.VK_PERIOD || weightcost.getText().contains(".") ) ) e.consume();
			}

			public void keyReleased(KeyEvent e){

			}

		}

				);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 4;
		priceForm.add(weightcost,c);

		//Enter Volume Price
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 5;
		priceForm.add(new JLabel("Volume Price: ",SwingConstants.RIGHT),c);	

		volumecost.addKeyListener(new KeyAdapter() {

			public void keyTyped(KeyEvent e){
				char c = e.getKeyChar();
				if( ( (c < '0') || (c > '9') ) && (c != KeyEvent.VK_BACK_SPACE) && (c != KeyEvent.VK_PERIOD || volumecost.getText().contains(".") ) ) e.consume();
			}

			public void keyReleased(KeyEvent e){
			}

		});

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 5;
		priceForm.add(volumecost,c);

		//Submit Button
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 6;
		c.gridwidth = 1;
		JButton submit = new JButton();
		submit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(edit==true){
					CustomerPriceUpdateEvent c = new CustomerPriceUpdateEvent(origin.getSelectedItem().toString(), destination.getSelectedItem().toString(), priority.getSelectedItem().toString(),Double.valueOf(weightcost.getText()), Double.valueOf(volumecost.getText()));
					parent.client.sendEvent(c);
				}
				else{
					CustomerPriceAddEvent c = new CustomerPriceAddEvent(origin.getSelectedItem().toString(), destination.getSelectedItem().toString(), priority.getSelectedItem().toString(),Double.valueOf(weightcost.getText()), Double.valueOf(volumecost.getText()));
					parent.client.sendEvent(c);
				}
				dispose();
			}

		});
		submit.setText("Submit");
		priceForm.add(submit,c);

		add(priceForm);		
		pack();
		setLocationRelativeTo(null);
		// Rendering wasn't really happening, so adding this in to fix
		validate();
		repaint();
		setVisible(true);


	}

	protected void showExitDialog() {
		int answer = JOptionPane.showConfirmDialog(null, "You want to quit?", "Quit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (answer == JOptionPane.YES_OPTION)
			this.dispose();
	}












}
