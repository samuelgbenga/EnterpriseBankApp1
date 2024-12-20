package com.samuel.ebankingenterpriseapp.model;


import com.samuel.ebankingenterpriseapp.entity.Account;
import com.samuel.ebankingenterpriseapp.entity.Loan;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;


    private List<Account> accounts;

    private List<Loan> loans;
}
