package com.payten.paytencreditproect2.CreditApi;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDbDto {

    private String name;
    private String surname;
    private Long userId;
    private Long telephoneNo;
    private Double monthlyIncome;
    private Integer creditScore;
    private Integer creditLimitMultiplier;
    private String creditResult;
    private Double creditLimit;

}
