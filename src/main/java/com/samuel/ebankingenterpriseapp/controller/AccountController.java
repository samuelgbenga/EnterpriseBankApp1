package com.samuel.ebankingenterpriseapp.controller;


import com.samuel.ebankingenterpriseapp.payload.request.AccountRequest;
import com.samuel.ebankingenterpriseapp.payload.response.ApiResponse;
import com.samuel.ebankingenterpriseapp.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/create/{branchId}")
    public ResponseEntity<ApiResponse> createAccount(
            @RequestParam List<Long> customerIds,
            @PathVariable Long branchId) {
        ApiResponse response = accountService.createAccount(branchId, customerIds);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<ApiResponse> getAccountById(@PathVariable Long accountId) {
        ApiResponse response = accountService.getAccountById(accountId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update-balance/{accountId}")
    public ResponseEntity<ApiResponse> updateAccountBalance(
            @PathVariable Long accountId,
            @RequestParam BigDecimal newBalance) {
        ApiResponse response = accountService.updateAccountBalance(accountId, newBalance);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{accountId}")
    public ResponseEntity<ApiResponse> deleteAccount(@PathVariable Long accountId) {
        ApiResponse response = accountService.deleteAccount(accountId);
        return ResponseEntity.ok(response);
    }

}
