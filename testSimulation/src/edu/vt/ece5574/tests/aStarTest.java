package edu.vt.ece5574.tests;


import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import edu.vt.ece5574.sim.AStar;
import java.util.*;
import sim.field.grid.*;
import sim.util.Int2D;


//public static void main(String[] args) throws Exception
//{       	
//	int[][] something  = {{0,0,1,0,1},
//	    				  {0,0,0,0,0},
//				    	  {1,0,0,1,0},
//				    	  {0,0,0,0,0},
//				    	  {1,1,0,0,0}};
//	IntGrid2D testGrid = new IntGrid2D(something);
//	Stack<Int2D> testPath = findPath(0, 0, 4, 3, testGrid); 
//	
//	System.out.println("stack: " + testPath);
//	Int2D a = testPath.pop();
//	System.out.println("First: " + a);
//}

public class aStarTest {

	int[][] something  = {{0,0,1,0,1},
						  {0,0,0,0,0},
						  {1,0,0,1,0},
						  {0,0,0,0,0},
						  {1,1,0,0,0}};
	IntGrid2D testGrid;
	Int2D a;
	Stack<Int2D> test;
	
	@Before
	public void init(){
	}
	
	@Test
	public void testResult(){
		IntGrid2D testGrid = new IntGrid2D(something);
		Stack<Int2D> test = AStar.findPath(0, 0, 4, 3, testGrid);
		a = test.pop();
		assertEquals(a.x, 1);
		assertEquals(a.y, 1);
	}
	
}
