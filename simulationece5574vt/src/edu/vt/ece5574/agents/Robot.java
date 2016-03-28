package edu.vt.ece5574.agents;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;

import edu.vt.ece5574.events.Event;
import edu.vt.ece5574.events.FireEvent;
import edu.vt.ece5574.events.IntruderEvent;
import edu.vt.ece5574.events.MoveRobotEvent;
import edu.vt.ece5574.events.UserMessageEvent;
import edu.vt.ece5574.events.WaterLeakEvent;
import edu.vt.ece5574.sim.Simulation;
import sim.engine.SimState;
import sim.util.MutableInt2D;

public class Robot extends Agent {

	private static final long serialVersionUID = 1;	
    
    private MutableInt2D robot_loc;
    private Vector<MutableInt2D> lastVisitedLocs;
    private int noOfSavedLocs =0;
    private int toBeSavedLocs =0;
    private boolean handlingEvent=false;
    private Event currEvent = null;
    List<Coordinate> route = null;
    Coordinate nextPoint = null;
    
    public int getX() { return robot_loc.x; }
    
    
    public int getY() { return robot_loc.y;}
    
    public boolean isBusy() { return handlingEvent; }
    
    
    
    
    
	/**
	 * Constructor to initialize position, id.
	 * @param rID : Robot ID
	 * @param bID : Building ID
	 * @param x_loc : x coordinate position of the robot
	 * @param y_loc : y coordinate position of the robot
	 */
	public Robot(String rID, String bID, int x_loc, int y_loc){
		super(rID, bID);
		robot_loc = new MutableInt2D(x_loc,y_loc);
		lastVisitedLocs = new Vector<MutableInt2D>();
		toBeSavedLocs = 8;
		//Simulation simState = (Simulation)state;
		//Building bld = (Building)simState.getAgentByID(buildingID);
		//toBeSavedLocs = (int)(bld.getFloorHeight()*bld.getFloorWidth())/bld.getNumRooms();
	}
	
	/**
	 * Not sure if this constructor will be used but it can be for testing and sets
	 * some simple values for what is needed.
	 * @param rID
	 * @param bID
	 */
	public Robot(String rID, String bID){
		super(rID, bID);
		robot_loc = new MutableInt2D(2,2);
	}
	/**
	 * Logic to simulate 'normal' movement of robot in the building 
	 * @param state 
	 */
	public void randomMovement(SimState state){
		Simulation simState = (Simulation)state;
		Building bld = (Building)simState.getAgentByID(buildingID);
		int x_pos = robot_loc.x;
		int y_pos = robot_loc.x;
		MutableInt2D new_loc;
		LinkedList<MutableInt2D> validGridList = new LinkedList<MutableInt2D>();
		SortedSet<Integer> set = new TreeSet<Integer>(); 
		for(int dx = -1; dx < 2; dx++){
			for(int dy = -1; dy < 2; dy++){
				if((dx==0) && (dy==0))
					continue;
				int x = dx+x_pos;
				int y = dy+y_pos;
				if(bld.checkStep(x,y)==true){
					int weight = checkIfRecentlyVisited(x,y);
					if(weight ==0)
						validGridList.add(new MutableInt2D(x,y));	
					else
						set.add(Integer.valueOf(weight));						
						
				}
			}
		}
		if(validGridList.isEmpty()){
			new_loc = lastVisitedLocs.elementAt(set.last()-1);
		}
		else{
			int nextLocIndex = state.random.nextInt(validGridList.size());
			new_loc = validGridList.get(nextLocIndex);
			
		}
		updateVisitedLocs(new_loc);
		robot_loc.x = new_loc.x;
		robot_loc.y = new_loc.y;
		//To-do Update location in server
		simState.storage.updRobotPos(super.getID(), robot_loc.x, robot_loc.y);
				
	}
	
	/**
	 * Add the latest location visited by the robot and update the visited loc list 
	 * @param new_loc 
	 */
	private void updateVisitedLocs(MutableInt2D new_loc){
		lastVisitedLocs.add(new_loc);
		if(toBeSavedLocs == noOfSavedLocs)
			lastVisitedLocs.remove(noOfSavedLocs);
		else
			noOfSavedLocs++;
	}

	/**
	 * Check if the given loc is in the recently visited loc list 
	 * @param x
	 * @param y 
	 */
	private int checkIfRecentlyVisited(int x, int y){
		int weight=0;
		int index=0;
	    for ( Iterator<MutableInt2D> iter = this.lastVisitedLocs.iterator(); iter.hasNext(); ) {  
	    	MutableInt2D temp = (MutableInt2D) iter.next();  
	    	index=index+1;
	        if((temp.x==x)&&(temp.y==y)){
	        	weight=index;
	        	break;
	        }
	
	    }  
	    return (weight);
	}


	public void addressEvent(){
		if(currEvent instanceof FireEvent){
			//Address FireEvent
			
		}
		else if(currEvent instanceof IntruderEvent){
			//Address event
		}
		else if(currEvent instanceof MoveRobotEvent){
			//Address event
		}
		else if(currEvent instanceof UserMessageEvent){
			//Address event
		}
		else if(currEvent instanceof WaterLeakEvent){
			//Address event
		}
		handlingEvent=false;
	}
	
	
	public void moveToEventSrc(SimState state){
		Simulation simState = (Simulation)state;
		int x_inc,y_inc;
		x_inc =  nextPoint.x - robot_loc.x;
		y_inc =  nextPoint.y - robot_loc.y;
		x_inc = (x_inc)/(Math.abs(x_inc));
		y_inc = (y_inc)/(Math.abs(y_inc));
		MutableInt2D new_loc = new MutableInt2D(robot_loc.x + x_inc, robot_loc.y + y_inc);
		updateVisitedLocs(new_loc);
		robot_loc.x = new_loc.x;
		robot_loc.y = new_loc.y;
		simState.storage.updRobotPos(super.getID(), robot_loc.x, robot_loc.y);
		
		if((robot_loc.x == currEvent.getX_pos()) && (robot_loc.y == currEvent.getY_pos() )){
			addressEvent();
		}
		else if((robot_loc.x == nextPoint.x) && (robot_loc.y == nextPoint.y )){
			nextPoint = route.get(0);
			route.remove(0);
		}
		
	}
	
	
	public void dealWithHouseEvents(SimState state,Building bld){
		currEvent = events.removeFirst();
		handlingEvent = true;
		Coordinate curr_coord = new Coordinate(robot_loc.x,
				robot_loc.y);
		Coordinate dest_coord = new Coordinate(currEvent.getX_pos(),
				currEvent.getY_pos());
		route = bld.getRoute(curr_coord, dest_coord);
		moveToEventSrc(state);
		
	}
	
	@Override
	public void step(SimState state) {
		
		Simulation simState = (Simulation)state;
		Building bld = (Building)simState.getAgentByID(buildingID);
		
		if(handlingEvent == true){
			moveToEventSrc(state);
		}
		else if(events.isEmpty()){
			randomMovement(state);
		}
		else{
			dealWithHouseEvents(state, bld);
		}
		
		
	}

}
