/**
 * 
 */
package edu.vt.ece5574.agents;

/**
 * @author vedahari
 *
 */
public class ClockTime {
	public static enum TIMEPERIOD {
	    UNKNOWN, MORNING, AFTERNOON, EVENING, NIGHT  
	}

	private int hour;
	private int minute;
	private int seconds;
	
	public ClockTime() {
		
	}
	
	public void setTime(int hr, int min, int sec){
		hour = hour % 24;
		minute = min % 60;
		seconds = sec % 60;
	}
	
	public TIMEPERIOD getTimePeriod(){
		if(hour >= 5 && hour < 12)
		{
			return TIMEPERIOD.MORNING;
		}
		else if (hour >=12 && hour < 17)
		{
			return TIMEPERIOD.AFTERNOON;
		}
		else if (hour >=17 && hour < 21)
		{
			return TIMEPERIOD.EVENING;
		}
		else if ((hour >= 21 && hour < 24)||((hour>=0 && hour <5)))
		{
			return TIMEPERIOD.NIGHT;
		}			
		return TIMEPERIOD.UNKNOWN;		
	}
}
