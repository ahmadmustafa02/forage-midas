package com.jpmc.midascore.service;

import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Balance;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BalanceService {

    @Autowired
    private UserRepository userRepository;

    public Balance getBalance(Long userId) {
        UserRecord user = userRepository.findById(userId.longValue());

        if (user == null) {
            // If user doesn't exist, return balance of 0
            return new Balance(0.0f);
        }

        return new Balance(user.getBalance());
    }
}