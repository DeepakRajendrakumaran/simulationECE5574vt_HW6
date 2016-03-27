package edu.vt.ece5574.gae;
import java.io.IOException;

import javax.servlet.http.*;

import edu.vt.ece5574.sim.Simulation;

@SuppressWarnings("serial")
public class Simulationece5574vtServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		Simulation sim = new Simulation(0);
		String[] temp = new String[10];

		// run is the entry point and is called with a list of strings
		//similarly to how it could be called from the cmdline
		//This is how doLoop is called so it's necessary to build it here 
		//or have the simulation build its list
		sim.run(temp);
		
		resp.setContentType("text/plain");
		resp.getWriter().println("Simulation Started");
	}
}
