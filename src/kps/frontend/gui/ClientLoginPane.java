package kps.frontend.gui;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import kps.backend.users.User;
import kps.frontend.MailClient;

public class ClientLoginPane {
	
	private MailClient mailClient;
	private ClientFrame parent;
	
	public ClientLoginPane(MailClient mailClient, ClientFrame parent){
		this.mailClient = mailClient;
		this.parent = parent;
		
		initialise();
	}
	
	private void initialise(){
		
		JLabel username = new JLabel("Username");
		JTextField usernameField = new JTextField(20);
		
		JLabel password = new JLabel("Password");
		JPasswordField passwordField = new JPasswordField();
		
		Object[] paneObjects = new Object[]{username, usernameField, password, passwordField};
		
		int result = JOptionPane.showConfirmDialog(null, paneObjects, "Login", JOptionPane.OK_CANCEL_OPTION);
		
		if (result == JOptionPane.OK_OPTION) {
			String usr = usernameField.getText();
			String pwd = null;
			try{
				hash(passwordField.getPassword());
			}catch (NoSuchAlgorithmException | InvalidKeySpecException | UnsupportedEncodingException ex) {
				ex.printStackTrace();
			}
			mailClient.authenticateUser(usr, pwd);
			User user = mailClient.getCurrentUser();
			while(user == null){
				//TODO: Better Lock plz
				mailClient.update();
				user = mailClient.getCurrentUser();
			}
			parent.setEnabled(true);
			parent.setTitle(parent.getTitle()+ " User: " + mailClient.getCurrentUser().username);
			parent.requestFocus();			
		}
		
		
	}
	
	private String hash(char[] password) throws NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException{
	       MessageDigest digest = MessageDigest.getInstance("SHA-1");
	       digest.reset();
	       return new BigInteger(1, digest.digest(new String(password).getBytes("UTF-8"))).toString(16);				
	}
}