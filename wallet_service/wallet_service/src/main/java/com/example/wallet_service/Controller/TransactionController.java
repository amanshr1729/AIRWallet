package com.example.wallet_service.Controller;

import com.example.wallet_service.Exception.BadTransactionRequest;
import com.example.wallet_service.Exception.ResourceNotFoundException;
import com.example.wallet_service.Model.Transaction;
import com.example.wallet_service.Model.User;
import com.example.wallet_service.Model.Wallet;
import com.example.wallet_service.Repository.TransactionRepository;
import com.example.wallet_service.Repository.WalletRepository;
import com.example.wallet_service.Service.UserService;
import com.example.wallet_service.Util.EmailService;
import com.example.wallet_service.Util.TransactionValidator;
import com.example.wallet_service.Util.WalletValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

@RestController
public class TransactionController {
    @Autowired
    private WalletRepository walletRepository;
   @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private UserService userService;

    WalletValidator walletValidator = new WalletValidator();
    TransactionValidator transactionValidator = new TransactionValidator();
    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @PostMapping("/transaction/sendMoney")
    public Transaction sendMoney(@RequestBody Transaction transaction) throws Exception {
        if(!transactionValidator.isValidTransaction(transaction)){
            throw new BadTransactionRequest("Invalid transaction");
        }
        transaction.setDate(new Date(Calendar.getInstance().getTime().getTime()));
        User sender = userService.getUserById(String.valueOf(transaction.getSenderId()));
        User receiver = userService.getUserById(String.valueOf(transaction.getReceiverId()));

        if(sender==null || receiver==null){
            logger.info("No wallet for sender or receiver");
            throw new BadTransactionRequest("Invalid sender or receiver");
        }
        Wallet senderWallet = walletRepository.findWalletByUserId(sender.getId());
        Wallet receiverWallet = walletRepository.findWalletByUserId(receiver.getId());
        if (senderWallet.getAccount_balance() < transaction.getAmount() ) {
            throw new Exception("Not Sufficient Balance");
        }

        senderWallet.setAccount_balance(senderWallet.getAccount_balance() - transaction.getAmount());
        receiverWallet.setAccount_balance(receiverWallet.getAccount_balance() + transaction.getAmount());
        transaction.setStatus("SUCCESS");
        logger.info(String.format("$$ -> Producing Transaction --> %s", transaction));
        walletRepository.save(senderWallet);
        walletRepository.save(receiverWallet);
        return transactionRepository.save(transaction);
    }

    @GetMapping("/getBalance/{id}")
    public int getBalanceById(@PathVariable int id){
        Wallet wallet =  walletRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException());
        return wallet.getAccount_balance();
    }
    @PostMapping("/addBalance/{id}")
    public Wallet addBalance(@PathVariable int id, @RequestBody int amount) throws Exception {
        if(amount < 0){
            throw new BadTransactionRequest("Invalid amount");
        }
        Wallet wallet = walletRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException());
        wallet.setAccount_balance(wallet.getAccount_balance() + amount);
        return walletRepository.save(wallet);
    }

    @GetMapping("/txnHistory/{id}")
    String getTransactionHistory(@PathVariable int id) {

        logger.info(String.format("$$ -> Gathering Your Transactions --> %s", id));
        sendTxnHistory(String.valueOf(id));
        return "You will get the file on your email shortly soon";
    }

    private void sendTxnHistory(String id) {
        int id1 = Integer.parseInt(id);
        ArrayList<Transaction> list =
                (ArrayList<Transaction>) transactionRepository.findBysidAndrid(id1);

        User user1 = userService.getUserById(String.valueOf(id1));

        String filename ="TransactionHistory_" + String.valueOf(Calendar.getInstance().getTime().getTime());
        try {
            FileWriter fw = new FileWriter(filename);

            for(int i=0;i<list.size();i++) {
                fw.append(list.get(i).getStatus());
                fw.append(',');
                int amt = list.get(i).getAmount();
                Integer obj = amt;
                fw.append(obj.toString());
                fw.append(',');
                fw.append(list.get(i).getDate().toString());
                fw.append(',');
                int id2 = list.get(i).getTxid();
                Integer obj2 = id2;
                fw.append(obj2.toString());
                fw.append(',');
                int rid = list.get(i).getReceiverId();
                obj = rid;
                fw.append(obj.toString());
                fw.append(',');
                int sid = list.get(i).getSenderId();
                obj = sid;
                fw.append(obj.toString());
                fw.append('\n');
            }
            fw.flush();
            fw.close();
            logger.info("CSV File is created successfully.");

            EmailService.sendEmailWithAttachments("","",user1.getName(),"",user1.getEmail(),"","",filename);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
