package com.samuel.ebankingenterpriseapp.service;

import com.samuel.ebankingenterpriseapp.payload.request.AccountRequest;
import com.samuel.ebankingenterpriseapp.payload.response.ApiResponse;

import java.math.BigDecimal;

public interface AccountService {

    ApiResponse createAccount(AccountRequest accountRequest, Long branchId, Long customerId);

    ApiResponse getAccountById(Long accountId);

    ApiResponse updateAccountBalance(Long accountId, BigDecimal newBalance);

    ApiResponse deleteAccount(Long accountId);
}
