package com.dev.kh.connectionsever.json.retrofit.login;

/*
 * Created by ChanYouvita on 1/11/18.
 */

import com.google.gson.annotations.SerializedName;

public class RetroLoginRespond {

    @SerializedName("USER_ID")
    private String use_id;

    @SerializedName("USER_IMG_PATH")
    private String user_img_path;

    public String getUserId() throws Exception {
        return use_id;
    }

    public String getUser_Img_Path() throws Exception {
        return user_img_path;
    }

}
