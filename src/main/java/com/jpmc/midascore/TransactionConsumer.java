package com.jpmc.midascore;

import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TransactionConsumer {

    @Autowired
    private TransactionService transactionService;

    @KafkaListener(topics = "${general.kafka-topic}")
    public void listen(Transaction transaction) {
        System.out.println("Received transaction: " + transaction);

        // Process the transaction through the service
        transactionService.processTransaction(transaction);
    }
}