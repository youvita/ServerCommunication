package com.dev.kh.connectionsever.helper.paser.retrofit;

/*
 * Created by ChanYouvita on 1/12/18.
 */
import com.google.gson.Gson;
import org.json.JSONObject;

public class MessageParser {

    public static Object convertObject(JSONObject object, Class cn) {
        Gson g = new Gson();
        return g.fromJson(String.valueOf(object), cn);
    }

}
