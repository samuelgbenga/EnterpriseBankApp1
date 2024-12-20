package com.samuel.ebankingenterpriseapp.model;


import com.samuel.ebankingenterpriseapp.entity.Customer;
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
public class LoanDto {

    private Long id;

    private BigDecimal amount;

    private BigDecimal interestRate;

    private LocalDate startDate;

    private LocalDate endDate;

    private String status; // Pending, Approved, Paid

    private Customer customer;
}
