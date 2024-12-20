package com.samuel.ebankingenterpriseapp.repository;

import com.samuel.ebankingenterpriseapp.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
