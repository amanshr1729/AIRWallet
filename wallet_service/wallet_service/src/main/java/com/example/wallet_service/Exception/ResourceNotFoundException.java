package com.example.wallet_service.Exception;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ResourceNotFoundException extends RuntimeException{
    public  ResourceNotFoundException () {
        super("Wallet Not Found");
    }

}
