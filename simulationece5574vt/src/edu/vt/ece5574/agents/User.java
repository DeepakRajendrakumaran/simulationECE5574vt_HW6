package edu.vt.ece5574.agents;

import java.util.LinkedList;
import edu.vt.ece5574.events.Event;
import edu.vt.ece5574.events.FireEvent;
import edu.vt.ece5574.events.IntruderEvent;
import edu.vt.ece5574.events.WaterLeakEvent;
import edu.vt.ece5574.sim.Simulation;
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
	private Coordinate location;
	private double randomStepFactor = 0.1;
	private double centerStepFactor = 0.2;
	private boolean fireNotification = false;
	private boolean waterLeakNotification = false;
	private boolean intruderNotification = false;
	
	/** Constructor to create a User in the Simulation Component
	 * @param usrID - ID of the user in the system
	 * @param building
	 * @param bAppUser
	 * @param loc
	 */
	public User(String usrID, String buildingID, boolean bAppUser, Coordinate loc){
		super(usrID, buildingID);
		isAppUser = bAppUser;
		location = loc;
	}
	
	/**
	 * @param usrID
	 * @param building
	 * @param bAppUser
	 */
	public User(String usrID, String buildingID, boolean bAppUser){
		super(usrID, buildingID);
		isAppUser = bAppUser;
	}
	
	public User(String usrID, String buildingID){
		super(usrID, buildingID);
		isAppUser = false;
	}	
	
	/**
	 * @return X co-ordinate of the User
	 */
	public int getX() {
		return location.x; 
	}	
	
	/**
	 * @return Y co-ordinate of the User
	 */
	public int getY() { 
		return location.y;
	}
	
	/**
	 * @return location - Location of the User
	 */
	public Coordinate getLocation(){
		return location;
	}

	/**
	 * Handler Function to handle different events received by an User
	 */
	public void handleUserEvents(){
		while(userEvents.size()!=0)
		{
			Event currentEvent = userEvents.removeFirst();
			clearAllNotification();
			if(currentEvent instanceof IntruderEvent){
				//Evaluate this is caused by this user
				if (isAppUser()){
					System.out.println("User receives Intruder Notification!");
					setIntruderNotification(true);
				}				
			}
			else if(currentEvent instanceof WaterLeakEvent){			
				//If needed handle this user event.
				if (isAppUser()){
					System.out.println("User receives WaterLeak Notification!");
					setWaterLeakNotification(true);
				}				
			}
			else if(currentEvent instanceof FireEvent){
				//If needed handle this user event.
				if (isAppUser()){
					System.out.println("User receives Fire Notification!");
					setFireNotification(true);
				}				
			}				
		}
	}		

	/**
	 * @param id
	 */
	public void setBuildingID(String id)
	{
		super.buildingID = id;
	}
	
	/**
	 * @return
	 */
	public boolean isAppUser(){
		return isAppUser;
	}	
	
	/**
	 * @param bAppUser
	 */
	public void setAppUser(boolean bAppUser){
		isAppUser = bAppUser;
	}
	
	/**
	 * Creates random movement for the User.
	 */
	public void createRandomMovement(SimState state)
	{
		//TODO: Create random event that is to be handled by the system
		Simulation simState = (Simulation)state;		
		int x = location.x;
		int y = location.y;
		//Move the user to a random location
		x = (int) (x + (randomStepFactor*state.random.nextDouble()*1.0-0.5));
		y = (int) (y + (randomStepFactor*state.random.nextDouble()*1.0-0.5));
		Building bld = (Building)simState.getAgentByID(buildingID);
		//Update only if the next step is valid.
		if (bld.checkStep(x, y)){
			location.x = x;
			location.y = y;
		}		
		//TODO::Verify whether the storage is updated or not
		simState.storage.updUserPos(super.getID(), location.x, location.y);	
	}	
	
	private void clearAllNotification(){
		fireNotification = false;
		intruderNotification = false;
		waterLeakNotification = false;
	}
	
	private void setWaterLeakNotification(boolean value)
	{
		waterLeakNotification = value;
	}
	
	public boolean getWaterLeakNotification()
	{
		return waterLeakNotification;		
	}
	
	private void setFireNotification(boolean value)
	{
		fireNotification = value;
	}
	
	public boolean getFireNotification()
	{
		return fireNotification;		
	}
	
	private void setIntruderNotification(boolean value)
	{
		intruderNotification = value;
	}
	
	public boolean getIntruderNotification()
	{
		return intruderNotification;		
	}
	
	/* (non-Javadoc)
	 * @see edu.vt.ece5574.agents.Agent#step(SimState)
	 */
	@Override
	public void step(SimState state) {
		//Simulation simState = (Simulation)state;
		super.step(state);
		if(events.isEmpty()){
			//if no event, create event for the robots to handle
			createRandomMovement(state);
		}
		else{
			//in case of events react
			handleUserEvents();
		}		
	}
}
