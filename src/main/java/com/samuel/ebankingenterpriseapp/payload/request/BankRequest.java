package com.samuel.ebankingenterpriseapp.payload.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankRequest {

    private String name;

    private String address;

    private String contactDetails;

    private boolean active;
}
