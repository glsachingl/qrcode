package com.infy.qrcode.exception;

public class CustomException extends Exception {
    private static final long serialVersionUID = 2454912479664413440L;

    private String responseId;

    public CustomException() {
        super();
    }

    public CustomException(String message,String responseId) {
        super(message);
        this.responseId = responseId;
    }

    public String getResponseId() {
        return responseId;
    }

    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

}