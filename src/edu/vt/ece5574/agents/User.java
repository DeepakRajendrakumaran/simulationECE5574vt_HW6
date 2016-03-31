package edu.vt.ece5574.agents;

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
	private static final long serialVersionUID = 1;
	private boolean isAppUser;
	private Coordinate location;
	private double randomStepFactor = 0.1;
	//private double centerStepFactor = 0.2;
	private boolean fireNotification = false;
	private boolean waterLeakNotification = false;
	private boolean intruderNotification = false;
	
	public User(String usrID, String buildingID, boolean bAppUser, int x, int y){
		super(usrID, buildingID);
		isAppUser = bAppUser;
		location = new Coordinate(x,y);		
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
		while(events.size()!=0)
		{
			Event currentEvent = events.removeFirst();
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
		//System.out.println("App user is set as "+bAppUser);
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
		setFireNotification(false);
		setIntruderNotification(false);
		setWaterLeakNotification(false);
	}
	
	private void setWaterLeakNotification(boolean value)
	{
		//System.out.println("setWaterLeakNotification entered with value as "+ value);
		waterLeakNotification = value;
	}
	
	public boolean getWaterLeakNotification()
	{
		//System.out.println("waterLeakNotification is "+waterLeakNotification);
		return waterLeakNotification;		
	}
	
	private void setFireNotification(boolean value)
	{
		fireNotification = value;
	}
	
	public boolean getFireNotification()
	{
		//System.out.println("getFireNotification is "+fireNotification);
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
		super.step(state);
		//System.out.println("User Step entered");
		//Simulation simState = (Simulation)state;		
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
