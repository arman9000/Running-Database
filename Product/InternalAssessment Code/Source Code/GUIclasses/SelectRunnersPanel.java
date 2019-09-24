package GUIclasses;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import InternalCode.Meet;
import InternalCode.PersonalRecord;
import InternalCode.Runner;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class SelectRunnersPanel extends JPanel {

	private PersonalRecord PR;
	private Meet myMeet;
	private JList myList;
	private ArrayList<Runner> myRunners;
	private MeetOptionWindow myParent;
	private JPanel myPrevious;
	private boolean source;

	/**
	 * Create the panel.
	 */
	public SelectRunnersPanel(PersonalRecord database, JPanel previous, MeetOptionWindow parent, Meet meet, boolean fromMainMenu) {
		source = fromMainMenu;
		setBackground(new Color(240, 248, 255));

		myPrevious = previous;
		myMeet = meet;
		PR = database;
		myParent = parent;
		setLayout(new MigLayout("", "[][grow][][][][][][][][][][][][][]", "[][][][][grow][][][][][]"));

		JLabel lblNewLabel = new JLabel("Select Runners to Add to " + myMeet.getName());

		add(lblNewLabel, "cell 1 0");

		myRunners = PR.getRunners();

		myList = new JList(getBasicData(myRunners));
		myList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		myList.setLayoutOrientation(JList.VERTICAL);
		myList.setVisibleRowCount(-1);

		JScrollPane scrollPane = new JScrollPane(myList);
		add(scrollPane, "cell 1 1 9 8,grow");

		JButton btnCancel = new JButton("Cancel Adding Meet");
		// returns to Meet Option Window if source is true, Meet Menu if source is false
		btnCancel.addActionListener(new ActionListener() {
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

		JButton btnNext = new JButton("Continue");
		// moves to Select Times Panel if any runners are selected
		// moves to Meet Menu if no runners are in the database or if no runners are selected
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				ArrayList<Runner> selectedRunners = getSelected();
				if (selectedRunners != null) {
					SelectTimesPanel timePanel = new SelectTimesPanel(PR, parent, getThis(), myMeet,
							selectedRunners, source);
					parent.replacePanel(timePanel);
				} else {
					
					
					int dialogButton = JOptionPane.YES_NO_OPTION;
					int dialogResult = JOptionPane.showConfirmDialog(null,
							"Do you want to create meet without selecting existing runners?", "Warning", dialogButton);
					if (dialogResult == dialogButton) {
						{
							myParent.createMeetWindow();
                            myParent.resetPanel();
							myParent.closeFrame();
						}

					}
				
				}

			}
		});
		add(btnNext, "cell 11 1 3 1,growx");

		JButton btnBack = new JButton("Back");
		// moves back to previous Add meet Panel
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myParent.replacePanel(myPrevious);
				String str = myMeet.getName();
				PR.removeMeetFromDatabase(str);
				myParent.updateLog(str + " has been removed from databse");
			}
		});
		add(btnBack, "cell 11 6 3 1,growx");
		add(btnCancel, "cell 11 8 3 1,growx");

	}

	// returns this as a helper method
	private JPanel getThis() {
		return this;
	}

	// retrieves all runners that are selected in the JList
	private ArrayList<Runner> getSelected() {

		ArrayList<Runner> selected = new ArrayList<Runner>();
		int[] indices = myList.getSelectedIndices();
		if (indices.length != 0) {
			for (int i = 0; i < indices.length; i++) {
				selected.add(myRunners.get(indices[i]));
			}

			return selected;
		} else
			return null;
	}
	
	
    // creates a String Array of selected runner's private information for display
	// ex. Gabe Rodriguez (Grade: 9, Level: JV)
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
