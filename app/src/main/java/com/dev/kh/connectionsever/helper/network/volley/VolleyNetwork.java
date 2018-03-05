package com.dev.kh.connectionsever.helper.network.volley;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class VolleyNetwork {
	private static int TIME_OUT	= 60 * 1000;

	/**
	 * VolleyNetwork Listener
	 */
	private OnNetworkListener mOnNetworkListener;

	/**
	 * VolleyNetwork Volley RequestQueue
	 */
	private static RequestQueue mRequestQueue = null;

	private Map<String, String> mHeaders = null;

	private Context mContext;

	public VolleyNetwork(Context context, OnNetworkListener onVolleyNetworkListener) {
		mContext = context;
		mOnNetworkListener = onVolleyNetworkListener;

		initRequestQueue();
	}

	public void requestVolleyNetwork(final String tranCode, final String url, final Map<String,String> param) {
		try {
			// Request
			StringRequest request = new StringRequest(StringRequest.Method.POST, url
					, new Response.Listener<String>() {
						@Override
						public void onResponse(String result) {

							try {
								mOnNetworkListener.onNetworkResponse(tranCode, result);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					},
					new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError e) {
							e.printStackTrace();
							mOnNetworkListener.onNetworkError(e);
						}
					})
			{
				@Override
				protected Response<String> parseNetworkResponse(
						NetworkResponse response) {
					try {
						return Response.success(new String(response.data,"utf-8"), HttpHeaderParser.parseCacheHeaders(response));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
						return null;
					}

				}

				@Override
				protected Map<String, String> getParams() throws AuthFailureError {
					return param;
				}

				@Override
				public Map<String, String> getHeaders() throws AuthFailureError {
					return mHeaders;
				}

			};

			// volley timeout setting
			request.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT,
					DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
					DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

			mRequestQueue.getCache().clear();
			mRequestQueue.add(request);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initRequestQueue() {
		try {
			if (mRequestQueue == null) {
				HttpParams httpparams = new BasicHttpParams();
				HttpConnectionParams.setConnectionTimeout(httpparams, TIME_OUT);
				HttpConnectionParams.setSoTimeout(httpparams, TIME_OUT);

				DefaultHttpClient mHttpClient = new DefaultHttpClient(httpparams);
				HttpStack httpStack = new HttpClientStack(mHttpClient);

				mRequestQueue = Volley.newRequestQueue(mContext, httpStack);

				mHeaders = new HashMap<>();
				mHeaders.put("charset", "UTF-8");
				mHeaders.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
