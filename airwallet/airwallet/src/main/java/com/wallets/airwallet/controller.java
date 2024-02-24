package com.wallets.airwallet;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class controller {
    @GetMapping("/welcome")
    public String getString(){
        return "Welcome to Airwallet!";
    }
}
