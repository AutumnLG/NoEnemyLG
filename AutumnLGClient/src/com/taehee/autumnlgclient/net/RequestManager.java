package com.taehee.autumnlgclient.net;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import android.content.Context;
import android.util.Log;

import com.barusoft.android.http.AsyncHttpClient;
import com.barusoft.android.http.RequestHandle;

public class RequestManager {

	private static RequestManager instance = null;
	private AsyncHttpClient client;
	private Context applicationContext;
	private final Map<Object, List<RequestHandle>> requestMap;

	private RequestManager(Context applicationContext) {
		this.applicationContext = applicationContext;
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), NetworkConfig.PORT_HTTP));
		schemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), NetworkConfig.PORT_HTTPS));
		client = new AsyncHttpClient(schemeRegistry);
		client.setTimeout(NetworkConfig.TIMEOUT);
		final HttpParams httpParams = client.getHttpClient().getParams();
		ConnManagerParams.setMaxTotalConnections(httpParams, 200);
		ConnManagerParams.setTimeout(httpParams, NetworkConfig.TIMEOUTSO);
		HttpConnectionParams.setStaleCheckingEnabled(httpParams, false);
		HttpConnectionParams.setSoTimeout(httpParams, NetworkConfig.TIMEOUTSO);
		HttpClientParams.setRedirecting(httpParams, false);

		ExecutorService threadPool;
		threadPool = Executors.newFixedThreadPool(255);
		client.setThreadPool((ThreadPoolExecutor) threadPool);

		requestMap = new WeakHashMap<Object, List<RequestHandle>>();
	}

	public synchronized static RequestManager Instance(Context context) {
		if (instance == null) {
			instance = new RequestManager(context);
		}
		return instance;
	}

	public synchronized RequestHandle startRequest(Context context, BrowserBase data) {
		if (/* NetworkCheck.isOnline(applicationContext) */true) {
			// Network state check 는 activity 내부에서 처리 하는 방향 고려

			if (data.isHttpPost) {
				Log.i("taehee", "startRequest post");
				return client.post(context, getFullPath(data), data.getHeader(), data.params, null, data.getJsonHandler());
			} else {
				Log.i("taehee", "startRequest get");
				return client.get(context, getFullPath(data), data.getHeader(), data.params, data.getJsonHandler());
			}
		} else {
			data.setStatusCode(NetworkStatus.NO_RESPONSE); // Network Exception
															// status;
			data.getRequestListener().onRequestFail(data);
			return null;
		}
	}

	public synchronized RequestHandle startRequestEntity(Context context, BrowserBase data, String jsonString) {
		StringEntity entity = null;
		try {
			entity = new StringEntity(jsonString, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (entity != null) {
			entity.setContentType("application/json");
			entity.setContentEncoding(HTTP.UTF_8);
			return client.post(context, getFullPath(data), entity, "application/json", data.getJsonHandler());
		} else {
			data.setStatusCode(NetworkStatus.NO_RESPONSE); 
			data.getRequestListener().onRequestFail(data);
			return null;
		}
	}

	/**
	 * Page 등에서 사용 할 리퀘스트 함수 context 대신 사용자 지정 key 를 전달 받는다.
	 * 
	 * @param key
	 *            page unique key
	 * @param data
	 *            browser data
	 */
	synchronized public void startRequest(Object key, BrowserBase data) {
		RequestHandle handle;

		if (/* NetworkCheck.isOnline(applicationContext) */true) {
			// Network state check 는 activity 내부에서 처리 하는 방향 고려
			// Logger.i(data.toString());

			if (data.isHttpPost) {
				handle = client.post(null, getFullPath(data), data.getHeader(), data.params, null, data.getJsonHandler());
			} else {
				handle = client.get(null, getFullPath(data), data.getHeader(), data.params, data.getJsonHandler());
			}
		} else {
			data.setStatusCode(NetworkStatus.NO_RESPONSE); // Network Exception
															// status;
			data.getRequestListener().onRequestFail(data);
		}

		List<RequestHandle> requestList = requestMap.get(key);
		if (requestList == null) {
			requestList = new LinkedList<RequestHandle>();
			requestMap.put(key, requestList);
		}
		requestList.add(handle);

		Iterator<RequestHandle> iterator = requestList.iterator();
		while (iterator.hasNext()) {
			if (iterator.next().shouldBeGarbageCollected()) {
				iterator.remove();
			}
		}
	}

	synchronized public RequestHandle startFileRequest(Context context, BrowserBase data) {
		if (data.isHttpPost) {
			return client.post(context, getFullPath(data), data.getHeader(), data.params, null, data.getFileHandler());
		} else {
			return client.get(context, getFullPath(data), data.getHeader(), data.params, data.getFileHandler());
		}
	}

	synchronized public void startFileRequest(Object key, BrowserBase data) {
		RequestHandle handle;

		if (data.isHttpPost) {
			handle = client.post(null, getFullPath(data), data.getHeader(), data.params, null, data.getFileHandler());
		} else {
			handle = client.get(null, getFullPath(data), data.getHeader(), data.params, data.getFileHandler());
		}

		List<RequestHandle> requestList = requestMap.get(key);
		if (requestList == null) {
			requestList = new LinkedList<RequestHandle>();
			requestMap.put(key, requestList);
		}
		requestList.add(handle);

		Iterator<RequestHandle> iterator = requestList.iterator();
		while (iterator.hasNext()) {
			if (iterator.next().shouldBeGarbageCollected()) {
				iterator.remove();
			}
		}
	}

	synchronized public void cancelRequest(Context context) {
		client.cancelRequests(context, true);
	}

	/**
	 * page 를 위한 cancelRequest page 의 고유 key 를 넘겨준다.
	 * 
	 * @param key
	 */
	synchronized public void cancelRequest(Object key) {
		List<RequestHandle> requestList = requestMap.get(key);
		if (requestList != null) {
			for (RequestHandle requestHandle : requestList) {
				requestHandle.cancel(true);
			}
			requestMap.remove(key);
		}
	}

	public String getFullPath(BrowserBase data) {
		Log.i("getFullPath", NetworkConfig.URL + "/" + data.requestPath);
		return NetworkConfig.URL + "/" + data.requestPath;
	}

}
