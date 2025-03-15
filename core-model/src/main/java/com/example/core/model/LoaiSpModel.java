package com.example.core.model;

import com.user.appbanhang.model.LoaiSp;

import java.util.List;

public class LoaiSpModel {
    boolean success;
    String message;
    List<LoaiSp> result;

    public List<LoaiSp> getResult() {
        return result;
    }

    public void setResult(List<LoaiSp> result) {
        this.result = result;
    }

    public boolean isSuccess() {

        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {

        return message;
    }

    public void setMessage(String message) {

        this.message = message;
    }

}
