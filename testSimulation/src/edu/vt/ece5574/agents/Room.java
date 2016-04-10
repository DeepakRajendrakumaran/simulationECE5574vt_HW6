package edu.vt.ece5574.agents;

import java.util.LinkedList;
import java.util.ArrayList;
import sim.engine.SimState;
import edu.vt.ece5574.roomconditions.Temperature;
import edu.vt.ece5574.sim.Simulation;
public class Room {
	

	private static final long serialVersionUID = 1;

	public int x;
	public int y;	
	public int width;
	public int height;
	public Temperature roomTemperature;
	protected Simulation state;


	//constructor 
	public Room(int x_, int y_, int width_, int height_,SimState state_){
			x = x_;
			y = y_;
			width = width_;
			height = height_;
			state = (Simulation)state_;
			roomTemperature = new Temperature(state);
	}

	public boolean inRoom(int x1, int y1){
		if( ( x1 >= x ) && (x1 <= (x + width - 1)) && ( y1 >= y ) && (y1 <= (y + height - 1)) ){
			return true;
		}
		return false;
	}

	public boolean crossesRoom(Room b){

		//check for each corners :-

		if( ( x >= b.x ) && (x <= (b.x + b.width - 1)) && ( y >= b.y ) && (y <= (b.y + b.height - 1)) ){
			return true;
		}

		if( ( (x + width - 1) >= b.x ) && ( (x + width - 1) <= (b.x + b.width - 1)) && ( y >= b.y ) && (y <= (b.y + b.height - 1)) ){
			return true;
		}

		if( ( x >= b.x ) && (x <= (b.x + b.width - 1)) && ( (y + height - 1) >= b.y ) && ((y + height - 1) <= (b.y + b.height - 1)) ){
			return true;
		}

		if( (  (x + width - 1) >= b.x ) && ( (x + width - 1) <= (b.x + b.width - 1)) && 
			( (y + height - 1) >= b.y ) && ((y + height - 1) <= (b.y + b.height - 1)) ){
			return true;
		}

		return false;
	}

	public void addDoor(int[][] tilemap, String side){

		if(width < 5 || height < 5){
			return;
		}
		int position ;
		//adds closed door
		if(side == "bottom"){
			position = y + height/2 ;
			tilemap[x + width-1][position] = 2 ; 
			tilemap[x + width-1][position + 1] = 2 ; 
			tilemap[x + width-1][position + 2] = 2 ; 


		}
		else if(side == "left"){
			position = x + width/2 ;
			tilemap[position][y] = 2 ; 
			tilemap[position + 1][y] = 2 ; 
			tilemap[position + 2][y] = 2 ; 


		}
		else if(side == "right"){
			position = x + width/2 ;
			tilemap[position][y + height - 1] = 2 ; 
			tilemap[position + 1][y + height - 1] = 2 ; 
			tilemap[position + 2][y + height - 1] = 2 ; 


		}
		else if(side == "top"){
			position = y + height/2 ;
			tilemap[x ][position] = 2 ; 
			tilemap[x ][position + 1] = 2 ; 
			tilemap[x ][position + 2] = 2 ; 


		}
	}

}