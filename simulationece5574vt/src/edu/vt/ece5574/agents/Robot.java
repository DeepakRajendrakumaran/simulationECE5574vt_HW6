package edu.vt.ece5574.agents;

import java.awt.Color;
import edu.vt.ece5574.events.Event;
import edu.vt.ece5574.events.FireEvent;
import edu.vt.ece5574.events.IntruderEvent;
import edu.vt.ece5574.events.MoveRobotEvent;
import edu.vt.ece5574.events.UserMessageEvent;
import edu.vt.ece5574.events.WaterLeakEvent;
import edu.vt.ece5574.sim.Simulation;
import sim.engine.SimState;
import sim.util.Double2D;
import sim.util.MutableDouble2D;

public class Robot extends Agent {

	private static final long serialVersionUID = 1;
	private Event currEvent = null;
	//private  Building bld;
	private boolean busy=false;
	
	public MutableDouble2D loc, velocity;
    public MutableDouble2D newLoc = new MutableDouble2D();
  //  public MutableDouble2D sumVector = new MutableDouble2D(0,0);

    public double  radius =2;
    
    public boolean isBusy() { return busy; }
        
    public double getX() { return loc.x; }
    public void setX( double newX ) { loc.x = newX; }
    
    public double getY() { return loc.y; }
    public void setY( double newY ) { loc.y = newY; }
    
  //  public double getVelocityX() { return velocity.x; }
  //  public void setVelocityX( double newX ) { velocity.x = newX; }
    
  //  public double getVelocityY() { return velocity.y; }
   // public void setVelocityY( double newY ) { velocity.y = newY; }
 
  //  public double getSpeed() { return speed; }
  //  public void setSpeed( double newSpeed ) { speed = newSpeed; }   
    
    public double getRadius() { return radius; }
    public void setRadius( double newRadius ) 
        {
        radius = newRadius;
        scale = 2 * radius;  
        } 
    
    
    
	
	public Robot( double newX, double newY,  Color c , String rID, String buildingID)
    {
    super(c, 2 * 2, rID, buildingID);  // scale is twice the radius
    
    
    //bld = blding;
    
    loc = new MutableDouble2D(newX, newY);
    //velocity = new MutableDouble2D(0, 0);
    
    radius = 2;
    
    
    //speed = 0.1;
    }
	
	/**
	 * Not sure if this constructor will be used but it can be for testing and sets
	 * some simple values for what is needed.
	 * @param rID
	 * @param bID
	 */
	public Robot(String rID, String bID){
		super(rID, bID);
		loc = new MutableDouble2D(2,2);
		radius = 2;
	}
	


	public void dealWithEvents(SimState state){
		Simulation simState = (Simulation)state;
		//Can't have a loop like this.  
		//The step function should be short and handling all events
		//inside a single step would be insanity.  They need to be 
		//broken down.  This may be your plan in the end but I figured I'd 
		//make a note regardless
		while(events.size()!=0){
			currEvent = events.removeFirst();
			busy=true;
			reactToEvent(simState);
			
		}
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
		busy=false;
	}
	
	public void reactToEvent(Simulation simState){
		double xpos = currEvent.getX_pos();
		double ypos = currEvent.getY_pos();
		if(loc.x<xpos){
			if((loc.x+1)<xpos)
				loc.x=loc.x+1;
			else
				loc.x=xpos;
		}
		else if(loc.x>xpos){
			if((loc.x-1)>xpos)
				loc.x=loc.x-1;
			else
				loc.x=xpos;
		}
		if(loc.y<ypos){
			if((loc.y+1)<ypos)
				loc.y=loc.y+1;
			else
				loc.y=ypos;
		}
		else if(loc.y>ypos){
			if((loc.y-1)>ypos)
				loc.y=loc.y-1;
			else
				loc.y=ypos;
		}
		simState.room.setObjectLocation(this, new Double2D(loc.x, loc.y));
		if((loc.x==xpos) && (loc.y==ypos)){
			addressEvent();
		}
	}
	
	public void randomMovement(SimState state) {
		Simulation simState = (Simulation)state;
		double xd = (state.random.nextDouble() - 0.5);
        double yd = (state.random.nextDouble() - 0.5);
        double xm = loc.x + xd;
        double ym = loc.y + yd;
        
        if(!(xm >= 0 && xm < simState.room.getWidth())){
       	  if(xm < 0){
       		  xm=0;
       	  }
       	  else if(xm >= simState.room.getWidth()){
       		  xm = simState.room.getWidth()-1;
       	  }
         }
        else if(!(ym >= 0 && ym < simState.room.getHeight())){
       	 if(ym<0){
       		 ym=0;
       	 }
       	 else if(ym >= simState.room.getHeight()){
       		 ym = simState.room.getHeight()-1;
       	 }
        }
        loc.x = xm;
        loc.y = ym;
        simState.room.setObjectLocation(this, new Double2D(xm, ym));
	}
	
	@Override
	public void step(SimState state) {
		
		Simulation simState = (Simulation)state;
		
		//Check for events
		
		if(busy==true){		

			reactToEvent(simState);
		}
		if(!(events ==null)){
		if(events.isEmpty()){
			//if no event, move randomly to collect sensor data
			randomMovement(state);
		}
		else{
			//in case of events react
			dealWithEvents(state);
		}
		
		}
	}

}
