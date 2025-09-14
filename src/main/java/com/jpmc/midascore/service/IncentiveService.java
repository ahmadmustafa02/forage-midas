package com.jpmc.midascore.service;

import com.jpmc.midascore.foundation.Incentive;
import com.jpmc.midascore.foundation.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class IncentiveService {

    @Autowired
    private RestTemplate restTemplate;

    private final String INCENTIVE_API_URL = "http://localhost:8080/incentive";

    public float getIncentive(Transaction transaction) {
        try {
            // Send POST request to incentive API
            Incentive incentive = restTemplate.postForObject(INCENTIVE_API_URL, transaction, Incentive.class);

            if (incentive != null) {
                System.out.println("Incentive received: " + incentive.getAmount());
                return incentive.getAmount();
            } else {
                System.out.println("No incentive received");
                return 0.0f;
            }
        } catch (Exception e) {
            System.err.println("Error calling incentive API: " + e.getMessage());
            return 0.0f;
        }
    }
}