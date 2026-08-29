package com.jcooldevelopment.easybank_api.controller;

import java.io.IOException;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jcooldevelopment.easybank_api.contracts.common.Apiresponse;
import com.jcooldevelopment.easybank_api.service.Pdf.PdfService;

@RestController
@RequestMapping("/api/operationreceipt")
public class OperationReceiptController {

    private final PdfService pdfService;

    public OperationReceiptController(PdfService pdfService){
        this.pdfService = pdfService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Apiresponse<byte[]>> getOperationReceipt(@PathVariable UUID id){
        byte[] pdf;
        try {
            pdf = this.pdfService.createOperationReceipt(id);
            return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new Apiresponse<byte[]>("Operation receipt created.", pdf));
        } catch (IOException e) {
            // TODO Auto-generated catch block  
            e.printStackTrace();
            return null;
        }
        
    }
}
