package com.dev.kh.connectionsever.helper.paser.volley;

/*
 * Created by ChanYouvita on 8/26/17.
 */

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import com.dev.kh.connectionsever.R;
import com.dev.kh.connectionsever.helper.conf.Conf;
import com.dev.kh.connectionsever.helper.network.volley.NetworkErrorCode;
import com.dev.kh.connectionsever.helper.network.volley.OnNetworkListener;
import com.dev.kh.connectionsever.helper.network.volley.VolleyNetwork;
import com.dev.kh.connectionsever.helper.ui.BizLoading;
import com.dev.kh.connectionsever.helper.ui.BizUtils;
import com.dev.kh.connectionsever.helper.ui.dialog.DlgAlert;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;

public class VolleyParser implements OnNetworkListener {
    private Context mContext;
    private OnPaserListener mListener;
    private BizLoading mLoading;
    private VolleyNetwork mVolleyNetwork;
    private boolean mIsDialog;

    public VolleyParser(Context context , OnPaserListener listener) {
        mContext       = context;
        mListener      = listener;
        mLoading       = new BizLoading(context);
        mVolleyNetwork = new VolleyNetwork(mContext , this);
    }

    public void request(final String tranCd, JSONObject object, boolean isDialog) throws JSONException {
        mIsDialog = isDialog;

        if (isDialog) {
            mLoading.showProgressDialog();
        }

        if(BizUtils.getNetworkStatus((Activity)mContext)) {
            JSONObject jobjectInput = new JSONObject();

            jobjectInput.put(ComTranCode.CNTS_CRTS_KEY_CODE  , "");
            jobjectInput.put(ComTranCode.KEY_TRAN_CODE  , tranCd);
            jobjectInput.put(ComTranCode.KEY_REQ_DATA   , object);

            Log.d("network::","Input: " + jobjectInput.toString());

            try {
                HashMap<String, String> params = new HashMap<>();
                params.put("JSONData", jobjectInput.toString());
                mVolleyNetwork.requestVolleyNetwork(tranCd, Conf.VOLLEY_BASE_URL, params);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            onErrorData(NetworkErrorCode.TRNS_ERRCD_INTERNET, null);
        }
    }

    private void onErrorData(final String errcd, Object error) {
        try {
            if (errcd.equals(NetworkErrorCode.TRNS_ERRCD_INTERNET)) {
                String title   = mContext.getString(R.string.message_sorry);
                String message = mContext.getString(R.string.message_no_internet_available);
                String button  = mContext.getString(R.string.message_ok);


                DlgAlert.showAlert(mContext, message, title, button, false, new DlgAlert.OnClickDlgListener() {
                    @Override
                    public void onClickDlgButton(int dialogIndex, DlgAlert.DIALOG_BTN buttonType) {
                        if (mIsDialog) {
                            mLoading.dismissProgressDialog();
                        }
                    }
                });
            }else{
                mListener.onError(error);
            }
        }catch (Exception e) {
            e.getStackTrace();
        }
    }

        @Override
    public void onNetworkResponse(String tranCode, Object object) {
        JSONObject jsonOutput;
        JSONArray jarrayResData   = null;
        try {
            Log.d("network::","Output: " + object.toString());

            jsonOutput = new JSONObject(object.toString());

            if (!jsonOutput.isNull(ComTranCode.KEY_RSLT_CD)) {
                String resultErrorCd = jsonOutput.getString(ComTranCode.KEY_RSLT_CD);
                if(!resultErrorCd.equals("0000")) {
                    mLoading.dismissProgressDialog();
                    return;
                }
            }

            if (!jsonOutput.isNull(ComTranCode.KEY_RES_DATA)) {
                jarrayResData = new JSONArray();
                jarrayResData.put(jsonOutput.getJSONObject(ComTranCode.KEY_RES_DATA));
            }
        }catch (Exception e) {
            e.getStackTrace();
            onErrorData(NetworkErrorCode.APP_ERRCD_UNKNOWN, object);
        }

        if (mIsDialog) {
            mLoading.dismissProgressDialog();
        }
        mListener.onResponse(tranCode, jarrayResData);
    }

    @Override
    public void onNetworkError(Object object) {
        if (mIsDialog) {
            mLoading.dismissProgressDialog();
        }
        onErrorData(NetworkErrorCode.APP_ERRCD_UNKNOWN, object);
    }

    public interface OnPaserListener {
        void onResponse(String tranCode, Object object);
        void onError(Object object);
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
