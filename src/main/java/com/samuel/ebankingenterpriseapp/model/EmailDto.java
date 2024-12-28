package com.samuel.ebankingenterpriseapp.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailDto {

    private String subject;

    private String event;


    private String fullName;

    private String recipient;
}
