package com.sdas204221.ManageHive.service;

import com.openhtmltopdf.outputdevice.helper.BaseRendererBuilder;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.sdas204221.ManageHive.model.Invoice;
import com.sdas204221.ManageHive.model.SalesLine;
import com.sdas204221.ManageHive.repository.SalesLineRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Service
public class InvoicePdfService1 {
    @Autowired
    private SalesLineRepository salesLineRepository;
    private final ServletContext servletContext;

    public InvoicePdfService1(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public byte[] generateInvoicePdf(HttpServletRequest request, HttpServletResponse response, Invoice invoice)
            throws ServletException, IOException, Exception {

              List<SalesLine> salesLines= salesLineRepository.findAllByInvoice(invoice);
        invoice.setSalesLines(salesLines);
        invoice.setTotalAmount(0);
        for (SalesLine salesLine: salesLines){
            invoice.setTotalAmount(
                    invoice.getTotalAmount()
                    +(
                            salesLine.getQuantity()
                            *(
                                    salesLine.getUnitPrice()
                                    +(
                                            salesLine.getUnitPrice()*(
                                                    salesLine.getTax()/100
                                                    )
                                            )
                                    )
                            )
            );
        }
        invoice.setSubtotal(invoice.getTotalAmount()- invoice.getDiscount());
        // Set the invoice as a request attribute so the JSP can access it.
        request.setAttribute("invoice", invoice);

        // Capture the JSP output into a String.
        final ByteArrayOutputStream htmlOut = new ByteArrayOutputStream();
        HttpServletResponse capturingResponse = new HttpServletResponseWrapper(response) {
            private final PrintWriter writer = new PrintWriter(htmlOut);
            @Override
            public PrintWriter getWriter() {
                return writer;
            }
        };

        RequestDispatcher rd = servletContext.getRequestDispatcher("/WEB-INF/views/invoice.jsp");
//        RequestDispatcher rd = servletContext.getRequestDispatcher("/invoice.jsp");
        rd.include(request, capturingResponse);
        capturingResponse.getWriter().flush();
        String htmlContent = htmlOut.toString("UTF-8");

        // Convert the captured HTML to PDF using Op
        //
        // enHTMLtoPDF.
        ByteArrayOutputStream pdfOut = new ByteArrayOutputStream();
        PdfRendererBuilder builder = new PdfRendererBuilder();
        builder.useDefaultPageSize(210,297, BaseRendererBuilder.PageSizeUnits.MM);
        builder.withHtmlContent(htmlContent, null);
        builder.toStream(pdfOut);
        builder.run();

        return pdfOut.toByteArray();
    }
}
