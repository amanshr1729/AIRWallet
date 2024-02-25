package com.example.wallet_service.Repository;

import com.example.wallet_service.Model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WalletRepository extends JpaRepository<Wallet, Integer> {

    @Query(value = "SELECT * FROM wallet w WHERE w.wallet_type= :wallet_type", nativeQuery = true)
    List<Wallet> getWalletsByWalletType(@Param("wallet_type") String wallet_type);
}
