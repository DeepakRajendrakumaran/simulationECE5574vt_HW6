package edu.vt.ece5574.sim;

/**
 * @author Vinit Gala
 *
 */

import io.swagger.client.*;
import io.swagger.client.api.*;
import io.swagger.client.model.*;

import java.math.BigDecimal;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;
import edu.vt.ece5574.main.*;


public class StorageAPI {
	
	private String baseURL;
	ApiClient client;
	
	public StorageAPI()
	{
		baseURL = new String ( "http://localhost:8080/api" );
		client = new ApiClient();
		client.setBasePath(baseURL);
	}
	
/*	
	public HttpResponse getRequest ( URI uri ) throws IOException
	{
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		HttpResponse response = null;
		
		try {
		    HttpGet request = new HttpGet(uri);
		    response = httpClient.execute(request);
		} catch (Exception e) {
			System.err.println(e);
		} finally {
		    httpClient.close();
		}
		return response;
	}
	
	public HttpResponse deleteRequest ( URI uri ) throws IOException
	{
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		HttpResponse response = null;
		
		try {
		    HttpDelete request = new HttpDelete(uri);
		    response = httpClient.execute(request);
		} catch (Exception e) {
			System.err.println(e);
		} finally {
		    httpClient.close();
		}
		return response;
	}
	
	public HttpResponse postRequest ( URI uri , JSONObject json ) throws IOException
	{
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		HttpResponse response = null;
		
		try {
		    HttpPost request = new HttpPost(uri);
		    StringEntity params = new StringEntity(json.toString());
		    request.addHeader("content-type", "application/json");
		    request.setEntity(params);
		    response = httpClient.execute(request);
		} catch (Exception e) {
			System.err.println(e);
		} finally {
		    httpClient.close();
		}
		return response;
	}
	
	public HttpResponse putRequest ( URI uri , JSONObject json ) throws IOException
	{
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		HttpResponse response = null;
		
		try {
		    HttpPut request = new HttpPut(uri);
		    StringEntity params = new StringEntity(json.toString());
		    request.addHeader("content-type", "application/json");
		    request.setEntity(params);
		    response = httpClient.execute(request);
		} catch (Exception e) {
			System.err.println(e);
		} finally {
		    httpClient.close();
		}
		return response;
	}
	*/
		
	public boolean updRobotPos ( String robotId , int xpos , int ypos )
	{
		Robot robot = new Robot();
		robot.setXpos(int2BD(xpos));
		robot.setYpos(int2BD(ypos));
		
		ByIDApi api = new ByIDApi(client);
		try
		{
			api.controllersDefaultControllerRobotsRobotIdPut(robotId, robot);
		}
		catch(ApiException e)
		{
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}
	
	public boolean updUserPos ( String userID , int xpos , int ypos ) 
	{
		User user = new User();
		user.setXpos(int2BD(xpos));
		user.setYpos(int2BD(ypos));
		
		ByIDApi api = new ByIDApi(client);
		try
		{
			api.controllersDefaultControllerUsersUserIdPut(userID, user);
		}
		catch(ApiException e)
		{
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}
	
	// returns the building id
	public String AddBuilding()
	{
		ByTypeApi obj1 = new ByTypeApi(client);
		Building b;
		try
		{
			b = obj1.controllersDefaultControllerBuildingsPost();
		}
		catch(ApiException e)
		{
			return e.getMessage();
		}
		return b.getId();
	}
	
	public List<Building> GetBuildings()
	{
		ByTypeApi api = new ByTypeApi(client);
		List<Building> buildings;
		try
		{
			buildings = api.controllersDefaultControllerBuildingsGet();
		}
		catch(ApiException e)
		{
			// log something, maybe we should just throw the exception
			return new ArrayList<Building>();
		}
		return buildings;
	}
	
	// deletes all buildings
	public String DeleteBuildings()
	{
		ByTypeApi api = new ByTypeApi(client);
		String s;
		try
		{
			s = api.controllersDefaultControllerBuildingsDelete();
		}
		catch(ApiException e)
		{
			return e.getMessage();
		}
		return s;
	}

	private BigDecimal int2BD(int i)
	{
		return new BigDecimal(i + "");
	}

}