package com.example.wallet_service.Controller;

import com.example.wallet_service.Exception.BadWalletRequest;
import com.example.wallet_service.Exception.ResourceNotFoundException;
import com.example.wallet_service.Model.User;
import com.example.wallet_service.Model.Wallet;
import com.example.wallet_service.Repository.TransactionRepository;
import com.example.wallet_service.Repository.WalletRepository;
import com.example.wallet_service.Service.UserService;
import com.example.wallet_service.Util.WalletValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class WalletController {
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    UserService userService;

    WalletValidator walletValidator = new WalletValidator();
    private static final Logger logger = LoggerFactory.getLogger(WalletController.class);

    @GetMapping("/wallet/{id}")
    Wallet findWalletById(@PathVariable int id) {
        return walletRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException());
    }

    @GetMapping("/wallet/getAllWallets")
    public List<Wallet> getAllWallets() {
        return walletRepository.findAll();
    }

    @GetMapping("/wallet/getAllUsersFromOtherService")
    public List<User> getAllUsersFromOtherService() {
        return userService.getAllUsers();
    }

    @GetMapping("/wallet/getAllUsersFromOtherServiceById/{id}")
    public User getAllUsersFromOtherServiceById(@PathVariable String id) {
        return userService.getUserById(id);
    }

   @PostMapping("/wallet/createWallet")
   @ResponseStatus(HttpStatus.CREATED)
    public Wallet createWallet(@RequestBody Wallet wallet) {
        if(!WalletValidator.isValidWallet(wallet)){
            throw new BadWalletRequest("Invalid wallet");
        }
        return walletRepository.save(wallet);
    }

    @PutMapping("/updateWallet")
    Wallet updateWallet(@RequestBody Wallet newWallet) {
        return walletRepository.save(newWallet);
    }


}