package com.samuel.ebankingenterpriseapp.payload.request;


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
public class LoanRequest {

    private String accountNumber;

    private BigDecimal amount;

    private int numberOfInstallments;

    private LocalDate endDate;



}
