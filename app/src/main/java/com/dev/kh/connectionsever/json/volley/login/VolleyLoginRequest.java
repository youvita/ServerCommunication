package com.dev.kh.connectionsever.json.volley.login;

/*
 * Created by ChanYouvita on 1/11/18.
 */

import org.json.JSONException;
import org.json.JSONObject;

public class VolleyLoginRequest extends JSONObject {
    public static final String RequestKey = "MYCD_MBL_P001";

    public void setUserId(String user_id) throws JSONException {
        put("USER_ID",user_id);
    }

    public void setUserPw(String user_pw) throws JSONException {
        put("USER_PW",user_pw);
    }
}
