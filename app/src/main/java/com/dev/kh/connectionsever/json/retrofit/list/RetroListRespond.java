
package com.dev.kh.connectionsever.json.retrofit.list;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class RetroListRespond {

    @SerializedName("REC")
    private List<RetroListRespondRec> mREC;

    public List<RetroListRespondRec> getREC() {
        return mREC;
    }

    public void setREC(List<RetroListRespondRec> REC) {
        mREC = REC;
    }

}
