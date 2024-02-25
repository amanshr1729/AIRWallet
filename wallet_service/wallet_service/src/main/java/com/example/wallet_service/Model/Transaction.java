package com.example.wallet_service.Model;

import com.example.wallet_service.Util.TransactionStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.*;

@Entity
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int  txid;
    @Column(name="amount")
    private int amount;
    @Column(name="senderId")
    private int senderId;
    @Column(name="receiverId")
    private int receiverId;
    @Column(name="date")
    private Date date;
    @Column(name="status")
    private String status;

    public Transaction(){

    }
    public Transaction(int txid, int amount, int senderId, int receiver, Date date, String status){
        this.txid = txid;
        this.amount = amount;
        this.senderId = senderId;
        this.receiverId = receiver;
        this.date = date;
        this.status = status;
    }
}
