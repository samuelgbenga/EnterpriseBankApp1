package com.samuel.ebankingenterpriseapp.service.impl;


import com.samuel.ebankingenterpriseapp.entity.Account;
import com.samuel.ebankingenterpriseapp.entity.Branch;
import com.samuel.ebankingenterpriseapp.entity.Customer;
import com.samuel.ebankingenterpriseapp.payload.request.AccountRequest;
import com.samuel.ebankingenterpriseapp.payload.response.ApiResponse;
import com.samuel.ebankingenterpriseapp.repository.AccountRepository;
import com.samuel.ebankingenterpriseapp.repository.BranchRepository;
import com.samuel.ebankingenterpriseapp.repository.CustomerRepository;
import com.samuel.ebankingenterpriseapp.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final BranchRepository branchRepository;
    private final CustomerRepository customerRepository;

    @Override
    public ApiResponse createAccount(AccountRequest accountRequest, Long branchId, Long customerId) {

        try {
            Optional<Branch> branchOptional = branchRepository.findById(branchId);
            Optional<Customer> customerOptional = customerRepository.findById(customerId);
            if (branchOptional.isPresent() && customerOptional.isPresent()) {
                Branch branch = branchOptional.get();

                Account account = Account.builder()
                        .accountNumber(accountRequest.getAccountNumber())
                        .balance(accountRequest.getBalance())
                        .branch(branch)
                        .active(true)
                        .build();
                account.getCustomers().add(customerOptional.get());
                customerOptional.get().getAccounts().add(account);

                Account savedAccount = accountRepository.save(account);
                return ApiResponse.builder()
                        .message("Account created successfully")
                        .object(savedAccount)
                        .build();
            } else {
                return ApiResponse.builder()
                        .message("Branch not found")
                        .build();
            }
        } catch (Exception e) {
            return ApiResponse.builder()
                    .message("Error creating account: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ApiResponse getAccountById(Long accountId) {
        try {
            Optional<Account> accountOptional = accountRepository.findById(accountId);
            if (accountOptional.isPresent()) {
                return ApiResponse.builder()
                        .message("Account details fetched successfully")
                        .object(accountOptional.get())
                        .build();
            } else {
                return ApiResponse.builder()
                        .message("Account not found")
                        .build();
            }
        } catch (Exception e) {
            return ApiResponse.builder()
                    .message("Error fetching account: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ApiResponse updateAccountBalance(Long accountId, BigDecimal newBalance) {
        try {
            Optional<Account> accountOptional = accountRepository.findById(accountId);
            if (accountOptional.isPresent()) {
                Account account = accountOptional.get();
                account.setBalance(newBalance);
                Account updatedAccount = accountRepository.save(account);

                return ApiResponse.builder()
                        .message("Account balance updated successfully")
                        .object(updatedAccount)
                        .build();
            } else {
                return ApiResponse.builder()
                        .message("Account not found")
                        .build();
            }
        } catch (Exception e) {
            return ApiResponse.builder()
                    .message("Error updating account balance: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ApiResponse deleteAccount(Long accountId) {
        try {
            Optional<Account> accountOptional = accountRepository.findById(accountId);
            if (accountOptional.isPresent()) {
                if(!accountOptional.get().isActive()){
                    return ApiResponse.builder()
                            .message("Account already deleted!")
                            .build();
                }
                accountOptional.get().setActive(false);
                return ApiResponse.builder()
                        .message("Account deleted successfully")
                        .build();
            } else {
                return ApiResponse.builder()
                        .message("Account not found")
                        .build();
            }
        } catch (Exception e) {
            return ApiResponse.builder()
                    .message("Error deleting account: " + e.getMessage())
                    .build();
        }
    }
}
