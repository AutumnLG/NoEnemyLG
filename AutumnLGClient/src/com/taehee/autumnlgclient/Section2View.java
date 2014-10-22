package com.taehee.autumnlgclient;

import android.app.Activity;

public class Section2View extends SectionView {

	public Section2View(Activity act) {
		super(act);
	}

	@Override
	public int getLayoutId() {
		return R.layout.fragment_section2;
	}

	@Override
	public void onActivityCreated() {

	}

	@Override
	public String getTitle() {
		return act.getString(R.string.title_section2);
	}
}
