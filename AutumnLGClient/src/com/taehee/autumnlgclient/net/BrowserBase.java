package com.taehee.autumnlgclient.net;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.message.BasicHeader;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.barusoft.android.http.FileAsyncHttpResponseHandler;
import com.barusoft.android.http.JsonHttpResponseHandler;
import com.barusoft.android.http.RequestParams;

public abstract class BrowserBase {

	public enum BROWSER_MODE {
		NONE, JSON, FILE,
	}

	protected Context mContext;
	protected int statusCode = -1;
	protected String statusString = null;
	protected String requestPath = null;
	protected boolean isUseHeader = true;
	protected boolean isAccessToken = true;
	protected String tempAccessToken = null;
	protected File file;
	protected JSONObject jsonObject;
	protected onRequestListener listener;
	protected Object requestObj;
	protected boolean isHttpPost = true;
	protected boolean isUseCookie = true;
	protected RequestParams params;

	protected BROWSER_MODE mode;

	/**
	 * context 대신 관리 할 requestKey pager 전용
	 */
	protected Object requestKey = null;

	private static final boolean isShowLog = true;
	private static final String TAG = "BrowserData";

	/**
	 * request path 로 생성
	 * 
	 * @param mContext
	 * @param requestString
	 *            : file path
	 * @param listener
	 * @param isHttpPost
	 * @param useAccessToken
	 */
	public BrowserBase(Context mContext, String requestPath, onRequestListener listener, boolean isHttpPost, boolean useAccessToken) {
		super();
		this.mContext = mContext;
		this.requestPath = requestPath;
		this.listener = listener;
		this.isHttpPost = isHttpPost;
		this.isAccessToken = useAccessToken;
		this.mode = BROWSER_MODE.FILE;
	}

	public String getRequestPath() {
		return this.requestPath;
	}

	public void setRequestKey(Object obj) {
		this.requestKey = obj;
	}

	public Object getRequestKey() {
		return this.requestKey;
	}

	public void setStatusCode(int code) {
		this.statusCode = code;
	}

	public int getStatusCode() {
		return this.statusCode;
	}

	public void setStatusString(String statusString) {
		this.statusString = statusString;
	}

	public String getStatusString() {
		return this.statusString;
	}

	public void setIsAccessToken(boolean isAccessToken) {
		this.isAccessToken = isAccessToken;
	}

	public boolean IsAccessToken() {
		return isAccessToken;
	}

	public void setTempAccessToken(String tempAccessToken) {
		this.tempAccessToken = tempAccessToken;
	}

	public void removeTempAcessToken() {
		this.tempAccessToken = null;
	}

	/**
	 * Http Post or Get <br>
	 * true : post <br>
	 * false : get
	 */
	protected void setHttpPost(boolean isHttpHost) {
		this.isHttpPost = isHttpHost;
	}

	/**
	 * use CookieStore (Default true) EagleApplication
	 */
	public void setUseCookie(boolean use) {
		this.isUseCookie = use;
	}

	public void setRequestParams(RequestParams params) {
		this.params = params;
	}

	public void setJsonObject(JSONObject object) {
		this.jsonObject = object;
	}

	public JSONObject getJsonObject() {
		return this.jsonObject;
	}

	/**
	 * 현재 모드를 리턴(Json or File)
	 * 
	 * @return
	 */
	public BROWSER_MODE getRequestMode() {
		return this.mode;
	}

//	public Header[] getHeader() {
//		List<Header> headers = new ArrayList<Header>();
//		headers.add(new BasicHeader("Accept", "*/*"));
//		headers.add(new BasicHeader("Connection", "keep-alive"));
//		headers.add(new BasicHeader("Content-Type", "application/xml"));
//		headers.add(new BasicHeader("app-agent", "platformCode=11;platformVer=4.2.1;deviceId=device#1;appName=wizzap;appVer=0.0.1;deviceModel=jmeter;networkType=WiFi;displayType=320|480"));
//		headers.add(new BasicHeader("Akey", "gY24x9xhDi1N7Spq+P+AY2cB2Nc="));
//		if (isAccessToken) {
//			headers.add(new BasicHeader("access-token", "bc240446#fbeb#400f#8e94#2cb00247af70"));
//		} else {
//
//		}
//		return headers.toArray(new Header[headers.size()]);
//	}

	 abstract public Header[] getHeader();

	// ///////////////////////////////////////////////////////////////////////////////////////

	public void setReqObject(Object obj) {
		this.requestObj = obj;
	}

	public Object getReqObject() {
		return this.requestObj;
	}

	public void setOnRequestListener(onRequestListener listener) {
		this.listener = listener;
	}

	public onRequestListener getRequestListener() {
		return this.listener;
	}

