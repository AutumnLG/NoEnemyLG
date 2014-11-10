package com.taehee.autumnlgserver;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.QueryResultList;
import com.taehee.autumnlgserver.model.ModelGoods;
import com.taehee.autumnlgserver.model.ModelGoodsList;

@SuppressWarnings("serial")
public class AutumnLGServerServlet extends HttpServlet {

	DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		req.setCharacterEncoding("UTF-8");
		resp.setContentType("plain;charset=UTF-8");
		
		Query q = new Query("ModelGoods");
		PreparedQuery pq = ds.prepare(q);
		
		ModelGoodsList mList = new ModelGoodsList();
		mList.modelGoods = new ArrayList<ModelGoods>();

		QueryResultList<Entity> results = pq.asQueryResultList(FetchOptions.Builder.withDefaults());
		for (Entity entity : results) {
			ModelGoods good = new ModelGoods();
			
			good.code = entity.getKey().getName();
			good.price = entity.getProperty("예상 공임").toString();
			good.basicPrice = entity.getProperty("기본 공임").toString();
			good.weight = entity.getProperty("중량").toString();
			good.cubic = entity.getProperty("큐통").toString();
			good.etc = entity.getProperty("2").toString();
			
			mList.modelGoods.add(good);
		}
		
		mList.status = "1";

		ObjectMapper om = new ObjectMapper();
		om.writeValue(resp.getWriter(), mList);
	}
}
