package edu.vt.ece5574.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import edu.vt.ece5574.events.Event;
import edu.vt.ece5574.sim.PushAPICaller;



public class PushAPITest {
	
String xapikey;
@Rule
public ExpectedException exception = ExpectedException.none();

@Before
public void init(){
String xapikey="F2yxLdt3dNfvsncGwl0g8eCik3OxNej3LO9M2iHj";
}

//Check that the function returns no events for incorrect user id
@Test
public void callIncorrectUserID(){
	String userID="DummyUser";
	ArrayList<Event> myEvents=PushAPICaller.callPushSystemAPI(userID);
	assertEquals(0,myEvents.size());
}

//check if the message is being parsed correctly to return the message type

@Test
public void callgetMessageType(){
	String body=" {\"messageId\": \"ac7ba131-efcf-11e5-b194-0bcd942e918c\",\"message\": {\"msg_type\": \"Fire\",\"body\": {\"floor\": 5,\"room\": 7}}}";
	String msg_type="";
	try {
		JSONObject fullbody=new JSONObject(body);
		msg_type=PushAPICaller.getMessageType(fullbody);
		assertEquals("fire",msg_type);
		
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		System.out.println(e.getMessage());
	}
}


//Check if correct number of events and event types are returned
@Test
public void validateEventType(){
ArrayList<Event> myEvents=PushAPICaller.callPushSystemAPI("JohnDoe7");
assertEquals(2,myEvents.size());
assertEquals("edu.vt.ece5574.events.FireEvent",myEvents.get(0).getClass().getName().toString());

}




}
