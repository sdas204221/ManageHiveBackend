package com.sdas204221.ManageHive.service.invoice.styles;

import com.sdas204221.ManageHive.model.Invoice;
import com.sdas204221.ManageHive.model.SalesLine;

public class ThermalReceiptStyle extends BaseInvoiceStyle {

    @Override
    public String getStyleName() {
        return "thermal";
    }

    @Override
    public String getStyleDescription() {
        return "Compact thermal receipt style optimized for 58mm-80mm thermal printers";
    }

    @Override
    protected void generateContent(StringBuilder html, Invoice invoice) {
        generateHeader(html, invoice);
        generateSeparator(html);
        generateInfoSection(html, invoice);
        generateSeparator(html);
        generateItemsTable(html, invoice);
        generateSeparator(html);
        generateTotalsSection(html, invoice);
        generateSeparator(html);
        generatePaymentInfo(html, invoice);
        generateFooter(html, invoice);
    }

    private void generateHeader(StringBuilder html, Invoice invoice) {
        html.append("<!-- Thermal Receipt Header -->")
                .append("<div class=\"receipt-header\">")
                .append("<div class=\"business-name\">").append(escapeHtml(invoice.getUser().getBusinessName())).append("</div>")
                .append("<div class=\"business-address\">").append(escapeHtml(invoice.getUser().getAddress())).append("</div>")
                .append("<div class=\"contact-info\">")
                .append("Ph: ").append(escapeHtml(invoice.getUser().getPhone())).append("<br/>")
                .append("Email: ").append(escapeHtml(invoice.getUser().getEmail()))
                .append("</div>")
                .append("</div>");
    }

    private void generateInfoSection(StringBuilder html, Invoice invoice) {
        String formattedDate = formatDate(invoice.getIssueDate());

        html.append("<!-- Receipt Info Section -->")
                .append("<div class=\"receipt-info\">")
                .append("<div class=\"info-row\">")
                .append("<span class=\"label\">INVOICE #:</span>")
                .append("<span class=\"value\">").append(invoice.getInvoiceNumber()).append("</span>")
                .append("</div>")
                .append("<div class=\"info-row\">")
                .append("<span class=\"label\">DATE:</span>")
                .append("<span class=\"value\">").append(formattedDate).append("</span>")
                .append("</div>")
                .append("<div class=\"customer-info\">")
                .append("<div class=\"customer-label\">CUSTOMER:</div>")
                .append("<div class=\"customer-name\">").append(escapeHtml(invoice.getCustomerName())).append("</div>");

        // Only show address if it's not too long for thermal receipt
        if (invoice.getCustomerAddress() != null && !invoice.getCustomerAddress().trim().isEmpty()) {
            String address = invoice.getCustomerAddress();
            if (address.length() > 35) {
                // Truncate long addresses for thermal receipts
                address = address.substring(0, 32) + "...";
            }
            html.append("<div class=\"customer-address\">").append(escapeHtml(address)).append("</div>");
        }

        html.append("</div>")
                .append("</div>");
    }

    private void generateItemsTable(StringBuilder html, Invoice invoice) {
        html.append("<!-- Receipt Items -->")
                .append("<div class=\"receipt-items\">");

        if (invoice.getSalesLines() != null) {
            for (SalesLine line : invoice.getSalesLines()) {
                double lineTotal = line.getQuantity() * line.getUnitPrice();

                // Truncate long descriptions for thermal receipts
                String description = line.getDescription();
                if (description != null && description.length() > 28) {
                    description = description.substring(0, 25) + "...";
                }

                html.append("<div class=\"item-row\">")
                        .append("<div class=\"item-desc\">").append(escapeHtml(description)).append("</div>")
                        .append("<div class=\"item-details\">")
                        .append("<span class=\"qty\">").append(formatNumber(line.getQuantity())).append("x</span>")
                        .append("<span class=\"price\">Rs.").append(formatNumber(line.getUnitPrice())).append("</span>")
                        .append("<span class=\"total\">Rs.").append(formatNumber(lineTotal)).append("</span>")
                        .append("</div>")
                        .append("</div>");
            }
        }

        html.append("</div>");
    }

    private void generateTotalsSection(StringBuilder html, Invoice invoice) {
        html.append("<!-- Receipt Totals -->")
                .append("<div class=\"receipt-totals\">")
                .append("<div class=\"total-row\">")
                .append("<span class=\"total-label\">TOTAL:</span>")
                .append("<span class=\"total-amount\">Rs.").append(formatNumber(invoice.getTotalAmount())).append("</span>")
                .append("</div>");

        // Only show discount if it's greater than 0
        if (invoice.getDiscount() > 0) {
            html.append("<div class=\"total-row\">")
                    .append("<span class=\"total-label\">DISCOUNT:</span>")
                    .append("<span class=\"total-amount\">Rs.").append(formatNumber(invoice.getDiscount())).append("</span>")
                    .append("</div>");
        }

        html.append("<div class=\"total-row final-total\">")
                .append("<span class=\"total-label\">NET AMOUNT:</span>")
                .append("<span class=\"total-amount\">Rs.").append(formatNumber(invoice.getSubtotal())).append("</span>")
                .append("</div>")
                .append("</div>");
    }

