package com.payten.paytencreditproect2.SmsApi;

import com.payten.paytencreditproect2.CreditApi.UserDbEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StarterSmsSender {

    private final TwilioConfiguration twilioConfiguration=new TwilioConfiguration();
    private final TwilioSmsSender twilioSmsSender=new TwilioSmsSender(twilioConfiguration);
    private final SmsService smsService=new SmsService(twilioSmsSender);
    private final SmsController smsController= new SmsController(smsService);

    private String phoneMessage="";

    public void sendMessage(UserDbEntity userDbEntityrecordToDb){

        phoneMessage +="YOUR CREDIT APPLICATION IS ";

        if (userDbEntityrecordToDb.getCreditResult().equals("APPROVED")){
            phoneMessage += " " + userDbEntityrecordToDb.getCreditResult() +
                    " AND ASSIGNED " +
                    userDbEntityrecordToDb.getCreditLimit() + " TL LIMIT";
        }

        if (userDbEntityrecordToDb.getCreditResult().equals("DENIED")){
            phoneMessage += "DENIED";
        }

        SmsRequest smsRequest = SmsRequest.builder()
                .message(phoneMessage)
                .phoneNumber(userDbEntityrecordToDb.getTelephoneNo().toString())
                .build();
        smsController.sendSms(smsRequest);

    }
}
