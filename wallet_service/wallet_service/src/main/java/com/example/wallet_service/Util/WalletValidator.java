package com.example.wallet_service.Util;

import com.example.wallet_service.Model.Wallet;

public class WalletValidator {
    public static boolean isValidWallet(Wallet wallet) {
        return wallet.getAccount_balance() >= 0 && wallet.getWallet_type().contains("phone");
    }
}
