package com.samuel.ebankingenterpriseapp.service.impl;

import com.samuel.ebankingenterpriseapp.entity.Bank;
import com.samuel.ebankingenterpriseapp.entity.Branch;
import com.samuel.ebankingenterpriseapp.entity.BranchManager;
import com.samuel.ebankingenterpriseapp.payload.request.BranchRequest;
import com.samuel.ebankingenterpriseapp.payload.response.ApiResponse;
import com.samuel.ebankingenterpriseapp.repository.BankRepository;
import com.samuel.ebankingenterpriseapp.repository.BranchManagerRepository;
import com.samuel.ebankingenterpriseapp.repository.BranchRepository;
import com.samuel.ebankingenterpriseapp.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


// consider this?
// every creation of manager should have a branch initiated.
// so i will include creation of a branch in the constructor of a manager
// so branch would not be created without a manager
// the manager of a branch can be updated
// which will automatically displace the other manager
// so it means a manager can be created without a branch and then later attached to a branch
// then another manager can be deleted and removed from being the manager of that branch hence the branch
// under that manager would be null
@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;

    private final BankRepository bankRepository;

    private final BranchManagerRepository branchManagerRepository;


    @Override
    public ApiResponse addBranchToBank(Long bankId, Long managerId, BranchRequest branchRequest) {



        try {
            Optional<BranchManager> branchManager = branchManagerRepository.findById(managerId);

            Optional<Bank> bank = bankRepository.findById(bankId);
            if (bank.isPresent() && branchManager.isPresent()) {
                Branch branch = Branch.builder()
                        .name(branchRequest.getName())
                        .branchCode(branchRequest.getBranchCode())
                        .branchManager(branchManager.get())
                        .location(branchRequest.getLocation())
                        .bank(bank.get()) // Link branch to the bank
                        .active(true) // Active by default
                        .build();
                branch = branchRepository.save(branch);
                return new ApiResponse("Branch added successfully", branch, null);
            } else {
                return new ApiResponse("Bank not found or Branch Manger Does Not Exist For this Branch", null, null);
            }
        } catch (Exception e) {
            return new ApiResponse("Error adding branch: " + e.getMessage(), null, null);
        }
    }

    @Override
    public ApiResponse updateBranch(Long branchId, BranchRequest branchRequest) {
        try {
            Optional<Branch> branchOptional = branchRepository.findById(branchId);
            if (branchOptional.isPresent()) {
                Branch branch = branchOptional.get();
                branch.setName(branchRequest.getName());
                branch.setBranchCode(branchRequest.getBranchCode());
                branch.setLocation(branchRequest.getLocation());
                branch = branchRepository.save(branch);
                return new ApiResponse("Branch updated successfully", branch, null);
            } else {
                return new ApiResponse("Branch not found", null, null);
            }
        } catch (Exception e) {
            return new ApiResponse("Error updating branch: " + e.getMessage(), null, null);
        }
    }

    @Override
    public ApiResponse fetchBranchDetails(Long branchId) {
        try {
            Optional<Branch> branch = branchRepository.findById(branchId);
            if (branch.isPresent()) {
                return new ApiResponse("Branch details fetched successfully", branch.get(), null);
            } else {
                return new ApiResponse("Branch not found", null, null);
            }
        } catch (Exception e) {
            return new ApiResponse("Error fetching branch details: " + e.getMessage(), null, null);
        }
    }

    @Override
    public ApiResponse listBranchesForBank(Long bankId) {
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
    public void softDeleteBranch(Long branchId) {

        try {
            Optional<Branch> branchOptional = branchRepository.findById(branchId);
            if (branchOptional.isPresent()) {
                Branch branch = branchOptional.get();
                branch.setActive(false); // Soft delete by setting active to false
                branchRepository.save(branch);
            }
        } catch (Exception e) {
            // Log or handle the error accordingly
        }

    }
}

// i am having List<object> and List<Branch> not same error which is resolved by
// Collections.singletonList(branches) instead of just including the branches directly
