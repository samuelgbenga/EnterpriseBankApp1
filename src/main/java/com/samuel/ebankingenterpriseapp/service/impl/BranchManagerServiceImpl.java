package com.samuel.ebankingenterpriseapp.service.impl;


import com.samuel.ebankingenterpriseapp.entity.Branch;
import com.samuel.ebankingenterpriseapp.entity.BranchManager;
import com.samuel.ebankingenterpriseapp.payload.request.BranchManagerRequest;
import com.samuel.ebankingenterpriseapp.payload.response.ApiResponse;
import com.samuel.ebankingenterpriseapp.repository.BranchManagerRepository;
import com.samuel.ebankingenterpriseapp.repository.BranchRepository;
import com.samuel.ebankingenterpriseapp.service.BranchManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BranchManagerServiceImpl implements BranchManagerService {

    private final BranchRepository branchRepository;

    private final BranchManagerRepository branchManagerRepository;

    @Override
    public ApiResponse addBranchManager(Long branchId, BranchManagerRequest branchManagerRequest) {
        try {
            Optional<Branch> branchOptional = branchRepository.findById(branchId);
            if (branchOptional.isPresent() && branchOptional.get().getBranchManager() == null) {
                Branch branch = branchOptional.get();
                BranchManager branchManager = BranchManager.builder()
                        .firstName(branchManagerRequest.getFirstName())
                        .lastName(branchManagerRequest.getLastName())
                        .contactNumber(branchManagerRequest.getContactNumber())
                        .email(branchManagerRequest.getEmail())
                        .branch(branch) // Linking the branch to the manager
                        .build();
                branchManagerRepository.save(branchManager);
                branch.setBranchManager(branchManager);
                branchRepository.save(branch);
                return new ApiResponse("Branch Manager added successfully", branchManager, null);
            } else {
                return new ApiResponse("Branch not found or Manager already exist", null, null);
            }
        } catch (Exception e) {
            return new ApiResponse("Error adding Branch Manager: " + e.getMessage(), null, null);
        }
    }

    @Override
    public ApiResponse updateBranchManager(Long branchId, BranchManagerRequest branchManagerRequest) {
        try {
            Optional<Branch> branchOptional = branchRepository.findById(branchId);

            if (branchOptional.isPresent()) {
                BranchManager branchManager = branchOptional.get().getBranchManager();
                branchManager.setFirstName(branchManagerRequest.getFirstName());
                branchManager.setLastName(branchManagerRequest.getLastName());
                branchManager.setContactNumber(branchManagerRequest.getContactNumber());
                branchManager.setEmail(branchManagerRequest.getEmail());
                branchManagerRepository.save(branchManager);
                return new ApiResponse("Branch Manager updated successfully", branchManager, null);
            } else {
                return new ApiResponse("Branch Manager not found", null, null);
            }
        } catch (Exception e) {
            return new ApiResponse("Error updating Branch Manager: " + e.getMessage(), null, null);
        }
    }

    @Override
    public ApiResponse fetchBranchManagerDetails(Long branchManagerId) {
        try {
            Optional<BranchManager> branchManagerOptional = branchManagerRepository.findById(branchManagerId);
            if (branchManagerOptional.isPresent()) {
                return new ApiResponse("Branch Manager details fetched successfully", branchManagerOptional.get(), null);
            } else {
                return new ApiResponse("Branch Manager not found", null, null);
            }
        } catch (Exception e) {
            return new ApiResponse("Error fetching Branch Manager details: " + e.getMessage(), null, null);
        }
    }

    @Override
    public ApiResponse fetchBranchManagerByBranch(Long branchId) {
        try {
            Optional<Branch> branchOptional = branchRepository.findById(branchId);
            if (branchOptional.isPresent()) {
                Branch branch = branchOptional.get();
                BranchManager branchManager = branch.getBranchManager(); // Assuming a Branch has a branchManager reference
                return new ApiResponse("Branch Manager fetched successfully", branchManager, null);
            } else {
                return new ApiResponse("Branch not found", null, null);
            }
        } catch (Exception e) {
            return new ApiResponse("Error fetching Branch Manager by Branch: " + e.getMessage(), null, null);
        }
    }

    @Override
    public ApiResponse deleteBranchManager(Long branchManagerId) {
        try {
            Optional<BranchManager> branchManagerOptional = branchManagerRepository.findById(branchManagerId);
            //branchManagerOptional.ifPresent(branchManager -> branchManager.setActive(false));
            if(branchManagerOptional.isPresent()){
                BranchManager branchManager = branchManagerOptional.get();
                if(!branchManager.isActive()) return  ApiResponse.builder().message("Manager Already Deleted!").build();
                branchManager.setActive(false);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return  ApiResponse.builder().message("Manager Deleted.").build();
    }
}
