package edu.vt.ece5574.agents;

import java.awt.Color;
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
import edu.vt.ece5574.events.WaterLeakEvent;
import edu.vt.ece5574.sim.Simulation;
import sim.engine.SimState;
import sim.util.MutableInt2D;

/**
 * The logic for robot movement
 * 1-move around the building to sense changes
 * 2-React to an event 
 * @author Deepak Rajendrakumaran
 *
 */
public class Robot extends Agent {

	private static final long serialVersionUID = 1;	
    
    private MutableInt2D robot_loc;
    private Vector<MutableInt2D> lastVisitedLocs;
    private int noOfSavedLocs =0;
    private int toBeSavedLocs =0;
    private boolean handlingEvent=false;
    private Event currEvent = null;
    private List<Coordinate> route = null;
    private Coordinate nextPoint = null;
    
    
	/**
	 * Constructor to initialize position, id.
	 * @param rID : Robot ID
	 * @param bID : Building ID
	 * @param x_loc : x coordinate position of the robot
	 * @param y_loc : y coordinate position of the robot
	 */
	public Robot(String rID, String bID, int x_loc, int y_loc){
		super(Color.blue,true,rID, bID);
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
		super(Color.blue,true,rID, bID);
		robot_loc = new MutableInt2D(2,2);
	}
	
	/**
   	 * Get current robot x coordinate
   	 */
    public int getX() { return robot_loc.x; }
    
    /**
   	 * Get current robot y coordinate
   	 */
    public int getY() { return robot_loc.y;}
    
    /**
	 * Check if robot is busy dealing with an event
	 */
    public boolean isBusy() { return handlingEvent; }
    
    
    
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
				
				//temp hack
			//	if((x>9) || (y>9))continue;
				//Hack end
				if(bld.checkStep(x,y)){
				//	System.out.println("Check Pass");
					int weight = checkIfRecentlyVisited(x,y);
					if(weight ==0){
						
						validGridList.add(new MutableInt2D(x,y));	
					}
					else{
						
						set.add(Integer.valueOf(weight));	
					}
						
				}
				/*else{
					System.out.println("Check Fail");
				}*/
			}
		}
		
		if(!validGridList.isEmpty()){
			int nextLocIndex = state.random.nextInt(validGridList.size());
			new_loc = validGridList.get(nextLocIndex);
			
		}
		else if(set.size()!=0){
			new_loc = lastVisitedLocs.elementAt(set.last()-1);			
		}
		else{
			new_loc=robot_loc;
		}
		
		updateVisitedLocs(new_loc);
		robot_loc.x = new_loc.x;
		robot_loc.y = new_loc.y;
		bld.updateAgentPos(this,robot_loc.x, robot_loc.y);
		//simState.storage.updRobotPos(super.getID(), robot_loc.x, robot_loc.y);
				
	}
	
	/**
	 * Add the latest location visited by the robot and update the visited loc list 
	 * @param new_loc - New location to be added  to the visited location.
	 */
	private void updateVisitedLocs(MutableInt2D new_loc){
		lastVisitedLocs.add(new_loc);
		if(toBeSavedLocs == noOfSavedLocs)
			lastVisitedLocs.remove(noOfSavedLocs-1);
		else
			noOfSavedLocs++;
	}

	/**
	 * Check if the given location is in the recently visited location list 
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



	/**
	 * This is where the robot deals with the event after reaching the event location
	 */
	public void addressEvent(){
		
		Building bld = (Building)simState.getAgentByID(buildingID);

		if(currEvent instanceof FireEvent){

			//Robot reducin temperature of room affected by fire. using default rate of 1.
			(bld.getRoomTempById(bld. getRoomId(robot_loc.getX(),robot_loc.getY()))).robotTempChange(1);

			// old Boolean Logic
			//((FireEvent) currEvent).turn_fireOff();			
		}
		
		else if(currEvent instanceof IntruderEvent){
			((IntruderEvent) currEvent).dealWithIntruder();
		}
		else if(currEvent instanceof MoveRobotEvent){
			((MoveRobotEvent) currEvent).RobotReachedLoc();
		}
		else if(currEvent instanceof WaterLeakEvent){
			((WaterLeakEvent) currEvent).turn_LeakOff();
		}
		handlingEvent=false;
	}
	
	/**
	 * Take one step while moving towards an event location
	 * @param state 
	 */
	public void moveToEventSrc(SimState state){
	
		Simulation simState = (Simulation)state;
		Building bld = (Building)simState.getAgentByID(buildingID);
		int x_inc,y_inc;
		x_inc =  nextPoint.x - robot_loc.x;
		y_inc =  nextPoint.y - robot_loc.y;
		if(x_inc!=0)
			x_inc = (x_inc)/(Math.abs(x_inc));
		if(y_inc!=0)
			y_inc = (y_inc)/(Math.abs(y_inc));
		MutableInt2D new_loc = new MutableInt2D(robot_loc.x + x_inc, robot_loc.y + y_inc);
		updateVisitedLocs(new_loc);
		robot_loc.x = new_loc.x;
		robot_loc.y = new_loc.y;
		bld.updateAgentPos(this,robot_loc.x, robot_loc.y);
		//simState.storage.updRobotPos(super.getID(), robot_loc.x, robot_loc.y);
			
	}
	
	/**
	 * Obtain route and get started moving towards an event after noticing an event
	 * First step after noticing an event
	 * @param state 
	 * @param bld - building in which the robot is moving
	 */
	public void dealWithHouseEvents(SimState state,Building bld){
		currEvent = events.removeFirst();
		handlingEvent = true;
		Coordinate curr_coord = new Coordinate(robot_loc.x,
				robot_loc.y);
		Coordinate dest_coord = new Coordinate(currEvent.getX_pos(),
				currEvent.getY_pos());
		route = bld.getRoute(curr_coord, dest_coord);
		if(route.size()==0){
			
			nextPoint = new Coordinate(currEvent.getX_pos(),
					currEvent.getY_pos());
		}
		else{
			
			nextPoint = route.remove(0);
			
		}
		moveToEventSrc(state);
		
	}
	
	@Override
	public void step(SimState state) {
		
		Simulation simState = (Simulation)state;
		Building bld = (Building)simState.getAgentByID(buildingID);
		
		super.step(state);
		
		if(handlingEvent == true){
			if(robot_loc.x == currEvent.getX_pos() && robot_loc.y == currEvent.getY_pos()){
				addressEvent();
			}
			else {
				if((robot_loc.x == nextPoint.x) && (robot_loc.y == nextPoint.y )){
					if(route.size()==0){
						nextPoint = new Coordinate(currEvent.getX_pos(),
								currEvent.getY_pos());
					}
					else{
						nextPoint = route.remove(0);
					}
				}
				else
					moveToEventSrc(state);
			}
			
		}
		else if(events.isEmpty()){
			randomMovement(state);
		}
		else{
			dealWithHouseEvents(state, bld);
		}
		
		
	}

}
