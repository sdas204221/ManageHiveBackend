package com.sdas204221.ManageHive.servlet;
import com.sdas204221.ManageHive.model.Invoice;
import com.sdas204221.ManageHive.model.SalesLine;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/invoice")
public class InvoiceServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Set response content type
        response.setContentType("text/html;charset=UTF-8");

        // Get invoice object from request attribute
        Invoice invoice = (Invoice) request.getAttribute("invoice");

        if (invoice == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invoice not found");
            return;
        }

        // Generate HTML
        PrintWriter out = response.getWriter();
        generateInvoiceHTML(out, invoice);
    }

    private void generateInvoiceHTML(PrintWriter out, Invoice invoice) {
        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"en\">");
        out.println("<head>");
        out.println("  <meta charset=\"UTF-8\" />");
        out.println("  <title>Invoice - " + escapeHtml(invoice.getUser().getBusinessName()) + "</title>");
        out.println("  <style media=\"all\">");
        out.println(getCSS());
        out.println("  </style>");
        out.println("</head>");
        out.println("<body>");
        out.println("  <div class=\"container\">");

        // Header
        generateHeader(out, invoice);

        // Info section
        generateInfoSection(out, invoice);

        // Invoice items table
        generateItemsTable(out, invoice);

        // Totals section
        generateTotalsSection(out, invoice);

        // Payment information
        generatePaymentInfo(out, invoice);

        // Footer
        generateFooter(out, invoice);

        out.println("  </div>");
        out.println("</body>");
        out.println("</html>");
    }

    private void generateHeader(PrintWriter out, Invoice invoice) {
        out.println("    <!-- Header: Company Info and Invoice Title -->");
        out.println("    <header>");
        out.println("      <div class=\"company-info\">");
        out.println("        <h2>" + escapeHtml(invoice.getUser().getBusinessName()) + "</h2>");
        out.println("        <p>" + escapeHtml(invoice.getUser().getAddress()) + "</p>");
        out.println("        <p>Phone: " + escapeHtml(invoice.getUser().getPhone()) + "</p>");
        out.println("        <p>Email: " + escapeHtml(invoice.getUser().getEmail()) + "</p>");
        out.println("      </div>");
        out.println("      <div class=\"invoice-title\">");
        out.println("        <h1>Invoice</h1>");
        out.println("      </div>");
        out.println("    </header>");
    }

    private void generateInfoSection(PrintWriter out, Invoice invoice) {
        String formattedDate = formatDate(invoice.getIssueDate());

        out.println("    <!-- Invoice and Customer Information -->");
        out.println("    <div class=\"info-section\">");
        out.println("      <table class=\"info-table\" width=\"100%\">");
        out.println("        <tr>");
        out.println("          <td width=\"25%\"><strong>Invoice Number:</strong></td>");
        out.println("          <td width=\"15%\">" + invoice.getInvoiceNumber() + "</td>");
        out.println("          <td width=\"25%\"><strong>Customer Name:</strong></td>");
        out.println("          <td width=\"35%\">" + escapeHtml(invoice.getCustomerName()) + "</td>");
        out.println("        </tr>");
        out.println("        <tr>");
        out.println("          <td><strong>Date:</strong></td>");
        out.println("          <td>" + formattedDate + "</td>");
        out.println("          <td><strong>Customer Address:</strong></td>");
        out.println("          <td>" + escapeHtml(invoice.getCustomerAddress()) + "</td>");
        out.println("        </tr>");
        out.println("      </table>");
        out.println("    </div>");
    }

    private void generateItemsTable(PrintWriter out, Invoice invoice) {
        out.println("    <!-- Invoice Items Table -->");
        out.println("    <table border=\"0\">");
        out.println("      <thead>");
        out.println("        <tr>");
        out.println("          <th>Description</th>");
        out.println("          <th>Quantity</th>");
        out.println("          <th>Unit Price</th>");
        out.println("          <th>Total</th>");
        out.println("        </tr>");
        out.println("      </thead>");
        out.println("      <tbody>");

        if (invoice.getSalesLines() != null) {
            for (SalesLine line : invoice.getSalesLines()) {
                double lineTotal = line.getQuantity() * line.getUnitPrice();

                out.println("        <tr>");
                out.println("          <td>");
                out.println("            <pre style=\"margin: 0; font-family: 'Helvetica Neue', Arial, sans-serif; font-size: 14px; text-align: left;\">" +
                        escapeHtml(line.getDescription()) + "</pre>");
                out.println("          </td>");
                out.println("          <td>" + formatNumber(line.getQuantity()) + "</td>");
                out.println("          <td>Rs. " + formatNumber(line.getUnitPrice()) + "</td>");
                out.println("          <td>Rs. " + formatNumber(lineTotal) + "</td>");
                out.println("        </tr>");
                out.println("        <tr>");
                out.println("          <td colspan=\"4\" style=\"padding: 0;\"><hr style=\"margin: 2px 0; border: none; border-top: 1px solid #ccc;\"/></td>");
                out.println("        </tr>");
            }
        }

        out.println("      </tbody>");
        out.println("    </table>");
    }

    private void generateTotalsSection(PrintWriter out, Invoice invoice) {
        out.println("    <!-- Totals Section -->");
        out.println("    <div class=\"totals\">");
        out.println("      <table border=\"0\">");
        out.println("        <tr>");
        out.println("          <td><strong>Total Amount</strong></td>");
        out.println("          <td>Rs. " + formatNumber(invoice.getTotalAmount()) + "</td>");
        out.println("        </tr>");
        out.println("        <tr>");
        out.println("          <td><strong>Discount</strong></td>");
        out.println("          <td>Rs. " + formatNumber(invoice.getDiscount()) + "</td>");
        out.println("        </tr>");
        out.println("        <tr>");
        out.println("          <td><strong>Subtotal</strong></td>");
        out.println("          <td>Rs. " + formatNumber(invoice.getSubtotal()) + "</td>");
        out.println("        </tr>");
        out.println("      </table>");
        out.println("    </div>");
        out.println("    <div class=\"clearfix\"></div>");
    }

    private void generatePaymentInfo(PrintWriter out, Invoice invoice) {
        out.println("    <!-- Payment Information -->");
        out.println("    <div class=\"payment-info\">");
        out.println("      <p><strong>Payment Terms:</strong> " + escapeHtml(invoice.getPaymentTerms()) + "</p>");
        out.println("      <p><strong>Payment Methods:</strong> " + escapeHtml(invoice.getPaymentMethod()) + "</p>");
        out.println("    </div>");
    }

    private void generateFooter(PrintWriter out, Invoice invoice) {
        out.println("    <!-- Footer -->");
        out.println("    <footer>");
        out.println("      <p>Thank you for choosing us!</p>");
        out.println("      <br/>");
        out.println("      <p style=\"text-align: right;\">" + escapeHtml(invoice.getUser().getBusinessName()) + "</p>");
        out.println("    </footer>");
    }

    private String formatDate(Date date) {
        if (date == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(date);
    }

    private String formatNumber(double number) {
        // Format number to remove unnecessary decimal places
        if (number == (long) number) {
            return String.format("%d", (long) number);
        } else {
            return String.format("%.2f", number);
        }
    }

    private String escapeHtml(String input) {
        if (input == null) return "";
        return input.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#x27;");
    }

    private String getCSS() {
        return """
        @page {
              size: A4;
              margin: 0.5cm;
            }
          body {
            font-family: 'Helvetica Neue', Arial, sans-serif;
            background: #fff;
            color: #333;
            line-height: 1.6;
            padding: 20px;
            margin: 0;
          }

          .container {
            max-width: 800px;
            margin: 0 auto;
            padding: 30px;
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
