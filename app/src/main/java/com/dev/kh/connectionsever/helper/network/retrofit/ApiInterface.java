package com.dev.kh.connectionsever.helper.network.retrofit;

/*
 * Created by ChanYouvita on 1/11/18.
 */
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("CardAPI.do")
    Call<ResponseBody> getRequestMessage(@HeaderMap Map<String, String> headers, @Query("JSONData") String requestData);

    @FormUrlEncoded
    @POST("CardAPI.do")
    Call<ResponseBody> postRequestMessage(@HeaderMap Map<String, String> headers, @Field("JSONData") String requestData);

}
