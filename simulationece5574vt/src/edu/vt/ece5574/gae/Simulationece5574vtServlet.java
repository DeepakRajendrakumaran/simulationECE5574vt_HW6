package edu.vt.ece5574.gae;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import edu.vt.ece5574.sim.Simulation;

@SuppressWarnings("serial")
public class Simulationece5574vtServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		if (req.getParameter("StartSim") != null) {

		Runnable myRunnable = new Runnable(){		
			public void run(){
				Simulation sim = new Simulation(0);
				String[] temp = new String[0];

				System.out.println("Starting the Simulation Thread");

				sim.run(temp);
			}
		};

		Thread thread = new Thread(myRunnable);
		thread.start();


		resp.setContentType("text/plain");
		resp.getWriter().println("Simulation Started");
	}
	
	if (req.getParameter("StopSim") != null) {
		
		System.out.println("Stopping the Simulation Thread");
		
		//Call Stop API here
		
		resp.setContentType("text/plain");
		resp.getWriter().println("Simulation Stopped");
		
	}

	if (req.getParameter("ViewLogs") != null) {
		
		System.out.println("Opening the simulation log file");
		
		//Call the find the simulation file
		
		resp.setContentType("text/plain");
		resp.getWriter().println("Simulation log file contents");
		
	}
	
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doPost(req, resp);
		
		System.out.println("Uploaded the simulation Config file");
		
		//Call the find the simulation file
		
		resp.setContentType("text/plain");
		resp.getWriter().println("Simulation config file uploaded");
		
	}	
	
}
