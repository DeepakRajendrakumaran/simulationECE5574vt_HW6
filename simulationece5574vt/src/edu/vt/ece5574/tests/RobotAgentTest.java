package edu.vt.ece5574.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.vt.ece5574.agents.Building;
import edu.vt.ece5574.agents.Robot;
import edu.vt.ece5574.events.FireEvent;
import edu.vt.ece5574.sim.Simulation;


/**
 * Test Robot Agent and see that they respond to events.
 * @author Deepak Rajendrakumaran
 *
 */
public class RobotAgentTest {

	Simulation sim;
	Building bld;
	Robot rob;
	
	@Before
	public void init(){
		sim = new Simulation(1);
		String rID ="1";
		String bID="0";
		bld = new Building(bID);
		sim.addAgent(bld);
		
		//To-Do:Figure out how to add agents
		rob = new Robot(rID,bID,5,100);
		assertTrue(sim.addAgent(rob));
	}
	
	@Test(timeout=1000)
	public void randomMovement(){
		double initial_x= rob.getX();
		double initial_y= rob.getY();
		
		rob.randomMovement(sim);
		assertFalse((rob.getX()==initial_x)&&(rob.getY()==initial_y));
			
		
	}
	
	@Test(timeout=1000)
	public void respondtoNoEvent(){
		
		rob.step(sim);
		assertFalse(rob.isBusy());
		
	}
	
	@Test(timeout=1000)
	public void respondtoFireEvent(){
		String details = 
				"{"
				+ "\"messageId\": \"0\","
				+ "\"message\": {"
					+ "\"msg_type\": \"fire\","
					+ "\"body\": {"
						+ "\"building\": \"0\","
						+ "\"room\": 1,"
						+ "\"floor\": 2,"
						+ "\"xpos\": 3,"
						+ "\"ypos\": 4,"
						+ "\"severity\": 5,"
						+ "\"action\": \"Extinguish\""
						+ "}"
					+ "}"
				+ "}";
		
		FireEvent event = new FireEvent();
		event.init(details);
		rob.addEvent(event);
		while((rob.getX()!= event.getX_pos())&&(rob.getY()!= event.getY_pos())){
			rob.step(sim);
		}
		
		assertEquals((int)rob.getX(),event.getX_pos());
		
	}
}
