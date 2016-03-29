package edu.vt.ece5574.events;

/**
 * The class for all WaterLeakEvents with attributes specific to these types
 * @author David Kindel
 *
 */
public class WaterLeakEvent extends Event{


	public WaterLeakEvent(){
		
	}
	

	public boolean init(String details){
		if(super.setBaseDetails(details)){
			return true;
		}
		return false;
	}


}
