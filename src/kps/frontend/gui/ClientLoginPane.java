package kps.frontend.gui;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class ClientLoginPane {

	public static void main(String[] args) {
		JLabel jUserName = new JLabel("User Name");
		JTextField userName = new JTextField();
		JLabel jPassword = new JLabel("Password");
		JTextField password = new JPasswordField();
		Object[] ob = {jUserName, userName, jPassword, password};
		int result = JOptionPane.showConfirmDialog(null, ob, "KPSmart Login", JOptionPane.OK_CANCEL_OPTION);

		if (result == JOptionPane.OK_OPTION) {
			String userNameValue = userName.getText();
			String passwordValue = password.getText();
			
		}

	}
}