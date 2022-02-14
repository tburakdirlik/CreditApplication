package com.payten.paytencreditproect2.CreditApi;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UserRequest {

    private Long userId;
    private String name;
    private String surname;
    private Long telephoneNo;
    private Double monthlyIncome;
    private Integer creditScore;
    private Integer creditLimitMultiplier;

}