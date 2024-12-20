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

    @PostMapping
    public ResponseEntity<ApiResponse> registerBank(@RequestBody BankRequest bankRequest) {
        ApiResponse response = bankService.registerBank(bankRequest);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{bankId}")
    public ResponseEntity<ApiResponse> updateBank(@PathVariable Long bankId, @RequestBody BankRequest bankRequest) {
        ApiResponse response = bankService.updateBank(bankId, bankRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{bankId}")
    public ResponseEntity<ApiResponse> fetchBankDetails(@PathVariable Long bankId) {
        ApiResponse response = bankService.fetchBankDetails(bankId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{bankId}/branches")
    public ResponseEntity<ApiResponse> fetchBankBranches(@PathVariable Long bankId) {
        ApiResponse response = bankService.fetchBankBranches(bankId);
        return ResponseEntity.ok(response);
    }

//    @GetMapping("/{bankId}/branch-managers")
//    public ResponseEntity<ApiResponse> fetchBankBranchManagers(@PathVariable Long bankId) {
//        ApiResponse response = bankService.fetchBankManagers(bankId);
//        return ResponseEntity.ok(response);
//    }

    @DeleteMapping("/{bankId}")
    public ResponseEntity<ApiResponse> softDeleteBank(@PathVariable Long bankId) {
        ApiResponse response =  bankService.softDeleteBank(bankId);
        return ResponseEntity.ok(response);
    }
}
