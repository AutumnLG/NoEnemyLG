package com.taehee.autumnlgclient.view;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.barusoft.android.http.RequestParams;
import com.taehee.autumnlgclient.R;
import com.taehee.autumnlgclient.net.BrowserBase;
import com.taehee.autumnlgclient.net.BrowserData;
import com.taehee.autumnlgclient.net.RequestManager;
import com.taehee.autumnlgclient.net.onRequestListener;

public class ViewIntroduce extends ViewBase implements OnClickListener, onRequestListener {

	public ViewIntroduce(Activity act) {
		super(act);
	}

	@Override
	public int getLayoutId() {
		return R.layout.fragment_section2;
	}

	@Override
	public void onActivityCreated() {
		act.findViewById(R.id.button1).setOnClickListener(this);
	}

	@Override
	public String getTitle() {
		return act.getString(R.string.title_section2);
	}

	@Override
	public void onClick(View v) {
		BrowserData data = new BrowserData(act, "autumnlgserver", this, false, false);
		RequestParams param = new RequestParams();
		param.put("name", "ryutaehee");
		data.setRequestParams(param);
		RequestManager.Instance(act).startRequest(act, data);
		
	}

	@Override
	public void onRequestStart(BrowserBase data) {
		Log.i("taehee", "onRequestStart");
	}

	@Override
	public void onRequestSuccess(BrowserBase data) {
		Log.i("taehee", "onRequestSuccess");
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
