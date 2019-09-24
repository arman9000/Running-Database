package InternalCode;

import java.util.ArrayList;

public class Runner {
	private String myName;
	private int myGradeLevel;
	private String myTeamLevel;
	private ArrayList<Race> myRaces = new ArrayList<Race>();
	private Time myFastestTime;

	public Runner(String name, int gradeLevel, String teamLevel, PersonalRecord PR) {
		myName = name;
		myGradeLevel = gradeLevel;
		myTeamLevel = teamLevel;
		PR.addRunnerToDatabase(this);

	}



	public Runner(String name, int gradeLevel, PersonalRecord PR) {
		myName = name;
		myGradeLevel = gradeLevel;
		myTeamLevel = null;
		PR.addRunnerToDatabase(this);
	}

	// adds a race to the runner
	public void addRace(Race race) {

		myRaces.add(race);
		if (myRaces.size() > 1)
			sortRaces();
		updateFastestTime();

	}

	// removes the race from the runner
	public void removeRace(String race) {

		for (int i = 0; i < myRaces.size(); i++) {
			if (myRaces.get(i).getName().equals(race)) {
				myRaces.remove(i);
				return;
			}
		}
		sortRaces();
		updateFastestTime();
	}

	// changes the time for the past in race parameter
	public void updateRace(Race race, Time time) {

		race.changeTime(time);

		sortRaces();
		updateFastestTime();
	}

    // updates the runner's PR
	private void updateFastestTime() {
		if (myRaces.size() != 0)
			myFastestTime = myRaces.get(0).getTime();
		else
			myFastestTime = new Time(0, 0, 0);

	}

	
	// Changes Name
	public void changeName(String name)
	{
		myName = name;
	}
	// Changes grade level
	public void changeGradeLevel(int gradeLevel) {
		myGradeLevel = gradeLevel;
	}
    // Changes Team level
	public void changeTeamLevel(String teamLevel) {
		myTeamLevel = teamLevel;
	}
	// Returns myName
	public String getName() {
		return myName;
	}
	// Returns myTeamLevel
	public String getTeamLevel() {
		return myTeamLevel;
	}
	// Returns myGradeLevel
	public int getGradeLevel() {
		return myGradeLevel;
	}
	//Returns fastest time
	public Time getFastestTime() {
		updateFastestTime();
		return myFastestTime;
	}
	// Returns race corresponding to raceName parameter
	public Race getRace(String raceName) {
		for (int i = 0; i < myRaces.size(); i++) {
			if (myRaces.get(i).equals(raceName)) {
				return myRaces.get(i);
			}
		}
		return null;
	}
	// Returns race corresponding to time parameter
	public Race getRace(Time time) {
		for (int i = 0; i < myRaces.size(); i++) {
			if (myRaces.get(i).getTime().equals(time)) {
				return myRaces.get(i);
			}
		}
		return null;
	}


	
    // returns the number of races the Runner competed in
	public int getNumRaces() {
		return myRaces.size();
	}

	// returns an Array List of Races
	public ArrayList<Race> getAllRaces() {
		return myRaces;
	}

	// returns a String Matrix that has the name and time of each race
	public String[][] getRaceDataMatrix() {

		String[][] races = new String[getNumRaces()][3];
		if (getNumRaces() > 0)
			for (int i = 0; i < races.length; i++) {
				Race race = myRaces.get(i);
				races[i][0] = i + 1 + "";
				races[i][1] = race.getName();
				races[i][2] = race.getTime().toString();

			}

		return races;
	}

	
	// uses mergeSort to order all the races by time
	private void sortRaces() {
		Time[] times = new Time[myRaces.size()];
		for (int i = 0; i < myRaces.size(); i++) {
			times[i] = myRaces.get(i).getTime();
		}

		int left = 0;
		int right = times.length - 1;
		mergeSort(times, left, right);
		ArrayList<Race> temp = new ArrayList<Race>();
		for (int i = 0; i < myRaces.size(); i++) {

			temp.add(i, getRace(times[i]));
		}

		myRaces = temp;

	}

	private void merge(Time arr[], int beg, int mid, int end) {
		// Find sizes of two subarrays to be merged
		int size1 = mid - beg + 1;
		int size2 = end - mid;

		/* Create temp arrays */
		Time first[] = new Time[size1];
		Time second[] = new Time[size2];

		/* Copy data to temp arrays */
		for (int i = 0; i < size1; ++i)
			first[i] = arr[beg + i];
		for (int j = 0; j < size2; ++j)
			second[j] = arr[mid + 1 + j];

		/* Merge the two arrays */

		// Initial indexes of first and second subarrays
		int i = 0, j = 0;

		// Initial index of merged subarry array
		int k = beg;
		while (i < size1 && j < size2) {
			if (first[i].compareTo(second[j]) <= 0) {
				arr[k] = first[i];
				i++;
			} else {
				arr[k] = second[j];
				j++;
			}
			k++;
		}

		/* Copy remaining elements of first[] if any */
		while (i < size1) {
			arr[k] = first[i];
			i++;
			k++;
		}

		/* Copy remaining elements of second[] if any */
		while (j < size2) {
			arr[k] = second[j];
			j++;
			k++;
		}
	}

	// Algorithm based on generic merge sort found on geeksforgeeks.org
	// Sorting method that sorts arr[beg..r] using
	// merge()
	private void mergeSort(Time arr[], int beg, int end) {
		if (beg < end) {
			// Find the middle point
			int m = (beg + end) / 2;

			// Sort first and second halves
			mergeSort(arr, beg, m);
			mergeSort(arr, m + 1, end);

			// Merge the sorted halves
			merge(arr, beg, m, end);
		}
	}

	
    // defines the toString() method of the class
	public String toString() {
		String temp = "";
		temp += "Name: " + myName + "\n\n";
		temp += "Race List" + "\n\n";
		for (int i = 0; i < myRaces.size(); i++) {
			temp += "  Meet Name: " + myRaces.get(i).getName() + "\n";
			temp += "  Time: " + myRaces.get(i).getTime().toString() + "\n";
		}
		return temp;
	}

}
