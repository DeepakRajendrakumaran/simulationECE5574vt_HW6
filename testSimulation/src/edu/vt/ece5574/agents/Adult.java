package edu.vt.ece5574.agents;

public class Adult extends User{
	
	private static final long serialVersionUID = 1L;

	public Adult(String id, String buildingID) {
		// TODO Auto-generated constructor stub
		//Adults are always assumed to have app
		super(id,buildingID,true);
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		super.move();
	}	
}
