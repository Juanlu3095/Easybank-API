package com.jcooldevelopment.easybank_api.service.Pdf;

import java.util.UUID;

import java.io.IOException;

public interface PdfService {
    
    String createOperationReceipt (UUID operationId) throws IOException;
}
