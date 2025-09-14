package com.jpmc.midascore.service;

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.TransactionRepository;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TransactionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private IncentiveService incentiveService;

    public void processTransaction(Transaction transaction) {
        // Find sender and recipient by ID
        UserRecord sender = userRepository.findById(transaction.getSenderId());
        UserRecord recipient = userRepository.findById(transaction.getRecipientId());

        // Validate transaction
        if (isValidTransaction(sender, recipient, transaction.getAmount())) {
            // Get incentive from API
            float incentive = incentiveService.getIncentive(transaction);

            // Process the transaction
            sender.setBalance(sender.getBalance() - transaction.getAmount());
            recipient.setBalance(recipient.getBalance() + transaction.getAmount() + incentive);

            // Save updated balances
            userRepository.save(sender);
            userRepository.save(recipient);

            // Save transaction record with incentive
            TransactionRecord record = new TransactionRecord(sender, recipient, transaction.getAmount(), incentive);
            transactionRepository.save(record);

            System.out.println("Transaction processed successfully: " + record);
        } else {
            System.out.println("Transaction rejected: Invalid transaction");
        }
    }

    private boolean isValidTransaction(UserRecord sender, UserRecord recipient, float amount) {
        // Check if sender exists
        if (sender == null) {
            return false;
        }

        // Check if recipient exists
        if (recipient == null) {
            return false;
        }

        // Check if sender has enough balance
        if (sender.getBalance() < amount) {
            return false;
        }

        return true;
    }
}