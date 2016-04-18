package edu.vt.ece5574.roomconditions;

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

}
