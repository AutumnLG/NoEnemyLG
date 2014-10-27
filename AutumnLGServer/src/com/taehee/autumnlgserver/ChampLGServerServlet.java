package com.taehee.autumnlgserver;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

@SuppressWarnings("serial")
public class ChampLGServerServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("plain;charset=UTF-8");
		
		String name = req.getParameter("name");
		String price = req.getParameter("price");
		
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Entity e = new Entity("product");
		e.setProperty("name", name);
		e.setProperty("price",price);
		ds.put(e);
		
		
		
		ModelTemp modelTemp = new ModelTemp();
		modelTemp.name = name;
		modelTemp.status = "0";
		ObjectMapper om = new ObjectMapper();
		om.writeValue(resp.getWriter(), modelTemp);

		resp.getWriter().println("무적엘지 : " + modelTemp.name);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		resp.setContentType("text/plain; charset=UTF-8");
//		String name = req.getParameter("name");
//		resp.getWriter().println("무적엘지 : " + name);
//		resp.getWriter().println("asdadsadadsadsdas");
//		
//		
//		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
//		Entity e = new Entity("product", 500);
//		e.setProperty("name", "onion ring 333333");
//		e.setProperty("price", 1500000);
//		ds.put(e);
	}

}
