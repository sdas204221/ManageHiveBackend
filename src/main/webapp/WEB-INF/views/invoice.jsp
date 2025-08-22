<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <title>Invoice - ${invoice.user.businessName}</title>
  <style media="all">
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
  </style>
</head>
<body>
  <div class="container">
    <!-- Header: Company Info and Invoice Title -->
    <header>
      <div class="company-info">
        <h2>${invoice.user.businessName}</h2>
        <p>${invoice.user.address}</p>
        <p>Phone: ${invoice.user.phone}</p>
        <p>Email: ${invoice.user.email}</p>
      </div>
      <div class="invoice-title">
        <h1>Invoice</h1>
      </div>
    </header>

    <!-- Invoice and Customer Information -->
    <div class="info-section">
      <table class="info-table" width="100%">
        <tr>
          <td width="25%"><strong>Invoice Number:</strong></td>
          <td width="15%">${invoice.invoiceNumber}</td>
          <td width="25%"><strong>Customer Name:</strong></td>
          <td width="35%">${invoice.customerName}</td>
        </tr>
        <tr>
          <td><strong>Date:</strong></td>
          <td>${fn:substring(invoice.issueDate, 8, 10)}-${fn:substring(invoice.issueDate, 5, 7)}-${fn:substring(invoice.issueDate, 0, 4)}</td>
          <td><strong>Customer Address:</strong></td>
          <td>${invoice.customerAddress}</td>
        </tr>
      </table>
    </div>

    <!-- Invoice Items Table -->
    <table border="0">
      <thead>
        <tr>
          <th>Description</th>
          <th>Quantity</th>
          <th>Unit Price</th>
          <th>Total</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="line" items="${invoice.salesLines}">
          <tr>
            <pre style="margin: 0; font-family: 'Helvetica Neue', Arial, sans-serif; font-size: 14px; text-align: left;"><c:out value="${line.description}" /></pre>
            <td>${line.quantity}</td>
            <td>Rs. ${line.unitPrice}</td>
            <td>Rs. ${line.quantity * line.unitPrice}</td>
          </tr>
          <tr>
              <td colspan="4" style="padding: 0;"><hr style="margin: 2px 0; border: none; border-top: 1px solid #ccc;"/></td>
            </tr>
        </c:forEach>
      </tbody>
    </table>

    <!-- Totals Section -->
    <div class="totals">
      <table border="0">
        <tr>
                  <td><strong>Total Amount</strong></td>
                  <td>Rs. ${invoice.totalAmount}</td>
                </tr>
        <tr>
          <td><strong>Discount</strong></td>
          <td>Rs. ${invoice.discount}</td>
        </tr>
        <tr>
                  <td><strong>Subtotal</strong></td>
                  <td>Rs. ${invoice.subtotal}</td>
                </tr>

      </table>
    </div>
    <div class="clearfix"></div>

    <!-- Payment Information -->
    <div class="payment-info">
      <p><strong>Payment Terms:</strong> ${invoice.paymentTerms}</p>
      <p><strong>Payment Methods:</strong> ${invoice.paymentMethod}</p>
    </div>

    <!-- Footer -->
    <footer>
      <p>Thank you for choosing us!</p>
        <br/>
      <p style="text-align: right;">${invoice.user.businessName}</p>
    </footer>
  </div>
</body>
</html>
