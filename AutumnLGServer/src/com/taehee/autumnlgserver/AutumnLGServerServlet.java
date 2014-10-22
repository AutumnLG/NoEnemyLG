package com.taehee.autumnlgserver;

import java.io.IOException;

import javax.servlet.http.*;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

@SuppressWarnings("serial")
public class AutumnLGServerServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

		Entity e = new Entity("product", 10001);
		e.setProperty("name", "silver ring");
		e.setProperty("price", 1000);
		ds.put(e);

		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
	}
}
