package edu.vt.ece5574.agents;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import sim.engine.SimState;


public class Building extends Agent{
	
	//private int minRooms = 1;
	//private int maxRooms = 10;

	private static final long serialVersionUID = 1;
	private Room building;
	
	
	public Building(String id) {
		super(id, id);
		
		Map<String, Room> rooms = new HashMap<String,Room>(50);

		
		building = new Room(id,10,10);
		
		Room a = new Room(id,5,10);
		Room b = new Room(id,4,10);
		
		this.addWall();
		this.addDoor();
		
		rooms.put("1",a);
		rooms.put("2",b);
		
		building.showRoom();
	}
	
	public boolean checkStep(int x, int y){
		return building.checkStep(x, y);
	}
	
	private void addWall(){
		for(int i =0;i<10;i++){
			Item wall = new Item(super.id ,Item.Obstacle.wall);
 			
			if(i != 5){
				building.insertObstacle(5, i, 1, 1, wall);
			}
		}
	}
	
	private void addDoor(){
		Item door = new Item(super.id,Item.Obstacle.door);

		building.insertObstacle(5, 5, 1, 1, door);
	}
	
	public void addEmergency(String id, int x, int y, Item.Obstacle type){
		Item fire = new Item(id,type);
		
		building.insertObstacle(x, y, 1, 1, fire);
	}
	
	public List<Coordinate> getRoute(Coordinate current,Coordinate destination){
		Coordinate mid = new Coordinate(5,5);
		
		List<Coordinate> myList = new ArrayList<Coordinate>();
		
		if(current.y>5 && destination.y<5 ||current.y<5 && destination.y>5 ){
			myList.add(mid);
		}
			
		return myList;
	}

	@Override
	public void step(SimState arg0) {
		// TODO Auto-generated method stub
		
	}

	
	public int getFloorHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getFloorWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getNumRooms() {
		// TODO Auto-generated method stub
		return 0;
	}
}
