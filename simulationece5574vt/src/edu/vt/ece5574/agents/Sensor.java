package edu.vt.ece5574.agents;


import edu.vt.ece5574.events.FireEvent;
import edu.vt.ece5574.events.WaterLeakEvent;
import edu.vt.ece5574.sim.Simulation;
import sim.engine.SimState;

//Class for sensors. - Author - Ameya Khandekar

public class Sensor extends Agent {
	

	private static final long serialVersionUID = 1;
	private String sensorType;  // for now storing the sensor Type as a string.

	//private  LinkedList<Event> sensorEvents;
	private int sensorID ; 

	public Sensor(String typeOfSensor, String id , int roomID_, String buildingID){
		super(id, buildingID);
		sensorType = typeOfSensor;
	}

	public Sensor(String typeOfSensor, String id, String buildingID){
		super(id, buildingID);
		sensorType = typeOfSensor;

	}


	public String getSensorType() { return sensorType; }
	public int getSensorID()   { return sensorID;   }





	@Override
	public void step(SimState state) {
		
		Simulation simState = (Simulation)state;
		// fireSensor adds fire event if temperature exceeds 1000 
		if(sensorType == "FIRE"){

			if(simState.random.nextInt(10000) > 1000){
				FireEvent fireEvent = new FireEvent();
				addEvent(fireEvent);
			}

		}

		// water leak sensor detects water leak if water pressure in the waterpipe goes below 100
		else if(sensorType == "WATERLEAK"){
			if(simState.random.nextInt(10000) < 100){
				WaterLeakEvent leakEvent = new WaterLeakEvent();
				addEvent(leakEvent);
			}

		}

		//will add case for intruder sensor later.


		//Check for events - for now not implemented.
		
/*		sensorEvents.addAll(simState.getEventsForRobotID(sensorID));

		if(!sensorEvents.isEmpty()){
			handleSensorEvents();
		}
*/
		
	}
}
