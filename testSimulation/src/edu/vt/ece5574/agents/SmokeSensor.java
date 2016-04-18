package edu.vt.ece5574.agents;

import edu.vt.ece5574.roomconditions.Temperature;
import edu.vt.ece5574.sim.Simulation;
import sim.engine.SimState;

// -------------------------------------------------------------------------
/**
 *  Senses the prescence of smoke.
 *
 *  @author Stephanie
 *  @version Apr 18, 2016
 */
public class SmokeSensor extends Sensor
{

    private static final long serialVersionUID = 1;
    int x,y;
    Building bld;
    //Smoke smoke;

    public SmokeSensor(String id_, String buildingID_, String sensorType_)
    {
        super(id_, buildingID_, "smoke");
        // TODO Auto-generated constructor stub
    }

    public SmokeSensor(String id_, String buildingID_,SimState state_, int x_, int y_) {

        super(id_,buildingID_,"weight");
        Simulation state = (Simulation)state_;
        x = x_;
        y = y_;
        bld = (Building)state.getAgentByID(buildingID);
        //smoke = bld.getRoomSmokeById(bld.getRoomId(x,y));
    }

}
