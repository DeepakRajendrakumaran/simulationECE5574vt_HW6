package edu.vt.ece5574.sim;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import edu.vt.ece5574.agents.Building;
import edu.vt.ece5574.agents.Robot;
import edu.vt.ece5574.events.Event;
import edu.vt.ece5574.events.FireEvent;
import edu.vt.ece5574.events.IntruderEvent;
import edu.vt.ece5574.events.WaterLeakEvent;
import edu.vt.ece5574.sim.Simulation;
import sim.engine.SimState;
import sim.engine.Steppable;

public class APICaller implements Steppable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1;
	int userID;
	int messageID;

	// Call both get and delete if messageID is known
	public Event callPushSystemAPI(String userID, String messageID)
			throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String url = "https://55izr0k3b7.execute-api.us-east-1.amazonaws.com/test/"
				+ userID + "/messages/" + messageID;
		// String url="http://team7restapi.appspot.com/api/robots/1";
		try {

			HttpGet httpget = new HttpGet(url);
			HttpDelete httpdelete = new HttpDelete(url);
			// System.out.println("Executing request " +
			// httpget.getRequestLine());

			// Create a custom response handler
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

				public String handleResponse(final HttpResponse response)
						throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity)
								: null;
					} else {
						throw new ClientProtocolException(
								"Unexpected response status: " + status);
					}
				}

			};
			String responseBody = httpclient.execute(httpget, responseHandler);

			// System.out.println(myObject);
			// System.out.println("Executing request " +
			// httpdelete.getRequestLine());
			httpclient.execute(httpdelete, responseHandler);

			// because response is not coming in correct format as of now
			responseBody = "{\"messageId\" : \"1aB32-SE324-22Oa\",\"body\" : { \"msg_type\": Fire, \"body\": {\"field\" : \"<stuff here>\"}}}";

			// Strip response to get only required data
			String responseBodyStripped = responseBody.substring(
					StringUtils.ordinalIndexOf(responseBody, "{", 2),
					StringUtils.ordinalIndexOf(responseBody, "}", 1) + 1);

			// CREATE EVENT
			String msg_type = Event.getEventType(responseBodyStripped);

			if (msg_type == "Fire") {
				FireEvent fire = new FireEvent();
				if (!fire.init(responseBodyStripped))
					System.out.println("Improper JSON format");
				else
					return fire;
			} else if (msg_type == "Intruder") {
				IntruderEvent intruder = new IntruderEvent();
				if (!intruder.init(responseBodyStripped))
					System.out.println("Improper JSON format");
				else
					return intruder;
			} else if (msg_type == "WaterLeak") {
				WaterLeakEvent waterleak = new WaterLeakEvent();
				if (!waterleak.init(responseBodyStripped))
					System.out.println("Improper JSON format");
				else
					return waterleak;
			}

			else {
				return null;
			}

		} finally {
			httpclient.close();
		}
		return null;
	}

	@Override
	public void step(SimState state) {
		// Check for events
		Simulation simState = (Simulation) state;

		Building bld = new Building("0");
		Robot rob = new Robot("1", "0");

		simState.addAgent(bld);
		simState.addAgent(rob);

		try {
			Event event = callPushSystemAPI("1", "1");
			rob.addEvent(event);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
