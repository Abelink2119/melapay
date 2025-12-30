package com.melapay.service;

import com.melapay.entity.Transaction;
import com.melapay.entity.User;
import com.melapay.entity.Wallet;
import com.melapay.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;
    private final TransactionService transactionService;

    public Wallet getWallet(User user) {
        return walletRepository.findByUser(user)
                .orElseGet(() -> createWallet(user));
    }

    public Wallet createWallet(User user) {
        Wallet wallet = new Wallet();
        wallet.setUser(user);
        wallet.setBalance(0);
        return walletRepository.save(wallet);
    }

    public Wallet deposit(User user, double amount) {
        Wallet wallet = getWallet(user);
        wallet.setBalance(wallet.getBalance() + amount);
        Wallet saved = walletRepository.save(wallet);
        transactionService.createTransaction(saved, amount, "Deposit");
        return saved;
    }

    public Wallet withdraw(User user, double amount) {
        Wallet wallet = getWallet(user);
        if (wallet.getBalance() < amount) throw new RuntimeException("Insufficient funds");
        wallet.setBalance(wallet.getBalance() - amount);
        Wallet saved = walletRepository.save(wallet);
        transactionService.createTransaction(saved, amount, "Withdrawal");
        return saved;
    }

    public void transfer(User sender, User receiver, double amount) {
        Wallet senderWallet = getWallet(sender);
        Wallet receiverWallet = getWallet(receiver);

        if (senderWallet.getBalance() < amount) throw new RuntimeException("Insufficient funds");

        senderWallet.setBalance(senderWallet.getBalance() - amount);
        receiverWallet.setBalance(receiverWallet.getBalance() + amount);

        walletRepository.save(senderWallet);
        walletRepository.save(receiverWallet);

        transactionService.createTransaction(senderWallet, amount, "Transfer Out");
        transactionService.createTransaction(receiverWallet, amount, "Transfer In");
    }
}
