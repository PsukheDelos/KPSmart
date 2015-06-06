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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import kps.distribution.event.CustomerPriceAddEvent;
import kps.distribution.event.CustomerPriceUpdateEvent;
import kps.distribution.event.TransportCostAddEvent;
import kps.distribution.event.TransportCostUpdateEvent;

public class CostFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	protected JLabel userLabel;
	protected JTextField userText;
	protected JButton loginButton;
	private Boolean edit = false;
	String[] types = { "Land", "Sea", "Air" };
	private ClientFrame parent;
	private JTextField company = new JTextField(20);
	private JTextField origin = new JTextField(20);
	private JTextField destination = new JTextField(20);
	private JComboBox<String> type = new JComboBox<String>(types);
	private JTextField weightcost = new JTextField(20);
	private JTextField volumecost = new JTextField(20);
	private JTextField maxweight = new JTextField(20);
	private JTextField maxvolume = new JTextField(20);
	private JTextField duration = new JTextField(20);
	private JTextField frequency = new JTextField(20);
	private JTextField day = new JTextField(20);

	public CostFrame(ClientFrame parent){
		super("--<< Edit Cost >>--");
		this.parent = parent;
		JPanel panel = new JPanel();
		initialise(panel);
		add(panel);
	}

	public CostFrame(ClientFrame parent, String company, String origin, String destination, String type,
			String weightcost, String volumecost, String maxweight, String maxvolume,
			String duration, String frequency, String day){
		super("--<< Edit Cost >>--");
		this.parent = parent;
		this.edit = true;

		this.company.setText(company);
		this.origin.setText(origin);
		this.destination.setText(destination);
		this.type.setSelectedItem(type);
		//		this.type.setText(type);
		this.weightcost.setText(weightcost);
		this.volumecost.setText(volumecost);
		this.maxweight.setText(maxweight);
		this.maxvolume.setText(maxvolume);
		this.duration.setText(duration);
		this.frequency.setText(frequency);
		this.day.setText(day);

		this.company.setEditable(false);
		this.origin.setEditable(false);
		this.destination.setEditable(false);
		this.type.setEditable(false);
		
		this.company.setEnabled(false);
		this.origin.setEnabled(false);
		this.destination.setEnabled(false);
		this.type.setEnabled(false);

		this.company.setBackground(Color.LIGHT_GRAY);
		this.origin.setBackground(Color.LIGHT_GRAY);
		this.destination.setBackground(Color.LIGHT_GRAY);
		this.type.setBackground(Color.LIGHT_GRAY);
		
		JPanel panel = new JPanel();
		initialise(panel);
		add(panel);
	}

	private void initialise(JPanel panel){
		//		setMinimumSize(new Dimension(320, 210));
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				showExitDialog();
			}
		});

		JPanel costForm = new JPanel();
		costForm.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		//Title: Edit Cost
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 1;

		JLabel title = new JLabel("Edit Cost", SwingConstants.LEFT);
		title.setFont(new Font(title.getFont().getFontName(), Font.PLAIN, 30));
		title.setForeground(Color.decode("#fffe9a"));

		costForm.add(title,c);

		//Company
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		JLabel label = new JLabel("Company: ",SwingConstants.RIGHT);
		costForm.add(label,c);	

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 1;
		costForm.add(company,c);

		//Origin
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		label = new JLabel("Origin: ",SwingConstants.RIGHT);
		costForm.add(label,c);	

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 2;
		costForm.add(origin,c);

		//Destination
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 3;
		label = new JLabel("Destination: ",SwingConstants.RIGHT);
		costForm.add(label,c);	

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 3;
		costForm.add(destination,c);

		//Type
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 4;
		label = new JLabel("Type: ",SwingConstants.RIGHT);
		costForm.add(label,c);	

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 4;
		costForm.add(type,c);

		//Weight Cost
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 5;
		label = new JLabel("Weight Cost: ",SwingConstants.RIGHT);
		costForm.add(label,c);	

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 5;
		costForm.add(weightcost,c);

		//Volume Cost
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 6;
		label = new JLabel("Volume Cost: ",SwingConstants.RIGHT);
		costForm.add(label,c);	

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 6;
		costForm.add(volumecost,c);

		//Max Weight
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 7;
		label = new JLabel("Max Weight: ",SwingConstants.RIGHT);
		costForm.add(label,c);	

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 7;
		costForm.add(maxweight,c);

		//Max Volume
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 8;
		label = new JLabel("Max Volume: ",SwingConstants.RIGHT);
		costForm.add(label,c);	

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 8;
		costForm.add(maxvolume,c);

		//Duration
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 9;
		label = new JLabel("Duration: ",SwingConstants.RIGHT);
		costForm.add(label,c);	

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 9;
		costForm.add(duration,c);

		//Frequency
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 10;
		label = new JLabel("Frequency: ",SwingConstants.RIGHT);
		costForm.add(label,c);	

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 10;
		costForm.add(frequency,c);

		//Day
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 11;
		label = new JLabel("Day: ",SwingConstants.RIGHT);
		costForm.add(label,c);	

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 11;
		costForm.add(day,c);

		//Submit Button
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 12;
		c.gridwidth = 1;
		JButton submit = new JButton();
		submit.setText("Submit");
		costForm.add(submit,c);
		submit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(edit==true){
					System.err.println("Submit Route Edit");
					TransportCostUpdateEvent t = new TransportCostUpdateEvent(company.getText(), origin.getText(), destination.getText(),
							(String)type.getSelectedItem(), Double.valueOf(weightcost.getText()), Double.valueOf(volumecost.getText()),
							Double.valueOf(maxweight.getText()), Double.valueOf(maxvolume.getText()), Double.valueOf(duration.getText()),
							Double.valueOf(frequency.getText()), day.getText());
					parent.client.sendEvent(t);
				}
				else{
					TransportCostAddEvent t = new TransportCostAddEvent(company.getText(), origin.getText(), destination.getText(),
							(String)type.getSelectedItem(), Double.valueOf(weightcost.getText()), Double.valueOf(volumecost.getText()),
							Double.valueOf(maxweight.getText()), Double.valueOf(maxvolume.getText()), Double.valueOf(duration.getText()),
							Double.valueOf(frequency.getText()), day.getText());
					parent.client.sendEvent(t);
				}
				dispose();
			}

		});
		add(costForm);		
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
