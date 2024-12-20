package com.samuel.ebankingenterpriseapp.payload.request;


import com.samuel.ebankingenterpriseapp.entity.Customer;
import com.samuel.ebankingenterpriseapp.enums.LoanStatus;
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



    private BigDecimal amount;

    private BigDecimal interestRate;

    private LocalDate startDate;

    private LocalDate endDate;

    private LoanStatus loanStatus = LoanStatus.PENDING; // Pending, Approved, Paid


}
