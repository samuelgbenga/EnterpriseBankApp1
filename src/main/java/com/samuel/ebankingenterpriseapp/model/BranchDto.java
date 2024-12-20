package com.samuel.ebankingenterpriseapp.model;


import com.samuel.ebankingenterpriseapp.entity.Account;
import com.samuel.ebankingenterpriseapp.entity.Bank;
import com.samuel.ebankingenterpriseapp.entity.BranchManager;
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

    private int branchCode;

    private boolean active;

    private BranchManager branchManager;

    private List<Account> accounts;

    private String bankName;

    private Long bankId;
}

