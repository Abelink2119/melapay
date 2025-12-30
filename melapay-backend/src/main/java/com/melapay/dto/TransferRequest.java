package com.melapay.dto;

import lombok.Data;

@Data
public class TransferRequest {
    private double amount;
    private String recipientEmail;
}
