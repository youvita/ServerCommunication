package com.dev.kh.connectionsever.helper.network.volley;

public interface OnNetworkListener {
    void onNetworkResponse(String tranCode, Object object);
    void onNetworkError(Object object);
}
