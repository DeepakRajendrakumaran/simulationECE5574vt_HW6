package edu.vt.ece5574.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.vt.ece5574.agents.Building;
import edu.vt.ece5574.agents.Robot;
import edu.vt.ece5574.events.FireEvent;
import edu.vt.ece5574.events.MoveRobotEvent;
import edu.vt.ece5574.sim.Simulation;


/**
 * Test Robot Agent and see that they respond to events.
 * @author Deepak Rajendrakumaran
 *
 */
public class RobotAgentTest {

	Simulation sim;
	Building bld;
	String bID="0";
	
	@Before
	public void init(){
		sim = new Simulation(1);		
		bld = new Building(bID);
		sim.addAgent(bld);
	}
	
	@Test(timeout=1000)
	public void addRobot(){
		Robot rob = new Robot("2",bID,5,100);
		assertTrue(sim.addAgent(rob));
			
		
	}
	
	@Test(timeout=1000)
	public void chckRobotID(){
		Robot rob = new Robot("3",bID,9,90);
		sim.addAgent(rob);
		assertTrue(rob.getID().compareTo("3")==0);
			
		
	}
	
	@Test(timeout=1000)
	public void chckRobotLoc(){
		int x_loc=55;
		int y_loc=19;
		Robot rob = new Robot("3",bID,x_loc,y_loc);
		sim.addAgent(rob);
		assertTrue((rob.getX()==x_loc)&&(rob.getY()==y_loc));
			
		
	}
	
	@Test(timeout=1000)
	public void randomMovement(){
		String rID ="1";
		Robot rob = new Robot(rID,bID,2,5);
		double initial_x= rob.getX();
		double initial_y= rob.getY();
		rob.randomMovement(sim);
		assertFalse((rob.getX()==initial_x)&&(rob.getY()==initial_y));
			
		
	}
	
	@Test(timeout=1000)
	public void respondtoNoEvent(){
		String rID ="1";
		Robot rob = new Robot(rID,bID,2,5);
		rob.step(sim);
		assertFalse(rob.isBusy());
		
	}
	
	@Test(timeout=1000)
	public void moveToaPoint(){
		String rID ="1";
		Robot rob = new Robot(rID,bID,2,5);

		String details = 
				"{"
				+ "\"messageId\": \"0\","
				+ "\"message\": {"
					+ "\"msg_type\": \"move robot\","
					+ "\"body\": {"
						+ "\"building\": \"0\","
						+ "\"room\": 1,"
						+ "\"floor\": 1,"
						+ "\"xpos\": 53,"
						+ "\"ypos\": 42,"
						+ "\"severity\": 5,"
						+ "\"action\": \"move\""
						+ "}"
					+ "}"
				+ "}";
		
		MoveRobotEvent event = new MoveRobotEvent();
		event.init(details);
		rob.addEvent(event);
		while(true){
			rob.step(sim);
			if(rob.isBusy()==false)
				break;
		}
		
		assertEquals((int)rob.getX(),event.getX_pos());
		assertEquals((int)rob.getY(),event.getY_pos());
		
	}
	
	@Test(timeout=1000)
	public void respondtoFireEvent(){
		String rID ="1";
		Robot rob = new Robot(rID,bID,2,5);

		String details = 
				"{"
				+ "\"messageId\": \"0\","
				+ "\"message\": {"
					+ "\"msg_type\": \"fire\","
					+ "\"body\": {"
						+ "\"building\": \"0\","
						+ "\"room\": 1,"
						+ "\"floor\": 2,"
						+ "\"xpos\": 29,"
						+ "\"ypos\": 32,"
						+ "\"severity\": 5,"
						+ "\"action\": \"Extinguish\""
						+ "}"
					+ "}"
				+ "}";
		
		FireEvent event = new FireEvent();
		event.init(details);
		rob.addEvent(event);
		while(true){
			rob.step(sim);
			if(rob.isBusy()==false)
				break;
		}
		
		assertEquals((int)rob.getX(),event.getX_pos());
		assertEquals((int)rob.getY(),event.getY_pos());
		
	}
}
