package com.samuel.ebankingenterpriseapp.repository;

import com.samuel.ebankingenterpriseapp.entity.BranchManager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BranchManagerRepository extends JpaRepository<BranchManager, Long> {
    List<BranchManager> findByBankId(Long bankId);
}
