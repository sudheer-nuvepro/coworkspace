package com.nuvepro.coworkspacebooking.Controller;

public class AddCoworkspaceResponse {

    private int coworkspaceId;
    private String responseMessage;
    private String status;

    public int getCoworkspaceId() {
        return coworkspaceId;
    }

    public void setCoworkspaceId(int coworkspaceId) {
        this.coworkspaceId = coworkspaceId;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
