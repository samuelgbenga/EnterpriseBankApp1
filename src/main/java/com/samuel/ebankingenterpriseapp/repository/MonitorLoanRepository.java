package com.samuel.ebankingenterpriseapp.repository;

import com.samuel.ebankingenterpriseapp.entity.MonitorLoan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MonitorLoanRepository extends JpaRepository<MonitorLoan, Long> {
    List<MonitorLoan> findByLoanId(Long loanId);
}
