package com.samuel.ebankingenterpriseapp.service;

import com.samuel.ebankingenterpriseapp.payload.request.BranchRequest;
import com.samuel.ebankingenterpriseapp.payload.response.ApiResponse;

public interface BranchService {

    ApiResponse addBranchToBank(Long bankId, BranchRequest branchRequest);

    ApiResponse updateBranch(Long branchId, BranchRequest branchRequest);

    ApiResponse fetchBranchDetails(Long branchId);


    ApiResponse softDeleteBranch(Long branchId);
}
