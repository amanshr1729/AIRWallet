package com.example.wallet_service.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @Column(name = "user_id")
    private int user_id;
    @Column(name = "account_balance")
    private int account_balance;
    @Column(name = "wallet_type")
    private String wallet_type;
    @Column(name = "is_active")
    private byte is_active;
}
