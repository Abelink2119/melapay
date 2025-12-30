package com.melapay.repository;

import com.melapay.entity.Wallet;
import com.melapay.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByUser(User user);
}
