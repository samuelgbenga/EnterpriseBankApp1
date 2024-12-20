package com.samuel.ebankingenterpriseapp.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
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

    private boolean active;

    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;



}
