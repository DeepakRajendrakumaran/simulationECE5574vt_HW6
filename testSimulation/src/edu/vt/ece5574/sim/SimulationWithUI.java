package edu.vt.ece5574.sim;

import java.awt.Color;

import javax.swing.JFrame;

import edu.vt.ece5574.agents.Building;
import sim.display.Controller;
import sim.display.Display2D;
import sim.display.GUIState;
import sim.engine.SimState;
import sim.field.grid.IntGrid2D;
import sim.portrayal.grid.FastValueGridPortrayal2D;
import sim.portrayal.grid.SparseGridPortrayal2D;

public class SimulationWithUI extends GUIState
{
	 public Display2D display;
	 public JFrame displayFrame;
	 FastValueGridPortrayal2D buildingPortrayal = new FastValueGridPortrayal2D("building", true); 
	 FastValueGridPortrayal2D wallPortrayal = new FastValueGridPortrayal2D("Walls", true); 
	 SparseGridPortrayal2D robotPortrayal = new SparseGridPortrayal2D();
	 SparseGridPortrayal2D userPortrayal = new SparseGridPortrayal2D();
	 SparseGridPortrayal2D sensorPortrayal = new SparseGridPortrayal2D();
	
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
	
	
	 public void setupPortrayals()
     {
		 Simulation sim = (Simulation)state;
		 Building building = (Building)sim.getAgentByID("0");

		 IntGrid2D build = building.getTileMap();
     // tell the portrayals what to portray and how to portray them
		 buildingPortrayal.setField(build);
		 buildingPortrayal.setMap(new sim.util.gui.SimpleColorMap(
	             0,
	             1,
	             new Color(0,0,0,0),
	             new Color(255,0,0,255) ));
		 
		 wallPortrayal.setField(building.getwalls());
		 wallPortrayal.setMap(new sim.util.gui.SimpleColorMap(
				  0,
	                1,
	                new Color(0,0,0,0),
	                new Color(128,64,64,255) ));
         
     // reschedule the displayer
     display.reset();

     // redraw the display
     display.repaint();
     }
	
	 public void start()
     {
     super.start();  // set up everything but replacing the display
     // set up our portrayals
     setupPortrayals();
     }
	
	 public void load(SimState state)
     {
     super.load(state);
     // we now have new grids.  Set up the portrayals to reflect that
     setupPortrayals();
     }
	
	public void init(Controller c)
    {
    super.init(c);
    
    // Make the Display2D.  We'll have it display stuff later.
    display = new Display2D(400,400,this); // at 400x400, we've got 4x4 per array position
    displayFrame = display.createFrame();
    c.registerFrame(displayFrame);   // register the frame so it appears in the "Display" list
    displayFrame.setVisible(true);

    // attach the portrayals from bottom to top
   
    display.attach(wallPortrayal,"Walls");
    display.attach(buildingPortrayal,"Building");
   // display.attach(userPortrayal,"Users");
  //  display.attach(sensorPortrayal,"Sensors");
    
    // specify the backdrop color  -- what gets painted behind the displays
    display.setBackdrop(Color.white);
    }
	
	  public void quit()
      {
      super.quit();
      
      // disposing the displayFrame automatically calls quit() on the display,
      // so we don't need to do so ourselves here.
      if (displayFrame!=null) displayFrame.dispose();
      displayFrame = null;  // let gc
      display = null;       // let gc
      }
}