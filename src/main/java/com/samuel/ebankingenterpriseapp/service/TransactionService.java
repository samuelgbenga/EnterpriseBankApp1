package com.samuel.ebankingenterpriseapp.service;

import com.samuel.ebankingenterpriseapp.payload.request.TransactionRequest;
import com.samuel.ebankingenterpriseapp.payload.response.ApiResponse;

import java.math.BigDecimal;

public interface TransactionService {

    ApiResponse deposit(TransactionRequest request);

     ApiResponse withdraw( TransactionRequest request);

     ApiResponse transfer( TransactionRequest request);

    ApiResponse getTransactionHistory(String accountNumber, int page, int size);

    ApiResponse getTransactionByDeposit(int page, int size);

    ApiResponse getTransactionByWithdrawal(int page, int size);

    ApiResponse getTransactionByTransfer(int page, int size);

    void monitorSuspiciousTransactions(BigDecimal thresholdAmount);
}
