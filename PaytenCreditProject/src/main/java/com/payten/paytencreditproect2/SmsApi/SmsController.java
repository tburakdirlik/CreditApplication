package com.payten.paytencreditproect2.SmsApi;

import org.springframework.beans.factory.annotation.Autowired;

public class SmsController {

    private final SmsService service;

    @Autowired
    public SmsController(SmsService service) {
        this.service = service;
    }

    public void sendSms(SmsRequest smsRequest) {
        service.sendSms(smsRequest);
    }
}