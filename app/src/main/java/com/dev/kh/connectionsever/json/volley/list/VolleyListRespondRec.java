package com.dev.kh.connectionsever.json.volley.list;

/*
 * Created by ChanYouvita on 1/11/18.
 */

import com.dev.kh.connectionsever.helper.paser.volley.MessageParser;

public class VolleyListRespondRec extends MessageParser {

    public VolleyListRespondRec(Object object) throws Exception {
        super.initRecvMessage(object);
    }

    public String getCard_No() throws Exception {
        return getString("CARD_NO");
    }

    public String getBiz_Nm() throws Exception {
        return getString("BIZ_NM");
    }

}
