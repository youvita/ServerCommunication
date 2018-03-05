package com.dev.kh.connectionsever.helper.paser.retrofit;

/*
 * Created by ChanYouvita on 1/11/18.
 */

import android.content.Context;
import android.util.Log;
import com.dev.kh.connectionsever.helper.conf.Conf;
import com.dev.kh.connectionsever.helper.network.retrofit.ApiInterface;
import com.dev.kh.connectionsever.helper.network.retrofit.PersistentCookieStore;
import com.dev.kh.connectionsever.helper.ui.BizLoading;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONObject;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitParser {

    private OnRetrofitListener mListener;
    private Context mContext;
    private BizLoading mLoading;

    public RetrofitParser(Context context, OnRetrofitListener listener) {
        mContext  = context;
        mListener = listener;
        mLoading  = new BizLoading(context);
    }
    
    public void request(final String tranCd, JSONObject object, final boolean isDialog, boolean method) throws Exception {

        if (isDialog) {
            mLoading.showProgressDialog();
        }

        JSONObject jobjectInput = new JSONObject();

        jobjectInput.put(ComTranCode.CNTS_CRTS_KEY_CODE  , "");
        jobjectInput.put(ComTranCode.KEY_TRAN_CODE  , tranCd);
        jobjectInput.put(ComTranCode.KEY_REQ_DATA   , object);

        Log.d("network::","Input: " + jobjectInput.toString());

        //+header
        HashMap<String, String> headers = new HashMap<>();
        headers.put("charset", "UTF-8");
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        headers.put("User-Agent", Conf.mUserAgent);

        ApiInterface service = getService(mContext);
        final Call<ResponseBody> request = method ? service.postRequestMessage(headers, jobjectInput.toString()) : service.getRequestMessage(headers, jobjectInput.toString());
        request.enqueue(new Callback<ResponseBody>() {

            @Override  //if this method is executed, the actual call has been made
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {

                    JSONObject jsonOutput;
                    try {

                        jsonOutput = new JSONObject(response.body().string());

                        Log.d("network::", "Output: " + jsonOutput);

                        if (!jsonOutput.isNull(ComTranCode.KEY_RSLT_CD)) {
                            String resultErrorCd = jsonOutput.getString(ComTranCode.KEY_RSLT_CD);
                            if (!resultErrorCd.equals("0000")) {
                                mLoading.dismissProgressDialog();
                                return;
                            }
                        }

                        if (isDialog) {
                            mLoading.dismissProgressDialog();
                        }
                        mListener.onResponse(tranCd, jsonOutput.getJSONObject(ComTranCode.KEY_RES_DATA));

                    } catch (Exception e) {
                        e.getStackTrace();
                        mListener.onError(response.message());
                    }
                }
            }

            @Override //maybe the call couldn't be made because of lack of connection.
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mListener.onError(t.toString());
            }
        });
    }

    private static Retrofit retrofit = null;

    private static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    private static Retrofit getClient(Context context) {

        // init cookie manager
        CookieManager cookieManager = new CookieManager(new PersistentCookieStore(context), CookiePolicy.ACCEPT_ALL);

//        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        OkHttpClient client = new OkHttpClient.Builder()
                .cookieJar(new JavaNetCookieJar(cookieManager))
                .retryOnConnectionFailure(true)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Conf.RETROF_BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
    
    private ApiInterface getService(Context context) {
        return getClient(context).create(ApiInterface.class);
    }

    public interface OnRetrofitListener {
        void onResponse(String tranCode, JSONObject object);
        void onError(String errMessage);
    }

    private class ComTranCode {
        static final String KEY_REQ_DATA 				= "REQ_DATA";
        static final String KEY_TRAN_CODE 				= "TRAN_NO";
        static final String CNTS_CRTS_KEY_CODE 			= "CNTS_CRTS_KEY";

        static final String KEY_RSLT_CD 				= "RSLT_CD";
        static final String KEY_RSLT_MSG				= "RSLT_MSG";
        static final String KEY_RES_DATA 				= "RESP_DATA";
    }
}
