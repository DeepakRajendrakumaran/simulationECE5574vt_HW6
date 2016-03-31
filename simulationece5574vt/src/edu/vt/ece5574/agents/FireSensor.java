package edu.vt.ece5574.agents;


import edu.vt.ece5574.events.Event;
import edu.vt.ece5574.events.FireEvent;
import sim.engine.SimState;

//Class for sensors. - Author - Ameya Khandekar

public class FireSensor extends Sensor {
	



	private static final long serialVersionUID = 1;
 

	private boolean fireStatus;

	//Constructors for Sensor class :- for now only providing support for 1st Agent class constructor
	public FireSensor(String id_, String buildingID_){
		super(id_,buildingID_,"fire");
	
	}

	public boolean getFireStatus(){
		return fireStatus;
	}

	@Override
	public void handleSensorEvents(){
		while(events.size()!=0)
		{
			Event currentEvent = events.removeFirst();
			if(currentEvent instanceof FireEvent){
				FireEvent fireevent = (FireEvent)currentEvent;
				if(fireevent.is_fireActive()){
					//send sensor data = "Fire Active" to storage API.
					fireStatus = true;
				}
				else{
					//send sensor data = "Fire de-activated" to storage API.
					fireStatus = false;
				}
				
			}
			
				
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
