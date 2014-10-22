package com.taehee.autumnlgclient.view;

import com.taehee.autumnlgclient.R;
import com.taehee.autumnlgclient.R.layout;
import com.taehee.autumnlgclient.R.string;

import android.app.Activity;

public class ViewIntroduce extends ViewBase {

	public ViewIntroduce(Activity act) {
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
