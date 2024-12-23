package com.samuel.ebankingenterpriseapp.service.impl;

import com.samuel.ebankingenterpriseapp.entity.Bank;
import com.samuel.ebankingenterpriseapp.entity.Branch;
import com.samuel.ebankingenterpriseapp.entity.BranchManager;
import com.samuel.ebankingenterpriseapp.model.BankDto;
import com.samuel.ebankingenterpriseapp.model.BranchDto;
import com.samuel.ebankingenterpriseapp.payload.request.BankRequest;
import com.samuel.ebankingenterpriseapp.payload.response.ApiResponse;
import com.samuel.ebankingenterpriseapp.repository.BankRepository;
import com.samuel.ebankingenterpriseapp.repository.BranchManagerRepository;
import com.samuel.ebankingenterpriseapp.repository.BranchRepository;
import com.samuel.ebankingenterpriseapp.service.BankService;
import com.samuel.ebankingenterpriseapp.service.BranchManagerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class BankServiceImpl implements BankService {

    private final BankRepository bankRepository;

    private final BranchRepository branchRepository;


    private final ModelMapper modelMapper;


    @Override
    public ApiResponse registerBank(BankRequest bankRequest) {
        try {
            Bank bank = Bank.builder()
                    .name(bankRequest.getName())
                    .address(bankRequest.getAddress())
                    .contactDetails(bankRequest.getContactDetails())
                    .build();
            bank = bankRepository.save(bank);

            BankDto bankDto = modelMapper.map(bank, BankDto.class);
            return new ApiResponse("Bank registered successfully", bankDto, null);
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

                BankDto bankDto = modelMapper.map(bank, BankDto.class);

                return new ApiResponse("Bank updated successfully", bankDto, null);
            } else {
                return new ApiResponse("Bank not found", null, null);
            }
        } catch (Exception e) {
            return new ApiResponse("Error updating bank: " + e.getMessage(), null, null);
        }
    }

    // Todo: Secure this endpoint
    @Override
    public ApiResponse fetchBankDetails(Long bankId) {
        try {
            Optional<Bank> bank = bankRepository.findById(bankId);
            if (bank.isPresent()) {
                BankDto bankDto = modelMapper.map(bank.get(), BankDto.class);
                return new ApiResponse("Bank details fetched successfully", bankDto, null);
            } else {
                return new ApiResponse("Bank not found", null, null);
            }
        } catch (Exception e) {
            return new ApiResponse("Error fetching bank details: " + e.getMessage(), null, null);
        }
    }


    // Todo: This will be corrected to get it directly from the list
    @Override
    public ApiResponse fetchBankBranches() {
        try {

            List<Bank> banks = bankRepository.findAll();
            List<Branch> branches = banks.getFirst().getBranches();
            if (!branches.isEmpty()) {
                return new ApiResponse("Branches fetched successfully", null, branches);
            } else {
                return new ApiResponse("No branches found for this bank", null, null);
            }
        } catch (Exception e) {
            return new ApiResponse("Error fetching branches: " + e.getMessage(), null, null);
        }
    }

    // Todo: Same with same function but this will return the manager attached to the branch
    @Override
    public ApiResponse fetchBankManagers() {
        try {
            List<Bank> banks = bankRepository.findAll();
            List<Branch> branches = banks.getFirst().getBranches();
            List<BranchManager> branchManagers = new ArrayList<>();
            for(Branch branch : branches){
                branchManagers.add(branch.getBranchManager());
            }
            if (!branchManagers.isEmpty()) {
                return new ApiResponse("Branch Managers fetched successfully", null, branchManagers);
            } else {
                return new ApiResponse("No Branch Manager found for this bank", null, null);
            }
        } catch (Exception e) {
            return new ApiResponse("Error fetching Branch Manager: " + e.getMessage(), null, null);
        }

    }



}
