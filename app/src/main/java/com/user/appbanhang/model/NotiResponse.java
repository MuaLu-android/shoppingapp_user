package com.user.appbanhang.model;

import android.security.keystore.StrongBoxUnavailableException;

public class NotiResponse {
    private String name;

    public NotiResponse(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
