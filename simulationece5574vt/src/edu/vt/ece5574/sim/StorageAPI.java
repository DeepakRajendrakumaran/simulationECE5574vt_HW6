package edu.vt.ece5574.sim;

/**
 
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
	
	public HttpResponse getRequest ( URI uri ) throws IOException
	{
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		HttpResponse response = null;
		
		try {
		    HttpGet request = new HttpGet(uri);
		    response = httpClient.execute(request);
		} catch (Exception e) {
			e.printStackTrace();
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
			e.printStackTrace();
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
			e.printStackTrace();
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
			e.printStackTrace();
		} finally {
		    httpClient.close();
		}
		return response;
	}
}
