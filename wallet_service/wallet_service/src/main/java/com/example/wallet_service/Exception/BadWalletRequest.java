package com.example.wallet_service.Exception;

public class BadWalletRequest extends RuntimeException {
public BadWalletRequest(String message){
    super(message);
}
}
