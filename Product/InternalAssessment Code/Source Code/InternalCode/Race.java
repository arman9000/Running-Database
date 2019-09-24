package InternalCode;

public class Race {
	private String myNameOfMeet;

	private Time myTime;

	public Race(String nameOfMeet, Time time) {
		myNameOfMeet = nameOfMeet;

		myTime = time;

	}

	// changes the time for the race based on the argument
	public void changeTime(Time time) {
		myTime = time;
	}

    //Returns a negative number if this < time
    //Returns a 0 if this = time
    //Returns a positive number if this > time
	public int compareTo(Race race) {
		return myTime.compareTo(race.getTime());
	}

	// returns the race time
	public Time getTime() {
		return myTime;
	}

    // returns the race name 
	public String getName() {
		return myNameOfMeet;
	}

	// checks if the name of this race is equal to the argument
	public boolean equals(String raceName) {
		return (this.getName()).equals(raceName);
	}
	
	// defines the toString() method of the class
	public String toString() {
		String a = "";
		a += "Meet Name: " + myNameOfMeet + "\n" + "Time:      " + myTime.toString();
		return a;
	}
}
