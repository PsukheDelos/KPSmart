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

import kps.distribution.event.CustomerPriceAddEvent;
import kps.distribution.event.CustomerPriceUpdateEvent;
import kps.distribution.event.LocationAddEvent;
import kps.distribution.event.LocationUpdateEvent;

public class LocationFrame extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	protected JLabel userLabel;
	protected JTextField userText;
	protected JButton loginButton;


	private ClientFrame parent;
	private JTextField location = new JTextField(20);
	private JTextField lon = new JTextField(20);
	private JTextField lat = new JTextField(20);

	Boolean edit = false;

	public LocationFrame(ClientFrame parent){
		super("--<< Edit Location >>--");
		this.parent = parent;
		JPanel panel = new JPanel();
		initialise(panel);
		add(panel);
	}

	public LocationFrame(ClientFrame parent, String location, String lon,String lat){
		super("--<< Edit Location >>--");
		this.parent = parent;
		this.edit = true;
		this.location.setText(location);
		this.lon.setText(lon);
		this.lat.setText(lat);

		this.location.setEditable(false);
		
		this.location.setEnabled(false);
		
		this.location.setBackground(Color.LIGHT_GRAY);

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

//		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		//Title: Locations
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 1;

		JLabel title = new JLabel("Edit Location", SwingConstants.LEFT);
		title.setFont(new Font(title.getFont().getFontName(), Font.PLAIN, 30));
		title.setForeground(Color.decode("#fffe9a"));

		panel.add(title,c);

		//Enter Location
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = c.gridy + 1;
		panel.add(new JLabel("Location: ",SwingConstants.RIGHT),c);	

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		panel.add(location,c);

		//Enter Longitude
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = c.gridy + 1;
		panel.add(new JLabel("Longitude: ",SwingConstants.RIGHT),c);	

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		panel.add(lon,c);

		//Enter Latitude
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = c.gridy + 1;
		panel.add(new JLabel("Latitude: ",SwingConstants.RIGHT),c);	


		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		panel.add(lat,c);

		//Submit Button
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = c.gridy + 1;
		c.gridwidth = 1;
		JButton submit = new JButton();
		submit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(edit==true){
					LocationUpdateEvent c = new LocationUpdateEvent(location.getText(), Double.valueOf(lon.getText()), Double.valueOf(lat.getText()));
					parent.client.sendEvent(c);
				}
				else{
					LocationAddEvent c = new LocationAddEvent(location.getText(), Double.valueOf(lon.getText()), Double.valueOf(lat.getText()));
					parent.client.sendEvent(c);
				}
				dispose();
			}

		});
		submit.setText("Submit");
		panel.add(submit,c);

		add(panel);		
		pack();
		setLocationRelativeTo(null);
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
