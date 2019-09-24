package InternalCode;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class PersonalRecord {
	private ArrayList<Runner> allRunners;
	private ArrayList<Meet> allMeets;
	private boolean fileExists;

	public PersonalRecord() {
		allRunners = new ArrayList<Runner>();
		allMeets = new ArrayList<Meet>();

	}

	public PersonalRecord(String fileName) {
		allRunners = new ArrayList<Runner>();
		allMeets = new ArrayList<Meet>();
		loadFile(fileName);
	}

	public void addRunnerToDatabase(Runner runner) {
		allRunners.add(runner);

	}

	public void addMeetToDatabase(Meet meet) {
		allMeets.add(meet);
	}

	// removes a runner matching the name, gradeLevel, and level arguments from database
	public void removeSingleRunnerFromDatabase(String name, int gradeLevel, String level) {
		Runner temp;
		if (checkRunner(name, gradeLevel, level)) {
			temp = getRunner(name, gradeLevel, level);
		} else {
			temp = null;
		}
		if (temp != null) {

			if (temp.getNumRaces() > 0) {

				removeHelper(temp);

			}

			allRunners.remove(temp);

		}

	}

	// removes allrunners that contain name argument and match gradelevel and level arguments
	public void removeMultipleRunnersFromDatabase(String name, int gradeLevel, String level) {

		for (int i = allRunners.size() - 1; i >= 0; i--) {

			Runner temp = allRunners.get(i);
			String tempName = allRunners.get(i).getName();
			
			int space = name.indexOf(" ");
			String first = name.substring(0, space);

			space++;
			String last = name.substring(space, name.length());
			boolean containsName = findHelper(tempName, first, last);

			boolean toDelete = false;
			if (containsName) {
				
				if (gradeLevel == 0 && level.equals("")) {
					toDelete = true;

				} else if (gradeLevel == 0) {
					{

						if (temp.getTeamLevel().equals(level))

							toDelete = true;
					}
				} else if (level.equals("")) {
					if (temp.getGradeLevel() == gradeLevel)
						toDelete = true;
				} else if (temp.getTeamLevel().equals(level) && temp.getGradeLevel() == gradeLevel) {
					toDelete = true;
				}
			} 
			if (toDelete) {
				if (temp.getNumRaces() > 0)
					removeHelper(temp);
				allRunners.remove(temp);
			}
		}
	}

	// deletes runner from all meets they competed in
	private void removeHelper(Runner temp) {
		ArrayList<Race> allRaces = temp.getAllRaces();
		for (int i = allRaces.size() - 1; i >= 0; i--) {
			String raceName = allRaces.get(i).getName();
			for (int j = 0; j < allMeets.size(); j++) {
				if (raceName.equals(allMeets.get(j).getName())) {
					allMeets.get(j).removeCompetitor(temp);
					j = allMeets.size();
				}
			}

		}
	}
	
    // deletes meet from database
	public void removeMeetFromDatabase(String name) {
		if (checkMeet(name)) {
			Meet temp = getMeet(name);
			ArrayList<Runner> competitors = temp.getCompetitors();
			for (int i = 0; i < competitors.size(); i++) {
				Runner runner = competitors.get(i);
				runner.removeRace(temp.getName());
			}
			allMeets.remove(temp);
		}
	}

	/**
	 * pre-condition : String name, int gradeLevel are all parameters passed in
	 * post-condition: Returns the runner with matching inputs or null
	 */
	public Runner getRunner(String name, int gradeLevel) {
		if (allRunners.size() > 0) {
			for (int i = 0; i < allRunners.size(); i++) {   
				if (allRunners.get(i).getName().equals(name) && allRunners.get(i).getGradeLevel() == gradeLevel) {
					return allRunners.get(i);
				}
			}
		}
		return null;
	}

	/**
	 * pre-condition : String name, int gradeLevel, String level are all parameters passed in
	 * post-condition: Returns the runner with matching inputs or null
	 */
	public Runner getRunner(String name, int gradeLevel, String level) {
		if (level == null || level.equals(""))
			return getRunner(name, gradeLevel);
		else if (allRunners.size() > 0) {
			for (int i = 0; i < allRunners.size(); i++) {
				if (allRunners.get(i).getName().equals(name) && allRunners.get(i).getGradeLevel() == gradeLevel
						&& allRunners.get(i).getTeamLevel().equals(level)) {
					return allRunners.get(i);
				}
			}
		}
		return null;
	}

	// returns the meet with the name argument
	public Meet getMeet(String name) {
		if (allMeets.size() > 0) {
			for (int i = 0; i < allMeets.size(); i++) {
				if (allMeets.get(i).getName().equals(name)) {
					return allMeets.get(i);
				}
			}
		}
		return null;
	}



	// returns the number of meets in Database
	public int getNumMeets() {
		return allMeets.size();
	}
	
	// creates an ArrayList<Runner> with runners that have a PR 
	private ArrayList<Runner> getFilledHelper() {
		if (allRunners.size() > 0) {
			ArrayList<Runner> filled = new ArrayList<Runner>();
			for (int i = 0; i < allRunners.size(); i++) {
				if (!(allRunners.get(i).getFastestTime().isEmpty()))
					filled.add(allRunners.get(i));
			}
			return filled;
		}
		return null;
	}

	// creates an ArrayList<Runner> with runners that do not have a PR 
	private ArrayList<Runner> getEmptyHelper() {
		if (allRunners.size() > 0) {
			ArrayList<Runner> emptied = new ArrayList<Runner>();
			for (int i = 0; i < allRunners.size(); i++) {
				if (allRunners.get(i).getFastestTime().isEmpty())
					emptied.add(allRunners.get(i));
			}
			return emptied;
		}
		return null;
	}

	/**
	 * pre-condition : allRunners is an ArrayList<Runner> 
	 * 				   method called from MainMenu
	 * post-condition: allRunners is sorted by PR
	 */
	private void sortRunnersByTime() {
		ArrayList<Runner> temp = new ArrayList<Runner>();
		ArrayList<Runner> toSort = getFilledHelper(); // places all runners that contain a PR into a separate ArrayList
		ArrayList<Runner> empties = getEmptyHelper(); // places all runners that do not contain a PR into a separate
		int compare;
		if (toSort != null) {
			if (toSort.size() == 1) {
				temp = toSort;
			} else if (toSort.size() > 1) {

				temp.add(toSort.get(0));

				compare = toSort.get(0).getFastestTime().compareTo(toSort.get(1).getFastestTime());

				if (compare > 0) {
					temp.add(0, toSort.get(1));
				} else
					temp.add(toSort.get(1));
				if (toSort.size() > 2) {
					for (int i = 2; i < toSort.size(); i++) {
						for (int j = i - 1; j >= 0; j--) {
							
							try {
								compare = toSort.get(i).getFastestTime().compareTo(temp.get(j).getFastestTime());
								if (compare > 0) {
									temp.add(j + 1, toSort.get(i));
									j = 0;

								} else if (j == 0) {
									temp.add(0, toSort.get(i));
								}
							} catch (Exception e) {
								// Runner has competed in no races and has no PR
							}
							
						}
					}

				}
			}
		}
		if (empties != null)
			for (int i = 0; i < empties.size(); i++) {
				temp.add(empties.get(i));
			}
		allRunners = temp;
	}
	
	/**
	 * pre-condition : allRunners is an ArrayList<Runner> 
	 * 				   method called from MainMenu
	 * post-condition: allRunners is sorted by name 
	 */
	private void sortRunnersByName() {
		ArrayList<Runner> temp = new ArrayList<Runner>();
		int compare;
		if (allRunners.size() > 1) {

			temp.add(allRunners.get(0));
			compare = allRunners.get(0).getName().compareTo(allRunners.get(1).getName());
			if (compare > 0) {
				temp.add(0, allRunners.get(1));
			} else
				temp.add(allRunners.get(1));

		}
		if (allRunners.size() > 2) {
			for (int i = 2; i < allRunners.size(); i++) {

				for (int j = i - 1; j >= 0; j--) {
					compare = allRunners.get(i).getName().compareTo(temp.get(j).getName());
					if (compare > 0) {
						temp.add(j + 1, allRunners.get(i));
						j = 0;

					} else if (j == 0) {
						temp.add(0, allRunners.get(i));
					}

				}
			}
			allRunners = temp;
		}
	}


	/**
	 * Text file data are stored in the following format.
	 * 
	 * #        PR    Grade     Level               Name                  Meet Name    
	 *   1    12:45:23    9       V                Rohan Patel              Pat Hadley  
	 *   
	 * All data will be read accordingly.
	 */
	
	private void loadFile(String filename) {
		String name;  // will store a Runner's name
		Scanner inFile; // Scanner object used to traverse file
		String meetName; // Holds name of meet associated with each Runner's PR
		try {
			//fileExists = false; 
			
			inFile = new Scanner(new File(filename));
			
			fileExists = true;
			while (inFile.hasNextLine()) {
				Time fastest;
				inFile.nextLine();
				int temp = inFile.nextInt();            // First Parameter that holds the numeric order that each runner (row) is traversed
				String formattedTime = inFile.next();   // Second Parameter that is read is PR, this is the runner's fastest time
				if(formattedTime.equals("No"))          // Accounts if the time is 0  
				{                                          
					 fastest = new Time(0,0,0);
					 inFile.next();
				}
			    else
			    	fastest = new Time(formattedTime); 
				int gradeLevel = inFile.nextInt();      // Third Parameter that gives grade level of runner;
				String level = "";
				String next;
				name = inFile.next();                   // Fourth Parameter is level, since level may be empty, the file may skip to reading name
				if (name.equals("JV") || name.equals("V") || name.equals("Frosh/Soph")) {
					level = name;
					name = "";
					name += inFile.next();
					name += " " + inFile.next();
				} else {
					name += " " + inFile.next();       // Fifth Parameter is Name of the runner
				}
				inFile.useDelimiter("\\s");   		
				for (int i = 0; i < 23 - name.length(); i++)    // moves file traversal point to exactly 2 spaces before next Parameter, allows
				{                                               // a delimiter to be used for next parameter
					inFile.next();
				}
				inFile.useDelimiter("\\s\\s");        // Sixth Parameter is Meet name, a delimiter is used to account for longer names.
				meetName = inFile.next();
				inFile.reset();
				Runner place;
				if (checkRunner(name, gradeLevel, level)) {           
					place = getRunner(name, gradeLevel, level);      // runner not created if in database
				} else {
					place = new Runner(name, gradeLevel, level, this); // runner is created
				}
				if (checkMeet(meetName)) {
					getMeet(meetName).addCompetitor(place, fastest);  // meet not created if already in database, runner added to meet
				} else {
					Meet event = new Meet(meetName, this);            // meet is created
					event.addCompetitor(place, fastest);              // runner added to meet
				}
				name = "";
				meetName = "";
			}
			inFile.close();                                           // file closed
		} catch (Exception E) {
			 // catches error where File does not exist
			if(fileExists != true)
			fileExists = false; 
		}
	}
	

	/**
	 * pre-condition : all data is ready to be exported
	 * post-condition: A new file with name "NameOfFile" is created 
	 * 			       or an existing file with the name is updated.
	 * 				   The file will contain PR's of all runners 
	 * 				   from the database sorted by time.
	 */
	public void writePRsheet(String NameOfFile) {
		try {
			sortRunnersByTime();
			String[][] mat = getPRMatrix();
			PrintWriter writer = new PrintWriter(NameOfFile);
			String res = " #        Time    Grade     Level               Name"
					+ "                  Meet Name   ";
			writer.print(res);
			String temp = "";
			for (int i = 0; i < mat.length; i++) {

				writer.println();
				writer.printf("%-5s", ("   " + mat[i][0]));
				writer.printf("%-12s", ("   " + mat[i][1]));
				writer.printf("%-10s", ("   " + mat[i][2]));
				writer.printf("%-15s", (" " + mat[i][3]));
				writer.printf("%-25s", ("   " + mat[i][4]));
				writer.printf("%-30s", ("   " + mat[i][5]));
			}

			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * pre-condition : all data is ready to be exported
	 * post-condition: A new file with name "NameOfFile" is created 
	 * 			       or an existing file with the name is updated.
	 * 				   The file will contain data of all runners 
	 * 				   from the database sorted by name.
	 */
	public void writeAllData(String fileName)
	{
		sortRunnersByName();
		try {
		PrintWriter writer = new PrintWriter(fileName);
		String res = " #        Time    Grade     Level               Name"
				+ "                  Meet Name   ";
		writer.print(res);
		String temp = "";
		for(int i = 0; i< allRunners.size(); i++)
		{
			int place = i+1;
			Runner runner = allRunners.get(i);
			ArrayList<Race> allRaces = runner.getAllRaces();
			String name = runner.getName();
			int grade = runner.getGradeLevel();
			String level = runner.getTeamLevel();
			if(allRaces.size() == 0)
			{
				writer.println();
				writer.printf("%-5s", ("   " + place));
				writer.printf("%-12s", ("   No Time"));
				writer.printf("%-10s", ("   " + grade));
				writer.printf("%-15s", (" " + level));
				writer.printf("%-25s", ("   " + name));
				writer.printf("%-30s", ("   "));
			}
			else
			{
			for(int j = 0; j< allRaces.size() ; j++)
			{
				Race race = allRaces.get(j);
				String time = race.getTime().toString();
				String meetName = race.getName();
				writer.println();
				writer.printf("%-5s", ("   " + place));
				writer.printf("%-12s", ("   " + time));
				writer.printf("%-10s", ("   " + grade));
				writer.printf("%-15s", (" " + level));
				writer.printf("%-25s", ("   " + name));
				writer.printf("%-30s", ("   " + meetName));
				
			}
			}
		}
		writer.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

	// checks if meet with meetName argument exists
	private boolean checkMeet(String meetName) {

		return getMeet(meetName) != null;
	}


    // checks if runner with name, gradeLevel, and level arguments exists
	private boolean checkRunner(String name, int gradeLevel, String level) {
		return getRunner(name, gradeLevel, level) != null;
	}

	/**
	 * pre-condition : Method is called from the MainMenu class
	 * post-condition: Returns a String[][] with data that is traversable and displayed
	 *                 in a JTable
	 */
	public String[][] getDataMatrix() {
		String grade;
		String number;
		String time;
		String level;
		String name;
		String meetName;
		int place = 0;
		String[][] data = new String[allRunners.size()][6];
		for (int i = 0; i < allRunners.size(); i++) {
			number = i + 1 + "";
			
			time = allRunners.get(i).getFastestTime().toString() + "";
			grade = allRunners.get(i).getGradeLevel() + "";

			name = allRunners.get(i).getName();
			if (allRunners.get(i).getTeamLevel() != null) {
				level = allRunners.get(i).getTeamLevel();
			} else
				level = "";
			if (!allRunners.get(i).getFastestTime().isEmpty())
				meetName = allRunners.get(i).getRace(allRunners.get(i).getFastestTime()).getName();
			else
				meetName = "";
			data[i] = getSingleData(number, time, grade, level, name, meetName);
			place = i;
		}
		return data;
	}
	

	/**
	 * pre-condition : All parameters refer to corresponding runner data
	 * post-condition: Returns an String[] with runner data to be used as a row in a matrix
	 */
	public String[][] findRunners(String name, int grade, String level) {
		String[][] data = getDataMatrix();
		ArrayList<Integer> locations = new ArrayList<Integer>();
		for (int i = 0; i < data.length; i++) {
			String gradeLevel = data[i][2];
			String runnerName = data[i][4];
			int space = name.indexOf(" ");
			String first = name.substring(0, space);

			space++;
			String last = name.substring(space, name.length());
	
			boolean containsName = findHelper(runnerName, first, last);;
		
			
			if (!level.equals("")) {
				String runnerLevel = data[i][3];
				if (grade != 0) {
					if (Integer.parseInt(gradeLevel) == grade && containsName && runnerLevel.equals(level)) {
						locations.add(i);
					}
				} else {
					if (containsName && runnerLevel.equals(level)) {
						locations.add(i);
					}
				}
			} else if (grade != 0) {
				if (Integer.parseInt(gradeLevel) == grade && containsName) {
					locations.add(i);
				}
			} else if (containsName)
				locations.add(i);

		}
		String[][] res = new String[locations.size()][6];
		for (int i = 0; i < locations.size(); i++) {
			res[i] = data[locations.get(i)];
		}
		return res;

	}

	// checks if the name argument contains both first and last
	private boolean findHelper(String name, String first, String last) {
		int space = name.indexOf(" ");
		String origFirst = name.substring(0, space).toUpperCase();

		space++;
		String origLast = name.substring(space, name.length()).toUpperCase();

		return (origFirst.contains(first.toUpperCase()) && origLast.contains(last.toUpperCase()));

	}
	
	// returns true if loadFile successfully reaches a file
	public boolean alertInitializeProgramWindow()
	{
		return fileExists;
	}

	// returns all runners
	public ArrayList<Runner> getRunners() {
		return allRunners;
	}

	// returns all Meets
	public ArrayList<Meet> getMeets() {
		return allMeets;
	}


    // returns a data matrix sorted by time
	private String[][] getPRMatrix() {
		sortRunnersByTime();
		return getDataMatrix();
	}

	// removes all empty meets from the database
	public void filterMeets() {

		for (int i = 0; i < allMeets.size(); i++) {
			if (allMeets.get(i).getNumCompetitors() == 0) {
				removeMeetFromDatabase(allMeets.get(i).getName());
				i--;
			}
		}
	}
	
	/**
	 * pre-condition : All parameters refer to corresponding runner data
	 * post-condition: Returns an String[] with runner data to be used as a row in a matrix
	 */
	private String[] getSingleData(String number, String time, String grade, 
			String level, String name,String meetName) {
		String[] data = new String[6];
		data[0] = number;
		data[1] = time;
		data[2] = grade;
		data[3] = level;
		data[4] = name;
		data[5] = meetName;
		return data;
	}

}
