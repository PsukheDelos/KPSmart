package kps.frontend.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import kps.backend.users.User;
import kps.frontend.MailClient;

public class ClientLoginFrame extends JFrame{
	
	private MailClient mailClient;
	protected JLabel userLabel;
	protected JTextField userText;
	protected JLabel passwordLabel;
	protected JPasswordField passwordText;
	protected JButton loginButton;
	
	private ClientFrame parent;
	
	private static Random rand = new Random(5225);

	public ClientLoginFrame(MailClient mailClient, ClientFrame parent){
		super("Login");
		this.mailClient = mailClient;
		this.parent = parent;
		JPanel panel = new JPanel();
		initialise(panel);
		add(panel);
	}
	
	private void initialise(JPanel panel){
		setMinimumSize(new Dimension(300, 150));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel loginForm = new JPanel();
		loginForm.setLayout(new GridLayout(3, 1, 10, 10));
		
		JPanel usernamePanel = new JPanel();
		usernamePanel.setLayout(new GridLayout(1, 2));
		
		userLabel = new JLabel("Username");
		//userLabel.setBounds(10, 10, 80, 25);
		usernamePanel.add(userLabel);

		userText = new JTextField(20);
		//userText.setBounds(100, 10, 160, 25);
		usernamePanel.add(userText);
		
		JPanel passwordPanel = new JPanel();
		passwordPanel.setLayout(new GridLayout(1, 2));

		passwordLabel = new JLabel("Password");
		//passwordLabel.setBounds(10, 40, 80, 25);
		passwordPanel.add(passwordLabel);

		passwordText = new JPasswordField(20);
		//passwordText.setBounds(100, 40, 160, 25);
		passwordPanel.add(passwordText);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 2, 20, 10));

		loginButton = new JButton("Login");
		//loginButton.setBounds(10, 80, 80, 25);
		loginButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				// Plain Text Username
				String username = userText.getText();
				String password = null;
				// Nab the Password, and hash it immediately
				try {
					password = passwordHash(passwordText.getPassword());
				} catch (NoSuchAlgorithmException | InvalidKeySpecException | UnsupportedEncodingException ex) {
					ex.printStackTrace();
				}
						
				mailClient.authenticateUser(username, password);
				User user = mailClient.getCurrentUser();
				while(user == null){
					//TODO: Better Lock plz
					mailClient.update();
					user = mailClient.getCurrentUser();
				}
				setVisible(false);
				parent.setEnabled(true);
				parent.setTitle(parent.getTitle()+ " User: " + mailClient.getCurrentUser().username);
				parent.requestFocus();
				// We don't need this anymore, so we should dispose it.
				dispose();
				
			}
			
			private String passwordHash(char[] cs) throws NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException{
			       MessageDigest digest = MessageDigest.getInstance("SHA-1");
			       digest.reset();
			       return new BigInteger(1, digest.digest(new String(cs).getBytes("UTF-8"))).toString(16);				
			}
		});
		buttonPanel.add(loginButton);
		
		JButton cancelButton = new JButton("Cancel");
		//cancelButton.setBounds(180, 80, 80, 25);
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		buttonPanel.add(cancelButton);
				
		loginForm.add(usernamePanel);
		loginForm.add(passwordPanel);
		loginForm.add(buttonPanel);
		// Padding for Styling.
		
		add(new JPanel(), BorderLayout.EAST);
		add(new JPanel(), BorderLayout.WEST);
		add(new JPanel(), BorderLayout.NORTH);
		add(new JPanel(), BorderLayout.SOUTH);
		
		
		add(loginForm);
		
		pack();
		setLocationRelativeTo(null);
		// Rendering wasn't really happening, so adding this in to fix
		validate();
		repaint();
		setVisible(true);
		
	
	}
	
	
	
	
	
	
	
	
	

}
