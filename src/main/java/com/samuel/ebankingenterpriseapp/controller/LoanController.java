package com.samuel.ebankingenterpriseapp.controller;


import com.samuel.ebankingenterpriseapp.enums.LoanStatus;
import com.samuel.ebankingenterpriseapp.payload.request.LoanRequest;
import com.samuel.ebankingenterpriseapp.payload.response.ApiResponse;
import com.samuel.ebankingenterpriseapp.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    /**
     * Endpoint to create a loan
     * @param loanRequest Loan request payload
     * @return ResponseEntity containing ApiResponse
     */
    @PostMapping
    public ResponseEntity<ApiResponse> createLoan(@RequestBody LoanRequest loanRequest) {
        ApiResponse response = loanService.createLoan(loanRequest);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint to update loan status to APPROVED or REJECTED
     * @param loanId Loan ID
     * @param status Status to update (APPROVED or REJECTED)
     * @return ResponseEntity containing ApiResponse
     */
    @PatchMapping("/{loanId}/status")
    public ResponseEntity<ApiResponse> updateLoanStatus(@PathVariable Long loanId,
                                                        @RequestParam LoanStatus status) {
        ApiResponse response = loanService.updateLoanStatusToRejectOrApproved(loanId, status);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint to get loan details
     * @param loanId Loan ID
     * @return ResponseEntity containing ApiResponse
     */
    @GetMapping("/{loanId}")
    public ResponseEntity<ApiResponse> getLoanDetails(@PathVariable Long loanId) {
        ApiResponse response = loanService.getLoanDetails(loanId);
        return ResponseEntity.ok(response);
    }


    /**
     * Endpoint to make a loan repayment
     * @param loanId Loan ID
     * @param repaymentAmount Repayment amount
     * @return ResponseEntity containing ApiResponse
     */
    @PostMapping("/{loanId}/repayment")
    public ResponseEntity<ApiResponse> makeRepayment(@PathVariable Long loanId,
                                                     @RequestParam BigDecimal repaymentAmount) {
        ApiResponse response = loanService.makeRepayment(loanId, repaymentAmount);
        return ResponseEntity.ok(response);
    }
}
