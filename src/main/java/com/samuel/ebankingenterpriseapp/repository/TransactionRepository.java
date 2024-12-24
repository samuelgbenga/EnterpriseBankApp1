package com.samuel.ebankingenterpriseapp.repository;

import com.samuel.ebankingenterpriseapp.entity.Transaction;
import com.samuel.ebankingenterpriseapp.enums.TransactionType;
import com.samuel.ebankingenterpriseapp.model.TransactionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transaction t " +
            "WHERE t.sourceAccount.id = :accountId " +
            "OR t.destinationAccount.id = :accountId")
    Page<Transaction> findByAccountId(@Param("accountId") Long accountId, Pageable pageable);

    @Query("SELECT new com.samuel.ebankingenterpriseapp.model.TransactionDto(" +
            "t.id, t.amount, t.transferType, t.transactionDate, " +
            "t.sourceAccount.accountNumber, t.destinationAccount.accountNumber) " +
            "FROM Transaction t " +
            "WHERE t.transferType = :transactionType")
    Page<TransactionDto> findByTransactionType(@Param("transactionType") TransactionType transactionType, Pageable pageable);

    @Query("SELECT new com.samuel.ebankingenterpriseapp.model.TransactionDto(" +
            "t.id, t.amount, t.transferType, t.destinationAccount.accountNumber, " +
            " t.transactionDate) " +
            "FROM Transaction t " +
            "WHERE t.transferType = :transactionType")
    Page<TransactionDto> findByTransactionTypeDeposit(@Param("transactionType") TransactionType transactionType, Pageable pageable);

    @Query("SELECT new com.samuel.ebankingenterpriseapp.model.TransactionDto(" +
            "t.id, t.amount, t.transferType, t.transactionDate, " +
            "t.sourceAccount.accountNumber) " +
            "FROM Transaction t " +
            "WHERE t.transferType = :transactionType")
    Page<TransactionDto> findByTransactionTypeWithdrawal(@Param("transactionType") TransactionType transactionType, Pageable pageable);
}
