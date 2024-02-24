package com.example.user_service.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @Column( name = "name")
    private String name;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name= "pnr")
    private String pnr;
    @Column(name= "is_kyc_done", nullable = false)
    private boolean is_kyc_done;
}
