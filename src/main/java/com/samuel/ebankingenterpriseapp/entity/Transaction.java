package com.samuel.ebankingenterpriseapp.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.samuel.ebankingenterpriseapp.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

    // to write about the transaction being done whether its a payment of debt
    // or it is other normal transaction
    private String transactionMessage;

    @Enumerated(EnumType.STRING)
    private TransactionType transferType; // Deposit, Withdrawal, Transfer


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

    @Column(nullable = false)
    private LocalDateTime transactionDate;


    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.transactionDate = now;

    }


}
