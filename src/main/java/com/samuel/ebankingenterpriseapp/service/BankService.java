package com.samuel.ebankingenterpriseapp.service;

import com.samuel.ebankingenterpriseapp.payload.request.BankRequest;
import com.samuel.ebankingenterpriseapp.payload.response.ApiResponse;

public interface BankService {
    ApiResponse registerBank(BankRequest bankRequest);
    ApiResponse updateBank(Long bankId, BankRequest bank);
    ApiResponse fetchBankDetails(Long bankId);
    ApiResponse fetchBankBranches(Long bankId);
  //  ApiResponse fetchBankManagers(Long managerId);
    ApiResponse softDeleteBank(Long bankId);
}
