package edu.vt.ece5574.agents;


import edu.vt.ece5574.events.Event;
import edu.vt.ece5574.events.FireEvent;
import edu.vt.ece5574.sim.Simulation;
import sim.engine.SimState;

//Class for sensors. - Author - Ameya Khandekar

public class FireSensor extends Sensor {
	



	private static final long serialVersionUID = 1;
	private String sensorType;  

	//Constructors for Sensor class :- for now only providing support for 1st Agent class constructor
	public FireSensor(String id_, String buildingID_){
		super(id_,buildingID_,"fire");
	
	}


	@Override
	public void handleSensorEvents(){
		//do nothing in base class
		while(events.size()!=0)
		{
			Event currentEvent = events.removeFirst();
			//take appropriate action.
				
		}
	}




	@Override
	public void step(SimState state) {
		super.step(state);
		if(events.isEmpty()){
			//do routine sensor checks
		}
		else{
			//in case of events react
			handleSensorEvents();
		}			
	}


}
