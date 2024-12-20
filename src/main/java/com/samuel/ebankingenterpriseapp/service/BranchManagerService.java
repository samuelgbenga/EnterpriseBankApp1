package com.samuel.ebankingenterpriseapp.service;

import com.samuel.ebankingenterpriseapp.payload.request.BranchManagerRequest;
import com.samuel.ebankingenterpriseapp.payload.response.ApiResponse;

public interface BranchManagerService {

    ApiResponse addBranchManager(Long branchId, BranchManagerRequest branchManagerRequest);

    ApiResponse updateBranchManager(Long branchManagerId, BranchManagerRequest branchManagerRequest);

    ApiResponse fetchBranchManagerDetails(Long branchManagerId);

    ApiResponse fetchBranchManagerByBranch(Long branchId);

    void deleteBranchManager(Long branchManagerId);
}
