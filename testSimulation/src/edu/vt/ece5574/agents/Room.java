package edu.vt.ece5574.agents;

import java.util.LinkedList;
import java.util.ArrayList;
import sim.engine.SimState;

public class Room {
	

	private static final long serialVersionUID = 1;

	public int x;
	public int y;	
	public int width;
	public int height;

	//constructor 
	public Room(int x_, int y_, int width_, int height_){
			x = x_;
			y = y_;
			width = width_;
			height = height_;
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

}