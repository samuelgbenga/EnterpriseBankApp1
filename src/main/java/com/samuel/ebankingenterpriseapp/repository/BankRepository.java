package com.samuel.ebankingenterpriseapp.repository;

import com.samuel.ebankingenterpriseapp.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank, Long> {
}
