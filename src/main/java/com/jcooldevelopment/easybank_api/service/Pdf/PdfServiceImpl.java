package com.jcooldevelopment.easybank_api.service.Pdf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
    public byte[] createOperationReceipt(UUID operationId) throws IOException{
        if(operationId != null){
            // Process Thymeleaf template
            Context context = new Context();
            // context.setVariable("domain", domain);
            String html = templateEngine.process("operationReceipt", context);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(baos, false);
            renderer.finishPDF();
            baos.close();
            byte[] pdfReceipt = baos.toByteArray();
            return pdfReceipt;
        }
        return null;
    }

    

}
