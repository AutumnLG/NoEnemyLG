package com.taehee.autumnlgserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

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

	DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("plain;charset=UTF-8");

		StringBuffer jb = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = req.getReader();
			while ((line = reader.readLine()) != null)
				jb.append(line);
		} catch (Exception e) { /* report an error */
		}

		ModelGemList mList = new ObjectMapper().readValue(jb.toString(), ModelGemList.class);
	

		mList.gemItems = new ArrayList<ModelGem>();
		
		for(ModelGem gem : mList.gemItems)
		{
			
			
			Entity e = new Entity("gem",gem.code);
			e.setProperty("cut", gem.cut);
			e.setProperty("gemType", gem.gemType);
			e.setProperty("width", gem.width);
			e.setProperty("height", gem.height);
			e.setProperty("gemPrice", gem.gemPrice);
			e.setProperty("weight", gem.weight);
			e.setProperty("purchasePrice", gem.purchasePrice);
			e.setProperty("changeDate", gem.changeDate);
			
			ds.put(e);
				
		}
		
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		resp.getWriter().println("무13엘지");
	}

}
