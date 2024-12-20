package com.samuel.ebankingenterpriseapp.service.impl;

import com.samuel.ebankingenterpriseapp.entity.Bank;
import com.samuel.ebankingenterpriseapp.entity.Branch;
import com.samuel.ebankingenterpriseapp.entity.BranchManager;
import com.samuel.ebankingenterpriseapp.model.BranchDto;
import com.samuel.ebankingenterpriseapp.payload.request.BranchRequest;
import com.samuel.ebankingenterpriseapp.payload.response.ApiResponse;
import com.samuel.ebankingenterpriseapp.repository.BankRepository;
import com.samuel.ebankingenterpriseapp.repository.BranchManagerRepository;
import com.samuel.ebankingenterpriseapp.repository.BranchRepository;
import com.samuel.ebankingenterpriseapp.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.util.Optional;



@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;

    private final BankRepository bankRepository;

    private final ModelMapper modelMapper;


    @Override
    public ApiResponse addBranchToBank(Long bankId, BranchRequest branchRequest) {

        try {
            Optional<Bank> bank = bankRepository.findById(bankId);
            if (bank.isPresent() ) {
                Branch branch = Branch.builder()
                        .name(branchRequest.getName())
                        .branchCode(branchRequest.getBranchCode())
                        .location(branchRequest.getLocation())
                        .bank(bank.get()) // Link branch to the bank
                        .active(true) // Active by default
                        .build();
                branch = branchRepository.save(branch);

                BranchDto branchDto = modelMapper.map(branch, BranchDto.class);

                return new ApiResponse("Branch added successfully", branchDto, null);
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
                branchRepository.save(branch);
                return new ApiResponse("Branch updated successfully", null, null);
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
                BranchDto branchDto = modelMapper.map(branch.get(), BranchDto.class);
                return new ApiResponse("Branch details fetched successfully", branchDto, null);
            } else {
                return new ApiResponse("Branch not found", null, null);
            }
        } catch (Exception e) {
            return new ApiResponse("Error fetching branch details: " + e.getMessage(), null, null);
        }
    }



    @Override
    public ApiResponse softDeleteBranch(Long branchId) {

        try {
            Optional<Branch> branchOptional = branchRepository.findById(branchId);
            if (branchOptional.isPresent()) {
                Branch branch = branchOptional.get();
                if(!branch.isActive()) return ApiResponse.builder().message("Branch has Already been Deleted!.").build();
                branch.setActive(false); // Soft delete by setting active to false
                branchRepository.save(branch);
                return ApiResponse.builder().message("Branch Deleted.").build();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return ApiResponse.builder().message("something went wrong while deleting branch.").build();

    }
}

