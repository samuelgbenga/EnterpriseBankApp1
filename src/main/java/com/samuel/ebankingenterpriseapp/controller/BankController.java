package com.samuel.ebankingenterpriseapp.controller;


import com.samuel.ebankingenterpriseapp.payload.request.BankRequest;
import com.samuel.ebankingenterpriseapp.payload.response.ApiResponse;
import com.samuel.ebankingenterpriseapp.service.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/banks")
@RequiredArgsConstructor
public class BankController {

    private final BankService bankService;

//    @PostMapping
//    public ResponseEntity<ApiResponse> registerBank(@RequestBody BankRequest bankRequest) {
//        ApiResponse response = bankService.registerBank(bankRequest);
//        return ResponseEntity.ok(response);
//    }

    @PutMapping("/{bankId}")
    public ResponseEntity<ApiResponse> updateBank(@PathVariable Long bankId, @RequestBody BankRequest bankRequest) {
        ApiResponse response = bankService.updateBank(bankId, bankRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse> fetchBankDetails() {
        ApiResponse response = bankService.fetchBankDetails();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/branches")
    public ResponseEntity<ApiResponse> fetchBankBranches() {
        ApiResponse response = bankService.fetchBankBranches();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/branch-managers")
    public ResponseEntity<ApiResponse> fetchBankBranchManagers() {
        ApiResponse response = bankService.fetchBankManagers();
        return ResponseEntity.ok(response);
    }


}
