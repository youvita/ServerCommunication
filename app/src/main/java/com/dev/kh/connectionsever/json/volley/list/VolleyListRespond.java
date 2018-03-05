package com.dev.kh.connectionsever.json.volley.list;

/*
 * Created by ChanYouvita on 1/11/18.
 */

import com.dev.kh.connectionsever.helper.paser.volley.MessageParser;

public class VolleyListRespond extends MessageParser {

    public VolleyListRespond(Object object) throws Exception {
        super.initRecvMessage(object);
    }

    public VolleyListRespondRec getListRecord() throws Exception {
        return new VolleyListRespondRec(getRecord("REC"));
    }
}
