package edu.vt.ece5574.events;

/**
 * The class for all FireEvents with attributes specific to these types
 * @author David Kindel
 *
 */
/**
 * Edit: Added simple flags to allow starting and stopping an event to allow sensor/robot interaction
 * @author Deepak Rajendrakumaran
 */
public class FireEvent extends Event {

	private boolean fireActive;
	
	public FireEvent() {
		fireActive = true;
	}
	
	/**
	 * used by sensor to check if even still active
	 */
	public boolean is_fireActive() {
		return fireActive;
	}
	
	/**
	 * used to robot to signal that event has been dealt with
	 */
	public void turn_fireOff() {
		 fireActive = false;
	}

	
	public boolean init(String details){
		if(super.setBaseDetails(details)){
			return true;
		}
		return false;
	}

}