    private void generatePaymentInfo(StringBuilder html, Invoice invoice) {
        html.append("<!-- Receipt Payment Info -->")
                .append("<div class=\"receipt-payment\">");

        if (invoice.getPaymentMethod() != null && !invoice.getPaymentMethod().trim().isEmpty()) {
            html.append("<div class=\"payment-method\">Payment: ").append(escapeHtml(invoice.getPaymentMethod())).append("</div>");
        }

        if (invoice.getPaymentTerms() != null && !invoice.getPaymentTerms().trim().isEmpty()) {
            html.append("<div class=\"payment-terms\">Terms: ").append(escapeHtml(invoice.getPaymentTerms())).append("</div>");
        }

        html.append("</div>");
    }

    private void generateFooter(StringBuilder html, Invoice invoice) {
        html.append("<!-- Receipt Footer -->")
                .append("<div class=\"receipt-footer\">")
                .append("<div class=\"thank-you\">THANK YOU!</div>")
                .append("<div class=\"visit-again\">Please visit again</div>")
                .append("</div>");
    }

    private void generateSeparator(StringBuilder html) {
        html.append("<div class=\"receipt-separator\"></div>");
    }

    @Override
    protected String getCSS() {
        return getBaseCSS() + """
        @page {
            size: 80mm;
            margin: 2mm;
        }
        
        body {
            font-family: 'Courier New', monospace;
            font-size: 11px;
            line-height: 1.3;
            padding: 2mm;
            margin: 0;
            width: 76mm;
            color: #000;
            background: #fff;
        }
        
        .container {
            max-width: 76mm;
            padding: 0;
            margin: 0;
        }
        
        .receipt-header {
            text-align: center;
            margin-bottom: 3mm;
        }
        
        .business-name {
            font-size: 14px;
            font-weight: bold;
            margin-bottom: 1mm;
            text-transform: uppercase;
        }
        
        .business-address {
            font-size: 10px;
            margin-bottom: 2mm;
            word-wrap: break-word;
        }
        
        .contact-info {
            font-size: 9px;
            line-height: 1.2;
        }
        
        .receipt-separator {
            border-top: 1px dashed #000;
            margin: 2mm 0;
            height: 0;
        }
        
        .receipt-info {
            font-size: 10px;
        }
        
        .info-row {
            display: flex;
            justify-content: space-between;
            margin-bottom: 1mm;
        }
        
        .label {
            font-weight: bold;
        }
        
        .value {
            text-align: right;
        }
        
        .customer-info {
            margin-top: 2mm;
        }
        
        .customer-label {
            font-weight: bold;
            font-size: 9px;
            margin-bottom: 1mm;
        }
        
        .customer-name {
            font-weight: bold;
            margin-bottom: 0.5mm;
        }
        
        .customer-address {
            font-size: 9px;
            line-height: 1.1;
            word-wrap: break-word;
        }
        
        .receipt-items {
            margin: 2mm 0;
        }
        
        .item-row {
            margin-bottom: 2mm;
        }
        
        .item-desc {
            font-weight: bold;
            margin-bottom: 0.5mm;
            word-wrap: break-word;
        }
        
        .item-details {
            display: flex;
            justify-content: space-between;
            font-size: 10px;
        }
        
        .qty {
            flex: 0 0 auto;
        }
        
        .price {
            flex: 1 1 auto;
            text-align: center;
        }
        
        .total {
            flex: 0 0 auto;
            font-weight: bold;
        }
        
        .receipt-totals {
            margin: 2mm 0;
        }
        
        .total-row {
            display: flex;
            justify-content: space-between;
            margin-bottom: 1mm;
            font-size: 11px;
        }
        
        .final-total {
            font-weight: bold;
            font-size: 12px;
            border-top: 1px solid #000;
            padding-top: 1mm;
            margin-top: 2mm;
        }
        
        .total-label {
            font-weight: bold;
        }
        
        .total-amount {
            font-weight: bold;
        }
        
        .receipt-payment {
            font-size: 9px;
            text-align: center;
        }
        
        .payment-method, .payment-terms {
            margin-bottom: 1mm;
        }
        
        .receipt-footer {
            text-align: center;
            margin-top: 3mm;
        }
        
        .thank-you {
            font-size: 12px;
            font-weight: bold;
            margin-bottom: 1mm;
        }
        
        .visit-again {
            font-size: 9px;
        }
        
        /* Print optimizations for thermal printers */
        @media print {
            body {
                -webkit-print-color-adjust: exact;
                print-color-adjust: exact;
            }
            
            .receipt-separator {
                page-break-inside: avoid;
            }
            
            .item-row {
                page-break-inside: avoid;
            }
        }
        
        /* Responsive adjustments for different thermal paper widths */
        @media (max-width: 60mm) {
            body {
                font-size: 10px;
                width: 56mm;
            }
            
            .container {
                max-width: 56mm;
            }
            
            .business-name {
                font-size: 12px;
            }
            
            .item-desc {
                font-size: 9px;
            }
        }
        """;
    }
}