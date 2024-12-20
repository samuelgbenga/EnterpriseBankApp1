package com.samuel.ebankingenterpriseapp.service;

import com.samuel.ebankingenterpriseapp.payload.request.CustomerRequest;
import com.samuel.ebankingenterpriseapp.payload.response.ApiResponse;

public interface CustomerService {

    ApiResponse registerCustomer(CustomerRequest customerRequest);

    ApiResponse fetchCustomerDetails(Long customerId);

    ApiResponse linkCustomerToAccount(Long customerId, Long accountId);

    ApiResponse updateCustomer(Long customerId, CustomerRequest customerRequest);

    void softDeleteCustomer(Long customerId);
}
