package com.sdas204221.ManageHive.service;

import com.openhtmltopdf.outputdevice.helper.BaseRendererBuilder;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.sdas204221.ManageHive.model.Invoice;
import com.sdas204221.ManageHive.model.SalesLine;
import com.sdas204221.ManageHive.repository.SalesLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class InvoicePdfService2 {

    @Autowired
    private SalesLineRepository salesLineRepository;

    @Autowired
    private InvoiceHtmlGeneratorService htmlGeneratorService;

    public byte[] generateInvoicePdf(Invoice invoice) throws Exception {

        // Fetch sales lines and calculate totals
        List<SalesLine> salesLines = salesLineRepository.findAllByInvoice(invoice);
        invoice.setSalesLines(salesLines);

        // Calculate total amount including tax
        invoice.setTotalAmount(0);
        for (SalesLine salesLine : salesLines) {
            double lineTotal = salesLine.getQuantity() *
                    (salesLine.getUnitPrice() +
                            (salesLine.getUnitPrice() * (salesLine.getTax() / 100)));
            invoice.setTotalAmount(invoice.getTotalAmount() + lineTotal);
        }
        invoice.setSubtotal(invoice.getTotalAmount() - invoice.getDiscount());

        // Generate HTML content using the service
        String htmlContent = htmlGeneratorService.generateInvoiceHtml(invoice,"thermal");

        // Convert the HTML to PDF using OpenHTMLtoPDF
        ByteArrayOutputStream pdfOut = new ByteArrayOutputStream();
        PdfRendererBuilder builder = new PdfRendererBuilder();
        builder.useDefaultPageSize(210, 297, BaseRendererBuilder.PageSizeUnits.MM);
        builder.withHtmlContent(htmlContent, null);
        builder.toStream(pdfOut);
        builder.run();

        return pdfOut.toByteArray();
    }
}