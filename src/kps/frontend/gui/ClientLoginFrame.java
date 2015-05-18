package kps.frontend.gui;

import java.awt.Dimension;
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
		panel.setLayout(null);

		userLabel = new JLabel("Username");
		userLabel.setBounds(10, 10, 80, 25);
		panel.add(userLabel);

		userText = new JTextField(20);
		userText.setBounds(100, 10, 160, 25);
		panel.add(userText);

		passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(10, 40, 80, 25);
		panel.add(passwordLabel);

		passwordText = new JPasswordField(20);
		passwordText.setBounds(100, 40, 160, 25);
		panel.add(passwordText);

		loginButton = new JButton("Login");
		loginButton.setBounds(10, 80, 80, 25);
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
				
				System.out.println("Username: "+ username);
				System.out.println("Password: "+ password);
				
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
		panel.add(loginButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setBounds(180, 80, 80, 25);
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);	
			}
		});
		panel.add(cancelButton);
			
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	
	}
	
	
	
	
	
	
	
	
	

}
