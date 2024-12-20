package com.samuel.ebankingenterpriseapp.payload.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BranchManagerRequest {

    private String firstName;
    private String lastName;
    private String contactNumber;
    private String email;

}
