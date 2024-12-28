package com.samuel.ebankingenterpriseapp.service.impl;

import com.samuel.ebankingenterpriseapp.entity.Account;
import com.samuel.ebankingenterpriseapp.entity.Loan;
import com.samuel.ebankingenterpriseapp.entity.MonitorLoan;
import com.samuel.ebankingenterpriseapp.enums.RepaymentStatus;
import com.samuel.ebankingenterpriseapp.model.LoanRepaymentSchedule;
import com.samuel.ebankingenterpriseapp.enums.LoanStatus;
import com.samuel.ebankingenterpriseapp.model.MonitorLoanDto;
import com.samuel.ebankingenterpriseapp.payload.request.LoanRequest;
import com.samuel.ebankingenterpriseapp.payload.request.TransactionRequest;
import com.samuel.ebankingenterpriseapp.payload.response.ApiResponse;
import com.samuel.ebankingenterpriseapp.repository.AccountRepository;
import com.samuel.ebankingenterpriseapp.repository.LoanRepository;
import com.samuel.ebankingenterpriseapp.repository.MonitorLoanRepository;
import com.samuel.ebankingenterpriseapp.service.LoanService;
import com.samuel.ebankingenterpriseapp.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;


    private final AccountRepository accountRepository;

    private final TransactionService transactionService;

    private final MonitorLoanRepository monitorLoanRepository;


    private final ModelMapper modelMapper;

    // temporal fixed rate will be calculated in the later features
    private final int INTEREST_RATE = 10;

    private int daysPerInstallment = 0;


    @Override
    public ApiResponse createLoan( LoanRequest loanRequest) {

        Optional<Account> accountOptional = accountRepository.findByAccountNumber(loanRequest.getAccountNumber());
        if (accountOptional.isEmpty() ) {
            return ApiResponse.builder()
                    .message("account not found")
                    .build();
        }


        // calculate the interest rate and get the actual installment payment.
        BigDecimal interestRate = BigDecimal.valueOf(INTEREST_RATE).divide(BigDecimal.valueOf(100));
        BigDecimal amountAfterInterest = loanRequest.getAmount()
                .add(loanRequest.getAmount().multiply(interestRate));

        BigDecimal actualInstallmentDue = amountAfterInterest
                .divide(BigDecimal.valueOf(loanRequest.getNumberOfInstallments()),  2, RoundingMode.HALF_UP);

        // actual amount to be paid after interest calculated


        Loan loan = Loan.builder()
                .amount(loanRequest.getAmount())
                .interestRate(new BigDecimal(INTEREST_RATE))
                .amountAfterInterest(amountAfterInterest)
                .loanStatus(LoanStatus.REVIEW)
                .actualInstallmentDue(actualInstallmentDue)
                .numberOfInstallments(loanRequest.getNumberOfInstallments())
                .endDate(loanRequest.getEndDate())
                .account(accountOptional.get())
                .build();

        Loan savedLoan = loanRepository.save(loan);

        return ApiResponse.builder()
                .message("Loan application created successfully")
                .object(savedLoan)
                .build();
    }

    // REVIEW, APPROVED, PENDING, PAID
    @Override
    public ApiResponse updateLoanStatusToRejectOrApproved(Long loanId, LoanStatus loanStatus) {
        Optional<Loan> loanOptional = loanRepository.findById(loanId);
        if (loanOptional.isEmpty()) {
            return ApiResponse.builder()
                    .message("Loan with ID " + loanId + " not found")
                    .build();
        }

        Loan loan = loanOptional.get();

        if (!loan.getEndDate().isAfter(LocalDate.now().plusMonths(8))) {
            return ApiResponse.builder()
                    .message("Loan with ID " + loanId + " End date still needs to be reviewed")
                    .build();
        }



        loan.setLoanStatus(loanStatus);

        if(loanStatus == LoanStatus.APPROVED){
            int numberOfInstallments = loan.getNumberOfInstallments();

            LocalDate startDate = LocalDate.now().plusMonths(2); // three months moratorium period.
            LocalDate endDate = loan.getEndDate();

            // update the proposed endDate to matching date inline with the start date.



            long totalDays = ChronoUnit.DAYS.between(startDate, endDate);
            this.daysPerInstallment = Math.toIntExact(totalDays / numberOfInstallments);

            // Add the loan repayment range (months) to the start dat
            loan.setLoanApprovalDate(LocalDate.now());
            loan.setStartDate(startDate);
            loan.setEndDate(startDate.plusDays(this.daysPerInstallment * numberOfInstallments));
            loan.setCurrentDueDate(startDate.plusDays(this.daysPerInstallment));
            loan.setCurrentDuePayment(loan.getActualInstallmentDue());
            loan.setAccruedPenaltyAmount(BigDecimal.ZERO);
            loan.setCurrentPenaltyRate(new BigDecimal(0.01));
            loan.setAmountPaidSoFar(BigDecimal.ZERO);

            // make loan deposit to that account
            transactionService.deposit(new TransactionRequest(loan.getAmount(),
                    loan.getAccount().getAccountNumber(),
                    null,
                    "This is a Loan Deposit"));

        }

        loanRepository.save(loan);

        return ApiResponse.builder()
                .message("Loan status updated to " + loanStatus)
                .object(loan)
                .build();

    }

    @Override
    public ApiResponse getLoanDetails(Long loanId) {
        Optional<Loan> loanOptional = loanRepository.findById(loanId);

        List<LoanRepaymentSchedule> loanRepaymentScheduleList = generateRepaymentSchedule(loanOptional.get(),
                loanOptional.get().getNumberOfInstallments());

        if (loanOptional.isEmpty()) {
            return ApiResponse.builder()
                    .message("Loan with ID " + loanId + " not found")
                    .build();
        }

        if (loanOptional.get().getLoanStatus() == LoanStatus.REJECTED) {
            return ApiResponse.builder()
                    .message("Loan with ID " + loanId + " Was Rejected!")
                    .build();
        }
        return ApiResponse.builder()
                .message("Loan details fetched successfully")
                .object(loanOptional.get())
                .objectList(loanRepaymentScheduleList)
                .build();
    }


    @Override
    public ApiResponse makeRepayment(Long loanId, BigDecimal repaymentAmount) {

        Optional<Loan> loanOptional = loanRepository.findById(loanId);
        if (loanOptional.isEmpty()) {
            return ApiResponse.builder()
                    .message("Loan with ID " + loanId + " not found")
                    .build();
        }

        Loan loan = loanOptional.get();

        if (loan.getLoanStatus() == LoanStatus.PAID) {
            return ApiResponse.builder()
                    .message("Loan has been paid completely")
                    .build();
        }

        if (loan.getLoanStatus() != LoanStatus.APPROVED) {
            return ApiResponse.builder()
                    .message("Loan is not in an approved state")
                    .build();
        }

        // call a method to update currentDuePayment and penalty rate  before payment is confirmed
        updateDues(loan);

        // return if the amount does not equal the current due payment
        if (loan.getCurrentDuePayment().compareTo(repaymentAmount) != 0) {
            return ApiResponse.builder()
                    .message("This is not the accurate amount to pay this is amount to be paid: "+ loan.getCurrentDuePayment().setScale(2, RoundingMode.HALF_UP))
                    .build();
        }

        // debit the loan account
        transactionService.withdraw(new TransactionRequest(repaymentAmount,
                null, loan.getAccount().getAccountNumber(),
                "Loan Debit"));

        // check if the payment is complete and return if true
        BigDecimal finalPayment = loan.getAmountPaidSoFar().add(repaymentAmount);
        BigDecimal actualAmountPlusAccruedRate = loan.getAmountAfterInterest().add(loan.getAccruedPenaltyAmount());
        if (loan.getNumberOfInstallmentPaid() + 1 == loan.getNumberOfInstallments() &&
        finalPayment.compareTo(actualAmountPlusAccruedRate) >= 0
        ) {
            loan.setLoanStatus(LoanStatus.PAID);
            loan.setAmountPaidSoFar(finalPayment);
            Loan updatedLoan = loanRepository.save(loan);

            // update the loan monitor here also
            updateMonitorLoan( updatedLoan);

            return ApiResponse.builder()
                    .message("Loan paid completely. Thank You our very valued customer")
                    .object(updatedLoan)
                    .build();
        }


        // update the accrued amount before reset
        loan.setAccruedPenaltyAmount(loan.getAccruedPenaltyAmount()
                .add(loan.getCurrentDuePayment())
                .subtract(loan.getActualInstallmentDue()));

        // Reset to default settle
        loan.setCurrentDuePayment(loan.getActualInstallmentDue());
        loan.setCurrentPenaltyRate(new BigDecimal(0.01));

        // update to next
        loan.setCurrentDueDate(loan.getCurrentDueDate().plusDays(this.daysPerInstallment));
        loan.setAmountPaidSoFar(loan.getAmountPaidSoFar().add(repaymentAmount));
        loan.setNumberOfInstallmentPaid(loan.getNumberOfInstallmentPaid() + 1);

        Loan updatedLoan = loanRepository.save(loan);

        // update the loan monitor here
        updateMonitorLoan( updatedLoan);

        return ApiResponse.builder()
                .message("Repayment processed successfully")
                .object(updatedLoan)
                .build();
    }

    // amount to be repaid service that calculates the accrued interest rate
    // and the current due payment and the current penalty rate. this will also include a send email functionality
    // on later update.

    //

    private List<LoanRepaymentSchedule> generateRepaymentSchedule(Loan loan, int numberOfInstallments) {
        BigDecimal installmentAmount = loan.getActualInstallmentDue();
        LocalDate startDate = loan.getStartDate();

        List<LoanRepaymentSchedule> loanRepaymentScheduleList = new ArrayList<>();

        for (int i = 1; i <= numberOfInstallments; i++) {
            LocalDate dueDate = startDate.plusDays(this.daysPerInstallment * i);
            LoanRepaymentSchedule repayment = LoanRepaymentSchedule.builder()
                    .dueDate(dueDate)
                    .amountDue(installmentAmount)
                    .build();
            loanRepaymentScheduleList.add(repayment);
        }

        return loanRepaymentScheduleList;
    }

    @Override
    public ApiResponse getLoanHistory(Long loanId) {
        Optional<Loan> loanOptional = loanRepository.findById(loanId);
        if (loanOptional.isEmpty()) {
            return ApiResponse.builder()
                    .message("Loan with ID " + loanId + " not found")
                    .build();
        }
        List<MonitorLoan> monitorLoans = monitorLoanRepository.findByLoanId(loanOptional.get().getId());

        List<MonitorLoanDto> monitorLoanDtoList = new ArrayList<>();

        for(MonitorLoan monitorLoan : monitorLoans){
            monitorLoanDtoList.add(modelMapper.map(monitorLoan, MonitorLoanDto.class));
        }

        return ApiResponse.builder()
                .message("Get Loan history")
                .objectList(monitorLoanDtoList)
                .build();
    }




    // Todo: after studying the bank internal interest loan rate. for now let it be a fixed rate.
    // Todo: for now lets assume that the customer meets payment
    // Todo: later on we will update the currentPayment whenever the overdue payment email alert is sent after due date.
    // Todo: this will be done later
    @Override
    public ApiResponse getOverduePayments() {
        return null;
    }

    @Override
    public ApiResponse getInitialInterestRate(int range, BigDecimal loanAmount) {
        return null;
    }


    // These methods are used within the function
    // calculate the increase in penalty as the months increases for overdue interest
    private int calculateInterestRate(Loan loan){
        int rate = 0;
        LocalDate now = LocalDate.now();
        LocalDate currentDueDate = loan.getCurrentDueDate();

        if(now.isAfter(currentDueDate)){
            int numberOfDaysPast = now.until(currentDueDate).getDays();
            rate = (numberOfDaysPast/this.daysPerInstallment) + 1;
        }
       // int monthsOverDue  =

        return rate;
    }
    // update the currentDue payment and interest rate penalty
    private void updateDues(Loan loan){
        BigDecimal actualInstallmentDue = loan.getActualInstallmentDue();

        BigDecimal initialPenaltyRate = new BigDecimal(0.01);

        BigDecimal newCurrentPenaltyRate = initialPenaltyRate.multiply(new BigDecimal(calculateInterestRate(loan)));

        BigDecimal penaltyAmount = actualInstallmentDue
                .multiply(newCurrentPenaltyRate)
                .divide(new BigDecimal(100), RoundingMode.HALF_UP);

        loan.setCurrentDuePayment(actualInstallmentDue.add(penaltyAmount));
        loan.setCurrentPenaltyRate(newCurrentPenaltyRate);

        loanRepository.save(loan);
    }

    // update the monitorLoan db
    private void updateMonitorLoan(Loan loan){

        MonitorLoan monitorLoan = new MonitorLoan();


        monitorLoan.setLoan(loan);
        monitorLoan.setActualDuePayment(loan.getActualInstallmentDue());
        monitorLoan.setActualDueDate(loan.getCurrentDueDate());
        monitorLoan.setCurrentDuePayment(loan.getCurrentDuePayment());
        if(LocalDate.now().isAfter(loan.getCurrentDueDate())){
            monitorLoan.setCurrentDueRepaymentSTatus(RepaymentStatus.DEFAULTED);
        }else{
            monitorLoan.setCurrentDueRepaymentSTatus(RepaymentStatus.NOT_DEFAULTED);
        }


        monitorLoanRepository.save(monitorLoan);
    }
}
