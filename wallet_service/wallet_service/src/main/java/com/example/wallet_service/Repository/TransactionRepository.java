package com.example.wallet_service.Repository;

import com.example.wallet_service.Model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    @Query(value = "SELECT * FROM transaction t where t.sender_id = :sender_id ",nativeQuery = true)
    public Transaction findByTransactionBySenderId(@Param("sender_id") int senderId);

    @Query(value = "SELECT * FROM transaction t where t.receiver_id = :receiver_id ",nativeQuery = true)
    public Transaction findByTransactionByReceiverId(@Param("receiver_id") int senderId);
    @Query(value = "SELECT * FROM transaction t where t.receiver_id = :userid OR t.sender_id =:userid ",nativeQuery = true)
    List<Transaction> findBysidAndrid(@Param("userid") int userid);
}
