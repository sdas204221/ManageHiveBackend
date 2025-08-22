package com.sdas204221.ManageHive.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Backup {
    List<User> users;
    List<Invoice> invoices;
    List<SalesLine> salesLines;
}
