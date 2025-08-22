package com.sdas204221.ManageHive.service.invoice.styles;

import com.sdas204221.ManageHive.model.Invoice;
import com.sdas204221.ManageHive.model.SalesLine;

public class ClassicInvoiceStyle extends BaseInvoiceStyle {

    @Override
    public String getStyleName() {
        return "classic";
    }

    @Override
    public String getStyleDescription() {
        return "Traditional professional invoice layout with clean typography for A4 or A3 paper Size";
    }

    @Override
    protected void generateContent(StringBuilder html, Invoice invoice) {
        generateHeader(html, invoice);
        generateInfoSection(html, invoice);
        generateItemsTable(html, invoice);
        generateTotalsSection(html, invoice);
        generatePaymentInfo(html, invoice);
        generateFooter(html, invoice);
    }

    private void generateHeader(StringBuilder html, Invoice invoice) {
        html.append("<!-- Classic Header -->")
                .append("<header>")
                .append("<div class=\"company-info\">")
                .append("<h2>").append(escapeHtml(invoice.getUser().getBusinessName())).append("</h2>")
                .append("<p>").append(escapeHtml(invoice.getUser().getAddress())).append("</p>")
                .append("<p>Phone: ").append(escapeHtml(invoice.getUser().getPhone())).append("</p>")
                .append("<p>Email: ").append(escapeHtml(invoice.getUser().getEmail())).append("</p>")
                .append("</div>")
                .append("<div class=\"invoice-title\">")
                .append("<h1>Invoice</h1>")
                .append("</div>")
                .append("</header>");
    }

    private void generateInfoSection(StringBuilder html, Invoice invoice) {
        String formattedDate = formatDate(invoice.getIssueDate());

        html.append("<!-- Classic Info Section -->")
                .append("<div class=\"info-section\">")
                .append("<table class=\"info-table\" width=\"100%\">")
                .append("<tr>")
                .append("<td width=\"25%\"><strong>Invoice Number:</strong></td>")
                .append("<td width=\"15%\">").append(invoice.getInvoiceNumber()).append("</td>")
                .append("<td width=\"25%\"><strong>Customer Name:</strong></td>")
                .append("<td width=\"35%\">").append(escapeHtml(invoice.getCustomerName())).append("</td>")
                .append("</tr>")
                .append("<tr>")
                .append("<td><strong>Date:</strong></td>")
                .append("<td>").append(formattedDate).append("</td>")
                .append("<td><strong>Customer Address:</strong></td>")
                .append("<td>").append(escapeHtml(invoice.getCustomerAddress())).append("</td>")
                .append("</tr>")
                .append("</table>")
                .append("</div>");
    }

    private void generateItemsTable(StringBuilder html, Invoice invoice) {
        html.append("<!-- Classic Items Table -->")
                .append("<table border=\"0\">")
                .append("<thead>")
                .append("<tr>")
                .append("<th>Description</th>")
                .append("<th>Quantity</th>")
                .append("<th>Unit Price</th>")
                .append("<th>Total</th>")
                .append("</tr>")
                .append("</thead>")
                .append("<tbody>");

        if (invoice.getSalesLines() != null) {
            for (SalesLine line : invoice.getSalesLines()) {
                double lineTotal = line.getQuantity() * line.getUnitPrice();

                html.append("<tr>")
                        .append("<td>")
                        .append("<pre style=\"margin: 0; font-family: 'Helvetica Neue', Arial, sans-serif; font-size: 14px; text-align: left;\">")
                        .append(escapeHtml(line.getDescription()))
                        .append("</pre>")
                        .append("</td>")
                        .append("<td>").append(formatNumber(line.getQuantity())).append("</td>")
                        .append("<td>Rs. ").append(formatNumber(line.getUnitPrice())).append("</td>")
                        .append("<td>Rs. ").append(formatNumber(lineTotal)).append("</td>")
                        .append("</tr>")
                        .append("<tr>")
                        .append("<td colspan=\"4\" style=\"padding: 0;\"><hr style=\"margin: 2px 0; border: none; border-top: 1px solid #ccc;\"/></td>")
                        .append("</tr>");
            }
        }

        html.append("</tbody>")
                .append("</table>");
    }

