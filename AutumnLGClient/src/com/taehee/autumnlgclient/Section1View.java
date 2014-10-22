package com.taehee.autumnlgclient;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Section1View extends SectionView {
	
	private ArrayList<ItemGoods> list = new ArrayList<>();
	private ArrayList<ItemGoods> searchList = new ArrayList<>();
	
	private Adapter adapter;
	
	public Section1View(Activity act) {
		super(act);
	}
	
	@Override
	public int getLayoutId() {
		return R.layout.fragment_section1;
	}
	
	@Override
	public void onActivityCreated() {
		Random rand = new Random();
		for (int i = 0; i < 60; i++) {
			ItemGoods item = new ItemGoods();
			item.code = String.valueOf(rand.nextInt(10000));
			item.name = "반지 " + rand.nextInt(10000);
			list.add(item);
		}
		this.searchList.addAll(list);
		ListView listView = (ListView) view.findViewById(R.id.listView1);
		this.adapter = new Adapter();
		listView.setAdapter(adapter);
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
			((TextView)convertView.findViewById(R.id.item_listview_tv_code)).setText(searchList.get(position).code);
			((TextView)convertView.findViewById(R.id.item_listview_tv_name)).setText(searchList.get(position).name);
			
			convertView.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					act.startActivity(new Intent(act, ActGoodsDetail.class));
				}
			});
			return convertView;
		}
		
	}
}
