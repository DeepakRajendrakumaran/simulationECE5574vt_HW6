package edu.vt.ece5574.roomconditions;
<<<<<<< HEAD

import edu.vt.ece5574.sim.Simulation;
import java.util.concurrent.atomic.AtomicInteger;
import sim.engine.SimState;

// -------------------------------------------------------------------------
/**
 *  Represents smoke presence.
 *
 *  @author Stephanie
 *  @version Apr 18, 2016
 */
public class Smoke
{
    protected String buildingID = "0"; //a building will have the same ID as building ID
    protected String roomID;

    private AtomicInteger smokeLevel;
    private Simulation state;

    public Smoke(SimState state_){
        state = (Simulation)state_;
        smokeLevel = new AtomicInteger(0);
    }

    public Smoke(int smoke_,SimState state_){
        state = (Simulation)state_;
        smokeLevel = new AtomicInteger(smoke_);
    }

    public int getSmokeLevel(){

        return smokeLevel.get();
    }

    public void defSmokeChange(){

        int val = smokeLevel.get();

        int prev = val;
        val = (((state.random.nextInt()%10) + 100)* val)/100  ;
        if(val < 75 || val > 10000){
            val = prev;
        }
        smokeLevel.set(val);
    }

    public void fireSmokeChange(int severity){

        int val = smokeLevel.get();

        if(val < 10000){
            val = val + severity*100;
        }
        smokeLevel.set(val);
    }

=======
import java.util.concurrent.atomic.AtomicBoolean;
import edu.vt.ece5574.sim.Simulation;
import sim.engine.SimState;

//Author :- Ameya Khandekar

// For now, the smoke is a random boolean. Since it plays the role of a secondary indicator of fire, this should work fine for now.


public class Smoke  {

	protected String buildingID = "0"; //a building will have the same ID as building ID
	protected String roomID;

    private AtomicBoolean smk;  
    private AtomicBoolean fireflag;
    private AtomicBoolean robotflag;
	//protected LinkedList<Event> events;
	private Simulation state;
	
	public Smoke(SimState state_){
		state = (Simulation)state_;
		smk = new AtomicBoolean(false);
/*		fireflag = new AtomicBoolean(false);
		robotflag = new AtomicBoolean(false);*/
	}
	

	public boolean getSmoke(){

		return smk.get();
	}

	public void defSmokeChange(){
		//default Temperature change
		boolean val ;

		int randomInt;
		randomInt = state.random.nextInt() ;
		if(randomInt%2 == 1){
			val = true;
		} 
		else{
			val = false;
		}

		smk.set(val);

	}	

/*	public void fireSmokeChange(){

	}

	public void robotSmokeChange(){

	}
*/
>>>>>>> 3b0ff2cd9b8bcf153ba1cd73bf45584debec42b2
}
