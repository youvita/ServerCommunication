
package com.dev.kh.connectionsever.json.retrofit.list;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class RetroListRespondRec {

    @SerializedName("CARD_NO")
    private String card_no;
    @SerializedName("BIZ_NM")
    private String biz_nm;

    public String getCardNo() {
        return card_no;
    }

    public void setCardNo(String card_no) {
        card_no = card_no;
    }

    public String getBizNm() {
        return biz_nm;
    }

    public void setBizNm(String biz_nm) {
        biz_nm = biz_nm;
    }
}
