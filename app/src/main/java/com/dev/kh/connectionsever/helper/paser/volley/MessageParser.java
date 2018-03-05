/**
 * <pre>
 * 통신단 Data 관리 Class
 *  - 송신 Data HashMap으로 구성되어 관리
 *  - 수신 Data JSONArray 로 구성되어 관리
 * @COPYRIGHT (c) 2010 WebCash, Inc. All Right Reserved.
 *
 * @author       : WebCash
 * @Description  : 
 * @History      : 
 *
 * </pre>
 **/
package com.dev.kh.connectionsever.helper.paser.volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MessageParser {
	private JSONArray mRecvMessage;
	private int mIdx = 0;	//
	
	protected MessageParser() {}
	protected void initRecvMessage(Object obj) throws Exception {

		if(obj.equals(JSONObject.NULL) || obj.equals("")) {
			mRecvMessage = new JSONArray("[]");
		}else {
			mRecvMessage = (JSONArray) obj;
		}
	}

	public boolean isRecvEOR() throws JSONException {
		return mIdx == getLength();
	}

	public void moveFirst() {
		mIdx = 0;
	}
	
	public void moveNext() {
		mIdx++;
	}
	
	public void moveLast() throws JSONException {
		mIdx = getLength() - 1;
	}
	
	public void movePrev() {
		mIdx--;
	}
	
	public void move(int pos) {
		mIdx = pos;
	}

	private boolean has(String key) throws JSONException {
		return mRecvMessage.getJSONObject(mIdx).has(key);
	}

	public boolean getBoolean(String key) throws JSONException {
		return has(key) && mRecvMessage.getJSONObject(mIdx).getBoolean(key);
	}

	public Object getRecord(String key) throws JSONException {
		if(!has(key)) return new JSONArray("[]");
		return mRecvMessage.getJSONObject(mIdx).get(key);
	}

	public JSONArray getArray(String key) throws JSONException {
		if(!has(key)) return new JSONArray("[]");
		return mRecvMessage.getJSONObject(mIdx).getJSONArray(key);
	}

	protected String getString(String key) throws JSONException {
		if(!has(key)) return "";
		return mRecvMessage.getJSONObject(mIdx).getString(key);
	}

	public void setString(String key, String value) throws JSONException {
		mRecvMessage.getJSONObject(mIdx).put(key, value);
	}

	public String getStringIdx(String key, int idx) throws JSONException {
		if(!has(key)) return "";
		return mRecvMessage.getJSONObject(idx).getString(key);
	}

	public int getInt(String key) throws JSONException {
		if(!has(key)) return 0;
		return mRecvMessage.getJSONObject(mIdx).getInt(key);
	}

	public int getLength() throws JSONException {
		return mRecvMessage.length();
	}

	public String getMessageToString() throws JSONException {
		return mRecvMessage.toString();
	}
}