package com.melapay.repository;

import com.melapay.entity.Transaction;
import com.melapay.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByWallet(Wallet wallet);
}
