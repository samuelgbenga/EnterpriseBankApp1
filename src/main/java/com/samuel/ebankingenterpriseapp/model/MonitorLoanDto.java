package com.samuel.ebankingenterpriseapp.model;

import com.samuel.ebankingenterpriseapp.entity.Loan;
import com.samuel.ebankingenterpriseapp.enums.RepaymentStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
public class MonitorLoanDto {

    private Long id;


    private Long loanId;

    private RepaymentStatus currentDueRepaymentSTatus;

    private BigDecimal actualDuePayment;

    private BigDecimal currentDuePayment;

    private LocalDate actualDueDate;

    private LocalDateTime createdAt;
}
