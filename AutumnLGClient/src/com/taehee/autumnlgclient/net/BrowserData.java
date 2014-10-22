package com.taehee.autumnlgclient.net;

import org.apache.http.Header;

import android.content.Context;

public class BrowserData extends BrowserBase {

	public BrowserData(Context mContext, String fullPath, onRequestListener listener, boolean isHttpPost, boolean useAccessToken) {
		super(mContext, fullPath, listener, isHttpPost, useAccessToken);
	}

	@Override
	public Header[] getHeader() {
		return null;
	}

}
