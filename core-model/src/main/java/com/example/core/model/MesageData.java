package com.example.core.model;

import com.user.appbanhang.model.Message;

public class MesageData {
    Message message;

    public MesageData(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
