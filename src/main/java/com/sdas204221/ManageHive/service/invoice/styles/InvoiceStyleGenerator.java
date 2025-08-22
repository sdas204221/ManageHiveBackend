package com.sdas204221.ManageHive.service.invoice.styles;

import com.sdas204221.ManageHive.model.Invoice;

public interface InvoiceStyleGenerator {
    String generateInvoiceHtml(Invoice invoice);
    String getStyleName();
    String getStyleDescription();
}