package edu.vt.ece5574.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.vt.ece5574.agents.Building;
import edu.vt.ece5574.agents.User;
import edu.vt.ece5574.events.FireEvent;
import edu.vt.ece5574.events.IntruderEvent;
import edu.vt.ece5574.events.WaterLeakEvent;
import edu.vt.ece5574.sim.Simulation;

/**
 * Test cases for Users class
 * 
 * @author Vedahari Narasimhan Ganesan 
 * */

public class UserTest {

	@Test
	public void checkDefaultBuildingId() {
		User user = new User("1", "0");
		assertEquals(user.getBuildingID(), "0");		
	}
	
	@Test
	public void checkBuildingId() {
		User user = new User("1", "0");
		user.setBuildingID("5000");
		assertEquals(user.getBuildingID(),"5000");		
	}
	
	@Test
	public void checkDefaultAppUser() {
		User user = new User("1", "0");
		assertEquals(user.isAppUser(),false);		
	}
	
	@Test
	public void checkAppUser() {
		User user = new User("1", "0");
		user.setAppUser(true);
		assertEquals(user.isAppUser(),true);		
	}
	
	@Test
	public void checkDefaultUser() {
		User user = new User("1", "0");
		assertEquals(user.getID(), "1");
		assertEquals(user.getBuildingID(),"0");
		assertEquals(user.isAppUser(),false);
	}
	
	Simulation sim;
	Building bld;
	User usr;
	String fireEventDetails = 
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
	
	String waterLeakEventDetails = 
			"{"
			+ "\"messageId\": \"0\","
			+ "\"message\": {"
				+ "\"msg_type\": \"water leak\","
				+ "\"body\": {"
					+ "\"building\": \"0\","
					+ "\"room\": 1,"
					+ "\"floor\": 2,"
					+ "\"xpos\": 3,"
					+ "\"ypos\": 4,"
					+ "\"severity\": 5,"
					+ "\"action\": \"fix plumbing\""
					+ "}"
				+ "}"
			+ "}";
	
	String intruderEventDetails = 
			"{"
			+ "\"messageId\": \"0\","
			+ "\"message\": {"
				+ "\"msg_type\": \"intruder\","
				+ "\"body\": {"
					+ "\"building\": \"0\","
					+ "\"room\": 1,"
					+ "\"floor\": 2,"
					+ "\"xpos\": 3,"
					+ "\"ypos\": 4,"
					+ "\"severity\": 5,"
					+ "\"action\": \"defend\""
					+ "}"
				+ "}"
			+ "}";
		
	@Before
	public void initialize(){
		sim = new Simulation(1);
		String uID ="1";
		String bID="0";
		bld = new Building(bID);
		sim.addAgent(bld);		
		//To-Do:Figure out how to add agents
		usr = new User(uID,bID,true,5,100);
		assertTrue(sim.addAgent(usr));
	}
	
	@Test(timeout=1000)
	public void respondtoFireEventUserWithApp(){		
		usr.setAppUser(true);		
		FireEvent event = new FireEvent();		
		event.init(fireEventDetails);
		usr.addEvent(event);			
		usr.step(sim);
		assertTrue(usr.getFireNotification());
	}
	
	@Test(timeout=1000)
	public void respondtoFireEventUserWithoutApp(){		
		usr.setAppUser(false);
		FireEvent event = new FireEvent();
		event.init(fireEventDetails);
		usr.addEvent(event);			
		usr.step(sim);
		assertEquals(usr.getFireNotification(),false);	
	}
	
	@Test(timeout=1000)
	public void respondtoWaterLeakEventUserWithApp(){		
		usr.setAppUser(true);
		WaterLeakEvent event = new WaterLeakEvent();		
		event.init(waterLeakEventDetails);
		usr.addEvent(event);	
		usr.step(sim);
		assertEquals(true,usr.getWaterLeakNotification());	
	}
	
	@Test(timeout=1000)
	public void respondtoWaterLeakEventUserWithoutApp(){		
		usr.setAppUser(false);
		WaterLeakEvent event = new WaterLeakEvent();
		event.init(waterLeakEventDetails);
		usr.addEvent(event);	
		usr.step(sim);
		assertEquals(usr.getWaterLeakNotification(),false);	
	}
	
	@Test
	public void respondtoIntruderEventUserWithApp(){		
		usr.setAppUser(true);
		IntruderEvent event = new IntruderEvent();
		event.init(intruderEventDetails);
		usr.addEvent(event);		
		usr.step(sim);
		assertEquals(usr.getIntruderNotification(),true);	
	}
	
	@Test(timeout=1000)
	public void respondtoIntruderEventUserWithoutApp(){
		usr.setAppUser(false);
		IntruderEvent event = new IntruderEvent();
		event.init(intruderEventDetails);
		usr.addEvent(event);			
		usr.step(sim);
		assertEquals(usr.getIntruderNotification(),false);	
	}
	
}
