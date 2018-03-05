package com.dev.kh.connectionsever.json.retrofit.list;

/*
 * Created by ChanYouvita on 1/11/18.
 */

import org.json.JSONException;
import org.json.JSONObject;

public class RetroListRequest extends JSONObject {
    public static final String RequestKey = "MYCD_MBL_L001";

    public void setINQ_DT(String ing_dt) throws JSONException {
        put("INQ_DT",ing_dt);
    }
}
