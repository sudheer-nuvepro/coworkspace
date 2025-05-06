package com.nuvepro.coworkspacebooking.dto;

import com.google.gson.annotations.SerializedName;

public class CreatePayment {

    @SerializedName("paymentAmount")
    private Long paymentAmount;

    public Long getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Long paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

}
