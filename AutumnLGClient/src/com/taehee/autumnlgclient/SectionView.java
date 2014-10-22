package com.taehee.autumnlgclient;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class SectionView {
	
	protected Activity act;
	protected View view;
	
	public SectionView(Activity act) {
		this.act = act;
	}
	
	public abstract int getLayoutId();
	
	public abstract void onActivityCreated();
	
	public abstract String getTitle();
	
	public View getView(LayoutInflater inflater, ViewGroup container) {
		this.view = inflater.inflate(getLayoutId(), container, false);
		return view;
	}
	
	public void onSectionAttached() {
		
	}
	
	public void onSearch(String text) {
		
	}
}
