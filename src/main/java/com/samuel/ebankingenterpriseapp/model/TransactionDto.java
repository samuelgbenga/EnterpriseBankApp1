package com.samuel.ebankingenterpriseapp.model;


import com.samuel.ebankingenterpriseapp.entity.Account;
import com.samuel.ebankingenterpriseapp.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDto {


    private Long id;

    private BigDecimal amount;

    private TransactionType transferType; // Deposit, Withdrawal, Transfer

    private LocalDate transactionDate;

   // private Account account;

    private String accountName;

    private Long accountId;
}
