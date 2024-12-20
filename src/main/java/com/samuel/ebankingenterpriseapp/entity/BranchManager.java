package com.samuel.ebankingenterpriseapp.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BranchManager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String contactNumber;
    private String email;

    @ManyToOne
    @JoinColumn(name = "bank_id", nullable = false)
    private Bank bank;  // A branch manager is associated with a specific branch

    @OneToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;



}
