package com.sdas204221.ManageHive.service.invoice.styles;

import com.sdas204221.ManageHive.model.Invoice;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class BaseInvoiceStyle implements InvoiceStyleGenerator {

    @Override
    public String generateInvoiceHtml(Invoice invoice) {
        StringBuilder html = new StringBuilder();

        html.append("<!DOCTYPE html>")
                .append("<html lang=\"en\">")
                .append("<head>")
                .append("<meta charset=\"UTF-8\" />")
                .append("<title>Invoice - ").append(escapeHtml(invoice.getUser().getBusinessName())).append("</title>")
                .append("<style media=\"all\">")
                .append(getCSS())
                .append("</style>")
                .append("</head>")
                .append("<body>")
                .append("<div class=\"container\">");

        generateContent(html, invoice);

        html.append("</div>")
                .append("</body>")
                .append("</html>");

        return html.toString();
    }

    protected abstract void generateContent(StringBuilder html, Invoice invoice);
    protected abstract String getCSS();

    // Common utility methods
    protected String formatDate(Date date) {
        if (date == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(date);
    }

    protected String formatNumber(double number) {
        if (number == (long) number) {
            return String.format("%d", (long) number);
        } else {
            return String.format("%.2f", number);
        }
    }

    protected String escapeHtml(String input) {
        if (input == null) return "";
        return input.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#x27;");
    }

    protected String getBaseCSS() {
        return """
        @page {
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

          .clearfix::after {
            content: "";
            display: table;
            clear: both;
          }
        """;
    }
}