package com.melapay.service;

import com.melapay.entity.Transaction;
import com.melapay.entity.Wallet;
import com.melapay.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public Transaction createTransaction(Wallet wallet, double amount, String type) {
        Transaction t = new Transaction();
        t.setWallet(wallet);
        t.setAmount(amount);
        t.setType(type);
        t.setCreatedAt(LocalDateTime.now());
        return transactionRepository.save(t);
    }

    public List<Transaction> getWalletTransactions(Wallet wallet) {
        return transactionRepository.findByWallet(wallet);
    }
}
