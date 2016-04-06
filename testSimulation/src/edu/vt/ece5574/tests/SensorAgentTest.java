package edu.vt.ece5574.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.vt.ece5574.agents.Building;
import edu.vt.ece5574.agents.Robot;
import edu.vt.ece5574.events.FireEvent;
import edu.vt.ece5574.events.WaterLeakEvent;
import edu.vt.ece5574.sim.Simulation;

import edu.vt.ece5574.agents.Sensor;
import edu.vt.ece5574.agents.FireSensor;
import edu.vt.ece5574.agents.WaterLeakSensor;




public class SensorAgentTest {


	Simulation sim;
	Building bld;
	String bID="0";
	
	@Before
	public void init(){
		sim = new Simulation(1);		
		bld = new Building(bID);
		sim.addAgent(bld);
	}
	

	@Test
	public void checkFireSensor() {
	

		Sensor firesensor = new FireSensor("1","0");
		assertEquals(firesensor.getSensorType(),"fire");	
		
	}
	
	@Test
	public void checkWaterLeakSensor() {
	

		Sensor waterleaksensor = new WaterLeakSensor("1","0");
		assertEquals(waterleaksensor.getSensorType(),"waterleak");	
		
	}
	
/*/*	@Test
	public void checkWaterLeakSensor() {
	

		Sensor firesensor = new Sensor("WATERLEAK", "1", "0");
		assertEquals(firesensor.getSensorType(),"WATERLEAK");	
		
	}
	
*/

	@Test(timeout=1000)
	public void burningFire(){

		FireSensor firesensor = new FireSensor("1","0");

		String details = 
				"{"
				+ "\"messageId\": \"0\","
				+ "\"message\": {"
					+ "\"msg_type\": \"fire\","
					+ "\"body\": {"
						+ "\"building\": \"0\","
						+ "\"room\": 1,"
						+ "\"floor\": 1,"
						+ "\"xpos\": 2,"
						+ "\"ypos\": 3,"
						+ "\"severity\": 5,"
						+ "\"action\": \"Extinguish\""
						+ "}"
					+ "}"
				+ "}";
		
		FireEvent event = new FireEvent();
		event.init(details);
		firesensor.addEvent(event);
		firesensor.step(sim);
		assertTrue(firesensor.getFireStatus());
		
	}


	@Test(timeout=1000)
	public void douseFire(){
		String rID ="1";
		Robot rob = new Robot(rID,bID,2,5);
		FireSensor firesensor = new FireSensor("1","0");

		String details = 
				"{"
				+ "\"messageId\": \"0\","
				+ "\"message\": {"
					+ "\"msg_type\": \"fire\","
					+ "\"body\": {"
						+ "\"building\": \"0\","
						+ "\"room\": 1,"
						+ "\"floor\": 1,"
						+ "\"xpos\": 2,"
						+ "\"ypos\": 3,"
						+ "\"severity\": 5,"
						+ "\"action\": \"Extinguish\""
						+ "}"
					+ "}"
				+ "}";
		
		FireEvent event = new FireEvent();
		event.init(details);
		rob.addEvent(event);
		firesensor.addEvent(event);
		while(true){
			rob.step(sim);
			//firesensor.step(sim);

			if(rob.isBusy()==false)
				break;
		}
		firesensor.step(sim);
		assertFalse(firesensor.getFireStatus());
		
	}

@Test(timeout=1000)
	public void leakingWater(){
		WaterLeakSensor waterleaksensor = new WaterLeakSensor("1","0");

		String details = 
				"{"
				+ "\"messageId\": \"0\","
				+ "\"message\": {"
					+ "\"msg_type\": \"water leak\","
					+ "\"body\": {"
						+ "\"building\": \"0\","
						+ "\"room\": 1,"
						+ "\"floor\": 1,"
						+ "\"xpos\": 2,"
						+ "\"ypos\": 3,"
						+ "\"severity\": 5,"
						+ "\"action\": \"fix plumbing\""
						+ "}"
					+ "}"
				+ "}";
		
		WaterLeakEvent event = new WaterLeakEvent();
		event.init(details);
		waterleaksensor.addEvent(event);
		waterleaksensor.step(sim);
		assertTrue(waterleaksensor.getWaterLeakStatus());
	}
	

}
