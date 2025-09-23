package com.sdas204221.ManageHive.service;

import com.sdas204221.ManageHive.exception.ResourceNotFoundException;
import com.sdas204221.ManageHive.model.Customer;
import com.sdas204221.ManageHive.model.User;
import com.sdas204221.ManageHive.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer getCustomerById(Long id, User user) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));

        // Verify ownership
        if (!customer.getUser().getUsername().equals(user.getUsername())) {
            throw new ResourceNotFoundException("Customer not found for this user");
        }

        return customer;
    }

    public List<Customer> getAllCustomers(User user) {
        return customerRepository.findByUser(user);
    }

    public Customer createCustomer(Customer customer, User user) {
        customer.setUser(user);
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Long id, Customer updatedCustomer, User user) {
        Customer existing = getCustomerById(id, user);

        if (updatedCustomer.getName() != null) existing.setName(updatedCustomer.getName());
        if (updatedCustomer.getAddress() != null) existing.setAddress(updatedCustomer.getAddress());
        if (updatedCustomer.getPhone() != null) existing.setPhone(updatedCustomer.getPhone());
        if (updatedCustomer.getEmail() != null) existing.setEmail(updatedCustomer.getEmail());

        return customerRepository.save(existing);
    }

    public void deleteCustomer(Long id, User user) {
        Customer existing = getCustomerById(id, user);
        customerRepository.delete(existing);
    }
}
