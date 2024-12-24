package com.samuel.ebankingenterpriseapp.controller;


import com.samuel.ebankingenterpriseapp.payload.request.TransactionRequest;
import com.samuel.ebankingenterpriseapp.payload.response.ApiResponse;
import com.samuel.ebankingenterpriseapp.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    /**
     * Deposit money into an account.
     */
    @PostMapping("/deposit")
    public ResponseEntity<ApiResponse> deposit(
            @RequestBody TransactionRequest request
    ) {
        ApiResponse response = transactionService.deposit(request);
        return ResponseEntity.ok(response);
    }


    /**
     * Transfer money between accounts.
     */
    @PostMapping("/transfer")
    public ResponseEntity<ApiResponse> transfer(
            @RequestBody TransactionRequest request
    ) {
        ApiResponse response = transactionService.transfer(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Withdraw money from an account.
     */
    @PostMapping("/withdraw")
    public ResponseEntity<ApiResponse> withdraw(
            @RequestBody TransactionRequest request
    ) {
        ApiResponse response = transactionService.withdraw( request);
        return ResponseEntity.ok(response);
    }


    /**
     * Get transaction history for a specific account with pagination.
     */
    @GetMapping("/history")
    public ResponseEntity<ApiResponse> getTransactionHistory(
            @RequestParam String accountNumber,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        ApiResponse response = transactionService.getTransactionHistory(accountNumber, page, size);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/history/deposit")
    public ResponseEntity<ApiResponse> getTransactionHistoryDeposit(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        ApiResponse response = transactionService.getTransactionByDeposit(page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/history/transfer")
    public ResponseEntity<ApiResponse> getTransactionHistoryTransfer(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        ApiResponse response = transactionService.getTransactionByTransfer(page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/history/withdrawal")
    public ResponseEntity<ApiResponse> getTransactionHistoryWithdrawal(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        ApiResponse response = transactionService.getTransactionByWithdrawal(page, size);
        return ResponseEntity.ok(response);
    }
    // Todo: the monitoring will be done internally
//    /**
//     * Monitor suspicious transactions above a certain threshold.
//     */
//    @GetMapping("/monitor")
//    public ResponseEntity<String> monitorSuspiciousTransactions(@RequestParam BigDecimal thresholdAmount) {
//        transactionService.monitorSuspiciousTransactions(thresholdAmount);
//        return ResponseEntity.ok("Suspicious transactions monitored successfully.");
//    }

}
