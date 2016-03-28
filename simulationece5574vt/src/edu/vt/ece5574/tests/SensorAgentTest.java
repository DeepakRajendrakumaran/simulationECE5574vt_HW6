package edu.vt.ece5574.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.vt.ece5574.agents.Sensor;



public class SensorAgentTest {

	//For now the sensor is generating its own events and not listening to any events. thus the only unit test possible is to check sensor type.

	@Test
	public void checkFireSensor() {
	

		Sensor firesensor = new Sensor("FIRE", "1", "0");
		assertEquals(firesensor.getSensorType(),"FIRE");	
		
	}
	
	@Test
	public void checkWaterLeakSensor() {
	

		Sensor firesensor = new Sensor("WATERLEAK", "1", "0");
		assertEquals(firesensor.getSensorType(),"WATERLEAK");	
		
	}
	

}
