package GUIclasses;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;

import InternalCode.Meet;
import InternalCode.PersonalRecord;
import InternalCode.Race;
import InternalCode.Runner;

import javax.swing.JLayeredPane;
import javax.swing.JMenuItem;

import net.miginfocom.swing.MigLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Color;
import javax.swing.border.CompoundBorder;

public class RaceMenu extends TableFormatter {

	private JPanel contentPane;
	private PersonalRecord PR;
	private Runner myRunner;
	private String[][] raceData;
	private JTable raceTable;
	private JPopupMenu popup;
	private JScrollPane scrollPane;
	private String[] headers = { " # ", "Meet Name", "Time" };
	private MainMenu mainMenu;
	private MeetMenu meetMenu;
	private NewTime myNewTime;
	private boolean mySource;

	/**
	 * Create the frame.
	 */
	public RaceMenu(PersonalRecord database, Runner runner, MainMenu main, MeetMenu meet, boolean fromMainMenu) {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setVisible(true);
		this.setTitle(runner.getName() + "'s Races");

		setBounds(100, 100, 562, 406);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 248, 255));
		contentPane.setForeground(new Color(240, 248, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[][grow][]", "[][][][][][][][][][][]"));

		PR = database;
		mainMenu = main;
		meetMenu = meet;
		myRunner = runner;
		mySource = fromMainMenu;
		raceData = myRunner.getRaceDataMatrix();

		raceTable = initializeLog(raceData, headers);

		scrollPane = new JScrollPane(raceTable);
		updateScrollPanes();
		contentPane.add(scrollPane, "cell 1 1 1 10,grow");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent e) {

				e.getWindow().dispose();
				if (fromMainMenu) {
					mainMenu.setVisible(true);
					mainMenu.clearArguments();
				} else
					meetMenu.setVisible(true);
			}
		});

	}

	
	/**
	 * pre-condition : contentPane is initialized as a JPanel 
	 * post-condition: Updates the data inside the alphabetic 
	 *				   list , updates the JTable, updates 
	 *				   contentPane
	 */
	public void updateScrollPanes() {

		if (scrollPane != null)
			updateMenus();
		raceData = myRunner.getRaceDataMatrix();
		raceTable = initializeLog(raceData, headers);
		contentPane.remove(scrollPane);
		popup = intializePopup(raceTable, popup);
		createTableListener(raceTable, popup);
		scrollPane = new JScrollPane(raceTable);
		scrollPane.setViewportBorder(new CompoundBorder());
		contentPane.add(scrollPane, "cell 1 3 2 14,grow");
		contentPane.revalidate();
		contentPane.repaint();

	}

	// creates right-click popup menu
	private JPopupMenu intializePopup(JTable table, JPopupMenu menu) {
		menu = new JPopupMenu();

		JMenuItem deleteItem = new JMenuItem("Delete Race");
		// deletes race from runner and updates corressponding meet
		deleteItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				String meetName = (String) table.getValueAt(row, 1);
				
				Meet current = PR.getMeet(meetName);
				String name = myRunner.getName();
				current.removeCompetitor(myRunner);
				mainMenu.updateLog(name + " is removed from " + meetName);
				updateScrollPanes();
			}
		});
		menu.add(deleteItem);

		JMenuItem changeTime = new JMenuItem("Change Time");
        // changes time for selected race
		changeTime.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int row = table.getSelectedRow();
				String meetName = (String) table.getValueAt(row, 1);
				Race race = myRunner.getRace(meetName);
				initializeNewTime(race);

			}
		});
		menu.add(changeTime);

		return menu;
	}

	// creates the new time window
	private void initializeNewTime(Race race) {
		myNewTime = new NewTime(PR, mainMenu, this, race);
	}

	// updates the Main Menu and Meet Menu when changes are made to races
	private void updateMenus() {
		mainMenu.updateScrollPanes();
		if (meetMenu != null) {
			meetMenu.updateScrollPanes();
		}
	}

}
