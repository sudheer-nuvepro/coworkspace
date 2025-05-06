package com.nuvepro.coworkspacebooking.Bean;

import org.springframework.stereotype.Component;

@Component
public class UserResponse {
    private String responseStatus;

    private String responseMessage;

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String getResponseMessage) {
        this.responseMessage = getResponseMessage;
    }


    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }
}