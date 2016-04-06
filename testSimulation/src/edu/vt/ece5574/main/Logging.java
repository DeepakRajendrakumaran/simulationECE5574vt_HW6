package edu.vt.ece5574.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Logging {

	private static final long serialVersionUID = 1;
	static Integer cx = 0;
	static Integer cy = 0;
	static String cAgent = "null";
	static String cId = "null";
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    List<String> list = new ArrayList<String>();
	    list.add(cAgent);
	    list.add(cId);
	    list.add(Integer.toString(cx));
	    list.add(Integer.toString(cy));
	    String json = new Gson().toJson(list);

	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(json);
	}
	
	public static void updateEvent(String Agent, String Id, int x, int y){
		cAgent = Agent;
		cId = Id;
		cx = x;
		cy = y;
	}
	
}
