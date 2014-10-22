package com.taehee.autumnlgclient.net;

public interface onRequestListener {

	public void onRequestStart(BrowserBase data);

	public void onRequestSuccess(BrowserBase data);

	public void onRequestFail(BrowserBase data);

	public void onRequestFinish(BrowserBase data);

	public void onRequestCancel(BrowserBase data);

	public void onRequestProgress(BrowserBase data, int bytesWritten, int totalSize);

}
