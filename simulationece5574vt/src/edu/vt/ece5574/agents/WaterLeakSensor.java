package edu.vt.ece5574.agents;


import edu.vt.ece5574.events.Event;
import edu.vt.ece5574.events.WaterLeakEvent;
import sim.engine.SimState;

//Class for sensors. - Author - Ameya Khandekar

public class WaterLeakSensor extends Sensor {
	



	private static final long serialVersionUID = 1;


	private boolean waterleakStatus;

	//Constructors for Sensor class :- for now only providing support for 1st Agent class constructor
	public WaterLeakSensor(String id_, String buildingID_){
		super(id_,buildingID_,"waterleak");
	
	}

	public boolean getWaterLeakStatus(){
		return waterleakStatus;
	}

	@Override
	public void handleSensorEvents(){
		while(events.size()!=0)
		{
			Event currentEvent = events.removeFirst();
			if(currentEvent instanceof WaterLeakEvent){
				WaterLeakEvent waterleakevent = (WaterLeakEvent)currentEvent;
				if(waterleakevent.is_WaterLeakActive()){
					//send sensor data = "WaterLeak Active" to storage API.
					waterleakStatus = true;
				}
				else{
					//send sensor data = "WaterLeak de-activated" to storage API.
					waterleakStatus = false;
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