    private void generateTotalsSection(StringBuilder html, Invoice invoice) {
        html.append("<!-- Classic Totals Section -->")
                .append("<div class=\"totals\">")
                .append("<table border=\"0\">")
                .append("<tr>")
                .append("<td><strong>Total Amount</strong></td>")
                .append("<td>Rs. ").append(formatNumber(invoice.getTotalAmount())).append("</td>")
                .append("</tr>")
                .append("<tr>")
                .append("<td><strong>Discount</strong></td>")
                .append("<td>Rs. ").append(formatNumber(invoice.getDiscount())).append("</td>")
                .append("</tr>")
                .append("<tr>")
                .append("<td><strong>Subtotal</strong></td>")
                .append("<td>Rs. ").append(formatNumber(invoice.getSubtotal())).append("</td>")
                .append("</tr>")
                .append("</table>")
                .append("</div>")
                .append("<div class=\"clearfix\"></div>");
    }

    private void generatePaymentInfo(StringBuilder html, Invoice invoice) {
        html.append("<!-- Classic Payment Info -->")
                .append("<div class=\"payment-info\">")
                .append("<p><strong>Payment Terms:</strong> ").append(escapeHtml(invoice.getPaymentTerms())).append("</p>")
                .append("<p><strong>Payment Methods:</strong> ").append(escapeHtml(invoice.getPaymentMethod())).append("</p>")
                .append("</div>");
    }

    private void generateFooter(StringBuilder html, Invoice invoice) {
        html.append("<!-- Classic Footer -->")
                .append("<footer>")
                .append("<p>Thank you for choosing us!</p>")
                .append("<br/>")
                .append("<p style=\"text-align: right;\">").append(escapeHtml(invoice.getUser().getBusinessName())).append("</p>")
                .append("</footer>");
    }

    @Override
    protected String getCSS() {
        return getBaseCSS() + """
          @page {
              size: A4;
              margin: 0.5cm;
            }
          header {
            overflow: hidden;
            margin-bottom: 10px;
            border-bottom: 1px solid #e0e0e0;
            padding-bottom: 7px;
          }

          header .company-info {
            float: left;
            width: 60%;
          }

          header .company-info h2 {
            font-size: 24pt;
            margin: 0 0 5px 0;
          }

          header .company-info p {
            font-size: 12pt;
            color: #555;
            margin: 0 0 5px;
          }

          header .invoice-title {
            float: right;
            text-align: right;
          }

          header .invoice-title h1 {
            font-size: 28pt;
            margin: 0;
          }

          .info-section {
            overflow: hidden;
            margin-bottom: 10px;
            border-bottom: 1px solid #e0e0e0;
            padding-bottom: 7px;
          }

          .info-box {
            width: 48%;
            float: left;
            font-size: 14px;
          }

          .info-box:last-child {
            float: right;
          }

          table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
            font-size: 14px;
          }

          table thead {
            background: #e0e0e0 !important;
          }

          table th, table td {
            padding: 10px;
            text-align: left;
            border: none;
          }

          .totals {
            margin-bottom: 10px;
            float: right;
            width: 300px;
            font-size: 14px;
          }

          .payment-info {
            clear: both;
            font-size: 14px;
            border-top: 1px solid #e0e0e0;
            padding-top: 7px;
            padding-bottom: 3px;
            margin-top: 10px;
          }

          footer {
            text-align: center;
            font-size: 16px;
            margin-top: 15px;
            border-top: 1px solid #e0e0e0;
            padding-top: 7px;
            color: #555;
          }
          .info-table {
              border-collapse: collapse;
              margin: 15px 0;
              font-size: 14px;
            }
            .info-table td {
              padding-top: 4px;
              padding-right: 4px;
              padding-bottom: 4px;
              padding-left: 0px;
              vertical-align: top;
              border-bottom: none;
            }
            .info-table tr:last-child td {
              border-bottom: none;
            }
        """;
    }
}