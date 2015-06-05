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

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import kps.backend.UserPermissions;
import kps.frontend.MailClient;
import kps.net.event.NewUserEvent;

public class NewUserFrame extends JFrame{
	
	
	protected JLabel userLabel;
	protected JTextField userText;
	protected JLabel passwordLabel;
	protected JPasswordField passwordText;
	protected JButton loginButton;
	
	private MailClient mailClient;
	
	public NewUserFrame(MailClient mailClient){
		this.mailClient = mailClient;
		initialise();
	}
	
	
	private void initialise(){
		setMaximumSize(new Dimension(300, 200));
		
		
		JPanel loginForm = new JPanel();
		loginForm.setLayout(new GridLayout(4, 1, 10, 10));
		
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
		
		JComboBox<UserPermissions> box = new JComboBox<UserPermissions>(new UserPermissions[]{UserPermissions.MANAGER, UserPermissions.CLERK });
		
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 2, 20, 10));
		
		JButton button = new JButton("Add");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Plain Text Username
				String username = userText.getText();
				String password = null;
				UserPermissions permissions = (UserPermissions)box.getSelectedItem(); 
				
				// Nab the Password, and hash it immediately
				try {
					password = passwordHash(passwordText.getPassword());
				} catch (NoSuchAlgorithmException | InvalidKeySpecException | UnsupportedEncodingException ex) {
					ex.printStackTrace();
				}	
				
				mailClient.sendEvent(new NewUserEvent(username, password, permissions));
				dispose();
				
			}
			
			private String passwordHash(char[] cs) throws NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException{
			       MessageDigest digest = MessageDigest.getInstance("SHA-1");
			       digest.reset();
			       return new BigInteger(1, digest.digest(new String(cs).getBytes("UTF-8"))).toString(16);				
			}
			
			
		});
		
		buttonPanel.add(button);
		
		button = new JButton("Cancel");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
		});
		
		buttonPanel.add(button);
		
		
		loginForm.add(usernamePanel);
		loginForm.add(passwordPanel);
		loginForm.add(box);
		loginForm.add(buttonPanel);
		// Padding for Styling.
		
		add(new JPanel(), BorderLayout.EAST);
		add(new JPanel(), BorderLayout.WEST);
		add(new JPanel(), BorderLayout.NORTH);
		add(new JPanel(), BorderLayout.SOUTH);
		
		add(loginForm);		
		pack();
		setLocationRelativeTo(null);
		
		setVisible(true);

		
		
	}

}
