package com.payten.paytencreditproect2.CreditApi;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Service {

    private Double monthlyIncome;
    private Integer creditScore;
    private Integer creditLimitMultiplier;
    private String creditResult;
    private Double creditLimit;

    public void calculete(Double monthlyIncome, Integer creditScore, Integer creditLimitMultiplier) {

        if (getCreditScore() < 500) {
            setCreditResult("DENIED");
            setCreditLimit(0.0);
        }

        if (getCreditScore() > 500 && getCreditScore() < 1000 && getMonthlyIncome() < 5000) {
            setCreditResult("APPROVED");
            setCreditLimit(10000.00);
        }

        if (getCreditScore() > 500 && getCreditScore() < 1000 && getMonthlyIncome() > 5000) {
            setCreditResult("APPROVED");
            setCreditLimit(20000.00);
        }

        if (getCreditScore() >= 1000) {
            setCreditResult("APPROVED");
            setCreditLimit(getMonthlyIncome() * getCreditLimitMultiplier());
        }
    }
}
