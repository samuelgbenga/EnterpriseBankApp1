package com.samuel.ebankingenterpriseapp.entity;

import com.samuel.ebankingenterpriseapp.enums.RepaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


// this will be used to checkmate customer loan payment history
// currentDuepaymentdate and actual due payment date
// currentDuepayment and actual due payment
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonitorLoan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "loan_id", nullable = false)
    private Loan loan;


    @Enumerated(EnumType.STRING)
    private RepaymentStatus currentDueRepaymentSTatus;

    private BigDecimal actualDuePayment;

    private BigDecimal currentDuePayment;

    private LocalDate actualDueDate;


    // is same as the date paid
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;


    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;

    }
}
