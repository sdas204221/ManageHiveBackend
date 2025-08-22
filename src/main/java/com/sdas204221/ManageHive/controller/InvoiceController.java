package com.sdas204221.ManageHive.controller;

import com.sdas204221.ManageHive.model.Invoice;
import com.sdas204221.ManageHive.service.InvoicePdfService;
import com.sdas204221.ManageHive.service.InvoiceService;
import com.sdas204221.ManageHive.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class InvoiceController {
    @Autowired
    private InvoicePdfService invoicePdfService;
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private UserService userService;
    @PostMapping("/invoice")
    public ResponseEntity<Long> addInvoice(@RequestBody Invoice invoice, @AuthenticationPrincipal UserDetails userDetails){
        invoice.setUser(
                userService.findById(userDetails.getUsername())
        );
//        System.out.println(invoice.getIssueDate().toString());
        invoiceService.save(invoice);
//        System.out.println(invoiceService.findById(invoice.getInvoiceNumber()).get().getIssueDate().toString());
        return new ResponseEntity<>(invoice.getInvoiceNumber(),HttpStatus.CREATED);
    }
    @DeleteMapping("/invoice")
    public ResponseEntity<?> deleteInvoice(@RequestBody Invoice invoice,@AuthenticationPrincipal UserDetails userDetails){
        Optional<Invoice> originalInvoice=invoiceService.findById(invoice.getInvoiceNumber());
        if (originalInvoice.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else if (
                originalInvoice
                        .get()
                        .getUser()
                        .getUsername()
                        .equals(
                                userDetails.getUsername()
                        )
        ){
            invoiceService.delete(originalInvoice.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            System.out.println(originalInvoice
                    .get()
                    .getUser()
                    .getUsername()
                    +"-"+
                    userDetails.getUsername()
            );
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    @GetMapping(value = "/invoice/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> getInvoicePdf(@PathVariable("id") long id,
                                                HttpServletRequest request,
                                                HttpServletResponse response,
                                                @AuthenticationPrincipal UserDetails userDetails) throws Exception {
        Optional<Invoice> originalInvoice=invoiceService.findById(id);
        if (originalInvoice.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else if (
                !originalInvoice
                        .get()
                        .getUser()
                        .getUsername()
                        .equals(
                                userDetails.getUsername()
                        )
        ){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Invoice invoice = originalInvoice.get();
        byte[] pdfBytes = invoicePdfService.generateInvoicePdf(invoice);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("inline")
                .filename("invoice-" + invoice.getInvoiceNumber() + ".pdf")
                .build());

        return ResponseEntity.ok().headers(headers).body(pdfBytes);
        //return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/invoice/{id}")
    public ResponseEntity<Invoice> getInvoiceJson(@PathVariable("id") long id,@AuthenticationPrincipal UserDetails userDetails) {
        Optional<Invoice> originalInvoice=invoiceService.findById(id);
        if (originalInvoice.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else if (
                !originalInvoice
                        .get()
                        .getUser()
                        .getUsername()
                        .equals(
                                userDetails.getUsername()
                        )
        ){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Invoice invoice = invoiceService.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));
        return ResponseEntity.ok(invoice);
    }
    @GetMapping("/invoices")
    public ResponseEntity<List<Invoice>> getAllInvoices(@AuthenticationPrincipal UserDetails userDetails){
        List<Invoice> allInvoices=invoiceService.getAllInvoicesByUser(userDetails.getUsername());
        return new ResponseEntity<List<Invoice>>(allInvoices,HttpStatus.OK);
    }
}
