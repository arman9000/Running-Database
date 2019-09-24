package GUIclasses;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import InternalCode.Meet;
import InternalCode.PersonalRecord;
import InternalCode.Race;
import InternalCode.Runner;
import InternalCode.Time;
import net.miginfocom.swing.MigLayout;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextArea;

import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import java.awt.Color;

public class MeetMenu extends TableFormatter {

	private JPanel contentPane;
	private MainMenu mainMenu;
	private JScrollPane scrollPane;
	private PersonalRecord PR;
	private JComboBox comboBox;
	private JTable meetTable;
	private JPopupMenu popup;

	private String[] headers = { " # ", "Time", "Grade", "Level", "Name" };
	private String[][] data;
	private Meet current;
	private ArrayList<Meet> allMeets;
	private JLabel lblFirstName;
	private JTextField fieldFirstName;
	private JLabel label;
	private JTextField fieldLastName;
	private JLabel label_1;
	private JRadioButton grade9;
	private JRadioButton grade10;
	private JRadioButton grade11;
	private JRadioButton grade12;
	private JLabel label_2;
	private JRadioButton btnJV;
	private JRadioButton btnV;
	private JRadioButton btnFroshSoph;
	private JButton addRunner;
	private JLabel lblTime;
	private JLabel lblMin;
	private JLabel lblSec;
	private JTextField minutesField;
	private JTextField secondsField;
	private JTextField millisecField;
	private JLabel label_3;
	private JLabel label_4;
	private ButtonGroup groupGrade;
	private ButtonGroup groupLevel;
	private JLabel labelMillisec;
	private JButton closeButton;
	private RaceMenu raceWindow;
	private JButton filterBtn;
	private NewTime myNewTime;
	private MeetOptionWindow myParent;
	private JButton btnAddMeet;
	private JButton btnRemoveThisMeet;
	private JButton btnChangeSelectedRunner;
	private JButton btnReset;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public MeetMenu(PersonalRecord database, MainMenu main, MeetOptionWindow parent) {

		PR = database;
		mainMenu = main;
		myParent = parent;
		allMeets = PR.getMeets();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 790, 526);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 248, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		this.setTitle("Meet Menu");
		contentPane.setLayout(new MigLayout("", "[][grow][grow][][50:n:50][50:n:50][][50:n:50][][50:n:50][]",
				"[][][][][][][][][][][][][][][][grow][][][]"));

		lblFirstName = new JLabel("First Name");
		contentPane.add(lblFirstName, "cell 4 1 7 1");

		fieldFirstName = new JTextField();
		fieldFirstName.setColumns(10);
		contentPane.add(fieldFirstName, "cell 4 2 6 1,growx");

		meetTable = new JTable();

		lblSec = new JLabel("Sec");
		contentPane.add(lblSec, "cell 7 9,alignx center");

		labelMillisec = new JLabel("Millisec");
		contentPane.add(labelMillisec, "cell 9 9");

		initializeComboBox(null);
		comboBox.setEditable(true);
		comboBox.setSelectedItem("Select a Meet");
		comboBox.setEditable(false);

		// Allows a user to select a meet from drop down menu
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Object o = (comboBox.getSelectedItem());
				String meetName = ((String) o);
				if (!meetName.equals("Select a Meet")) {
					if (PR.getMeet(meetName) != null)

						current = PR.getMeet(meetName);
				}

