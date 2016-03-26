package edu.vt.ece5574.sim;

import sim.display.GUIState;
import sim.engine.SimState;

public class SimulationWithUI extends GUIState
{
	public SimulationWithUI()
	{
		super(new Simulation(System.currentTimeMillis()));
	}
	
	public SimulationWithUI(SimState state)
	{
		super(state);
	}
	
	public static String getName()
	{
		return "Smart Building Simulation 1.0.0";
	}
	
	
	/*  This is how we can start the simulation: Just "java SimulationWithUI"
		The main method just creates a controller, which by default is the Mason console.
	*/
	public static void main(String[] args)
	{
		new SimulationWithUI().createController();
	}
}