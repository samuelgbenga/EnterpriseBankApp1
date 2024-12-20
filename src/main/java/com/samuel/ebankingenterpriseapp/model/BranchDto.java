package com.samuel.ebankingenterpriseapp.model;


import com.samuel.ebankingenterpriseapp.entity.Account;
import com.samuel.ebankingenterpriseapp.entity.Bank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BranchDto {

    private Long id;

    private String name;

    private String location;

    private Bank bank;

    private int branchCode;

    private boolean active;

    private List<Account> accounts;
}

