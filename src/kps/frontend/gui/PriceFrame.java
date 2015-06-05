package kps.frontend.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import kps.backend.database.PriceRepository;
import kps.distribution.event.CustomerPriceUpdateEvent;

public class PriceFrame extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	protected JLabel userLabel;
	protected JTextField userText;
	protected JButton loginButton;


	private ClientFrame parent;
	private String type;
	private JTextField origin = new JTextField(20);
	private JTextField destination = new JTextField(20);
	private JTextField priority = new JTextField(20);
	private JTextField weightcost = new JTextField(20);
	private JTextField volumecost = new JTextField(20);

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
		this.origin.setText(fromText);
		this.destination.setText(toText);
		this.priority.setText(priorityText);
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

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 4;
		priceForm.add(weightcost,c);

		//Enter Volume Price
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 5;
		priceForm.add(new JLabel("Volume Price: ",SwingConstants.RIGHT),c);	


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
				// TODO Auto-generated method stub
				if(edit==true){
					CustomerPriceUpdateEvent c = new CustomerPriceUpdateEvent(origin.getText(), destination.getText(), priority.getText(),Double.valueOf(weightcost.getText()), Double.valueOf(volumecost.getText()));
					parent.client.sendEvent(c);
//					parent.client.processEvent(c);
//					parent.getMailClient().sendEvent(new CustomerPriceUpdateEvent(fromText.getText(), toText.getText(), priorityText.getText(),Double.valueOf(weightText.getText()), Double.valueOf(volText.getText())));
//					PriceRepository.updatePrice(fromText.getText(), toText.getText(), priorityText.getText(), Double.valueOf(weightText.getText()), Double.valueOf(volText.getText()));
				}
				else{
					PriceRepository.add(origin.getText(), destination.getText(), priority.getText(), Double.valueOf(weightcost.getText()), Double.valueOf(volumecost.getText()));
				}
				parent.updatePrices();
				parent.updateOrigin();
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
