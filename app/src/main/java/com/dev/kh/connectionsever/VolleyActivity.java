package com.dev.kh.connectionsever;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.dev.kh.connectionsever.json.volley.list.VolleyListRequest;
import com.dev.kh.connectionsever.json.volley.list.VolleyListRespond;
import com.dev.kh.connectionsever.json.volley.list.VolleyListRespondRec;
import com.dev.kh.connectionsever.json.volley.login.VolleyLoginRequest;
import com.dev.kh.connectionsever.helper.paser.volley.VolleyParser;
import com.dev.kh.connectionsever.json.volley.login.VolleyLoginRespond;

public class VolleyActivity extends AppCompatActivity implements VolleyParser.OnPaserListener{
    private VolleyParser mTranParser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTranParser = new VolleyParser(this, this);

        /* login request */
        onSendRequest(VolleyLoginRequest.RequestKey);

    }

    /*
     * json input
     */
    private void onSendRequest(String tranCode) {
        try {
            if (tranCode.equals(VolleyLoginRequest.RequestKey)) {
                VolleyLoginRequest jsonLoginRequest = new VolleyLoginRequest();
                jsonLoginRequest.setUserId("expert55");
                jsonLoginRequest.setUserPw("qwer1234!");
                mTranParser.request(tranCode, jsonLoginRequest, true);
            }else{
                VolleyListRequest jsonListRequest = new VolleyListRequest();
                jsonListRequest.setINQ_DT("");
                mTranParser.request(tranCode, jsonListRequest, true);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * json output
     */
    @Override
    public void onResponse(String tranCode, Object object) {
        try{
            if (tranCode.equals(VolleyLoginRequest.RequestKey)) {
                VolleyLoginRespond jsonLoginRespond = new VolleyLoginRespond(object);
                Log.d(">>>>>", "User id: " + jsonLoginRespond.getUserName());
                Log.d(">>>>>", "User img: " + jsonLoginRespond.getImgUrl());

                /* list request */
                onSendRequest(VolleyListRequest.RequestKey);
            }else{
                VolleyListRespond jsonListRespond = new VolleyListRespond(object);
                VolleyListRespondRec jsonRec = jsonListRespond.getListRecord();

                for (int i = 0; i < jsonRec.getLength(); i++) {
                    Log.d(">>>>>", "Card No: " + jsonRec.getCard_No());
                    Log.d(">>>>>", "Biz Nm: " + jsonRec.getBiz_Nm());
                    jsonRec.moveNext();
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Object object) {

    }
}
