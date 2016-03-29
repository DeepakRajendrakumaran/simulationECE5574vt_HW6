package edu.vt.ece5574.gae;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import edu.vt.ece5574.sim.Simulation;
/**
 * This is the main user side interface of the simulator. This is the link between the simulator 
 * and web page interfaces like upload config file, start, stop, view log etc.
 * This is based on Google App Engine framework and JavaServlet Technology.
 * @author: Bishwajit Dutta, bdutta@vt.edu
 * */

@SuppressWarnings("serial")
public class Simulationece5574vtServlet extends HttpServlet {

	private simRunnable runner;
	private Thread simthread;
	private Simulation sim;
	private int isSimulatorRunning=0;

	class simRunnable implements Runnable{		
		public void run(){

			System.out.println("Starting the Simulation Thread");
			String[] temp = new String[0];

			sim.run(temp);
		}	
	}

	@Override
	public void init() throws ServletException {
		super.init();
		System.out.println("Initializing the simulator");

		runner = new simRunnable();       
		simthread = new Thread(runner);

		sim = new Simulation(0);

	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		if (req.getParameter("StartSim") != null) {	

			if(isSimulatorRunning == 1)
			{
				System.out.println("Simulator is already running");				
				resp.setContentType("text/plain");
				resp.getWriter().println("Simulator is already running");
			}else
			{
				simthread.start();
				isSimulatorRunning=1;
				System.out.println("Simulation Started");
				resp.setContentType("text/plain");
				resp.getWriter().println("Simulation Started");
			}
		}

		if (req.getParameter("StopSim") != null) {

			if(isSimulatorRunning == 0)
			{
				System.out.println("Simulator is already stopped");				
				resp.setContentType("text/plain");
				resp.getWriter().println("Simulator is already stopped");	
			}else
			{
				System.out.println("Stopping the Simulation Thread");				
				resp.setContentType("text/plain");
				resp.getWriter().println("Stopping the Simulation Thread");

				isSimulatorRunning =0;
				sim.end();
			}
		}

		if (req.getParameter("ViewLogs") != null) {

			System.out.println("Opening the simulation log file");
			resp.setContentType("text/plain");
			resp.getWriter().println("Opening the simulation log file");

			//Call the simulation log file
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		if(isSimulatorRunning == 1)
		{

			System.out.println("Pls stop the simulator before config file upload");
			resp.setContentType("text/plain");
			resp.getWriter().println("Pls stop the simulator before config file upload");	

		}
		else
		{
			//Update the config file

			System.out.println("Simulation config file uploaded");
			resp.setContentType("text/plain");
			resp.getWriter().println("Simulation config file uploaded");
		}

	}	

}
