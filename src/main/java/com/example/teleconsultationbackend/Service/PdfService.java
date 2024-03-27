package com.example.teleconsultationbackend.Service;

import java.io.ByteArrayInputStream;

public interface PdfService {
    public ByteArrayInputStream generatePdf(int prescriptionId);
}
