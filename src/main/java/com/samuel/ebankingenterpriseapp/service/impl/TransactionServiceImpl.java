package com.samuel.ebankingenterpriseapp.service.impl;

import com.samuel.ebankingenterpriseapp.entity.Account;
import com.samuel.ebankingenterpriseapp.entity.Transaction;
import com.samuel.ebankingenterpriseapp.enums.TransactionType;
import com.samuel.ebankingenterpriseapp.payload.request.TransactionRequest;
import com.samuel.ebankingenterpriseapp.payload.response.ApiResponse;
import com.samuel.ebankingenterpriseapp.repository.AccountRepository;
import com.samuel.ebankingenterpriseapp.repository.TransactionRepository;
import com.samuel.ebankingenterpriseapp.service.TransactionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;


    /**
     * Process a deposit transaction.
     */
    @Override
    @Transactional
    public ApiResponse deposit( TransactionRequest request) {



        Optional<Account> accountOptional = accountRepository.findByAccountNumber(request.getRecipientAccountNumber());
        if (accountOptional.isEmpty()) {
            return ApiResponse.builder()
                    .message("Account not found")
                    .build();
        }

        Account account = accountOptional.get();
        account.setBalance(account.getBalance().add(request.getAmount()));

        Transaction transaction = Transaction.builder()
                .amount(request.getAmount())
                .transferType(TransactionType.DEPOSIT)
                .transactionDate(LocalDate.now())
                .account(account)
                .build();

        transactionRepository.save(transaction);
        accountRepository.save(account);

        return ApiResponse.builder()
                .message("Deposit successful")
                .object(transaction)
                .build();
    }


    /**
     * Process a withdrawal transaction.
     */
    @Override
    @Transactional
    public ApiResponse withdraw( TransactionRequest request) {
        Optional<Account> accountOptional = accountRepository.findByAccountNumber(request.getSourceAccountNumber());
        if (accountOptional.isEmpty()) {
            return ApiResponse.builder()
                    .message("Account not found")
                    .build();
        }

        Account account = accountOptional.get();
        if (account.getBalance().compareTo(request.getAmount()) < 0) {
            return ApiResponse.builder()
                    .message("Insufficient balance")
                    .build();
        }

        account.setBalance(account.getBalance().subtract(request.getAmount()));

        Transaction transaction = Transaction.builder()
                .amount(request.getAmount())
                .transferType(TransactionType.WITHDRAWAL)
                .transactionDate(LocalDate.now())
                .account(account)
                .build();

        transactionRepository.save(transaction);
        accountRepository.save(account);

        return ApiResponse.builder()
                .message("Withdrawal successful")
                .object(transaction)
                .build();
    }


    /**
     * Process a transfer transaction.
     */
    @Override
    @Transactional
    public ApiResponse transfer( TransactionRequest request) {
        Optional<Account> sourceAccountOptional = accountRepository.findByAccountNumber(request.getSourceAccountNumber());
        Optional<Account> destinationAccountOptional = accountRepository.findByAccountNumber(request.getRecipientAccountNumber());

        if (sourceAccountOptional.isEmpty() || destinationAccountOptional.isEmpty()) {
            return ApiResponse.builder()
                    .message("Source or destination account not found")
                    .build();
        }

        if (sourceAccountOptional.get() == destinationAccountOptional.get()) {
            return ApiResponse.builder()
                    .message("Source or destination account not found")
                    .build();
        }

        Account sourceAccount = sourceAccountOptional.get();
        Account destinationAccount = destinationAccountOptional.get();

        if (sourceAccount.getBalance().compareTo(request.getAmount()) < 0) {
            return ApiResponse.builder()
                    .message("Insufficient balance in source account")
                    .build();
        }

        sourceAccount.setBalance(sourceAccount.getBalance().subtract(request.getAmount()));
        destinationAccount.setBalance(destinationAccount.getBalance().add(request.getAmount()));

        Transaction transaction = Transaction.builder()
                .amount(request.getAmount())
                .transferType(TransactionType.TRANSFER)
                .transactionDate(LocalDate.now())
                .account(sourceAccount)
                .build();

        transactionRepository.save(transaction);
        accountRepository.save(sourceAccount);
        accountRepository.save(destinationAccount);

        return ApiResponse.builder()
                .message("Transfer successful")
                .object(transaction)
                .build();
    }


    /**
     * Retrieve transaction history with pagination and filtering.
     */
    @Override
    public ApiResponse getTransactionHistory( String accountNumber, int page, int size) {
        Optional<Account> accountOptional = accountRepository.findByAccountNumber(accountNumber);
        if (accountOptional.isEmpty()) {
            return ApiResponse.builder()
                    .message("Account not found")
                    .build();
        }

        Page<Transaction> transactions = transactionRepository.findByAccountId(
                accountOptional.get().getId(),
                PageRequest.of(page, size)
        );

        return ApiResponse.builder()
                .message("Transaction history retrieved successfully")
                .objectList(transactions.getContent())
                .build();
    }


    /**
     * Monitor suspicious transactions (e.g., unusually large transfers).
     * Todo: check if the account transferred to is new
     * Todo: get the highest amount of transaction that person as done and alert
     * Todo: monitor the average amount of transaction per day
     * Todo: the average amount of transaction to one person per day
     * This would be more internal does not need an endpoint
     */
    @Override
    public void monitorSuspiciousTransactions(BigDecimal thresholdAmount) {
        transactionRepository.findAll().stream()
                .filter(transaction -> transaction.getAmount().compareTo(thresholdAmount) > 0)
                .forEach(transaction -> {
                    // Add logic to flag or alert about suspicious transactions.
                    System.out.println("Suspicious transaction detected: " + transaction);
                });
    }
}
