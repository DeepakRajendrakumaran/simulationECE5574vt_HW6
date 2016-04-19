package edu.vt.ece5574.agents;
import edu.vt.ece5574.sim.StorageAPI;
import edu.vt.ece5574.events.Event;
import edu.vt.ece5574.events.FireEvent;
import edu.vt.ece5574.events.IntruderEvent;
import edu.vt.ece5574.events.WaterLeakEvent;
import edu.vt.ece5574.sim.AStar;
import edu.vt.ece5574.sim.Simulation;
import java.awt.Color;
import java.util.List;
import java.util.Stack;

import sim.engine.SimState;
import sim.util.Int2D;
import sim.util.MutableInt2D;


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
	private boolean onMission = false;
	private String APIID = "";
	private StorageAPI userStorage;
	
	private Coordinate destination;
	Stack<Int2D> routePath;

	public boolean isOnMission() {
		return onMission;
	}

	public void setOnMission(boolean onMission) {
		this.onMission = onMission;
	}

	public User(Simulation state,String userid, String buildingID, boolean bAppUser, int x, int y){
		super(Color.GREEN,true,userid, buildingID);
		isAppUser = bAppUser;
		location = new Coordinate(x,y);
		
		if(state.connectStorage==true)
		{
			userStorage = state.storage;
			APIID = userStorage.addUser(buildingID, this);
		}
	}
	
	public User(SimState state, String userID, String buildingID, boolean bAppUser, int x, int y)
	{
		super(userID,buildingID);
		isAppUser = bAppUser;
		location = new Coordinate(x, y);
		
		Simulation simState = (Simulation)state;
		if(simState.connectStorage==true)
		{
			userStorage = new StorageAPI();
			APIID = userStorage.addUser(buildingID, this);
		}
	
	}

	/**
	 * @param usrID
	 * @param building
	 * @param bAppUser
	 */
	public User(String usrID, String buildingID, boolean bAppUser){
		super(usrID, buildingID);
		isAppUser = bAppUser;
		
		userStorage = new StorageAPI();
		APIID = userStorage.addUser(buildingID, this);
	}

	public User(String usrID, String buildingID){
		super(usrID, buildingID);
		isAppUser = false;
		
		userStorage = new StorageAPI();
		APIID = userStorage.addUser(buildingID, this);
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
		//double randomX = (randomStepFactor*state.random.nextDouble()*1.0-0.5);
		//double randomY = (randomStepFactor*state.random.nextDouble()*1.0-0.5);
		int direction = state.random.nextInt(2);
		if (direction == 0)
		{
			direction = 1;
		}
		else
		{
			direction = -1;
		}
		Building bld = (Building)simState.getAgentByID(buildingID);
		
		int randomX = 1;
		int randomY = 1;
		
		
		if(bld.checkStep((int) (x + direction*randomX), (int) (y))){
			x = (int) (x + direction*randomX);			
			location.x = x;						
		}
		
		else if (bld.checkStep((int) (x), (int) (y + direction*randomY))) {
			y = (int) (y + direction*randomY);			
			location.y = y;
		}
		
		else if(bld.checkStep((int) (x - direction*randomX), (int) (y))){
			x = (int) (x - direction*randomX);			
			location.x = x;						
		}
		
		else if(bld.checkStep((int) (x), (int) (y-direction*randomY))){
			y = (int) (y - direction*randomY);			
			location.y = y;						
		}
		
		else if(bld.checkStep((int) (x + direction*randomX), (int) (y + direction*randomY))){
			x = (int) (x + direction*randomX);
			y = (int) (y + direction*randomY);
			location.x = x;
			location.y = y;
		}
		
		else if(bld.checkStep((int) (x - direction*randomX), (int) (y - direction*randomY))){
			x = (int) (x - direction*randomX);
			y = (int) (y - direction*randomY);
			location.x = x;
			location.y = y;
		}
		//Update to the simulation window
		bld.updateAgentPos(this,location.x, location.y);
		//Verify whether the storage is updated or not
		// Update position in storage API
		if(simState.connectStorage==true)
			userStorage.updUserPos(APIID, location.x, location.y);
		
		//simState.storage.updUserPos(super.getID(), location.x, location.y);	
	}	
	
	/*
	 * Create movement based on the time in the system.
	 * Creates event based on the system clock.
	 * Night an intruder walks in. After 15 minutes the intruder walks out.
	 * Morning a 
	 * 
	 */
	public void createMovementBasedOnTime()
	{
		
	}
	
	/**
	 * 
	 * @param state
	 */
	public void createMissionMovement(SimState state)
	{
		//Move to a particular room
		setOnMission(true);
		destination = new Coordinate(23, 5);		
	}
	
	public void moveOnMission(SimState state)
	{
		//System.out.println("MoveOnMission Entered");
		Int2D nextPoint;
		if (destination == null)
		{
			return;
		}
		Simulation simState = (Simulation)state;
		Building bld = (Building)simState.getAgentByID(buildingID);
		routePath = AStar.findPath(location.x, location.y, destination.x, destination.y, bld.getObstacles());		
			
		if (routePath == null)
		{
			System.out.println("RoutePath is NULL!");
			return;
		}		
		
		if(routePath.isEmpty()){
			nextPoint = new Int2D(destination.x, destination.y);			
		}
		else{
			nextPoint = routePath.pop();
		}
			
		location.x = nextPoint.getX();
		location.y = nextPoint.getY();		
		bld.updateAgentPos(this,location.x, location.y);
		
		// Update position in storage API
		if(simState.connectStorage==true)
			userStorage.updUserPos(APIID, location.x, location.y);
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
	
	public void move(){
		//TODO: Implement in different sub classes.
	}	

	/* 
	 * @see edu.vt.ece5574.agents.Agent#step(SimState)
	 */
	@Override
	public void step(SimState state) {
		super.step(state);
		//System.out.println("User Step entered");
		//Simulation simState = (Simulation)state;		
		if(events.isEmpty()){
			//Modifications made just for now. Need to re-define the actions.
			if (isOnMission()==false)
			{
				createMissionMovement(state);
				moveOnMission(state);
			}
			else
			{
				createRandomMovement(state);
			}
			//if no event, create event for the robots to handle
			//createRandomMovement(state);
		}
		else{
			//in case of events react
			handleUserEvents();
		}		
	}
}
