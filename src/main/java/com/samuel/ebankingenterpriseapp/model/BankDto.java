package com.samuel.ebankingenterpriseapp.model;

import com.samuel.ebankingenterpriseapp.entity.Branch;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankDto {


    private Long id;

    private String name;

    private String address;

    private String contactDetails;

    private List<Branch> branches;
}
