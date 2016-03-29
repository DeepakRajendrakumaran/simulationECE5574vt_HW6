package edu.vt.ece5574.sim;

/**
 * @author Vinit Gala
 *
 */

import java.io.IOException;
import java.net.URI;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;


public class StorageAPI {
	
	private String baseURL;
	
	public StorageAPI()
	{
		baseURL = new String ( "http://localhost:8080/api/" );
	}
	
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
	
	public boolean updRobotPos ( String robotID , int xpos , int ypos ) 
	{
		try 
		{
			JSONObject json = new JSONObject();
			URI uri = new URI ( baseURL + "robots/" + robotID );
			HttpResponse response;
		
			json.put( "xpos", xpos );
			json.put( "ypos", ypos );
		
			response = putRequest ( uri , json );
		
			if ( response == null || response.getStatusLine().getStatusCode() != 200 )
				return false;
		}
		catch ( Exception e )
		{
			System.err.println(e);
			return false;
		}
		
		return true;
	}
	
	public boolean updUserPos ( String userID , int xpos , int ypos ) 
	{
		try 
		{
			JSONObject json = new JSONObject();
			URI uri = new URI ( baseURL + "users/" + userID );
			HttpResponse response;
		
			json.put( "xpos", xpos );
			json.put( "ypos", ypos );
		
			response = putRequest ( uri , json );
		
			if ( response == null || response.getStatusLine().getStatusCode() != 200 )
				return false;
		}
		catch ( Exception e )
		{
			System.err.println(e);
			return false;
		}
		return true;
	}
}