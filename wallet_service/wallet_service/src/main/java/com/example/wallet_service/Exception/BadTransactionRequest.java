package com.example.wallet_service.Exception;

public class BadTransactionRequest extends Exception {
    public BadTransactionRequest(String invalidTransaction) {
        super(invalidTransaction);
    }
}
