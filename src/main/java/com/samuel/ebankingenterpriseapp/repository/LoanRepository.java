package com.samuel.ebankingenterpriseapp.repository;

import com.samuel.ebankingenterpriseapp.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
}
