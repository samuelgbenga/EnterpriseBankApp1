package com.samuel.ebankingenterpriseapp.model;

import com.samuel.ebankingenterpriseapp.entity.Branch;
import com.samuel.ebankingenterpriseapp.entity.Customer;
import com.samuel.ebankingenterpriseapp.entity.Transaction;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDto {

    private Long id;

    private String accountNumber;

    private BigDecimal balance;

    private Branch branch;

    private List<Customer> customers;

    private List<Transaction> transactions;
}
