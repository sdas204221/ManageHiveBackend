package com.sdas204221.ManageHive.service;

import com.sdas204221.ManageHive.exception.InvoiceAlreadyExistsException;
import com.sdas204221.ManageHive.exception.SalesLineAlreadyExistsException;
import com.sdas204221.ManageHive.model.Invoice;
import com.sdas204221.ManageHive.model.SalesLine;
import com.sdas204221.ManageHive.repository.InvoiceRepository;
import com.sdas204221.ManageHive.repository.SalesLineRepository;
import com.sdas204221.ManageHive.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private SalesLineRepository salesLineRepository;
    @Autowired
    UserRepository userRepository;

    public void save(Invoice invoice){
        if (invoiceRepository.findById(invoice.getInvoiceNumber()).isPresent()){
            throw new InvoiceAlreadyExistsException();
        }else {
            for (SalesLine salesLine:invoice.getSalesLines()){
                if (salesLineRepository.findById(salesLine.getSid()).isPresent()){
                    throw new SalesLineAlreadyExistsException();
                }else {
                    salesLine.setInvoice(invoice);
                }
            }
            invoiceRepository.save(invoice);
            for (SalesLine salesLine:invoice.getSalesLines()){
                salesLineRepository.save(salesLine);
            }
        }
    }

    public Optional<Invoice> findById(long invoiceNumber) {
        return invoiceRepository.findById(invoiceNumber);
    }

    public void delete(Invoice invoice) {
        List<SalesLine> salesLines=salesLineRepository.findAllByInvoice(invoice);
        for (SalesLine salesLine:salesLines){
            salesLineRepository.delete(salesLine);
        }
        invoiceRepository.delete(invoice);
    }

    public List<Invoice> getAllInvoicesByUser(String username) {
        List<Invoice> invoices=invoiceRepository.findAllByUser(
                userRepository.findByUsername(username)
        );
        for (Invoice invoice:invoices){
            List<SalesLine> salesLines=salesLineRepository.findAllByInvoice(invoice);
            double sum=0;
            for (SalesLine salesLine:salesLines){
                sum+=salesLine.getQuantity()*(salesLine.getUnitPrice()*(1+(salesLine.getTax()/100)));
            }
            invoice.setSubtotal(sum);
            invoice.setTotalAmount(invoice.getSubtotal()-invoice.getDiscount());
        }
        return invoices;
    }
}
