package kps.frontend.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import kps.backend.database.PriceRepository;
import kps.distribution.event.CustomerPriceUpdateEvent;

public class PriceFrame extends JFrame{

	protected JLabel userLabel;
	protected JTextField userText;
	protected JButton loginButton;


	private ClientFrame parent;
	private String type;
	private JTextField fromText = new JTextField(20);
	private JTextField toText = new JTextField(20);
	private JTextField priorityText = new JTextField(20);
	private JTextField weightText = new JTextField(20);
	private JTextField volText = new JTextField(20);

	Boolean edit = false;

	public PriceFrame(ClientFrame parent, String type){
		super("--<< " + type + " Price >>--");
		this.parent = parent;
		this.type = type;
		JPanel panel = new JPanel();
		initialise(panel);
		add(panel);
	}

	public PriceFrame(ClientFrame parent, String fromText, String toText, String priorityText,String weightText,String volText){
		super("--<< Edit Price >>--");
		this.parent = parent;
		this.type = "Edit";
		this.edit = true;
		this.fromText.setText(fromText);
		this.toText.setText(toText);
		this.priorityText.setText(priorityText);
		this.weightText.setText(weightText);
		this.volText.setText(volText);

		this.fromText.setEditable(false);
		this.toText.setEditable(false);
		this.priorityText.setEditable(false);
		
		this.fromText.setEnabled(false);
		this.toText.setEnabled(false);
		this.priorityText.setEnabled(false);
		
		this.fromText.setBackground(Color.LIGHT_GRAY);
		this.toText.setBackground(Color.LIGHT_GRAY);
		this.priorityText.setBackground(Color.LIGHT_GRAY);

		JPanel panel = new JPanel();
		initialise(panel);
		add(panel);
	}

	private void initialise(JPanel panel){
		setMinimumSize(new Dimension(320, 210));
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

		JLabel priceTitle = new JLabel(type + " Price", SwingConstants.LEFT);
		priceTitle.setFont(new Font(priceTitle.getFont().getFontName(), Font.PLAIN, 30));
		priceTitle.setForeground(Color.decode("#fffe9a"));

		priceForm.add(priceTitle,c);

		//Enter From
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		JLabel from = new JLabel("From: ",SwingConstants.RIGHT);
		priceForm.add(from,c);	

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 1;
		priceForm.add(fromText,c);

		//Enter To
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		JLabel to = new JLabel("To: ",SwingConstants.RIGHT);
		priceForm.add(to,c);	

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 2;
		priceForm.add(toText,c);

		//Enter priority
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		JLabel priority = new JLabel("Priority: ",SwingConstants.RIGHT);
		priceForm.add(priority,c);	

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 3;
		priceForm.add(priorityText,c);

		//Enter Weight Price
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 1;
		JLabel weightPrice = new JLabel("Weight Price: ",SwingConstants.RIGHT);
		priceForm.add(weightPrice,c);	

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 4;
		priceForm.add(weightText,c);

		//Enter Volume Price
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 1;
		JLabel volPrice = new JLabel("Volume Price: ",SwingConstants.RIGHT);
		priceForm.add(volPrice,c);	

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 5;
		priceForm.add(volText,c);

		//Submit Button
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 6;
		c.gridwidth = 1;
		JButton submit = new JButton();
		submit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(edit==true){
					parent.client.sendEvent(new CustomerPriceUpdateEvent(fromText.getText(), toText.getText(), priorityText.getText(),Double.valueOf(weightText.getText()), Double.valueOf(volText.getText())));
//					parent.getMailClient().sendEvent(new CustomerPriceUpdateEvent(fromText.getText(), toText.getText(), priorityText.getText(),Double.valueOf(weightText.getText()), Double.valueOf(volText.getText())));
//					PriceRepository.updatePrice(fromText.getText(), toText.getText(), priorityText.getText(), Double.valueOf(weightText.getText()), Double.valueOf(volText.getText()));
				}
				else{
					PriceRepository.addPrice(fromText.getText(), toText.getText(), priorityText.getText(), Double.valueOf(weightText.getText()), Double.valueOf(volText.getText()));
				}
				parent.updatePrice();
				parent.updateFrom();
				dispose();
			}

		});
		submit.setText("Submit");
		priceForm.add(submit,c);

		//		JPanel usernamePanel = new JPanel();
		//		usernamePanel.setLayout(new GridLayout(1, 2));
		//		
		//		userLabel = new JLabel("Username");
		//		usernamePanel.add(userLabel);
		//
		//		userText = new JTextField(20);
		//		usernamePanel.add(userText);
		//		
		//		JPanel passwordPanel = new JPanel();
		//		passwordPanel.setLayout(new GridLayout(1, 2));
		//		
		//		JPanel buttonPanel = new JPanel();
		//		buttonPanel.setLayout(new GridLayout(1, 2, 20, 10));
		//
		//		loginButton = new JButton("Login");
		//		loginButton.addActionListener(new ActionListener(){
		//			public void actionPerformed(ActionEvent e) {
		////				// Plain Text Username
		////				String username = userText.getText();
		////				String password = null;
		////				// Nab the Password, and hash it immediately
		////				try {
		////					password = passwordHash(passwordText.getPassword());
		////				} catch (NoSuchAlgorithmException | InvalidKeySpecException | UnsupportedEncodingException ex) {
		////					ex.printStackTrace();
		////				}
		////						
		////				mailClient.authenticateUser(username, password);
		////				User user = mailClient.getCurrentUser();
		////				while(user == null){
		////					//TODO: Better Lock plz
		////					mailClient.update();
		////					user = mailClient.getCurrentUser();
		////				}
		////				setVisible(false);
		////				parent.setEnabled(true);
		////				parent.setTitle(parent.getTitle()+ " User: " + mailClient.getCurrentUser().username);
		////				parent.requestFocus();
		////				// We don't need this anymore, so we should dispose it.
		////				dispose();
		//				
		//			}
		//			
		//		});
		//		buttonPanel.add(loginButton);
		//		
		//		JButton cancelButton = new JButton("Cancel");
		//		//cancelButton.setBounds(180, 80, 80, 25);
		//		cancelButton.addActionListener(new ActionListener() {
		//			public void actionPerformed(ActionEvent e) {
		//				showExitDialog();
		//			}
		//		});
		//		buttonPanel.add(cancelButton);
		//				
		//		priceForm.add(usernamePanel);
		//		priceForm.add(passwordPanel);
		//		priceForm.add(buttonPanel);
		//		// Padding for Styling.
		//		
		//		add(new JPanel(), BorderLayout.EAST);
		//		add(new JPanel(), BorderLayout.WEST);
		//		add(new JPanel(), BorderLayout.NORTH);
		//		add(new JPanel(), BorderLayout.SOUTH);
		//		
		//		
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
