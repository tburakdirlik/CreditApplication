package com.payten.paytencreditproect2.CreditApi;

import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "UserDbEntity")
@Table(name = "UserDbEntity", uniqueConstraints = {@UniqueConstraint(name = "user_id_unique", columnNames = "user_id"),
                                                    @UniqueConstraint(name = "telephone_no_unique", columnNames = "telephone_no")})
public class UserDbEntity {

    @Id
    @Column(name = "user_id",length=50, nullable = false )
    private Long userId;

    @Column(name = "name",length=50, nullable = false)
    private String name;

    @Column(name = "surname",length=50, nullable = false)
    private String surname;

    @Column(name = "telephone_no", nullable = false)
    private Long telephoneNo;

    @Column(name = "monthly_income",length=12, nullable = false)
    private Double monthlyIncome;

    @Column(name = "credit_score",length=6, nullable = false)
    private Integer creditScore;

    @Column(name = "credit_limit_multiplier", length=6)
    private Integer creditLimitMultiplier;

    @Column(name = "credit_result", nullable = false)
    private String creditResult;

    @Column(name = "credit_limit", nullable = false)
    private Double creditLimit;
}

