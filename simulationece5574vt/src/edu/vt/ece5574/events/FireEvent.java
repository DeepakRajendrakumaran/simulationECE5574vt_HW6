package edu.vt.ece5574.events;

/**
 * The class for all FireEvents with attributes specific to these types
 * @author David Kindel
 *
 */
public class FireEvent extends Event {

	public FireEvent() {
		
	}

	public boolean init(String details){
		if(super.setBaseDetails(details)){
			return true;
		}
		return false;
	}

}
