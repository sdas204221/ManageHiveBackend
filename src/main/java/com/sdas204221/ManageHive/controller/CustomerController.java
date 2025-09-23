package com.sdas204221.ManageHive.controller;

import com.sdas204221.ManageHive.model.Customer;
import com.sdas204221.ManageHive.model.User;
import com.sdas204221.ManageHive.service.CustomerService;
import com.sdas204221.ManageHive.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private UserService userService;

    // Get single customer
    @GetMapping("/customer/{id}")
    public Customer getCustomer(@PathVariable Long id,
                                @AuthenticationPrincipal UserDetails userDetails) {
        return customerService.getCustomerById(id, userService.findById(userDetails.getUsername()));
    }

    // Get all customers for authenticated user
    @GetMapping("/customers")
    public List<Customer> getCustomers(@AuthenticationPrincipal UserDetails userDetails) {
        return customerService.getAllCustomers(userService.findById(userDetails.getUsername()));
    }

    // Create new customer
    @PostMapping("/customer")
    public Customer createCustomer(@RequestBody Customer customer,
                                   @AuthenticationPrincipal UserDetails userDetails) {
        return customerService.createCustomer(customer, userService.findById(userDetails.getUsername()));
    }

    // Update customer
    @PatchMapping("/customer/{id}")
    public Customer updateCustomer(@PathVariable Long id,
                                   @RequestBody Customer customer,
                                   @AuthenticationPrincipal UserDetails userDetails) {
        return customerService.updateCustomer(id, customer, userService.findById(userDetails.getUsername()));
    }

    // Delete customer
    @DeleteMapping("/customer/{id}")
    public void deleteCustomer(@PathVariable Long id,
                               @AuthenticationPrincipal UserDetails userDetails) {
        customerService.deleteCustomer(id, userService.findById(userDetails.getUsername()));
    }
}
