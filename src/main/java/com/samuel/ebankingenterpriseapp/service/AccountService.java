package com.samuel.ebankingenterpriseapp.service;

import com.samuel.ebankingenterpriseapp.payload.request.AccountRequest;
import com.samuel.ebankingenterpriseapp.payload.response.ApiResponse;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    ApiResponse createAccount(Long branchId, List<Long> customerIds);

    ApiResponse getAccountById(Long accountId);

    ApiResponse updateAccountBalance(Long accountId, BigDecimal newBalance);

    ApiResponse deleteAccount(Long accountId);
}
