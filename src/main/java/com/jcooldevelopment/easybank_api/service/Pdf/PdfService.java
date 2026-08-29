package com.jcooldevelopment.easybank_api.service.Pdf;

import java.util.UUID;

import java.io.IOException;

public interface PdfService {
    
    byte[] createOperationReceipt (UUID operationId) throws IOException;
}
