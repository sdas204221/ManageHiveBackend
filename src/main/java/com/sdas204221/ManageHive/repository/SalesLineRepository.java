package com.sdas204221.ManageHive.repository;

import com.sdas204221.ManageHive.model.Invoice;
import com.sdas204221.ManageHive.model.SalesLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesLineRepository extends JpaRepository<SalesLine,Long> {
    List<SalesLine> findAllByInvoice(Invoice invoice);
}
