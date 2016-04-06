package edu.vt.ece5574.main;


import edu.vt.ece5574.sim.Simulation;

public class Brain {

    private int isSimulatorRunning;	
	private Simulation sim;
    
	
	public void init(){
		System.out.println("Initializing the simulator");
		

		sim = new Simulation(0);
	}
	
	
	public void start(){
		
		System.out.println("Starting the Simulation Thread");
		String[] temp = new String[0];
		this.isSimulatorRunning=1;
		sim.run(temp);
	}
	
	public void stop(){
		
		System.out.println("Stopping the Simulation Thread");

		this.isSimulatorRunning =0;
		sim.end();
		
	}
	
	
}
