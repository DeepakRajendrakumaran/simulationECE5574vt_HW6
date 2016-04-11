package edu.vt.ece5574.agents;

/*
tilemap key :- 
0 - empty position
1 - wall
2 - closed door
3 - open door
*/



public class Item {
	
	public static enum Obstacle {
	    openspace,wall,closeddoor,opendoor, emergency  
	}
	
	private String buildingID;
	public Obstacle type;
	
	public Item(String buildingID, Obstacle type){
		this.buildingID = buildingID;
		this.type = type;
	}

	public String getBuildingId() {
		return buildingID;
	}


}
