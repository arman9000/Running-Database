package GUIclasses;

import javax.swing.JPanel;
import javax.swing.JTextField;

import InternalCode.Meet;
import InternalCode.PersonalRecord;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class AddMeetPanel extends JPanel {
	private JTextField nameField;
	private MainMenu mainMenu;
	private PersonalRecord PR;
	private MeetOptionWindow myParent;
	private MeetMenu myMeets;
	private JPanel meetSelector;
	private Meet myMeet;
	private boolean source;

	/**
	 * Create the panel.
	 */
	public AddMeetPanel(MeetOptionWindow parent, MeetMenu meets, PersonalRecord database, MainMenu main,
			boolean fromMainMenu) {
		source = fromMainMenu;
		setBackground(new Color(240, 248, 255));
		mainMenu = main;
		PR = database;
		myParent = parent;
		myMeets = meets;
		
		setLayout(new MigLayout("", "[][][][][][][][][grow][][][][][][][]", "[][][][][][][grow][]"));

		JLabel lblMeetName = new JLabel("Meet Name");
		add(lblMeetName, "cell 1 2 14 1,alignx left");

		nameField = new JTextField();
		add(nameField, "cell 1 3 14 1,growx");
		nameField.setColumns(10);
		

		JButton createMeetBtn = new JButton("Create Meet");
		//button will create the Select Meets Panel and replace the current panel
		createMeetBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String name = nameField.getText();
				if (!name.equals("")) {
					if (PR.getMeet(name) == null) {
						myMeet = new Meet(name, PR);
						myParent.updateLog(name + " is a new meet added to database");
						nameField.setText("");
						if (database.getRunners().size() > 0) {
							initializeMeetSelector();
							myParent.replacePanel(meetSelector);
						} else {
							myParent.createMeetWindow();
                            myParent.resetPanel();
							myParent.closeFrame();
						}
					} else
						JOptionPane.showMessageDialog(new JFrame(), "Meet Already Exists", "Error",
								JOptionPane.ERROR_MESSAGE);

				} else {
					JOptionPane.showMessageDialog(new JFrame(), "Enter a Meet Name", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		add(createMeetBtn, "cell 1 5 14 1,growx");

		JButton cancelBtn = new JButton("Cancel Adding Meet");
		// returns to Meet Option Window if source is true, Meet Menu if source is false
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (source) {
					myParent.resetPanel();
					nameField.setText("");
				} else {

					myParent.createMeetWindow();
					myParent.closeFrame();
					nameField.setText("");
				}
			}
		});
		add(cancelBtn, "cell 1 7 6 1,growx");

		this.setVisible(true);

	}

	// creates Select Meet Panel
	private void initializeMeetSelector() {
		meetSelector = new SelectRunnersPanel(PR, this, myParent, myMeet, source);
	}

}
