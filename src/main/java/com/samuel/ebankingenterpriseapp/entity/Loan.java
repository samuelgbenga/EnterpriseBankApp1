package com.samuel.ebankingenterpriseapp.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.samuel.ebankingenterpriseapp.enums.LoanStatus;
import com.samuel.ebankingenterpriseapp.enums.RepaymentStatus;
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
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    // amount after interest is calculated
    private BigDecimal amountAfterInterest;

    private int numberOfInstallments; // number of installment;

    private BigDecimal interestRate; // interest rate for total amount borrowed

    private LocalDate loanApprovalDate; // with 2 months moratorium period

    private LocalDate startDate; // with 2 months moratorium period from the approval date

    private LocalDate endDate;

    private BigDecimal actualInstallmentDue; // fixed base on calculated installment to be paid over the period of time.

    // amount to pay for current installment
    // this amount will increase as the currentPenaltyRate increases
    private BigDecimal currentDuePayment;

    private BigDecimal currentPenaltyRate; // in percentage default will be 0.01% of the currentDuePayment amount

    private LocalDate currentDueDate;

    private BigDecimal accruedPenaltyAmount;  // accumulated increase by reason of penalty

    private BigDecimal amountPaidSoFar;

    private int numberOfInstallmentPaid;

    @Enumerated(EnumType.STRING)
    private LoanStatus loanStatus; // Pending, Approved, Paid


    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        LocalDateTime now = LocalDateTime.now();
        this.updatedAt = now;

    }
}
