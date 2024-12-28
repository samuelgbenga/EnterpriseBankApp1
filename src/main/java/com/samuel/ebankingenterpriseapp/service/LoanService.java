package com.samuel.ebankingenterpriseapp.service;

import com.samuel.ebankingenterpriseapp.enums.LoanStatus;
import com.samuel.ebankingenterpriseapp.payload.request.LoanRequest;
import com.samuel.ebankingenterpriseapp.payload.response.ApiResponse;

import java.math.BigDecimal;

public interface LoanService {

    ApiResponse createLoan( LoanRequest loanRequest);

    ApiResponse updateLoanStatusToRejectOrApproved(Long loanId, LoanStatus loanStatus);

    ApiResponse getLoanDetails(Long loanId);

    ApiResponse makeRepayment(Long loanId, BigDecimal repaymentAmount);

    //ApiResponse generateRepaymentSchedule(Long loanId, int numberOfInstallments);

    ApiResponse getLoanHistory(Long loanId);

    ApiResponse getOverduePayments();

    ApiResponse getInitialInterestRate( int range, BigDecimal loanAmount);
}
