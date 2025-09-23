package com.sdas204221.ManageHive.repository;

import com.sdas204221.ManageHive.model.Product;
import com.sdas204221.ManageHive.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByUser(User user);

    Product findByProductNameAndUser(String productName, User user);
}
