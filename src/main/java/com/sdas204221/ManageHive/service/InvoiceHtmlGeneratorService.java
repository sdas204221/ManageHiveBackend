package com.sdas204221.ManageHive.service;

import com.sdas204221.ManageHive.model.Invoice;
import com.sdas204221.ManageHive.service.invoice.styles.InvoiceStyleGenerator;
import com.sdas204221.ManageHive.service.invoice.styles.ClassicInvoiceStyle;
import com.sdas204221.ManageHive.service.invoice.styles.ThermalReceiptStyle;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class InvoiceHtmlGeneratorService {

    private final Map<String, InvoiceStyleGenerator> styleGenerators;

    public InvoiceHtmlGeneratorService() {
        styleGenerators = new HashMap<>();
        styleGenerators.put("classic", new ClassicInvoiceStyle());
        styleGenerators.put("thermal", new ThermalReceiptStyle());
    }

    public String generateInvoiceHtml(Invoice invoice, String style) {
        InvoiceStyleGenerator generator = styleGenerators.get(style.toLowerCase());

        if (generator == null) {
            // Default to classic if style not found
            generator = styleGenerators.get("classic");
        }

        return generator.generateInvoiceHtml(invoice);
    }

    // Overloaded method for backward compatibility
    public String generateInvoiceHtml(Invoice invoice) {
        return generateInvoiceHtml(invoice, "classic");
    }

    // Method to get available styles
    public java.util.Set<String> getAvailableStyles() {
        return styleGenerators.keySet();
    }

    // Method to add new style dynamically (useful for plugins)
    public void registerStyle(String styleName, InvoiceStyleGenerator generator) {
        styleGenerators.put(styleName.toLowerCase(), generator);
    }
}