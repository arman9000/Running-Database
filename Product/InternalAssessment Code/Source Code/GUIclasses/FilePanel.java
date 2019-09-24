package GUIclasses;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTextField;

import InternalCode.PersonalRecord;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class FilePanel extends JPanel {
	private JTextField textField;
	private InitializeProgram myParent;

	/**
	 * Create the panel.
	 */
	public FilePanel(InitializeProgram parent) {
		setBackground(new Color(240, 248, 255));
		setForeground(new Color(240, 248, 255));
		myParent = parent;

		setLayout(new MigLayout("", "[][grow][]", "[][][][][][grow][]"));

		JLabel lblEnterFileName = new JLabel("Enter File Name (not case sensitive)");
		add(lblEnterFileName, "cell 1 1,alignx center");

		textField = new JTextField();
		add(textField, "cell 1 2,growx");
		textField.setColumns(10);

		JButton btnInitializeProgram = new JButton("Initialize Program");
		// creates the database and launches Main Menu
		btnInitializeProgram.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = textField.getText();
				if (name.length()<4 || (name.length() >= 4 && !name.substring(name.length() - 4).equals(".txt"))) {
					name += ".txt";	
					}
				PersonalRecord PR = new PersonalRecord(name);
				if (!PR.alertInitializeProgramWindow()) {
					int dialogButton = JOptionPane.YES_NO_OPTION;
					int dialogResult = JOptionPane.showConfirmDialog(null,
							"The file does not exist, Do you want to create the database with out reading a data file?",
							"Warning", dialogButton);
					if (dialogResult == dialogButton) {
						MainMenu main = new MainMenu(PR);
						main.setVisible(true);
						myParent.closeFrame();
					}
				} else {
					MainMenu main = new MainMenu(PR);
					main.setVisible(true);
					myParent.closeFrame();
			}

			}
		});
		add(btnInitializeProgram, "cell 1 4,growx");

		JButton btnNewButton = new JButton("Cancel");
		// returns to the Initialize Program MEnu
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myParent.resetPanel();
			}
		});
		add(btnNewButton, "cell 1 6,growx");

	}

}
