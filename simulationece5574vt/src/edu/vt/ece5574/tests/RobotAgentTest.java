package edu.vt.ece5574.tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
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
	Robot Rob;
	
	@Before
	public void init(){
		sim = new Simulation(1);
		String rID ="";
		String bID="";
		//To-Do:Figure out how to add agents
		Rob = new Robot(rID,bID,5,100,sim);
	}
	
	@Test(timeout=1000)
	public void randomMovement(){
		double initial_x= Rob.getX();
		double initial_y= Rob.getY();
		
		Rob.randomMovement(sim);
		assertFalse((Rob.getX()==initial_x)&&(Rob.getY()==initial_y));
			
		
	}
	
	@Test(timeout=1000)
	public void respondtoNoEvent(){
		
		Rob.step(sim);
		assertFalse(Rob.isBusy());
		
	}
	
	@Test(timeout=1000)
	public void respondtoFireEvent(){
		//FireEvent event = createFire();
		String details = 
				"{"
				+ "\"messageId\": \"0\","
				+ "\"body\":{"
				+ "\"msg_type\": \"fire\","
				+ "\"body\": {"
				+ "\"building\": 0,"
				+ "\"room\": 1,"
				+ "\"floor\": 2,"
				+ "\"xpos\": 3,"
				+ "\"ypos\": 4,"
				+ "\"severity\": 5,"
				+ "\"action\": \"Extinguish\","
				+ "\"robots\": [0,1]" //id is the id of the agent to handle the event
				+ "}"
				+ "}"
				+ "}";
		
		FireEvent event = new FireEvent();
		event.init(details);
		sim.incomingEvent(event);
		while((Rob.getX()!= event.getX_pos())&&(Rob.getY()!= event.getY_pos())){
			Rob.step(sim);
		}
		
		assertEquals((int)Rob.getX(),event.getX_pos());
		
	}
}
