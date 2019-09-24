package InternalCode;

import java.util.ArrayList;

public class Meet {
	private ArrayList<Runner> allRunners;
	private String myNameOfMeet;

	public Meet(String name, PersonalRecord PR) {
		myNameOfMeet = name;

		PR.addMeetToDatabase(this);
		allRunners = new ArrayList<Runner>();
	}


	// returns the meet name
	public String getName() {
		return myNameOfMeet;
	}

	// adds a runner to the meet using the runner argument and assigns them a time from time argument
	public String addCompetitor(Runner runner, Time time) {

		if (!checkRunner(runner)) {
			

			allRunners.add(runner);
			runner.addRace(new Race(myNameOfMeet, time));
			return (runner.getName() + " has been added to " + myNameOfMeet);
		} else
		{
			updateRunner(runner, time);
			return (runner.getName() + " has time changed to " + time.toString() + " for " +   myNameOfMeet);
		}

	}

	// removes a single competitor from the meet
	public void removeCompetitor(Runner runner) {
		if (runner != null && checkRunner(runner)) {
			Runner temp = getRunner(runner.getName(), runner.getGradeLevel());
	
			temp.removeRace(myNameOfMeet);
			allRunners.remove(temp);
		}
	}

	// returns number of runners in the meet
	public int getNumCompetitors()
	{
		return allRunners.size();
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
		ArrayList<Runner> toSort = getFilledHelper();
		ArrayList<Runner> empties = getEmptyHelper();
		int compare;

		if (toSort != null && toSort.size() != 0) {
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
		String[][] data = new String[allRunners.size()][5];
		for (int i = 0; i < allRunners.size(); i++) {


			number = i + 1 + "";
			time = allRunners.get(i).getRace(myNameOfMeet).getTime().toString();
			grade = allRunners.get(i).getGradeLevel() + "";
			name = allRunners.get(i).getName();

			if (allRunners.get(i).getTeamLevel() != null) {
				level = allRunners.get(i).getTeamLevel();
			} else
				level = "";

			data[i] = getSingleData(number, time, grade, level, name);
			place = i;
		}
		return data;
	}

	/**
	 * pre-condition : All parameters refer to corresponding runner data
	 * post-condition: Returns an String[] with runner data to be used as a row in a matrix
	 */
	private String[] getSingleData(String number, String time, String grade, String level, String name) {
		String[] data = new String[5];
		data[0] = number;
		data[1] = time;
		data[2] = grade;
		data[3] = level;
		data[4] = name;
		return data;
	}
	
	/**
	 * pre-condition : allRunners is an ArrayList<Runner> 
	 * 				   method called from MainMenu
	 * post-condition: allRunners is sorted by name 
	 */
	public void sortRunnersByName() {
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

	// changes the time for the race object in the runner argument
	private void updateRunner(Runner runner, Time time) {

		runner.updateRace(runner.getRace(myNameOfMeet), time);

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
		if(level == null || level.equals("")   )
			return getRunner(name,gradeLevel);
		else if (allRunners.size() > 0) {
			for (int i = 0; i < allRunners.size(); i++) {
					if (allRunners.get(i).getName().equals(name) && allRunners.get(i).getGradeLevel() == gradeLevel
							&& allRunners.get(i).getTeamLevel() == level) {
						return allRunners.get(i);
					}

				
			}

		}
		return null;
	}

	// returns an ArrayList<Runner> with all the runners who competed in the meet
	public ArrayList<Runner> getCompetitors() {
		return allRunners;
	}

	// checks if the runner argument has competed in the meet
	private boolean checkRunner(Runner runner) {
		return getRunner(runner.getName(), runner.getGradeLevel(), runner.getTeamLevel()) != null;
	}
}
