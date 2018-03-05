package com.dev.kh.connectionsever;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.dev.kh.connectionsever.helper.paser.retrofit.MessageParser;
import com.dev.kh.connectionsever.helper.paser.retrofit.RetrofitParser;
import com.dev.kh.connectionsever.json.retrofit.list.RetroListRequest;
import com.dev.kh.connectionsever.json.retrofit.list.RetroListRespond;
import com.dev.kh.connectionsever.json.retrofit.list.RetroListRespondRec;
import com.dev.kh.connectionsever.json.retrofit.login.RetroLoginRequest;
import com.dev.kh.connectionsever.json.retrofit.login.RetroLoginRespond;
import com.dev.kh.connectionsever.json.volley.login.VolleyLoginRequest;
import org.json.JSONObject;

public class RetrofitActivity extends AppCompatActivity implements RetrofitParser.OnRetrofitListener{
    private RetrofitParser mRetrofitParser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRetrofitParser = new RetrofitParser(this, this);

        /* */
        onRetrofitRequest(VolleyLoginRequest.RequestKey);
    }

    private void onRetrofitRequest(String tranCd) {
        try {
            if (tranCd.equals(VolleyLoginRequest.RequestKey)) {
                RetroLoginRequest json = new RetroLoginRequest();
                json.setUserId("expert55");
                json.setUserPw("qwer1234!");
                mRetrofitParser.request(RetroLoginRequest.RequestKey, json, true, false);
            }else {
                RetroListRequest json = new RetroListRequest();
                json.setINQ_DT("");
                mRetrofitParser.request(RetroListRequest.RequestKey, json, true, false);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResponse(String tranCode, JSONObject object) {
        try {
            if (tranCode.equals(VolleyLoginRequest.RequestKey)) {
                RetroLoginRespond res = (RetroLoginRespond) MessageParser.convertObject(object, RetroLoginRespond.class);
                Log.d(">>>>>", "User id: " + res.getUserId());
                Log.d(">>>>>", "User img: " + res.getUser_Img_Path());

                onRetrofitRequest(RetroListRequest.RequestKey);
            }else{
                RetroListRespond res = (RetroListRespond) MessageParser.convertObject(object, RetroListRespond.class);

                for (int i = 0; i < res.getREC().size(); i++) {
                    Log.d(">>>>>", "Card No: " + res.getREC().get(i).getCardNo());
                    Log.d(">>>>>", "Biz Nm: " + res.getREC().get(i).getBizNm());
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(String errMessage) {
        Log.d(">>>>>>", "errMessage: " + errMessage);
    }
}
