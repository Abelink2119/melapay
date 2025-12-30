package com.melapay.controller;

import com.melapay.dto.DepositRequest;
import com.melapay.dto.TransferRequest;
import com.melapay.dto.WithdrawRequest;
import com.melapay.entity.User;
import com.melapay.entity.Wallet;
import com.melapay.repository.UserRepository;
import com.melapay.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallet")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class WalletController {

    private final WalletService walletService;
    private final UserRepository userRepository;

    @GetMapping
    public Wallet getWallet(@AuthenticationPrincipal User user) {
        return walletService.getWallet(user);
    }

    @PostMapping("/deposit")
    public Wallet deposit(@AuthenticationPrincipal User user, @RequestBody DepositRequest req) {
        return walletService.deposit(user, req.getAmount());
    }

    @PostMapping("/withdraw")
    public Wallet withdraw(@AuthenticationPrincipal User user, @RequestBody WithdrawRequest req) {
        return walletService.withdraw(user, req.getAmount());
    }

    @PostMapping("/transfer")
    public String transfer(@AuthenticationPrincipal User sender, @RequestBody TransferRequest req) {
        User receiver = userRepository.findByEmail(req.getRecipientEmail())
                .orElseThrow(() -> new RuntimeException("Recipient not found"));

        walletService.transfer(sender, receiver, req.getAmount());
        return "Transfer successful";
    }
}
