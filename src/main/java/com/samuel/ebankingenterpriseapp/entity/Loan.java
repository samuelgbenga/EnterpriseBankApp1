package com.samuel.ebankingenterpriseapp.entity;


import com.samuel.ebankingenterpriseapp.enums.LoanStatus;
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
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    private BigDecimal interestRate;

    private LocalDate startDate;

    private LocalDate endDate;

    private LoanStatus loanStatus; // Pending, Approved, Paid

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
}
