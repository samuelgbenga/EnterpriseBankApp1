package com.samuel.ebankingenterpriseapp.service.impl;


import com.samuel.ebankingenterpriseapp.entity.Account;
import com.samuel.ebankingenterpriseapp.entity.Customer;
import com.samuel.ebankingenterpriseapp.payload.request.CustomerRequest;
import com.samuel.ebankingenterpriseapp.payload.response.ApiResponse;
import com.samuel.ebankingenterpriseapp.repository.AccountRepository;
import com.samuel.ebankingenterpriseapp.repository.CustomerRepository;
import com.samuel.ebankingenterpriseapp.service.AccountService;
import com.samuel.ebankingenterpriseapp.service.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {


    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final AccountService accountService;

    @Override
    public ApiResponse registerCustomer(CustomerRequest customerRequest) {
        Customer customer = new Customer();
        customer.setFirstName(customerRequest.getFirstName());
        customer.setLastName(customerRequest.getLastName());
        customer.setEmail(customerRequest.getEmail());
        customer.setPhoneNumber(customerRequest.getPhoneNumber());
        customer.setActive(true);
        customerRepository.save(customer);
        return ApiResponse.builder()
                .message("Customer registered successfully")
                .object(customer)
                .build();
    }

    @Override
    public ApiResponse fetchCustomerDetails(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        return ApiResponse.builder()
                .message("Customer details fetched successfully")
                .object(customer)
                .objectList(customer.getAccounts())
                .build();
    }

    @Override
    public ApiResponse linkCustomerToAccount(Long customerId, Long accountId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));

        customer.getAccounts().add(account);
        account.getCustomers().add(customer);
        customerRepository.save(customer);
        accountRepository.save(account);

        return ApiResponse.builder()
                .message("Customer linked to account successfully")
                .object(customer)
                .build();
    }

    @Override
    public ApiResponse updateCustomer(Long customerId, CustomerRequest customerRequest) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        customer.setFirstName(customerRequest.getFirstName());
        customer.setLastName(customerRequest.getLastName());
        customer.setEmail(customerRequest.getEmail());
        customer.setPhoneNumber(customerRequest.getPhoneNumber());
        customerRepository.save(customer);
        return ApiResponse.builder()
                .message("Customer updated successfully")
                .object(customer)
                .build();
    }

    @Override
    public ApiResponse softDeleteCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        if(!customer.isActive()) return new ApiResponse("Customer already Deleted!", null, null );
        customer.setActive(false);
        customerRepository.save(customer);
        return new ApiResponse("Customer Deleted", null, null);
    }
}
