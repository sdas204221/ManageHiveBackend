package com.sdas204221.ManageHive.repository;

import com.sdas204221.ManageHive.model.Customer;
import com.sdas204221.ManageHive.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // Find all customers for a given user
    List<Customer> findByUser(User user);

    // Optional: find by email
    Customer findByEmail(String email);

    // Optional: find by phone
    Customer findByPhone(String phone);
}
