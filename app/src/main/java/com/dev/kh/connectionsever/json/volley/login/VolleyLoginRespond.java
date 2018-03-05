package com.dev.kh.connectionsever.json.volley.login;

/*
 * Created by ChanYouvita on 1/11/18.
 */

import com.dev.kh.connectionsever.helper.paser.volley.MessageParser;

public class VolleyLoginRespond extends MessageParser {

    public VolleyLoginRespond(Object object) throws Exception {
        super.initRecvMessage(object);
    }

    public String getUserName() throws Exception {
        return getString("USER_NM");
    }

    public String getImgUrl() throws Exception {
        return getString("IMG_URL");
    }
}
