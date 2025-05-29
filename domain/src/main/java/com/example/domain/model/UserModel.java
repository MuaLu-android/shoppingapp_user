package com.example.domain.model;

import java.util.List;

public class UserModel {
    boolean success;
    String message;
    List<UserItems> result;

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

    public List<UserItems> getResult() {
        return result;
    }

    public void setResult(List<UserItems> result) {
        this.result = result;
    }
}
