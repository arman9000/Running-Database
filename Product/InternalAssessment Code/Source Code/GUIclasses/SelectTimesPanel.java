package GUIclasses;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import InternalCode.Meet;
import InternalCode.PersonalRecord;
import InternalCode.Race;
import InternalCode.Runner;
import InternalCode.Time;
import net.miginfocom.swing.MigLayout;

public class SelectTimesPanel extends JPanel {

	/**
	 * Create the panel.
	 */

	private JTextField minField;
	private JTextField secField;
	private JTextField milliSecField;
	private int[] myIndices;
	private String[] myRunnerStringData;
	private ArrayList<Runner> mySelectedRunners;
	private PersonalRecord PR;
	private MeetOptionWindow myParent;
	private int myIndex;
	private JLabel lblNewLabel;
	private Meet myMeet;
	private JPanel myPrevious;
	private ArrayList<Time> myTimes;
	private boolean source;

	public SelectTimesPanel(PersonalRecord database, MeetOptionWindow parent, JPanel previous, Meet meet,
			ArrayList<Runner> runnerData, boolean fromMainMenu) {
		setBackground(new Color(240, 248, 255));

		source = fromMainMenu;
		myMeet = meet;
		PR = database;
		myParent = parent;
		mySelectedRunners = runnerData;
		myRunnerStringData = getBasicData(runnerData);
		myIndex = 0;
		myPrevious = previous;
		myTimes = new ArrayList<Time>();

		setLayout(new MigLayout("", "[][grow][][grow][][grow][]", "[][][][][][][][][grow][]"));

		lblNewLabel = new JLabel("Enter Time for " + myRunnerStringData[myIndex]);

		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		add(lblNewLabel, "cell 1 1 5 1,alignx center");

		minField = new JTextField();
		minField.setColumns(10);
		setNumericOnly(minField);

		JLabel lblMin = new JLabel("Min");
		add(lblMin, "cell 1 3,alignx center");

		JLabel lblSec = new JLabel("Sec");
		add(lblSec, "cell 3 3,alignx center");

		JLabel lblMillisec = new JLabel("MilliSec");
		add(lblMillisec, "cell 5 3,alignx center");
		minField = new JTextField();
		minField.setColumns(10);
		setNumericOnly(minField);
		add(minField, "cell 1 4,growx");

		JLabel label = new JLabel(":");
		add(label, "cell 2 4,alignx trailing");

		secField = new JTextField();
		secField.setColumns(10);
		setNumericOnly(secField);
		add(secField, "cell 3 4,growx");

		JLabel label_1 = new JLabel(":");
		add(label_1, "cell 4 4,alignx trailing");

		milliSecField = new JTextField();
		milliSecField.setColumns(10);
		setNumericOnly(milliSecField);
		add(milliSecField, "cell 5 4,growx");

		JButton btnContinue;
		if (mySelectedRunners.size() != 1)
			btnContinue = new JButton("Continue");
		else
			btnContinue = new JButton("Finish");
		// if btnContinue displays "Continue" - this will move to the next runner and
		// ask for input
		// if btnContinue displays "Finish" - this will move to the Meet Menu after
		// creating a new meet
		btnContinue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Time temp = readTime();
				if (temp != null && !temp.isEmpty()) {

					if ((myIndex == 0 && myTimes.size() == 0) || myIndex == myTimes.size()) {
						myTimes.add(temp);
					} else if (myTimes.get(myIndex).compareTo(temp) != 0)
						myTimes.set(myIndex, temp);

					myIndex++;
					if (myIndex < myTimes.size())
						setFields();
					else

						clearArguments();
					if (btnContinue.getText().equals("Finish")) {
						myParent.resetPanel();

						initializeMeet();
						myParent.createMeetWindow();
						myParent.closeFrame();

					} else {

						if (!btnContinue.getText().equals("Finish")) {
							lblNewLabel.setText("Enter Time for " + myRunnerStringData[myIndex]);
							if (myIndex == mySelectedRunners.size() - 1) {
								btnContinue.setText("Finish");
							}
						}

					}

				}

				else
					JOptionPane.showMessageDialog(new JFrame(),
							"At least one field (minutes, seconds, milliseconds) must have a non-zero value and seconds must be below 60", "Error",
							JOptionPane.WARNING_MESSAGE);
			}

		});
		add(btnContinue, "cell 5 6,growx");

		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (myIndex == 0) {
					myParent.replacePanel(myPrevious);

				} else {
					btnContinue.setText("Continue");
					Time temp = readTime();
					if (temp != null && temp.compareTo(new Time(0, 0, 0)) != 0) {
						if (myIndex == myTimes.size()) {
							myTimes.add(temp);

						} else if (myTimes.get(myIndex).compareTo(temp) != 0) {
							myTimes.set(myIndex, temp);

						}

					}

					myIndex--;

					setFields();
					lblNewLabel.setText("Enter Time for " + myRunnerStringData[myIndex]);

				}

			}
		});
		add(btnBack, "cell 1 6,growx");

		JButton btnCancelAddingMeet = new JButton("Cancel Adding Meet");
		// returns to Meet Option Window if source is true, Meet Menu if source is false
		btnCancelAddingMeet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = myMeet.getName();
				PR.removeMeetFromDatabase(str);
				myParent.updateLog(str + " has been removed from databse");
				if (source) {
					myParent.resetPanel();
				} else {
					myParent.createMeetWindow();
					myParent.closeFrame();
				}
			}
		});
		add(btnCancelAddingMeet, "cell 1 9 5 1,alignx center");

	}

	private void initializeMeet() {
		for (int i = 0; i < mySelectedRunners.size(); i++) {
			Runner runner = mySelectedRunners.get(i);
			Time time = myTimes.get(i);
			myMeet.addCompetitor(runner, time);
		}
	}

	public void setNumericOnly(JTextField jTextField) {
		jTextField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if ((!Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
					e.consume();
				}
				if (jTextField.getText().length() >= 2) // limit to 2 characters
					e.consume();
			}
		});
	}

	private Time readTime() {

		int min;
		int seconds;
		int millisec;

		if (minField.getText().equals("") && secField.getText().equals("") && milliSecField.getText().equals("")) {
			return null;
		} else if (!secField.getText().equals("") && Integer.parseInt(secField.getText()) > 59) {
			return null;
		} else {
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

	private void clearArguments() {
		minField.setText("");
		secField.setText("");
		milliSecField.setText("");
	}

	private void setFields() {
		Time temp = myTimes.get(myIndex);
		minField.setText(temp.getMin() + "");

		secField.setText(temp.getSec() + "");

		milliSecField.setText(temp.getMillisec() + "");
	}

	private String[] getBasicData(ArrayList<Runner> allRunners) {
		String[] res = new String[allRunners.size()];
		for (int i = 0; i < allRunners.size(); i++) {
			String toAdd = "";
			Runner temp = allRunners.get(i);
			String name = temp.getName();
			int grade = temp.getGradeLevel();
			String level = temp.getTeamLevel();
			toAdd += name;
			if (grade != 0 || (level != null && !level.equals(""))) {
				toAdd += " (";
				if (grade != 0) {
					toAdd += "Grade: " + grade;
					if (level != null && !level.equals("")) {
						toAdd += ", Level: " + level;
					}

				} else {
					toAdd += "Level: " + level;
				}
				toAdd += ")";
			}

			res[i] = toAdd;
		}

		return res;
	}

}