package com.taehee.autumnlgserver;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.appengine.api.datastore.Query;



@SuppressWarnings("serial")
public class AutumnLGServerServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

//		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
//
//		Entity e = new Entity("product", 10002);
//		e.setProperty("name", "red ring");
//		e.setProperty("price", 100);
//		ds.put(e);
		String name = req.getParameter("name");
//		JSONObject obj = new JSONObject();
//		try {
//			obj.put("name", name);
//			obj.put("status", "0");
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
		Query q = new Query("gem");
		
		ModelTemp modelTemp = new ModelTemp();
		modelTemp.name = name;
		modelTemp.status = "0";
		ObjectMapper om = new ObjectMapper();
		om.writeValue(resp.getWriter(), modelTemp);
//		System.out.println("get name : " + name);
//		resp.setContentType("text/plain");
//		resp.getWriter().print(obj);
//		resp.getWriter().flush();
//		resp.getWriter().close();
	}
}
