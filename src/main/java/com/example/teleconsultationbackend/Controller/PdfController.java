package com.example.teleconsultationbackend.Controller;

import com.example.teleconsultationbackend.Repository.PrescriptionRepository;
import com.example.teleconsultationbackend.Service.PdfService;
import com.example.teleconsultationbackend.Service.PdfServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
public class PdfController {
    @Autowired
    private PdfService pdfService;

    @Autowired
    PrescriptionRepository prescriptionRepository;

    @GetMapping("/viewpres/{prescription_id}")
    public ResponseEntity<InputStreamResource> viewPdf(@PathVariable String prescription_id) throws IOException {
        // Generate the PDF

        PdfServiceImplementation pdfService = new PdfServiceImplementation(prescriptionRepository);
        ByteArrayInputStream pdf = pdfService.generatePdf(Integer.parseInt(prescription_id));

        HttpHeaders httpHeaders = new HttpHeaders();

        return ResponseEntity
                .ok()
                .headers(httpHeaders)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdf));
    }
}
