package edu.vt.ece5574.roomconditions;
import java.util.concurrent.atomic.AtomicInteger;
import edu.vt.ece5574.sim.Simulation;
import sim.engine.SimState;

//Author :- Ameya Khandekar

/*each room can have a waterLevel class whcih can be accessed by the agents present inside the rooms. 

The building agent will update the waterLevel according to a default in each step.
The other agents can accordingly update the WaterLevel variables in each step as the need arises. */

public class WaterLevel  {

	private static final long serialVersionUID = 1;
	

	protected String buildingID = "0"; //a building will have the same ID as building ID
	protected String roomID;

    private AtomicInteger waterLvl;  
	//protected LinkedList<Event> events;
	private Simulation state;
	
	public WaterLevel(SimState state_){
		state = (Simulation)state_;
		waterLvl = new AtomicInteger(0);
	}
	
	public WaterLevel(int waterLevel_,SimState state_){
		state = (Simulation)state_;
		waterLvl = new AtomicInteger(waterLevel_);
	}

	public int getWaterLevel(){

		return waterLvl.get();
	}

	public void defWaterLevelChange(){
		//default WaterLevel change
		int val = waterLvl.get();

		val = (((state.random.nextInt()%2) + 100)* val)/100  ; 
		waterLvl.set(val);

	}	

	public void leakWaterLevelChange(int severity){

		int val = waterLvl.get();

		val = val + severity*10;
		waterLvl.set(val);

	}

	public void robotWaterLevelChange(int rate){

		int val = waterLvl.get();
		val = val - rate*100;
		waterLvl.set(val);
	}

}
