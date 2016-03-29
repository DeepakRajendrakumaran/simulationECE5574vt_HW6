package edu.vt.ece5574.events;

/**
 * The class for all MoveRobotEvents with attributes specific to these types
 * @author David Kindel
 *
 */
public class MoveRobotEvent extends Event{

	public MoveRobotEvent() {
		
	}

	public boolean init(String details){
		if(super.setBaseDetails(details)){
			return true;
		}
		return false;
	}
}
