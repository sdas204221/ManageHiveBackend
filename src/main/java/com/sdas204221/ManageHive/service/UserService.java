package com.sdas204221.ManageHive.service;

import com.sdas204221.ManageHive.exception.UserAlreadyExistsException;
import com.sdas204221.ManageHive.model.Backup;
import com.sdas204221.ManageHive.model.Invoice;
import com.sdas204221.ManageHive.model.SalesLine;
import com.sdas204221.ManageHive.model.User;
import com.sdas204221.ManageHive.repository.InvoiceRepository;
import com.sdas204221.ManageHive.repository.SalesLineRepository;
import com.sdas204221.ManageHive.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SalesLineRepository salesLineRepository;
    public void save(User user) {
        if (userRepository.findByUsername(user.getUsername())!=null){
            throw new UserAlreadyExistsException("UserAlreadyExists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public User findById(String username) {
        return userRepository.findByUsername(username);
    }

    public void deleteInvoice(Invoice invoice) {
        List<SalesLine> salesLines=salesLineRepository.findAllByInvoice(invoice);
        for (SalesLine salesLine:salesLines){
            salesLineRepository.delete(salesLine);
        }
        invoiceRepository.delete(invoice);
    }

    public void delete(String username){
        User user=findById(username);
        List<Invoice> invoices=invoiceRepository.findAllByUser(user);
        for (Invoice invoice:invoices){
            deleteInvoice(invoice);
        }
        userRepository.delete(user);
    }

    public void update(User user) {
        User original=findById(user.getUsername());
        if (user.getPassword()!=null){
            original.setPassword(
                    passwordEncoder.encode(user.getPassword())
            );
        }
        if (user.getBusinessName()!=null){
            original.setBusinessName(user.getBusinessName());
        }
        if (user.getAddress()!=null){
            original.setAddress(user.getAddress());
        }
        if (user.getPhone()!=null){
            original.setPhone(user.getPhone());
        }
        if (user.getEmail()!=null){
            original.setEmail(user.getEmail());
        }
        userRepository.save(original);
    }
    public void lockUser(User user){
        User original=findById(user.getUsername());
        original.setAccountLocked(true);
        userRepository.save(original);
    }
    public void unlockUser(User user){
        User original=findById(user.getUsername());
        original.setAccountLocked(false);
        userRepository.save(original);
    }
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    public Backup userBackup(String username){
        User user=userRepository.findByUsername(username);
        List<User> users=new ArrayList<User>();
        users.add(user);
        List<Invoice> invoices =invoiceRepository.findAllByUser(user);
        List<SalesLine> salesLines=new ArrayList<SalesLine>();
        for (Invoice invoice:invoices){
            salesLines.addAll(salesLineRepository.findAllByInvoice(invoice));
        }
        return new Backup(
                users,
                invoices,
                salesLines
        );
    }
    public Backup fullBackup(){
        return new Backup(
                userRepository.findAll(),
                invoiceRepository.findAll(),
                salesLineRepository.findAll()
        );
    }
}
