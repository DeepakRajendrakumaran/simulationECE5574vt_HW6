package edu.vt.ece5574.agents;

import java.util.LinkedList;
import edu.vt.ece5574.events.Event;
import edu.vt.ece5574.events.FireEvent;
import edu.vt.ece5574.events.IntruderEvent;
import edu.vt.ece5574.events.WaterLeakEvent;
import sim.engine.SimState;


/**
 * Class for representing User/People in the 
 * simulation environment. 
 * @author Vedahari Narasimhan Ganesan 
 */

public class User extends Agent{
	
	LinkedList<Event> userEvents;
	private static final long serialVersionUID = 1;
	private boolean isAppUser;
	public void handleUserEvents(){
		while(userEvents.size()!=0)
		{
			Event currentEvent = userEvents.removeFirst();
			if(currentEvent instanceof IntruderEvent){
				//Evaluate this is caused by this user
			}
			else if(currentEvent instanceof WaterLeakEvent){			
				//If needed handle this user event.
			}
			else if(currentEvent instanceof FireEvent){
				//If needed handle this user event.
			}
				
		}
	}
	
	public User(String usrID, String building, boolean bAppUser){
		super(usrID, building);
		isAppUser = bAppUser;
	}
	
	public User(String usrID, String building){
		super(usrID, building);
		isAppUser = false;
	}
	
	
	
	public void setBuildingID(String id)
	{
		super.buildingID = id;
	}
	
	public boolean isAppUser(){
		return isAppUser;
	}	
	
	public void setAppUser(boolean bAppUser){
		isAppUser = bAppUser;
	}
	
	public void createRandomEvent()
	{
		//TODO: Create random event that is to be handled by the system
	}	
	
	@Override
	public void step(SimState state) {
		//Simulation simState = (Simulation)state;
		if(events.isEmpty()){
			//if no event, create event for the robots to handle
			createRandomEvent();
		}
		else{
			//in case of events react
			handleUserEvents();
		}		
	}
}
