package com.taehee.autumnlgserver;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.taehee.autumnlgserver.model.ModelGoods;
import com.taehee.autumnlgserver.model.ModelGoodsList;

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
		
		
		ModelGoodsList mList = new ObjectMapper().readValue(jb.toString(), ModelGoodsList.class);

//		mList.modelGoods = new ArrayList<ModelGoods>();
		
		for(ModelGoods goods : mList.modelGoods)
		{
			
			
			Entity e = new Entity("ModelGoods",goods.code);
			e.setProperty("예상 공임", goods.price);
			e.setProperty("기본 공임", goods.basicPrice);
			e.setProperty("중량", goods.weight);
			e.setProperty("큐통", goods.cubic);
			e.setProperty("2", goods.etc);
			
//			e.setProperty("price", goods.price);
//			e.setProperty("basicPrice", goods.basicPrice);
//			e.setProperty("weight", goods.weight);
//			e.setProperty("cubic", goods.cubic);
////			e.setProperty("etc", goods.etc);
//			
			ds.put(e);
		}
		
		resp.setStatus(HttpServletResponse.SC_OK);
		
		ModelBase mReturn = new ModelBase();
		mReturn.status = ""+mList.modelGoods.size();
		
		ObjectMapper om = new ObjectMapper();
		om.writeValue(resp.getWriter(), mReturn);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		resp.getWriter().println("무15엘지");
	}

}
