package GUIclasses;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.PopupMenuListener;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.JPopupMenu;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.util.ArrayList;
import java.util.Enumeration;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;

import InternalCode.Meet;
import InternalCode.PersonalRecord;
import InternalCode.Runner;
import net.miginfocom.swing.MigLayout;
import java.awt.SystemColor;
import java.awt.Color;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

public class MainMenu extends TableFormatter {

	private JPanel contentPane;
	private JTextField fieldLastName;
	private PersonalRecord PR;
	private JTabbedPane tabbedPane;
	private JTable runnerTable;
	private JTable PRtable;
	private JTextArea changeLog;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane2;
	private JScrollPane scrollPane3;
	private JTextField fieldFirstName;
	private JRadioButton grade11;
	private String[][] PRdata;
	private String[][] runnerList;
	private String[] headers = { " # ", "Time (PR's)", "Grade", "Level", "Name", "Meet Name" };
	private ButtonGroup groupGrade;
	private ButtonGroup groupLevel;
	private JButton btnAdd;
	private JButton btnSearchRunner;
	private MeetMenu myMeets;
	private JPopupMenu popupMenu;
	private JPopupMenu popupMenu2;
	private RaceMenu raceWindow;


	/**
	 * Create the frame.
	 */
	public MainMenu(PersonalRecord database) {

		PR = database;
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 792, 541);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 248, 255));
		contentPane.setForeground(new Color(153, 180, 209));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setTitle("Main Menu");
		setContentPane(contentPane);
		contentPane.setLayout(
				new MigLayout("", "[][grow][][][][][]", "[][][][][][][][][][][][][][][][][][grow][][][][][][][][][]"));

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(new Color(0, 255, 255));

		contentPane.add(tabbedPane, "cell 1 1 1 26,grow");

		ToolTipManager.sharedInstance().setInitialDelay(0);
		runnerList = PR.getDataMatrix();
		runnerTable = initializeLog(runnerList, headers);
		runnerTable.setRowSelectionAllowed(true);
		runnerTable.setComponentPopupMenu(popupMenu);
		scrollPane = new JScrollPane(runnerTable);
		scrollPane.setViewportBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		tabbedPane.addTab("Runner Interface", null, scrollPane, null);
		updateInterfaceTab(runnerTable);

		changeLog = new JTextArea();
		changeLog.setEditable(false);
		changeLog.setText("Database initialized");
		scrollPane3 = new JScrollPane(changeLog);
		tabbedPane.addTab("Change Log", null, scrollPane3, null);

		JLabel lblFirstName = new JLabel("First Name");
		contentPane.add(lblFirstName, "cell 3 2 2 1");

		fieldFirstName = new JTextField();
		fieldFirstName.setColumns(10);
		contentPane.add(fieldFirstName, "cell 3 3 4 1,growx");

		JLabel lblLastName = new JLabel("Last Name");
		contentPane.add(lblLastName, "cell 3 4 2 1");

		fieldLastName = new JTextField();
		contentPane.add(fieldLastName, "cell 3 5 4 1,growx");
		fieldLastName.setColumns(10);

		JLabel lblGrade = new JLabel("Grade Level");
		contentPane.add(lblGrade, "cell 3 6 4 1,alignx left,aligny baseline");

		groupGrade = new ButtonGroup();

		JRadioButton grade9 = new JRadioButton("9");
		grade9.setBackground(new Color(240, 248, 255));
		grade9.setActionCommand("9");
		contentPane.add(grade9, "cell 3 8");

		JRadioButton grade10 = new JRadioButton("10");
		grade10.setBackground(new Color(240, 248, 255));
		grade10.setActionCommand("10");
		contentPane.add(grade10, "cell 4 8");

		grade11 = new JRadioButton("11");
		grade11.setBackground(new Color(240, 248, 255));
		grade11.setActionCommand("11");
		contentPane.add(grade11, "cell 5 8");
		groupGrade.add(grade11);

		groupGrade.add(grade10);
		groupGrade.add(grade9);

		JRadioButton grade12 = new JRadioButton("12");
		grade12.setBackground(new Color(240, 248, 255));
		grade12.setActionCommand("12");
		contentPane.add(grade12, "cell 6 8");
		groupGrade.add(grade12);

		JLabel lblTeam = new JLabel("Team Level");
		contentPane.add(lblTeam, "cell 3 9 4 1");

		JRadioButton rdbtnJv = new JRadioButton("JV");
		rdbtnJv.setBackground(new Color(240, 248, 255));
		rdbtnJv.setActionCommand("JV");
		contentPane.add(rdbtnJv, "cell 3 10");

		JRadioButton rdbtnV = new JRadioButton("V");
		rdbtnV.setBackground(new Color(240, 248, 255));
		rdbtnV.setActionCommand("V");
		contentPane.add(rdbtnV, "cell 4 10");

		groupLevel = new ButtonGroup();
		groupLevel.add(rdbtnJv);
		groupLevel.add(rdbtnV);

		btnAdd = new JButton("Add Runner");
		// adds new runner to the database based on inputed name, grade, and level
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String name = readName(fieldFirstName, fieldLastName);

				int grade = readGrade(groupGrade);
				if (grade != 0 && !name.equals(" ")) {

					boolean haslevel = groupLevel.getSelection() != null;
					String teamLevel = "";

					if (haslevel) {

						teamLevel = groupLevel.getSelection().getActionCommand();

					}

					if (PR.getRunner(name, grade, teamLevel) == null) {
						Runner runner;
						if (teamLevel.equals("Frosh/Soph") && (grade != 9 && grade != 10)) {
							JOptionPane.showMessageDialog(new JFrame(), "Frosh/Soph Runners must be in grades 9 or 10",
									"Error", JOptionPane.INFORMATION_MESSAGE);
						} else {
							runner = new Runner(name, grade, teamLevel, PR);
							updateScrollPanes();
							clearArguments();

							updateLog(name + " added to Database");
						}
					}

				} else {

					JOptionPane.showMessageDialog(new JFrame(), "Input a name AND select a grade to add a runner",
							"Error", JOptionPane.WARNING_MESSAGE);
				}

			}
		});
		btnAdd.setToolTipText("Adds a new runner that matches name, grad, and level(optional) inputs");
		contentPane.add(btnAdd, "cell 3 15 4 1,growx");

		JRadioButton rdbtnFroshsoph = new JRadioButton("Frosh/Soph");
		rdbtnFroshsoph.setBackground(new Color(240, 248, 255));
		rdbtnFroshsoph.setActionCommand("Frosh/Soph");
		contentPane.add(rdbtnFroshsoph, "cell 5 10 2 1,growy");
		groupLevel.add(rdbtnFroshsoph);

		btnSearchRunner = new JButton("Search Runner");
		// searches for runners that contain inputed name, and match inputed grade/level
		btnSearchRunner.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String name = readName(fieldFirstName, fieldLastName);
				int grade = readGrade(groupGrade);
				String level = readLevel(groupLevel);
				if (!(name.equals(" ") && grade == 0 && level.equals(""))) {
					runnerList = PR.findRunners(name, grade, level);

					runnerTable = initializeLog(runnerList, headers);
					updateInterfaceTab(runnerTable);
					updateLog("Search Complete");
				} else
					JOptionPane.showMessageDialog(new JFrame(), "No search criteria inputted", "Error",
							JOptionPane.WARNING_MESSAGE);
			}
		});

		btnSearchRunner.setToolTipText("<html><p width=\"480\">"
				+ "Displays all runners that CONTAIN name inputs, match grade inut, and match level input At least one input criteria is required (name, grade, level)"
				+ "</p></html>");
		contentPane.add(btnSearchRunner, "cell 3 13 4 1,growx");

		JButton btnRemoveRunner = new JButton("Remove Runner");
		// removes for runners that contain inputed name, and match inputed grade/level
		btnRemoveRunner.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String name = readName(fieldFirstName, fieldLastName);
				if (!(readGrade(groupGrade) == 0 && readLevel(groupLevel).equals("") && name.equals(" "))) {

					int dialogButton = JOptionPane.YES_NO_OPTION;
					int dialogResult = JOptionPane.showConfirmDialog(null,
							"Are you sure you want to delete ALL runners that contain inputted criteria?", "Warning",
							dialogButton);
					if (dialogResult == dialogButton) {
						PR.removeMultipleRunnersFromDatabase(name, readGrade(groupGrade), readLevel(groupLevel));
						updateScrollPanes();

						updateLog(name + " was deleted from Database");

					}
					clearArguments();

				} else {
					JOptionPane.showMessageDialog(new JFrame(), "No remove criteria inputted", "Error",
							JOptionPane.WARNING_MESSAGE);
				}

			}
		});
		btnRemoveRunner.setToolTipText("<html><p width=\"480\">"
				+"Removes ALL runners that meet input criteria. At least one input criteria is required (name, grade, level)"+ "</p></html>");
		contentPane.add(btnRemoveRunner, "cell 3 14 4 1,growx");

		JButton btnExportPr = new JButton("Export PR Sheet");
		// creates a "PRsheet" text file
		btnExportPr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PR.writePRsheet("PRsheet.txt");
				JOptionPane.showMessageDialog(new JFrame(), "PR Sheet has been exported",
						"", JOptionPane.INFORMATION_MESSAGE);
				updateLog("PR Sheet Exported");
			}
		});

		JButton btnAddMeet = new JButton("Open Meet Option Window");
		// adds new meet to database
		btnAddMeet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initializeMeets();
				updateLog("Meet Window Opened");
			}

		});

		JButton btnReset = new JButton("Reset");
		// resets main display
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateScrollPanes();

				updateLog("Main Menu Reset");
				clearArguments();
			}
		});

		JButton btnChangeRunner = new JButton("Change Runner");
		// changes runner's private data (name, grade, or level)
		btnChangeRunner.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {
					int row = runnerTable.convertRowIndexToModel(runnerTable.getSelectedRow());

					String name = (String) runnerTable.getModel().getValueAt(row, 4);
					String gradeLevel = (String) runnerTable.getModel().getValueAt(row, 2);
					int grade = Integer.parseInt(gradeLevel);
					String level = (String) runnerTable.getModel().getValueAt(row, 3);

					Runner temp = PR.getRunner(name, grade, level);

					boolean updated;
					boolean updatedName;

					String newName = readName(fieldFirstName, fieldLastName);
					if (!newName.equals(" ")) {
						temp.changeName(newName);
						updatedName = true;
						updated = true;
					} else
						updatedName = false;
					
					newName = name;

					int newGrade = readGrade(groupGrade);
					String newLevel = readLevel(groupLevel);
					if(newGrade == 0 && newLevel.equals(""))
					{
						updated = true;
					}
					else if (newGrade != 0 && newLevel.equals("")) {
						if (level.equals("Frosh/Soph") && (newGrade != 9 && newGrade != 10)) {
							updated = false;
						} else {
							temp.changeGradeLevel(newGrade);
							updated = true;
						}
					} else if (!newLevel.equals("") && newGrade == 0) {
						if (newLevel.equals("Frosh/Soph") && (grade != 9 && grade != 10)) {

							updated = false;
						} else {
							temp.changeTeamLevel(newLevel);
							updated = true;
						}
					} else {
						if (newLevel.equals("Frosh/Soph") && (newGrade != 9 && newGrade != 10)) {

							updated = false;
						} else {
							temp.changeTeamLevel(newLevel);
							temp.changeGradeLevel(newGrade);
							updated = true;
						}
					}

					if ( updated ) {

						updateLog(newName + " had their data changed");
						updateScrollPanes();
						clearArguments();
					} else if (updatedName) {
						updateLog(newName + " had their data changed");
						updateScrollPanes();
						clearArguments();
						JOptionPane.showMessageDialog(new JFrame(),
								"Only the Runner's name was changed, Frosh/Soph Runners must be in grades 9 or 10",
								"Alert", JOptionPane.INFORMATION_MESSAGE);

					} else {

						JOptionPane.showMessageDialog(new JFrame(), "Frosh/Soph Runners must be in grades 9 or 10",
								"Error", JOptionPane.INFORMATION_MESSAGE);
					}

				} catch (Exception E) {
					JOptionPane.showMessageDialog(new JFrame(), "A runner must be selected", "Error",
							JOptionPane.WARNING_MESSAGE);
				}

			}
		});
		btnChangeRunner.setToolTipText("Edits the Selected Runner's Data based on inputs");
		contentPane.add(btnChangeRunner, "cell 3 16 4 1,growx");
		btnReset.setToolTipText("Resets Display to display all Runners in database");
		contentPane.add(btnReset, "cell 3 21 4 1,growx");
		btnAddMeet.setToolTipText("Initialize Meet Option Window");
		contentPane.add(btnAddMeet, "cell 3 22 4 1,growx");

		JButton btnExportAlphabeticData = new JButton("Export All Data");
		// exports all data sorted alphabetically by name
		btnExportAlphabeticData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PR.writeAllData("AlphabeticData.txt");
				JOptionPane.showMessageDialog(new JFrame(), "All Data has been exported",
						"", JOptionPane.INFORMATION_MESSAGE);
				updateLog("Alphabetic List Exported");
			}
		});
		btnExportAlphabeticData.setToolTipText(
				"<html><p width=\"380\">Exports all data sorted by name to a new file named \"Alphabetic List\" If the file already exists, it will  be updated</p></html>");
		contentPane.add(btnExportAlphabeticData, "cell 3 23 4 1,growx");
		btnExportPr.setToolTipText("<html><p width=\"320\">"
				+ "Exports only PR data to a new file named \"PR Sheet\" If the file already exists, it will  be updated"
				+ "</p></html>");
		contentPane.add(btnExportPr, "cell 3 24 4 1,growx");

		JButton btnClose = new JButton("Exit Program");
		// closes and exits program
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int dialogButton = JOptionPane.YES_NO_OPTION;
				int dialogResult = JOptionPane.showConfirmDialog(null,
						"Are you sure you want to exit the program at this time?", "Warning", dialogButton);
				if (dialogResult == dialogButton) {

					dispose();

				}
			}
		});
		contentPane.add(btnClose, "cell 3 25 4 1,growx");

		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent e) {

				int dialogButton = JOptionPane.YES_NO_OPTION;
				int dialogResult = JOptionPane.showConfirmDialog(null,
						"Are you sure you want to exit the program at this time?", "Warning", dialogButton);
				if (dialogResult == dialogButton) {

					dispose();
				}

			}
		});

	}

	// creates the MeetOptionWindow
	private void initializeMeets() {
		MeetOptionWindow bill = new MeetOptionWindow(PR, this, myMeets, true);
		bill.setVisible(true);

		this.setVisible(false);
	}

	// Creates the race window
	private void initializeRaces(Runner runner) {

		raceWindow = new RaceMenu(PR, runner, this, myMeets, true);
		this.setVisible(false);
	}

	// clears all input fields
	public void clearArguments() {
		clearArguments(fieldFirstName, fieldLastName, groupGrade, groupLevel, null, null, null);
	}

	/**
	 * pre-condition : contentPane is initialized as a JPanel post-condition:
	 * Updates the data inside the runner list , updates the JTable, updates
	 * contentPane
	 */
	public void updateScrollPanes() {
		runnerList = PR.getDataMatrix();
		runnerTable = initializeLog(runnerList, headers);
		updateInterfaceTab(runnerTable);
	}

	// updates the runner interface JTable 
	private void updateInterfaceTab(JTable table) {
		
		popupMenu = initializePopup(table, popupMenu);
		createTableListener(table, popupMenu);
		scrollPane = new JScrollPane(table);
		tabbedPane.setComponentAt(0, scrollPane);

	}

    //updates the change log tab
	public void updateLog(String str) {
		String temp = changeLog.getText();
		temp += "\n\n" + str;
		changeLog.setText(temp);
		scrollPane3 = new JScrollPane(changeLog);
		tabbedPane.setComponentAt(1, scrollPane3);

	}

	// creates right click popup menu
	private JPopupMenu initializePopup(JTable table, JPopupMenu menu) {
		menu = new JPopupMenu();

		JMenuItem deleteItem = new JMenuItem("Delete Runner");
		deleteItem.setToolTipText("Removes runner and all races from database");
		//deletes selected runner from database
		deleteItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.convertRowIndexToModel(table.getSelectedRow());
				
				String name = (String) table.getModel().getValueAt(row, 4);
				String gradeLevel = (String) table.getModel().getValueAt(row, 2);
				int grade = Integer.parseInt(gradeLevel);
				String level = (String) table.getModel().getValueAt(row, 3);
				PR.removeSingleRunnerFromDatabase(name, grade, level);

				updateScrollPanes();
				updateLog(name + " was deleted from Database");
			}
		});
		menu.add(deleteItem);

		JMenuItem editRaces = new JMenuItem("Edit Races");
	    editRaces.setToolTipText("Opens Race Menu");
	    // opens Race Menu with data from selected runner
		editRaces.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int row = table.convertRowIndexToModel(table.getSelectedRow());

				String name = (String) table.getModel().getValueAt(row, 4);
				String gradeLevel = (String) table.getModel().getValueAt(row, 2);
	
				int grade = Integer.parseInt(gradeLevel);
				String level = (String) table.getModel().getValueAt(row, 3);

				Runner runner = null;
				if (level != "")
					runner = PR.getRunner(name, grade, level);
				else
					runner = PR.getRunner(name, grade);

				initializeRaces(runner);

				updateScrollPanes();

				updateLog("Race Window Opened");
			}

		});
		menu.add(editRaces);

		return menu;
	}

}
