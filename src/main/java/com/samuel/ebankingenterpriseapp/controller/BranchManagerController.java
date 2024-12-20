package com.samuel.ebankingenterpriseapp.controller;


import com.samuel.ebankingenterpriseapp.payload.request.BranchManagerRequest;
import com.samuel.ebankingenterpriseapp.payload.response.ApiResponse;
import com.samuel.ebankingenterpriseapp.service.BranchManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bank-managers")
@RequiredArgsConstructor
public class BranchManagerController {

    private final BranchManagerService branchManagerService;

    @PostMapping("/add/{branchId}")
    public ResponseEntity<ApiResponse> addBranchManager(@PathVariable Long branchId,
                                                        @RequestBody BranchManagerRequest branchManagerRequest) {
        ApiResponse response = branchManagerService.addBranchManager(branchId, branchManagerRequest);
        return ResponseEntity.status(response.getMessage().contains("success") ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @PutMapping("/update/{branchManagerId}")
    public ResponseEntity<ApiResponse> updateBranchManager(@PathVariable Long branchManagerId,
                                                           @RequestBody BranchManagerRequest branchManagerRequest) {
        ApiResponse response = branchManagerService.updateBranchManager(branchManagerId, branchManagerRequest);
        return ResponseEntity.status(response.getMessage().contains("success") ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .body(response);
    }

    @GetMapping("/details/{branchManagerId}")
    public ResponseEntity<ApiResponse> fetchBranchManagerDetails(@PathVariable Long branchManagerId) {
        ApiResponse response = branchManagerService.fetchBranchManagerDetails(branchManagerId);
        return ResponseEntity.status(response.getMessage().contains("success") ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .body(response);
    }

    @GetMapping("/by-branch/{branchId}")
    public ResponseEntity<ApiResponse> fetchBranchManagerByBranch(@PathVariable Long branchId) {
        ApiResponse response = branchManagerService.fetchBranchManagerByBranch(branchId);
        return ResponseEntity.status(response.getMessage().contains("success") ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .body(response);
    }

    @DeleteMapping("/delete/{branchManagerId}")
    public ResponseEntity<ApiResponse> deleteBranchManager(@PathVariable Long branchManagerId) {
        ApiResponse response = branchManagerService.deleteBranchManager(branchManagerId);
        return ResponseEntity.status(response.getMessage().contains("success") ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .body(response);
    }
}
