package com.samuel.ebankingenterpriseapp.service.impl;

import com.samuel.ebankingenterpriseapp.entity.Bank;
import com.samuel.ebankingenterpriseapp.entity.Branch;
import com.samuel.ebankingenterpriseapp.payload.request.BankRequest;
import com.samuel.ebankingenterpriseapp.payload.response.ApiResponse;
import com.samuel.ebankingenterpriseapp.repository.BankRepository;
import com.samuel.ebankingenterpriseapp.repository.BranchRepository;
import com.samuel.ebankingenterpriseapp.service.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class BankServiceImpl implements BankService {

    private final BankRepository bankRepository;

    private final BranchRepository branchRepository;


    @Override
    public ApiResponse registerBank(BankRequest bankRequest) {
        try {
            Bank bank = Bank.builder()
                    .name(bankRequest.getName())
                    .address(bankRequest.getAddress())
                    .contactDetails(bankRequest.getContactDetails())
                    .active(true)  // Banks are active by default
                    .build();
            bank = bankRepository.save(bank);
            return new ApiResponse("Bank registered successfully", bank, null);
        } catch (Exception e) {
            return new ApiResponse("Error registering bank: " + e.getMessage(), null, null);
        }
    }

    @Override
    public ApiResponse updateBank(Long bankId, BankRequest bankRequest) {
        try {
            Optional<Bank> existingBank = bankRepository.findById(bankId);
            if (existingBank.isPresent()) {
                Bank bank = existingBank.get();
                bank.setName(bankRequest.getName());
                bank.setAddress(bankRequest.getAddress());
                bank.setContactDetails(bankRequest.getContactDetails());
                bank = bankRepository.save(bank);
                return new ApiResponse("Bank updated successfully", bank, null);
            } else {
                return new ApiResponse("Bank not found", null, null);
            }
        } catch (Exception e) {
            return new ApiResponse("Error updating bank: " + e.getMessage(), null, null);
        }
    }

    @Override
    public ApiResponse fetchBankDetails(Long bankId) {
        try {
            Optional<Bank> bank = bankRepository.findById(bankId);
            if (bank.isPresent()) {
                return new ApiResponse("Bank details fetched successfully", bank.get(), null);
            } else {
                return new ApiResponse("Bank not found", null, null);
            }
        } catch (Exception e) {
            return new ApiResponse("Error fetching bank details: " + e.getMessage(), null, null);
        }
    }

    @Override
    public ApiResponse fetchAllBanks() {
        try {
            List<Bank> banks = bankRepository.findAll();

            return new ApiResponse("Banks fetched successfully", null, Collections.singletonList(banks));
        } catch (Exception e) {
            return new ApiResponse("Error fetching banks: " + e.getMessage(), null, null);
        }
    }

    @Override
    public ApiResponse fetchBankBranches(Long bankId) {
        try {
            List<Branch> branches = branchRepository.findByBankId(bankId);
            if (!branches.isEmpty()) {
                return new ApiResponse("Branches fetched successfully", null, Collections.singletonList(branches));
            } else {
                return new ApiResponse("No branches found for this bank", null, null);
            }
        } catch (Exception e) {
            return new ApiResponse("Error fetching branches: " + e.getMessage(), null, null);
        }
    }

    @Override
    public void softDeleteBank(Long bankId) {

        try {
            Optional<Bank> existingBank = bankRepository.findById(bankId);
            if (existingBank.isPresent()) {
                Bank bank = existingBank.get();
                bank.setActive(false); // Set the bank as inactive (soft delete)
                bankRepository.save(bank);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
