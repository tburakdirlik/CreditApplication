package com.payten.paytencreditproect2.CreditApi;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDbEntity, Long> {
}