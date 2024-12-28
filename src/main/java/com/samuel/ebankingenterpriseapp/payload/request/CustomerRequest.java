package com.samuel.ebankingenterpriseapp.payload.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerRequest {


    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

}
