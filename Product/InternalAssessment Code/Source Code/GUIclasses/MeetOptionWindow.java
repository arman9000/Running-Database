package GUIclasses;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import InternalCode.PersonalRecord;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;

public class MeetOptionWindow extends JFrame {

	private JPanel addPanel;
	private JPanel contentPane;
	private MeetMenu myMeets;
	private MainMenu mainMenu;
	private PersonalRecord PR;
	private boolean source;


	/**
	 * Create the frame.
	 */
	public MeetOptionWindow(PersonalRecord database, MainMenu main, MeetMenu toUpdate, boolean fromMainMenu) {
		source = fromMainMenu;
		mainMenu = main;
		PR = database;
		myMeets = toUpdate;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 541, 404);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 248, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		this.setTitle("Meet Option Window");
		myMeets = new MeetMenu(database,main, this);


		JButton btnAddMeet = new JButton("Add a New Meet");
		btnAddMeet.setFont(new Font("Tahoma", Font.PLAIN, 25));
		// Moves to the AddMeetPanel to prompt for meet name
		btnAddMeet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initializeMeetPanel();
			  replacePanel(addPanel);
			}
		});
		btnAddMeet.setBounds(10, 11, 505, 169);
		btnAddMeet.setToolTipText("Moves through series of steps to add a new meet");
		contentPane.add(btnAddMeet);
		
		JButton btnManageExistingMeets = new JButton("Manage Existing Meets");
		btnManageExistingMeets.setFont(new Font("Tahoma", Font.PLAIN, 25));
		// Opens Meet Menu
		btnManageExistingMeets.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(database.getMeets().size() == 0)
				{
					int dialogButton = JOptionPane.YES_NO_OPTION;
					int dialogResult = JOptionPane.showConfirmDialog(null,
							"There are no meets in the database, are you sure you want to open meet menu?", "Warning", dialogButton);
					if (dialogResult == dialogButton) {
						createMeetWindow();
				        closeFrame();
							
						}
				}
				else
				{
				createMeetWindow();
			        closeFrame();
				}
			}
		});
		btnManageExistingMeets.setBounds(10, 191, 505, 169);
		btnManageExistingMeets.setToolTipText("Opens Meet Menu");
		contentPane.add(btnManageExistingMeets);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent e) {

		        e.getWindow().dispose();
		        if(source)
		        {
		        mainMenu.setVisible(true);
		        mainMenu.clearArguments();
		        }
		        else
		        {
		        myMeets.setVisible(true);
		        myMeets.clearArguments();
		        	
		        }
		      
		       
		    }
		});
	}
	
	public void createMeetWindow()
	{
		myMeets = new MeetMenu(PR, mainMenu,this);
		 myMeets.setVisible(true);
		
	}
	public void initializeMeetPanel()
	{
		addPanel = new AddMeetPanel(this, myMeets, PR, mainMenu, source) ;
	}
	
	public void replacePanel(JPanel panel)
	{
		setContentPane(panel);
		invalidate();
		revalidate();
		
	}
	
	public void setSource(boolean fromMainMenu)
	{
		source = fromMainMenu;
	}
	public JPanel getAddMeetPanel()
	{
		return addPanel;
	}
	
	
	public void resetPanel()
	{
		setContentPane(contentPane);
		invalidate();
		revalidate();
	}
	public MeetMenu getMeetWindow()
	{
		return myMeets;
	}
	
	public void updateLog(String str)
	{
		mainMenu.updateLog(str);
	}
	
	public void closeFrame()
	{

		this.dispose();
	}
	
	
}
