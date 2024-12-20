package com.samuel.ebankingenterpriseapp.repository;

import com.samuel.ebankingenterpriseapp.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BranchRepository extends JpaRepository<Branch, Long> {
    List<Branch> findByBankId(Long bankId);
}