				updateScrollPanes();

			}
		});
		contentPane.add(comboBox, "cell 1 1 2 1,growx");

		label = new JLabel("Last Name");
		contentPane.add(label, "cell 4 3 7 1");

		fieldLastName = new JTextField();
		fieldLastName.setColumns(10);
		contentPane.add(fieldLastName, "cell 4 4 6 1,growx");

		label_1 = new JLabel("Grade Level");
		contentPane.add(label_1, "cell 4 5 7 1");

		groupGrade = new ButtonGroup();

		grade9 = new JRadioButton("9");
		grade9.setBackground(new Color(240, 248, 255));
		grade9.setActionCommand("9");
		contentPane.add(grade9, "cell 4 6");

		grade10 = new JRadioButton("10");
		grade10.setBackground(new Color(240, 248, 255));
		grade10.setActionCommand("10");
		contentPane.add(grade10, "cell 5 6");

		grade11 = new JRadioButton("11");
		grade11.setBackground(new Color(240, 248, 255));
		grade11.setActionCommand("11");
		contentPane.add(grade11, "cell 7 6");

		grade12 = new JRadioButton("12");
		grade12.setBackground(new Color(240, 248, 255));
		grade12.setActionCommand("12");
		contentPane.add(grade12, "cell 9 6");

		groupGrade.add(grade9);
		groupGrade.add(grade10);
		groupGrade.add(grade11);
		groupGrade.add(grade12);

		label_2 = new JLabel("Team Level");
		contentPane.add(label_2, "cell 4 7 7 1");

		btnJV = new JRadioButton("JV");
		btnJV.setBackground(new Color(240, 248, 255));
		btnJV.setActionCommand("JV");
		contentPane.add(btnJV, "cell 4 8");

		btnV = new JRadioButton("V");
		btnV.setBackground(new Color(240, 248, 255));
		btnV.setActionCommand("V");
		contentPane.add(btnV, "cell 5 8");

		btnFroshSoph = new JRadioButton("Frosh/Soph");
		btnFroshSoph.setBackground(new Color(240, 248, 255));
		btnFroshSoph.setActionCommand("Frosh/Soph");
		contentPane.add(btnFroshSoph, "cell 7 8 4 1");

		groupLevel = new ButtonGroup();
		groupLevel.add(btnJV);
		groupLevel.add(btnV);
		groupLevel.add(btnFroshSoph);

		lblTime = new JLabel("Time");
		contentPane.add(lblTime, "cell 4 9 1 2");

		lblMin = new JLabel("Min");
		contentPane.add(lblMin, "cell 5 9,alignx center");

		minutesField = new JTextField();
		setNumericOnly(minutesField);
		contentPane.add(minutesField, "cell 5 10,growx");
		minutesField.setColumns(10);

		label_3 = new JLabel(":");
		contentPane.add(label_3, "cell 6 10");

		secondsField = new JTextField();
		setNumericOnly(secondsField);
		secondsField.setColumns(10);
		contentPane.add(secondsField, "cell 7 10,growx");

		label_4 = new JLabel(":");
		contentPane.add(label_4, "cell 8 10,alignx trailing");

		millisecField = new JTextField();
		setNumericOnly(millisecField);
		millisecField.setColumns(10);
		contentPane.add(millisecField, "cell 9 10,growx");

		closeButton = new JButton("Return to Main Menu");
		// returns to Main Menu
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();

				mainMenu.setVisible(true);

			}
		});

		filterBtn = new JButton("Remove Empty Meets");
		// removes all meets with no competitors from the database
		filterBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PR.filterMeets();

				Object o = (comboBox.getSelectedItem());
				String meetName = ((String) o);

				updateComboBox(meetName);
				mainMenu.updateLog("Empty Meets removed from databse");

			}
		});
		filterBtn.setToolTipText("Removes ALL meets with no competitors from the database");

		addRunner = new JButton("Add New Runner to this Meet");

		addRunner.setToolTipText("<html><p width=\"320\">"
				+ "Creates a New Runner with name, grade, and level inputs. Adds them to selected meet with inputted time."
				+ "</p></html>");
		contentPane.add(addRunner, "cell 4 11 6 1,growx");
		// creates a new runner and adds them to the database. Also accepts a time input
		// and adds the runner to the selected meet
		addRunner.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

					String selected = comboBox.getSelectedItem().toString();
					if (selected.equals("Select a Meet"))
						throw new Exception();
					String name = readName(fieldFirstName, fieldLastName);
					if (selected != null) {

						Time time = readTime();
						Runner runner = null;
						int grade = readGrade(groupGrade);
						if (grade != 0 && !name.equals(" ") && time != null && !time.isEmpty()) {
							boolean haslevel = groupLevel.getSelection() != null;
							String teamLevel = "";
							if (haslevel) {

								teamLevel = groupLevel.getSelection().getActionCommand();

							}
							if (PR.getRunner(name, grade, teamLevel) == null) {

								if (teamLevel.equals("Frosh/Soph") && (grade != 9 && grade != 10)) {
									JOptionPane.showMessageDialog(new JFrame(),
											"Frosh/Soph Runners must be in grades 9 or 10", "Error",
											JOptionPane.INFORMATION_MESSAGE);
								} else {
									runner = new Runner(name, grade, teamLevel, PR);

								}
							} else {
								JOptionPane.showMessageDialog(new JFrame(),
										"The runner defined by the inputted criteria is already a competitor in this meet",
										"Error", JOptionPane.WARNING_MESSAGE);
							}
							if (runner != null) {
								String temp = current.addCompetitor(runner, time);

								mainMenu.updateLog(temp);
								updateScrollPanes();
								clearArguments();
							}

						} else {
							JOptionPane.showMessageDialog(new JFrame(),
									"Name, Grade, and Time are required, At least one time field (minutes, seconds, or milliseconds) must have a non-zero value and seconds must be below 60 ",
									"Error", JOptionPane.WARNING_MESSAGE);
						}

					}
				} catch (Exception E) {
					JOptionPane.showMessageDialog(new JFrame(), "No meet is selected", "Error",
							JOptionPane.WARNING_MESSAGE);
				}

			}
		});

		btnRemoveThisMeet = new JButton("Remove this Meet");
		btnRemoveThisMeet.setToolTipText("Deletes Meet from database, removes the race from all competitors");
		// deletes the meet and removes all competitors from the meet. Also updates the
		// races of each runner
		btnRemoveThisMeet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String selected = comboBox.getSelectedItem().toString();
					if (selected.equals("Select a Meet"))
						throw new Exception();
					int dialogButton = JOptionPane.YES_NO_OPTION;
					int dialogResult = JOptionPane.showConfirmDialog(null,
							"Are you sure you want to remove this meet from the database?", "Warning", dialogButton);
					if (dialogResult == dialogButton) {
						String name = current.getName();
						PR.removeMeetFromDatabase(name);
						current = null;
						updateComboBox("");
						updateScrollPanes();
						mainMenu.updateLog("Meet: " + name + " was removed from databse");

					}
				} catch (Exception E) {
					JOptionPane.showMessageDialog(new JFrame(), "No meet is selected", "Error",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		btnChangeSelectedRunner = new JButton("Change Selected Runner");
		// Changes runner's private data (name, grade, or level) as well as their time
		// for selected meet
		btnChangeSelectedRunner.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Time time = readTime();

					int row = meetTable.convertRowIndexToModel(meetTable.getSelectedRow());

					String name = (String) meetTable.getModel().getValueAt(row, 4);

					String gradeLevel = (String) meetTable.getModel().getValueAt(row, 2);

					int grade = Integer.parseInt(gradeLevel);
					String level = (String) meetTable.getModel().getValueAt(row, 3);

					Runner temp = PR.getRunner(name, grade, level);

					boolean updatedTime = false;
					boolean sameTime = false;
					boolean updatedGradeAndLevel;
					boolean updatedName;
					boolean sameGradeAndLevel = false;

					String newName = readName(fieldFirstName, fieldLastName);

					if (!newName.equals(" ")) {
						temp.changeName(newName);
						updatedName = true;
					} else
						updatedName = false;
					newName = name;

					String forMessage = "";
					String forMessage2 = "";
					int newGrade = readGrade(groupGrade);
					String newLevel = readLevel(groupLevel);
					if (newGrade == 0 && newLevel.equals("")) {
						updatedGradeAndLevel = true;
						sameGradeAndLevel = true;
					} else if (newGrade != 0 && newLevel.equals("")) {
						if (level.equals("Frosh/Soph") && (newGrade != 9 && newGrade != 10)) {

							updatedGradeAndLevel = false;
						} else {
							forMessage = "grade was";
							forMessage2 = "grade";
							temp.changeGradeLevel(newGrade);
							updatedGradeAndLevel = true;
						}
					} else if (!newLevel.equals("") && newGrade == 0) {
						if (newLevel.equals("Frosh/Soph") && (grade != 9 && grade != 10)) {

							updatedGradeAndLevel = false;
						} else {
							forMessage = "level was";
							forMessage2 = "level";
							temp.changeTeamLevel(newLevel);
							updatedGradeAndLevel = true;
						}
					} else {
						if (newLevel.equals("Frosh/Soph") && (newGrade != 9 && newGrade != 10)) {

							updatedGradeAndLevel = false;
						} else {
							forMessage = "grade and level were";
							forMessage2 = "grade and level";
							temp.changeTeamLevel(newLevel);
							temp.changeGradeLevel(newGrade);
							updatedGradeAndLevel = true;
						}
					}

					if (minutesField.getText().length() == 0 && secondsField.getText().length() == 0
							&& millisecField.getText().length() == 0) {
						updatedTime = true;
						sameTime = true;
					} else if (time != null && !time.isEmpty()) {

						Time oldTime = temp.getRace(current.getName()).getTime();
						if (oldTime.equals(time)) {
							sameTime = true;
						} else
							temp.updateRace(temp.getRace(current.getName()), time);

						updatedTime = true;
					}

					if (updatedGradeAndLevel || updatedTime || updatedName) {
						mainMenu.updateLog(newName + " had their data changed");
						updateScrollPanes();
						clearArguments();
					}

					if (updatedName && updatedTime) {

						if (!updatedGradeAndLevel) {

							if (!sameTime) {

								JOptionPane.showMessageDialog(new JFrame(),
										"Only the Runner's name and their time were changed, Frosh/Soph Runners must be in grades 9 or 10",
										"Alert", JOptionPane.INFORMATION_MESSAGE);
							} else {

								JOptionPane.showMessageDialog(new JFrame(),
										"Only the Runner's name was changed, Frosh/Soph Runners must be in grades 9 or 10",
										"Alert", JOptionPane.INFORMATION_MESSAGE);
							}
						}
					} else if (updatedName && !updatedTime) {

						if (!updatedGradeAndLevel) {

							JOptionPane.showMessageDialog(new JFrame(),
									"Only the Runner's name was changed Frosh/Soph Runners must be in grades 9 or 10, At least one time field must have a non-zero value and seconds must be below 60",
									"Alert", JOptionPane.INFORMATION_MESSAGE);
						} else {

							if (sameGradeAndLevel) {

								JOptionPane.showMessageDialog(new JFrame(),
										"Only the Runner's name was changed, At least one time field must have a non-zero value and seconds must be below 60",
										"Alert", JOptionPane.INFORMATION_MESSAGE);
							} else {

								JOptionPane.showMessageDialog(new JFrame(), "Only the Runner's name, " + forMessage2
										+ " were changed, At least one time field must have a non-zero value and seconds must be below 60",
										"Alert", JOptionPane.INFORMATION_MESSAGE);
							}
						}

					} else if (updatedTime && !updatedName) {

						if (!updatedGradeAndLevel) {

							if (!sameTime) {

								JOptionPane.showMessageDialog(new JFrame(),
										"Only the Runner's time was changed, Frosh/Soph Runners must be in grades 9 or 10",
										"Alert", JOptionPane.INFORMATION_MESSAGE);
							} else {

								JOptionPane.showMessageDialog(new JFrame(),
										"Frosh/Soph Runners must be in grades 9 or 10", "Error",
										JOptionPane.INFORMATION_MESSAGE);
							}
						}

					} else {

						if (!updatedGradeAndLevel) {

							JOptionPane.showMessageDialog(new JFrame(),
									"Frosh/Soph Runners must be in grades 9 or 10, At least one time field must have a non-zero value and seconds must be below 60",
									"Alert", JOptionPane.INFORMATION_MESSAGE);
						} else {

							if (sameGradeAndLevel) {

								JOptionPane.showMessageDialog(new JFrame(),
										"At least one time field must have a non-zero value and seconds must be below 60",
										"Alert", JOptionPane.INFORMATION_MESSAGE);
							} else {

								JOptionPane.showMessageDialog(new JFrame(), "Only the Runner's " + forMessage
										+ " changed, At least one time field must have a non-zero value and seconds must be below 60",
										"Alert", JOptionPane.INFORMATION_MESSAGE);
							}
						}
					}

				} catch (Exception E) {
					JOptionPane.showMessageDialog(new JFrame(), "A runner must be selected", "Error",
							JOptionPane.WARNING_MESSAGE);
				}

			}
		});
		btnChangeSelectedRunner.setToolTipText("Changes Selected Runner's Data.");
		contentPane.add(btnChangeSelectedRunner, "cell 4 12 6 1,growx");

		contentPane.add(btnRemoveThisMeet, "cell 4 13 6 1,growx");

		btnReset = new JButton("Reset");
		// Resets Display
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateScrollPanes();

				mainMenu.updateLog("Meet Menu Reset");
				clearArguments();
			}
		});
		btnReset.setToolTipText("Resets Display to display all Runners in meet");
		contentPane.add(btnReset, "cell 4 14 6 1,growx");
		contentPane.add(filterBtn, "cell 4 16 6 1,growx");

		btnAddMeet = new JButton("Add New Meet");
		btnAddMeet.setToolTipText(
				"Opens a separate window with directions to create a meet with runners already in database");
		// Creates a new meet by returning to the Meet Option Window
		btnAddMeet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();

				myParent.setVisible(true);
				myParent.setSource(false);
				myParent.initializeMeetPanel();
				myParent.replacePanel(myParent.getAddMeetPanel());

			}
		});
		contentPane.add(btnAddMeet, "cell 4 17 6 1,growx");
		contentPane.add(closeButton, "cell 4 18 6 1,growx");

		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent e) {

				e.getWindow().dispose();

				mainMenu.setVisible(true);
			}
		});

	}

	// clears all the input fields
	public void clearArguments() {
		clearArguments(fieldFirstName, fieldLastName, groupGrade, groupLevel, minutesField, secondsField,
				millisecField);
	}

	/**
	 * pre-condition : contentPane has been initialized as a JPanel post-condition:
	 * Updates an existing or creates a new comboBox, if selected is the name of an
	 * object in the comboBox, the corresponding object will be selected
	 */
	public void updateComboBox(String selected) {
		if (comboBox != null) {
			contentPane.remove(comboBox);
		}
		initializeComboBox(selected);

	}

	/**
	 * pre-condition : contentPane has been initialized as a JPanel post-condition:
	 * Creates a new comboBox, if selected is the name of an object in the comboBox,
	 * the corresponding object will be selected
	 */
	private void initializeComboBox(String selected) {

		ArrayList<Meet> meets = PR.getMeets();
		String[] meetNames = new String[meets.size()];
		int index = -1;
		for (int i = 0; i < meetNames.length; i++) {
			String temp = meets.get(i).getName();
			meetNames[i] = temp;
			if (selected != null && temp.equals(selected)) {
				index = i;
			}
		}

		comboBox = new JComboBox(meetNames);
		comboBox.setEditable(true);
		if (index == -1)
			comboBox.setSelectedItem("Select a Meet");
		else
			comboBox.setSelectedItem(selected);
		comboBox.setEditable(false);

		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Object o = (comboBox.getSelectedItem());
				String meetName = ((String) o);
				if (!meetName.equals("Create New Meet")) {
					if (PR.getMeet(meetName) != null)

						current = PR.getMeet(meetName);
				}
				updateScrollPanes();
			}
		});

		contentPane.add(comboBox, "cell 1 1 2 1,growx");
		contentPane.revalidate();
		contentPane.repaint();

	}

	// reads time from inputs and creates Time object
	private Time readTime() {

		int min;
		int seconds;
		int millisec;

		if (minutesField.getText().equals("") && secondsField.getText().equals("")
				&& millisecField.getText().equals("")) {
			return null;
		} else if (!secondsField.getText().equals("") && Integer.parseInt(secondsField.getText()) > 59) {
			return null;
		} else {
			if (!minutesField.getText().equals(""))
				min = Integer.parseInt(minutesField.getText());
			else
				min = 0;

			if (!secondsField.getText().equals(""))
				seconds = Integer.parseInt(secondsField.getText());
			else
				seconds = 0;
			if (!millisecField.getText().equals(""))
				millisec = Integer.parseInt(millisecField.getText());
			else
				millisec = 0;

		}

		Time time = new Time(min, seconds, millisec);
		return time;

	}

	/**
	 * pre-condition : contentPane is initialized as a JPanel post-condition:
	 * Updates the data inside the alphabetic list , updates the JTable, updates
	 * contentPane
	 */
	public void updateScrollPanes() {
		if (current != null) {
			data = current.getDataMatrix();
			meetTable = initializeLog(data, headers);
		} else {
			data = new String[0][0];
			meetTable = initializeLog(data, headers);
		}

		if (scrollPane != null)
			contentPane.remove(scrollPane);
		popup = intializePopup(meetTable, popup);
		createTableListener(meetTable, popup);
		scrollPane = new JScrollPane(meetTable);

		contentPane.add(scrollPane, "cell 1 3 2 18,grow");
		contentPane.revalidate();
		contentPane.repaint();

		mainMenu.updateScrollPanes();
	}

	// creates Race Menu
	private void initializeRaces(Runner runner) {
		raceWindow = new RaceMenu(PR, runner, mainMenu, this, false);
		this.setVisible(false);
	}

	// creates a New Time window
	private void initializeNewTime(Race race) {
		myNewTime = new NewTime(PR, mainMenu, this, race);
	}

	// creates right-click popup menu
	private JPopupMenu intializePopup(JTable table, JPopupMenu menu) {
		menu = new JPopupMenu();

		JMenuItem deleteItem = new JMenuItem("Delete Runner");
		deleteItem.setToolTipText("Removes runner from this meet");
		// deletes selected runner from selected meet
		deleteItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.convertRowIndexToModel(table.getSelectedRow());

				String name = (String) table.getModel().getValueAt(row, 4);
				String gradeLevel = (String) table.getModel().getValueAt(row, 2);
				int grade = Integer.parseInt(gradeLevel);
				String level = (String) table.getModel().getValueAt(row, 3);

				current.removeCompetitor(current.getRunner(name, grade, level));

				updateScrollPanes();
				mainMenu.updateLog(name + " was deleted from meet");

			}
		});
		menu.add(deleteItem);

		JMenuItem changeTime = new JMenuItem("Change Time");
		changeTime.setToolTipText(
				"Open Another Window to Add New Time Parameters, This can also be done through the Change Selected Runner Button");
		// opens a New Time window to input a new time for selected runner
		changeTime.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.convertRowIndexToModel(table.getSelectedRow());
				String name = (String) table.getModel().getValueAt(row, 4);
				String gradeLevel = (String) table.getModel().getValueAt(row, 2);
				int grade = Integer.parseInt(gradeLevel);
				String level = (String) table.getModel().getValueAt(row, 3);
				Runner temp = PR.getRunner(name, grade, level);
				Race race = temp.getRace(current.getName());
				initializeNewTime(race);

			}
		});
		menu.add(changeTime);

		JMenuItem editRaces = new JMenuItem("Edit Races");
		editRaces.setToolTipText("Opens Race Menu");
		// opens Race Menu for selected runner
		editRaces.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int row = table.convertRowIndexToModel(table.getSelectedRow());
				String name = (String) table.getModel().getValueAt(row, 4);

				String gradeLevel = (String) table.getModel().getValueAt(row, 2);

				int grade = Integer.parseInt(gradeLevel);
				String level = (String) table.getModel().getValueAt(row, 3);

				Runner runner = current.getRunner(name, grade);
				initializeRaces(runner);
				mainMenu.updateLog("Race Window Opened");

			}
		});
		menu.add(editRaces);

		return menu;
	}
}
