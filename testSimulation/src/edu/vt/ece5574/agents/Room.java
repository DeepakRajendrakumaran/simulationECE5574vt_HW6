package edu.vt.ece5574.agents;

import edu.vt.ece5574.sim.Matrix;

public class Room {
	private String buildingID;
	
	private Matrix matrix;
	
	public int X=0;
	public int Y=0;
	
	private int width;
	private int height;
	
	final int minWidth = 3;
    final int maxWidth = 100;
    final int minHeight = 3;
    final int maxHeight = 100;
    
	public Room(String buildingID, int width, int height){
		this.buildingID = buildingID;
		
		try {
            if (  width < minWidth  ||  width > maxWidth  ) {
                width = minWidth;
            }

            if (  height < minHeight  ||  height > maxHeight  ) {
                height = minHeight;
            }
        }
        catch ( Exception error ) {
          
        }
	
	 this.matrix = new Matrix(width,height);
	}
	
	public void insertObstacle(int x, int y, int width, int height, Item item){
    	int code = 0;
    	
    	  switch (item.type) {
          case obstacle:  code = 5;
                   break;
          case emergency:  code = 6;
                   break;
          case wall: code = 1;
          		   break;
          case door: code = -1; 
          		   break;
          default: code = 0;
                   break;
      }
    	
    	 for (int i = x;i < x + width;i++){
    		 for (int j=y; j < y + height; j++){
    			 this.matrix.data[i][j] = code;
    		 }
    	 }
    	  
    }
	
	public boolean checkStep(int x, int y){
		
		if (  x < this.width  ||  width > this.height  ) {
            return false;
        }
		else if (  y < this.height  ||  height > this.height  ) {
            return false;
        }
		else if(matrix.data[x][y] > 0){
			return false;
		}
		else 
		return true; 
	}

	public String getBuildingId() {
		return buildingID;
	}

	
	public void showRoom(){
		this.matrix.show();
	}
	
}
