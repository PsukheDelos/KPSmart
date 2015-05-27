package kps.frontend.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class RouteFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	protected JLabel userLabel;
	protected JTextField userText;
	protected JButton loginButton;


	private ClientFrame parent;
	private String type;
	private JTextField fromText = new JTextField(20);
	private JTextField toText = new JTextField(20);
	private JTextField methodText = new JTextField(20);
	private JTextField categoryText = new JTextField(20);

	public RouteFrame(ClientFrame parent, String type){
		super("--<< " + type + " Route >>--");
		this.parent = parent;
		this.type = type;
		JPanel panel = new JPanel();
		initialise(panel);
		add(panel);
	}

	public RouteFrame(ClientFrame parent, String fromText, String toText, String methodText,String categoryText){
		super("--<< Edit Route >>--");
		this.parent = parent;
		this.type = "Edit";
		
		this.fromText.setText(fromText);
		this.toText.setText(toText);
		this.methodText.setText(methodText);
		this.categoryText.setText(categoryText);
		
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

		JLabel priceTitle = new JLabel(type + " Route", SwingConstants.LEFT);
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
		JLabel priority = new JLabel("Method: ",SwingConstants.RIGHT);
		priceForm.add(priority,c);	

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 3;
		priceForm.add(methodText,c);

		//Enter Weight Price
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 1;
		JLabel weightPrice = new JLabel("Category: ",SwingConstants.RIGHT);
		priceForm.add(weightPrice,c);	

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 4;
		priceForm.add(categoryText,c);

		//Submit Button
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 5;
		c.gridwidth = 1;
		JButton submit = new JButton();
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
