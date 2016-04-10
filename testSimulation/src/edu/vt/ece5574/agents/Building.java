package edu.vt.ece5574.agents;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.ListIterator;

import edu.vt.ece5574.roomconditions.Temperature;
import edu.vt.ece5574.sim.Simulation;
import sim.engine.SimState;

//Author - Ameya Khandekar
/*
tilemap key :- 
0 - empty position
1 - wall
2 - closed door
3 - open door
*/


public class Building extends Agent{
	
	//private int minRooms = 1;
	//private int maxRooms = 10;

	private static final long serialVersionUID = 1;
	protected int width;
	protected int height; 
	protected LinkedList<Room> rooms;

	protected int[][] tilemap;

	protected Simulation state;
	protected Temperature hallTemperature; 

	public Building(String id){
		super(id, id);
		rooms = new LinkedList<Room>();

		//Dummy - needs to be removed later.
	}
	

	public Building(String id,SimState state_){
		super(id, id);
		state = (Simulation)state_;
		rooms = new LinkedList<Room>();
		
		width = 30;
		height = 30;

		tilemap = new int[width][height]; //creates a building with a tilemap full of 0s. Thus empty building with plain floor.

		for(int i = 0 ; i < width ; i++){
				tilemap[i][0] = 1; //1 indicates wall
				tilemap[i][height - 1] = 1;
		}

		for(int j = 0 ; j < height ; j++){
				tilemap[0][j] = 1; //1 indicates wall
				tilemap[width - 1][j] = 1;
		}
		hallTemperature = new Temperature(state);
		addRoom(1, 1, 10, 10);
		rooms.get(0).addDoor(tilemap,"bottom");
		addRoom(19,1,10,10);
		rooms.get(1).addDoor(tilemap,"right");
		addRoom(1,19,10,10);
		rooms.get(2).addDoor(tilemap,"left");
		addRoom(19,19,10,10);
		rooms.get(3).addDoor(tilemap,"top");


		//add Door to Hallway from outside - 
		tilemap[0][14] = 2;
		tilemap[0][15] = 2;
		tilemap[0][16] = 2;
		tilemap[0][17] = 2;
	}
	
	//constructor 
	public Building(String id, int width_, int height_, SimState state_){
				super(id, id);
				state = (Simulation)state_;

				width = width_;
				height = height_;
				tilemap = new int[width][height]; //creates a building with a tilemap full of 0s. Thus empty building with plain floor.

			for(int i = 0 ; i < width ; i++){
				tilemap[i][0] = 1; //1 indicates wall
				tilemap[i][height - 1] = 1;
			}

			for(int j = 0 ; j < height ; j++){
				tilemap[0][j] = 1; //1 indicates wall
				tilemap[width - 1][j] = 1;
			}
			hallTemperature = new Temperature(state);
			rooms = new LinkedList<Room>();
	}

	public int getRoomId(int x, int y){
		
		if( ( x >= 0 ) && (x <= (width - 1)) && ( y >= 0 ) && (y <= (height - 1)) ){

			for(int idx = 0; idx < rooms.size(); idx++)
      		{
      			if((rooms.get(idx)).inRoom(x,y))
      			{
      				return idx; // id of room ranges from 0 to totalRooms - 1.
      			}
      		}

      		return -1; // -1 indicates hall area
		}
		else{
			return -2; // - 2 indicates x,y doesn't belong to the building 
		}

	}

	public void displayLayout(){
		System.out.println("Current Layout");
		for(int i = 0; i < width; i++){
			for(int j = 0 ; j < height; j++){
				System.out.print(tilemap[i][j]);
				System.out.print(" ");
			}
			System.out.println(" ");
		}
	}

	public Temperature getRoomTempById(int idx){

		if(idx == -1){
			return hallTemperature;
		}
		else
			return (rooms.get(idx)).roomTemperature ;

	}

	public int[][] getTileMap(){

		return tilemap;
	}
	public boolean addRoom(int x, int y, int w, int h){

		Room tempRoom = new Room(x,y,w,h,state);

	//check if it crosses another room :- 
		for(int idx = 0; idx < rooms.size(); idx++)
      	{
      		if(tempRoom.crossesRoom(rooms.get(idx)))
      		{
      			System.out.println(" can't add room ");
      			return false;
      		}
      	}


		//check if fits main building :-
		if(( x > 0) && ((x < width - 1)) && ( (x + w - 1) > 0) && ((x + w - 1) < (width - 1) ) &&
			( y > 0) && (y < (height - 1)) && ( (y + h - 1) > 0) && ((y + h - 1) < (height - 1 )) ){

			rooms.add(tempRoom);
			//update tilemap
			for(int i = x ; i < x + w ; i++){
				tilemap[i][y] = 1; //1 indicates wall
				tilemap[i][y + h - 1] = 1;
			}

			for(int j = y ; j < y + h ; j++){
				tilemap[x][j] = 1; //1 indicates wall
				tilemap[x + w - 1][j] = 1;
			}
			return true;
		}
		else{
			return false;
		}
		//return true;
	}
	
	public boolean checkStep(int x, int y){
		return true;
		//Dummy - needs to be changed.

	}
	
	
	public List<Coordinate> getRoute(Coordinate current,Coordinate destination){
		List<Coordinate> myList = new ArrayList<Coordinate>();
		return myList;
		//Dummy - needs to be changed.

	} 
	
	@Override
	public void step(SimState arg0) {
		// TODO Auto-generated method stub
		super.step(arg0);
		
	}


}
