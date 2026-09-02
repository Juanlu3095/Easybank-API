package com.jcooldevelopment.easybank_api.service.Pdf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.jcooldevelopment.easybank_api.repository.OperationRepository;

@Service
public class PdfServiceImpl implements PdfService{

    private final OperationRepository operationRepository;
    private final TemplateEngine templateEngine;

    public PdfServiceImpl(OperationRepository operationRepository, TemplateEngine templateEngine){
        this.operationRepository = operationRepository;
        this.templateEngine = templateEngine;
    }

    // https://es.stackoverflow.com/questions/497677/generar-cabecera-pdf-en-thymeleaf-flying-saucer-pdf-itext
    @Override
    public String createOperationReceipt(UUID operationId) throws IOException{
        if(operationId != null){
            // Process Thymeleaf template
            Context context = new Context();
            // context.setVariable("domain", domain);
            // Careful with Flying Saucer, it does not work with modern css (Flexbox, rem, etc.)
            String html = templateEngine.process("operationReceipt", context);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(baos, false);
            renderer.finishPDF();
            baos.close();
            byte[] pdfReceipt = baos.toByteArray();

            // Transform to base64 since every browser can read this format and is easier for frontend
            // https://stackoverflow.com/questions/50260391/open-pdf-from-bytes-array-in-angular-5
            String string = Base64.getEncoder().encodeToString(pdfReceipt);
            return string;
        }
        return null;
    }

    

}
