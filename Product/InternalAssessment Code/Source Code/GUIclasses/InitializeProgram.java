package GUIclasses;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import InternalCode.PersonalRecord;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;

public class InitializeProgram extends JFrame {

	private JPanel contentPane;
	private JButton btnNewButton;
	private JButton btnInitializeNewDatabase;
    private FilePanel panel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InitializeProgram frame = new InitializeProgram();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame with all buttons, fields, labels, and panes
	 */
	public InitializeProgram() {
		try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 248, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[][][grow][][]", "[][grow][][grow][]"));
		this.setTitle("Iniatilize Program");
		
		
		btnNewButton = new JButton("Initialize Program from Text File");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		// creates a new file using a inputted text file
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initializeFilePanel();
				replacePanel(panel);
				
			}
		});
		contentPane.add(btnNewButton, "cell 2 1,grow");
		
		btnInitializeNewDatabase = new JButton("Initialize New Program");
		// creates new program from scratch
		btnInitializeNewDatabase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PersonalRecord PR = new PersonalRecord();
				MainMenu main = new MainMenu(PR);
				main.setVisible(true);
				closeFrame();
			}
		});
		btnInitializeNewDatabase.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPane.add(btnInitializeNewDatabase, "cell 2 3,grow");
	}
	
	// creates the File Panel 
	private void initializeFilePanel()
	{
		 panel = new FilePanel(this);
	}
	
	// replaces the current JPanel with the parameter
	private void replacePanel(JPanel panel)
	{
		setContentPane(panel);
		invalidate();
		revalidate();
		
	}
	
	//resets the panel back to the current panel
	public void resetPanel()
	{
		setContentPane(contentPane);
		invalidate();
		revalidate();
	}
	
	// closes program
	public void closeFrame()
	{
		this.dispose();
	}
	
}
