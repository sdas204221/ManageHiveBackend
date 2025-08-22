package com.sdas204221.ManageHive.repository;

import com.sdas204221.ManageHive.model.Invoice;
import com.sdas204221.ManageHive.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice,Long> {
    List<Invoice> findAllByUser(User user);
}
