package com.samuel.ebankingenterpriseapp.controller;


import com.samuel.ebankingenterpriseapp.payload.request.BranchRequest;
import com.samuel.ebankingenterpriseapp.payload.response.ApiResponse;
import com.samuel.ebankingenterpriseapp.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/branches")
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    /**
     * Add a new branch to a bank.
     *
     * @param bankId the ID of the bank
     * @param branchRequest the branch details
     * @return ApiResponse wrapped in ResponseEntity
     */
    @PostMapping("/add/{bankId}")
    public ResponseEntity<ApiResponse> addBranchToBank(@PathVariable Long bankId,
                                                       @RequestBody BranchRequest branchRequest) {
        ApiResponse response = branchService.addBranchToBank(bankId, branchRequest);
        return ResponseEntity.ok(response);
    }

    /**
     * Update branch details.
     *
     * @param branchId the ID of the branch to update
     * @param branchRequest the updated branch details
     * @return ApiResponse wrapped in ResponseEntity
     */
    @PutMapping("/update/{branchId}")
    public ResponseEntity<ApiResponse> updateBranch(@PathVariable Long branchId,
                                                    @RequestBody BranchRequest branchRequest) {
        ApiResponse response = branchService.updateBranch(branchId, branchRequest);
        return ResponseEntity.ok(response);
    }

    /**
     * Fetch branch details by branch ID.
     *
     * @param branchId the ID of the branch
     * @return ApiResponse wrapped in ResponseEntity
     */
    @GetMapping("/details/{branchId}")
    public ResponseEntity<ApiResponse> fetchBranchDetails(@PathVariable Long branchId) {
        ApiResponse response = branchService.fetchBranchDetails(branchId);
        return ResponseEntity.ok(response);
    }

    /**
     * Soft delete a branch by setting its active status to false.
     *
     * @param branchId the ID of the branch to delete
     * @return ResponseEntity with no content
     */
    @DeleteMapping("/delete/{branchId}")
    public ResponseEntity<Void> softDeleteBranch(@PathVariable Long branchId) {
        branchService.softDeleteBranch(branchId);
        return ResponseEntity.noContent().build();
    }
}
