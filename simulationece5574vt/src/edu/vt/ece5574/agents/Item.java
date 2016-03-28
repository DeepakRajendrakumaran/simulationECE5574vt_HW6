package edu.vt.ece5574.agents;

public class Item {
	
	public static enum Obstacle {
	    obstacle, emergency, wall, door 
	}
	
	private String buildingID;
	public Obstacle type;
	
	public Item(String id, Obstacle type){
		buildingID = id;
		this.type = type;
	}

	public String getBuildingId() {
		return buildingID;
	}


}
