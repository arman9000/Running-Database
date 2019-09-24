package InternalCode;

public class Time implements Comparable {
	private int myMin;
	private int mySec;
	private int myMillisec;

	public Time(int min, int sec, int millisec) {
		myMin = min;
		mySec = sec;
		myMillisec = millisec;
	}
	
	public Time (String formattedTime)
	{
	 myMin = Integer.parseInt(formattedTime.substring(0, 2));
	 mySec = Integer.parseInt(formattedTime.substring(3, 5));
	 myMillisec = Integer.parseInt(formattedTime.substring(6, 8));
	}
	
	// checks if time is 0 minutes, 0 seconds, and 0 milliseconds
	public boolean isEmpty()
	{
		return myMin == 0 && mySec == 0 && myMillisec == 0;
	}

	// changes myMin, mySec, and myMilliSec to that of the parameter
	public void updateTime(Time time) {
		myMin = time.getMin();
		mySec = time.getSec();
		myMillisec = time.getMillisec();
	}

	//defines the toString() of the class
	public String toString() {
		if(!this.isEmpty())
		{
		String min = myMin +"";
		if(myMin < 10)
			min = "0" + myMin;
		String sec = mySec+"";
		if(mySec < 10)
			sec = "0" + mySec;
		String milliSec = myMillisec +"";
		if(myMillisec < 10)
			milliSec = "0" + myMillisec;
		return min + ":" + sec + ":" + milliSec + "";
		}
		else
			return "No Time ";
	}

	//returns minutes
	public int getMin() {
		return myMin;
	}

	// returns seconds
	public int getSec() {
		return mySec;
	}

	// returns milliseconds
	public int getMillisec() {
		return myMillisec;
	}

	public boolean equals(Time time) {
		return this.compareTo((Object) time) == 0;
	}

	/**
	 * pre-condition : An object is passed in to compare with this
	 * post-condition: Returns a negative number if this < time
	 *                 Returns a 0 if this = time
	 *                 Returns a positive number if this > time
	 */
	public int compareTo(Object time) {
		Time newTime = (Time) time;
		int compare = 0;
		
		if (myMin < newTime.getMin()) {
			compare = 6000 * (myMin - newTime.getMin());
		} else if (myMin > newTime.getMin()) {
			compare = 6000 * (myMin - newTime.getMin());
		}
		if (mySec < newTime.getSec())
			compare += 100 * (mySec - newTime.getSec());
		else if (mySec > newTime.getSec()) 
		{
			compare += 100 * (  mySec - newTime.getSec());
		}
		if (myMillisec < newTime.getMillisec()) 
		{
			compare += myMillisec - newTime.getMillisec();
		}
		else if (myMillisec > newTime.getMillisec())
			compare +=  myMillisec - newTime.getMillisec();

		return compare;

	}

}