	/**
	 * 다운로드 받은 파일을 저장 하기 위해 빈 파일을 생성하여 전달
	 * 
	 * @param file
	 *            경로 지정한 파일
	 */
	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * Download 받은 파일
	 * 
	 * @return
	 */
	public File getFile() {
		return this.file;
	}

	public JsonHttpResponseHandler getJsonHandler() {
		return new JsonHttpResponseHandler() {
			@Override
			public void onStart() {
				super.onStart();
				if (listener != null) {
					listener.onRequestStart(BrowserBase.this);
				}
			}

			@Override
			public void onFinish() {
				super.onFinish();
				if (listener != null) {
					listener.onRequestFinish(BrowserBase.this);
				}
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);

				if (isShowLog) {
					Log.i(TAG, "full response : " + response.toString());
				}

				BrowserBase.this.statusCode = response.optInt("status");

				if (BrowserBase.this.statusCode != NetworkStatus.SUCCESS && listener != null) {
					listener.onRequestFail(BrowserBase.this);
				} else {
					jsonObject = response;
					if (listener != null) {
						listener.onRequestSuccess(BrowserBase.this);
					}
				}
			}

			@Override
			public void onProgress(int bytesWritten, int totalSize) {
				super.onProgress(bytesWritten, totalSize);
				if (listener != null) {
					listener.onRequestProgress(BrowserBase.this, bytesWritten, totalSize);
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
				super.onFailure(statusCode, headers, throwable, errorResponse);
				if (throwable != null) {
					if (throwable instanceof ConnectTimeoutException) {
						BrowserBase.this.statusCode = NetworkStatus.TIMEOUT; // Exception
																				// code
																				// 정의
					} else {
						if (BrowserBase.this.statusCode == -1) {
							BrowserBase.this.statusCode = NetworkStatus.NO_RESPONSE;
						}
					}
				}
				if (listener != null) {
					listener.onRequestFail(BrowserBase.this);
				}
			}

			@Override
			public void onRetry(int retryNo) {
				// TODO Auto-generated method stub
				super.onRetry(retryNo);
				Log.i(TAG, "onRetry " + retryNo);
			}

			@Override
			public void onCancel() {
				super.onCancel();
				if (listener != null) {
					listener.onRequestCancel(BrowserBase.this);
				}
			}
		};
	}

	// 현재 이어받기는 고려하지 않음.
	public FileAsyncHttpResponseHandler getFileHandler() {
		if (this.file == null) {
			return new FileAsyncHttpResponseHandler(mContext) {

				@Override
				public void onStart() {
					super.onStart();
					if (listener != null) {
						listener.onRequestStart(BrowserBase.this);
					}
				}

				@Override
				public void onFinish() {
					super.onFinish();
					if (listener != null) {
						listener.onRequestFinish(BrowserBase.this);
					}
				}

				@Override
				public void onSuccess(int statusCode, Header[] headers, File file) {
					BrowserBase.this.file = file;

					if (listener != null) {
						listener.onRequestSuccess(BrowserBase.this);
					}
				}

				@Override
				public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
					if (listener != null) {
						BrowserBase.this.statusCode = -1;
						listener.onRequestFail(BrowserBase.this);
					}
				}

				public void onProgress(int bytesWritten, int totalSize) {
					super.onProgress(bytesWritten, totalSize);
					if (listener != null) {
						listener.onRequestProgress(BrowserBase.this, bytesWritten, totalSize);
					}
				};

				@Override
				public void onCancel() {
					super.onCancel();
					if (listener != null) {
						listener.onRequestCancel(BrowserBase.this);
					}
				}
			};
		} else {
			return new FileAsyncHttpResponseHandler(this.file) {

				@Override
				public void onStart() {
					super.onStart();
					if (listener != null) {
						listener.onRequestStart(BrowserBase.this);
					}
				}

				@Override
				public void onFinish() {
					super.onFinish();
					if (listener != null) {
						listener.onRequestFinish(BrowserBase.this);
					}
				}

				@Override
				public void onSuccess(int statusCode, Header[] headers, File file) {
					BrowserBase.this.file = file;

					if (listener != null) {
						listener.onRequestSuccess(BrowserBase.this);
					}
				}

				@Override
				public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
					if (listener != null) {
						BrowserBase.this.statusCode = -1;
						listener.onRequestFail(BrowserBase.this);
					}
				}

				public void onProgress(int bytesWritten, int totalSize) {
					super.onProgress(bytesWritten, totalSize);
					if (listener != null) {
						listener.onRequestProgress(BrowserBase.this, bytesWritten, totalSize);
					}
				};

				@Override
				public void onCancel() {
					super.onCancel();
					if (listener != null) {
						listener.onRequestCancel(BrowserBase.this);
					}
				}
			};
		}

	}
}
