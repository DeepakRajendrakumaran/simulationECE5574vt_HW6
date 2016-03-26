package edu.vt.ece5574.tests;

import static org.junit.Assert.*;

import java.awt.Color;
import org.junit.Before;
import org.junit.Test;

import edu.vt.ece5574.agents.Robot;
import edu.vt.ece5574.events.FireEvent;
import edu.vt.ece5574.sim.Simulation;
import sim.util.Double2D;

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
		Rob = new Robot(sim.room.getWidth() * 0.5 + sim.random.nextDouble() - 0.5,
				sim.room.getHeight() * 0.5 + sim.random.nextDouble() - 0.5,Color.WHITE, "1", "0");
    	sim.room.setObjectLocation(Rob,
    			new Double2D(sim.room.getWidth() * 0.5 + sim.random.nextDouble() - 0.5,
    					sim.room.getHeight() * 0.5 + sim.random.nextDouble() - 0.5));
	}
	
	@Test(timeout=1000)
	public void randomMovement(){
		double initial_x= Rob.loc.x;
		double initial_y= Rob.loc.y;
		
		Rob.randomMovement(sim);
		assertFalse((Rob.loc.x==initial_x)&&(Rob.loc.y==initial_y));
			
		
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
		while((Rob.loc.x!= event.getX_pos())&&(Rob.loc.y!= event.getY_pos())){
			Rob.step(sim);
		}
		
		assertEquals((int)Rob.loc.x,event.getX_pos());
		
	}
}
