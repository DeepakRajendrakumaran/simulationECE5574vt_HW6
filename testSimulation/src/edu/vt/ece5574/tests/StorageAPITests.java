package edu.vt.ece5574.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import edu.vt.ece5574.sim.StorageAPI;
import io.swagger.client.model.Building;

/**
 * Test cases for StorageAPI class
 * 
 * @author Vinit Gala 
 * */

public class StorageAPITests{
	/*
	@Test
    public void testGet() throws URISyntaxException, IOException
    {
    	StorageAPI var = new StorageAPI();
    	URI uri;
		uri = new URI ( "http://localhost:8080/api/sensors/");
		assertEquals( 200 , var.getRequest(uri).getStatusLine().getStatusCode() );
    }
	
	@Test
    public void testPost() throws JSONException, URISyntaxException, IOException
    {
    	StorageAPI var = new StorageAPI();
    	URI uri;
		uri = new URI ( "http://localhost:8080/api/sensors/");
		JSONObject json = new JSONObject();
		json.put("robot", "robot1");
		json.put("from", "string");
		json.put("buildingID", "building1");
		json.put("room", 1);
		json.put("ypos", 30);
		json.put("xpos", 20);
		json.put("id", "sensor1");
		json.put("type", "fire");
		json.put("floor", 0 );
		assertEquals( 200 , var.postRequest(uri,json).getStatusLine().getStatusCode() );
    }
    
	@Test
    public void testPut() throws JSONException, URISyntaxException, IOException
    {
    	StorageAPI var = new StorageAPI();
    	URI uri;
		uri = new URI ( "http://localhost:8080/api/sensors/sensor1");
		JSONObject json = new JSONObject();
		json.put("ypos", 99);
		json.put("xpos", 99);
		assertEquals( 200 , var.putRequest(uri,json).getStatusLine().getStatusCode() );
    }
    
    @Test
    public void testDelete() throws URISyntaxException, IOException
    {
    	StorageAPI var = new StorageAPI();
    	URI uri;
		uri = new URI ( "http://localhost:8080/api/sensors/sensor1");
		assertEquals( 200 , var.deleteRequest(uri).getStatusLine().getStatusCode() );
    }
	*/
	
	// This test will remove all the buildings from whatever aws storage is being used
	@Test
	public void addBuildingsByTypeGetByTypeDeleteById()
	{
		StorageAPI var = new StorageAPI();

		var.DeleteBuildings();
		
		String building_id_1 = var.AddBuilding();
		String building_id_2 = var.AddBuilding();
		String building_id_3 = var.AddBuilding();
		
		List<Building> buildings = var.GetBuildings();
		for(int i = 0; i < buildings.size(); i++)
		{
			assertTrue("Assert that the buildings stored correspond to the IDs sent",
					Objects.equals(buildings.get(i).getId(), building_id_1) || 
					Objects.equals(buildings.get(i).getId(), building_id_2) || 
					Objects.equals(buildings.get(i).getId(), building_id_3));
		}
		
		String s = var.DeleteBuildings();
		System.out.println(s + " in StorageAPITests.java method addBuildingsByTypeGetByTypeDeleteById");
		
		buildings = var.GetBuildings();
		assertTrue("Asserting that get buildings returns an empty list when it is empty", buildings.isEmpty());
		
	}
	
    @Test
    public void updRobotPos()
    {
    	StorageAPI var = new StorageAPI();
    	assertEquals( var.updRobotPos( "robot1", 10 , 20 ), true ) ;
    }
    
    @Test
    public void updUserPos()
    {    	
    	StorageAPI var = new StorageAPI();
    	assertEquals( var.updUserPos( "user1", 10 , 20 ) ,  true ) ;
    }
}
