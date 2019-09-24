package GUIclasses;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import InternalCode.PersonalRecord;
import InternalCode.Race;
import InternalCode.Time;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import net.miginfocom.swing.MigLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class NewTime extends JFrame {

	private JPanel contentPane;
	private JTextField minField;
	private JTextField secField;
	private JTextField milliSecField;
	private JLabel label;
	private JLabel label_1;
	private JButton btnCancel;
	private JButton btnDone;
	private JLabel lblNewLabel;
    private TableFormatter myParent;
    private PersonalRecord PR;
    private Race myRace;
    private MainMenu mainMenu;
	/**
	 * Launch the application.
	 */


	/**
	 * Create the frame.
	 */
	public NewTime(PersonalRecord database, MainMenu main, TableFormatter Parent, Race race) {
		
		mainMenu = main;
		PR = database;
		myParent = Parent;
		myRace = race;
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 248, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[][grow][][grow][][grow][]", "[][][][][][][]"));
		
		lblNewLabel = new JLabel("Enter New Time");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPane.add(lblNewLabel, "cell 1 1 5 1,alignx center");
		
		JLabel lblMin = new JLabel("Min");
		contentPane.add(lblMin, "cell 1 3,alignx center");
		
		JLabel lblSec = new JLabel("Sec");
		contentPane.add(lblSec, "cell 3 3,alignx center");
		
		JLabel lblMillisec = new JLabel("MilliSec");
		contentPane.add(lblMillisec, "cell 5 3,alignx center");
		
		minField = new JTextField();
		minField.setColumns(10);
		setNumericOnly(minField);
		contentPane.add(minField, "cell 1 4,growx");
		
		label = new JLabel(":");
		contentPane.add(label, "cell 2 4,alignx trailing");
		
		secField = new JTextField();
		secField.setColumns(10);
		setNumericOnly(secField);
		contentPane.add(secField, "cell 3 4,growx");
		
		label_1 = new JLabel(":");
		contentPane.add(label_1, "cell 4 4,alignx trailing");
		
		milliSecField = new JTextField();
		milliSecField.setColumns(10);
		setNumericOnly(milliSecField);
		contentPane.add(milliSecField, "cell 5 4,growx");
		
		
		this.setVisible(true);
		btnCancel = new JButton("Cancel");
		// Closes New Time window and returns to parent window
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		
			    myParent.setVisible(true);
			    dispose();
			}
		});
		contentPane.add(btnCancel, "cell 1 6,growx");
		
		btnDone = new JButton("Done");
		// Changes time for selected runner and returns to parent window
		btnDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Time time = readTime();
				if(time == null || time.isEmpty())
				{
					JOptionPane.showMessageDialog(new JFrame(), 
							"At least one field (minutes, seconds, milliseconds) must have a non-zero value"
							+ " and seconds must be below 60", "Error",
							JOptionPane.WARNING_MESSAGE);
				}
				else
				{
				myRace.changeTime(time);
				mainMenu.updateLog("Time is changed");
				myParent.updateScrollPanes();
				myParent.setVisible(true);
				dispose();
			}
			}
		});
		contentPane.add(btnDone, "cell 5 6,growx");
		

		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent e) {

				e.getWindow().dispose();

				myParent.setVisible(true);
			}
		});
	}
	
	public void setNumericOnly(JTextField jTextField) {
		jTextField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if ((!Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
					e.consume();
				}
				 if (jTextField.getText().length() >= 2 ) // limit to 3 characters
		                e.consume();
			}
		});
	}
	
	private Time readTime() {
		int min;
		int seconds;
		int millisec;

		if (minField.getText().equals("") && secField.getText().equals("")
				&& milliSecField.getText().equals("")) {
			return null;
		} else if(!secField.getText().equals("") && Integer.parseInt(secField.getText()) >59)
		{
			return null;
		}else {
			if (!minField.getText().equals(""))
				min = Integer.parseInt(minField.getText());
			else
				min = 0;

			if (!secField.getText().equals(""))
				seconds = Integer.parseInt(secField.getText());
			else
				seconds = 0;
			if (!milliSecField.getText().equals(""))
				millisec = Integer.parseInt(milliSecField.getText());
			else
				millisec = 0;

		}

		Time time = new Time(min, seconds, millisec);
		return time;
		
	}

}
