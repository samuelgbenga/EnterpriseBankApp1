package com.samuel.ebankingenterpriseapp.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.samuel.ebankingenterpriseapp.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private TransactionType transferType; // Deposit, Withdrawal, Transfer

    private LocalDate transactionDate;

    // Relationship to the source account
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "source_account_id")
    private Account sourceAccount;

    // Relationship to the destination account
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "destination_account_id")
    private Account destinationAccount;

}
