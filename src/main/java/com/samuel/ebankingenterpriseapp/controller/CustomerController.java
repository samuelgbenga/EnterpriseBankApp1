package com.samuel.ebankingenterpriseapp.controller;


import com.samuel.ebankingenterpriseapp.payload.request.CustomerRequest;
import com.samuel.ebankingenterpriseapp.payload.response.ApiResponse;
import com.samuel.ebankingenterpriseapp.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerCustomer(@RequestBody CustomerRequest customerRequest) {
        ApiResponse response = customerService.registerCustomer(customerRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<ApiResponse> fetchCustomerDetails(@PathVariable Long customerId) {
        ApiResponse response = customerService.fetchCustomerDetails(customerId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{customerId}/link-account/{accountId}")
    public ResponseEntity<ApiResponse> linkCustomerToAccount(@PathVariable Long customerId, @PathVariable Long accountId) {
        ApiResponse response = customerService.linkCustomerToAccount(customerId, accountId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<ApiResponse> updateCustomer(@PathVariable Long customerId, @RequestBody CustomerRequest customerRequest) {
        ApiResponse response = customerService.updateCustomer(customerId, customerRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<ApiResponse> softDeleteCustomer(@PathVariable Long customerId) {
        ApiResponse response = customerService.softDeleteCustomer(customerId);
        return ResponseEntity.ok(response);
    }

}
