package com.Devex.Controller.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Devex.Entity.Dwallet;
import com.Devex.Entity.TransactionHistory;
import com.Devex.Entity.User;
import com.Devex.Sevice.DwalletService;
import com.Devex.Sevice.SessionService;
import com.Devex.Sevice.TransactionHistoryService;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin("*")
@RestController
public class DwalletRestController {
    @Autowired
    private DwalletService dwalletService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private TransactionHistoryService transactionHistoryService;

    @GetMapping("/api/walletCustomer")
    public Dwallet getBalanceWallet() {
        User u = sessionService.get("user");
        String username = u.getUsername();
        // System.out.println("ngu á: " + username);
        return dwalletService.getDwalletCustomerByUsername(username);
    }

    @GetMapping("/api/historyWalletCustomer")
    public List<TransactionHistory> getHistoryTransaction() {
        User u = sessionService.get("user");
        String username = u.getUsername();
        Dwallet dwallet = dwalletService.getDwalletCustomerByUsername(username);
        List<TransactionHistory> listHistoryTransactions = transactionHistoryService
                .getTransactionByIdWallet(dwallet.getId());
        return listHistoryTransactions;

    }

}
