package com.siuhim.hello.exception;

import java.util.List;

public class ValidationException extends Exception {

    private List<String> messages;

    public ValidationException(List<String> msgList) {
        this.messages = msgList;
    }

    public List<String> getMessages() {
        return this.messages;
    }
}
