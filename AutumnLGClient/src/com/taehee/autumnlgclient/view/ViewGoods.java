package com.taehee.autumnlgclient.view;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taehee.autumnlgclient.ActGoodsDetail;
import com.taehee.autumnlgclient.R;
import com.taehee.autumnlgclient.model.ModelGoods;
import com.taehee.autumnlgclient.model.ModelGoodsList;
import com.taehee.autumnlgclient.net.BrowserBase;
import com.taehee.autumnlgclient.net.BrowserData;
import com.taehee.autumnlgclient.net.RequestManager;
import com.taehee.autumnlgclient.net.onRequestListener;

public class ViewGoods extends ViewBase implements onRequestListener {

	private ArrayList<ModelGoods> list = new ArrayList<ModelGoods>();
	private ArrayList<ModelGoods> searchList = new ArrayList<ModelGoods>();

	private Adapter adapter;

	public ViewGoods(Activity act) {
		super(act);
	}

	@Override
	public int getLayoutId() {
		return R.layout.fragment_section1;
	}

	@Override
	public void onActivityCreated() {
		
		ListView listView = (ListView) view.findViewById(R.id.listView1);
		this.adapter = new Adapter();
		listView.setAdapter(adapter);
		
		startRequestGoods();

	}

	@Override
	public String getTitle() {
		return act.getString(R.string.title_section1);
	}

	@Override
	public void onSearch(String text) {
		super.onSearch(text);
		this.searchList.clear();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).code.contains(text)) {
				this.searchList.add(list.get(i));
			}
		}
		this.adapter.notifyDataSetChanged();
	}
	
	private void startRequestGoods() {
		BrowserData data = new BrowserData(act, "autumnlgserver", this, false, false);
		RequestManager.Instance(act).startRequest(act, data);
	}

	private class Adapter extends BaseAdapter {

		@Override
		public int getCount() {
			return searchList.size();
		}

		@Override
		public Object getItem(int position) {
			return searchList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView = act.getLayoutInflater().inflate(R.layout.item_listview, null);
			((TextView) convertView.findViewById(R.id.item_listview_tv_code)).setText(searchList.get(position).code);
			((TextView) convertView.findViewById(R.id.item_listview_tv_name)).setText(searchList.get(position).name);
			((TextView) convertView.findViewById(R.id.item_listview_tv_cost)).setText(searchList.get(position).price);

			convertView.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					act.startActivity(new Intent(act, ActGoodsDetail.class));
				}
			});
			return convertView;
		}

	}

	@Override
	public void onRequestStart(BrowserBase data) {
		Log.i("taehee", "onRequestStart");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onRequestSuccess(BrowserBase data) {
		Log.i("taehee", "onRequestSuccess");
		if (data != null) {
			try {
				ModelGoodsList modelGoodsList = new ObjectMapper().readValue(data.getJsonObject().toString(), ModelGoodsList.class);
				if (modelGoodsList != null) {
					this.list = (ArrayList<ModelGoods>) modelGoodsList.modelGoods.clone();
					this.searchList = (ArrayList<ModelGoods>) modelGoodsList.modelGoods.clone();
					this.adapter.notifyDataSetChanged();
				}
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onRequestFail(BrowserBase data) {
		Log.i("taehee", "onRequestFail");
	}

	@Override
	public void onRequestFinish(BrowserBase data) {
		Log.i("taehee", "onRequestFinish");
	}

	@Override
	public void onRequestCancel(BrowserBase data) {
		Log.i("taehee", "onRequestCancel");
	}

	@Override
	public void onRequestProgress(BrowserBase data, int bytesWritten, int totalSize) {
		Log.i("taehee", "onRequestProgress");
	}
}
