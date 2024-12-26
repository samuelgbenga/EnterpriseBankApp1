package com.samuel.ebankingenterpriseapp.model;


import com.samuel.ebankingenterpriseapp.entity.Account;
import com.samuel.ebankingenterpriseapp.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor

public class TransactionDto {


    private Long id;

    private BigDecimal amount;

    private TransactionType transferType; // Deposit, Withdrawal, Transfer

    private LocalDateTime transactionDate;

    private String sourceAccount;

    private String destinationAccount;



    // Constructor for the query
    public TransactionDto(Long id, BigDecimal amount, TransactionType transferType,
                          LocalDateTime transactionDate, String sourceAccount) {
        this.id = id;
        this.amount = amount;
        this.transferType = transferType;
        this.transactionDate = transactionDate;
        this.sourceAccount = sourceAccount;


    }

    // i adjusted the constructor so it can overload for action specific to deposit or withdrawal
    public TransactionDto(Long id, BigDecimal amount, TransactionType transferType,
                           String destinationAccount, LocalDateTime transactionDate) {
        this.id = id;
        this.amount = amount;
        this.transferType = transferType;
        this.transactionDate = transactionDate;
        this.destinationAccount = destinationAccount;


    }


    // Constructor for the query
    public TransactionDto(Long id, BigDecimal amount, TransactionType transferType,
                          LocalDateTime transactionDate, String sourceAccount, String destinationAccount) {
        this.id = id;
        this.amount = amount;
        this.transferType = transferType;
        this.transactionDate = transactionDate;
        this.sourceAccount = sourceAccount!=null ? sourceAccount: "N/A";
        this.destinationAccount = destinationAccount!= null ? destinationAccount : "N/A";

    }

    /**
     * Todo: try staring with the modified constructor first
     *
     */


}
