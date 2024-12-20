package com.samuel.ebankingenterpriseapp.model;

import com.samuel.ebankingenterpriseapp.entity.Bank;
import com.samuel.ebankingenterpriseapp.entity.Branch;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BranchManagerDto {

    private Long id;

    private String firstName;
    private String lastName;
    private String contactNumber;
    private String email;

    private Bank bank;  // A branch manager is associated with a specific branch

    private Branch branch;
}
