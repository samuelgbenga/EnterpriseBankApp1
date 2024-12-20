package com.samuel.ebankingenterpriseapp.service;

import com.samuel.ebankingenterpriseapp.payload.request.BranchRequest;
import com.samuel.ebankingenterpriseapp.payload.response.ApiResponse;

public interface BranchService {

    ApiResponse addBranchToBank(Long bankId, Long managerId, BranchRequest branchRequest);

    ApiResponse updateBranch(Long branchId, BranchRequest branchRequest);

    ApiResponse fetchBranchDetails(Long branchId);

    ApiResponse listBranchesForBank(Long bankId);

    void softDeleteBranch(Long branchId);
}