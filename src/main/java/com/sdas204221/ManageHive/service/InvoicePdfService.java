package com.sdas204221.ManageHive.service;

import com.openhtmltopdf.outputdevice.helper.BaseRendererBuilder;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.sdas204221.ManageHive.model.Invoice;
import com.sdas204221.ManageHive.model.SalesLine;
import com.sdas204221.ManageHive.repository.InvoiceRepository;
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
public class InvoicePdfService extends InvoicePdfService2{

}
