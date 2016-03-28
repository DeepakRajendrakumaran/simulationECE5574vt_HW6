package edu.vt.ece5574.sim;

import java.util.HashMap;
import java.util.LinkedList;

import edu.vt.ece5574.agents.Agent;
import edu.vt.ece5574.agents.Building;
import edu.vt.ece5574.agents.Robot;
import edu.vt.ece5574.events.Event;
import sim.engine.*;
import sim.field.continuous.Continuous2D;
import sim.util.Double2D;
import java.awt.*;

/**
 * The root of the simulation.  This is where things get started and the magic happens.
 * @author David Kindel
 *
 */
public class Simulation extends SimState {

	public Configuration config;
	
	private static final long serialVersionUID = 1;
    public Continuous2D room = new Continuous2D(1.0,100,100);
    public int numRobots;
    
    
 
    public HashMap<String, Agent> agents; //map the agent id to the agent itself
    
    public Simulation(long seed){
    	super(seed); //needs to be first line, can't just set seed here
    	/*
    	config = new Configuration("config.properties");
    	
    	numRobots = Integer.parseInt(config.getProp("numRobots"));
        boolean debug = Boolean.parseBoolean(config.getProp("debug")); //will be read in from config.  If it's a debug, we'll set seed manually
        //seed = 5;
        seed = Long.parseLong(config.getProp("seedValue"));
        
        if(debug){
        	if(seed == 0){
        		System.err.println("Seed cannot be set to 0. Running sim with default seed.");
        	}
        	else{
        		setSeed(seed);
            	System.out.println("seed modified to: " + seed());
            	System.out.println("Ignore other message noting the job number and seed value.");
        	}
        }
        */
        agents = new HashMap<String, Agent>();
    }
    
	public void run(String[] args) {
		
		doLoop(Simulation.class, args);
		System.exit(0);
	}


    public void start() {
        super.start();  // very important!  This resets and cleans out the Schedule.
        
        //clear the room of previous actors
        room.clear();

        int numBuildings = Integer.parseInt(config.getProp("numBuildings"));

        for(int i = 0; i < numRobots; i++){
        	Robot agent = new Robot(room.getWidth() * 0.5 + random.nextDouble() - 0.5,
					room.getHeight() * 0.5 + random.nextDouble() - 0.5,Color.WHITE, new Integer(i+numBuildings).toString(), "0");
        	room.setObjectLocation(agent,
        			new Double2D(room.getWidth() * 0.5 + random.nextDouble() - 0.5,
        					room.getHeight() * 0.5 + random.nextDouble() - 0.5));
        	agents.put(new Integer(i+1).toString(), agent);
        	schedule.scheduleRepeating(agent);
        }

        for(int i = 0; i < numBuildings; i++){
        	agents.put(new Integer(i).toString(), new Building(new Integer(i).toString()));
        }
    }
    
    /**
     * For *any* incoming event, there's at least 1 associated ID of a user accepting 
     * that event.  Take that ID and find the agent for it.  Add the event to that agent's 
     * event list. 
     * 
     * This only adds the event to agents who have been added to the simulation and does *not*
     * create new ones if unseen ids are provided.  It will only skip over them
     * @param event The event to add.  
     */
    public void incomingEvent(Event event){
    	LinkedList<String> acceptingAgents = event.getAgentsToAccept();
    	for(int i = 0; i < acceptingAgents.size(); i++){
    		Agent agent = agents.get(acceptingAgents.get(i));
    		if(agent != null){ //The agent must be in the agent list to be able to act on an event
    			agent.addEvent(event);
    		}
    	}
    }
    
    /**
     * Add an agent into the environment. It gets placed into the global agent list if 
     * there wasn't an agent with that ID previously.  If there was, false is returned.
     * 
     * This will certainly be called by a building agent but not necessarily by anything else.  
     * The building will receive an "add robot" event and will then have to call this method.
     * 
     * This may also be used in testing
     * 
     * This function should never be called if the building hasn't already been added
     * unless you're adding a building itself.
     * Furthermore, every agent must be assigned to a building prior to calling this.
     * 
     * @param agent The agent to place into the simulation
     * @return True if the agent is placed in with a unique ID.  False if it couldn't be added
     * due to a bad ID or no building ID set
     */
    public boolean addAgent(Agent agent){
    	if(agent == null){
    		return false;
    	}
    	System.out.println();
    	if(agent.getBuildingID() == null || agent.getID() == null || 
    			(agents.get(agent.getBuildingID()) == null 
    			&& agent.getClass() != Building.class)){
			return false;
    	}
    	
    	if(agents.get(agent.getID()) != null){
    		return false;
    	}
    	agents.put(agent.getID(), agent);
    	return true;
    }
    
    /**
     * Remove the agent from the simulation environment by removing it from the global agent list.
     * 
     * @param agent The agent to remove
     * @return Returns the agent that was removed if it was in the list, null if it wasn't.
     */
    public Agent removeAgent(Agent agent){
    	if(agent == null){
    		return null;
    	}
    	return agents.remove(agent.getID());
    }

    /**
     * Get the agent by the provided string ID
     * @param id the id of the agent
     * @return the agent object itself or null if not found
     */
	public Agent getAgentByID(String id) {
		return agents.get(id);
	}
	
	/**
	 * Notifies the agent that a message is waiting on the server side.
	 * @param id The ID of the agent for a waiting message
	 * @return True if the agent is found and its message waiting flag set
	 */
	public boolean agentPushReceived(String id){
		Agent agent = agents.get(id);
		if(agent == null){
			return false;
		}
		agent.setMessageWaiting();
		return true;
	}
}
