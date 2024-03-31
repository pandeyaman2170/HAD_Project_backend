package com.example.teleconsultationbackend.Service;

import com.example.teleconsultationbackend.DTO.PrescriptionDetails;
import com.example.teleconsultationbackend.Entity.Patient;
import com.example.teleconsultationbackend.Entity.Prescription;
import com.example.teleconsultationbackend.Repository.PrescriptionRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Set;

@Service
public class PdfServiceImplementation implements  PdfService{
   // private final PrescriptionRepository prescriptionRepository;


    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private PatientService patientService;

//    @Autowired
//    public PdfServiceImplementation(PrescriptionRepository prescriptionRepository) {
//        this.prescriptionRepository = prescriptionRepository;
//    }
    @Override
    public ByteArrayInputStream generatePdf(int prescriptionId) {
        try {
            //Prescription prescription = prescriptionRepository.findPrescriptionByPrescriptionId(prescriptionId);
            PrescriptionDetails prescription = prescriptionService.getPrescriptionById(prescriptionId);
            Patient patient = patientService.getPatientByPatientId(prescription.getPatientId());
            if (prescription == null) {
                throw new IllegalArgumentException("Prescription not found with id: " + prescriptionId);
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter pdfWriter = PdfWriter.getInstance(document, out);
            document.open();
            PdfContentByte canvas = pdfWriter.getDirectContentUnder();
            canvas.setColorFill(new BaseColor(255, 253, 208));
            canvas.rectangle(0, 0, document.getPageSize().getWidth(), document.getPageSize().getHeight());
            canvas.fill();

            // ######################### Adding Header ###################################
            // Our main aim is to put doctor name on the top left then the logo.
            // Firstly create one table to hold a table and a logo with only 1 entry
            PdfPTable headerTable = new PdfPTable(3);
            headerTable.setWidthPercentage(100);

            PdfPCell key = new PdfPCell();
            PdfPCell value = new PdfPCell();

            key.setPadding(6);
            key.setBorderWidth(0);
            key.setBorderColorTop(new BaseColor(30, 58, 138));

            value.setPadding(6);
            value.setBorderWidth(0);

            Font keyFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 15);
            Font valueFont = FontFactory.getFont(FontFactory.HELVETICA, 15);

            keyFont.setColor(new BaseColor(30, 58, 138));
            key.setPhrase(new Phrase(" ", keyFont));
            headerTable.addCell(key);
            key.setPhrase(new Phrase(" ", keyFont));
            headerTable.addCell(key);
            key.setPhrase(new Phrase("Dr." + prescription.getDoctorName(), keyFont));
            headerTable.addCell(key);

            key.setPhrase(new Phrase(" ", keyFont));

            keyFont.setColor(new BaseColor(30, 58, 138));
            key.setPhrase(new Phrase(" ", keyFont));
            headerTable.addCell(key);
            key.setPhrase(new Phrase(" ", keyFont));
            headerTable.addCell(key);
            key.setPhrase(new Phrase("Doctor ID: " + prescription.getDoctorId(), keyFont));
            headerTable.addCell(key);

            keyFont.setColor(new BaseColor(30, 58, 138));
            key.setPhrase(new Phrase(" ", keyFont));
            headerTable.addCell(key);
            key.setPhrase(new Phrase(" ", keyFont));
            headerTable.addCell(key);
            key.setPhrase(new Phrase("Hospital name: " + prescription.getHospital_name(), keyFont));
            headerTable.addCell(key);

            keyFont.setColor(new BaseColor(30, 58, 138));
            key.setPhrase(new Phrase(" ", keyFont));
            headerTable.addCell(key);
            key.setPhrase(new Phrase(" ", keyFont));
            headerTable.addCell(key);
            key.setPhrase(new Phrase("Department: " + prescription.getDepartment_name(), keyFont));
            headerTable.addCell(key);

            document.add(headerTable);


            // ================================ Trying to add image to the pdf ==================================
            Image logo = Image.getInstance("src/main/java/com/example/teleconsultationbackend/Images/logoforPrescription.png");
            logo.scaleAbsolute(140, 40);
            logo.setAbsolutePosition(40, 750);
            document.add(logo);

            // ################################ Trying to Create Table for patient details ###################################

            // ---------------------------------- Adding Patient Details -------------------------------
            PdfPTable patientTable = new PdfPTable(3);

            patientTable.setWidthPercentage(100);
            patientTable.setSpacingBefore(35);
            patientTable.setHorizontalAlignment(0);

            key = new PdfPCell();

            key.setPadding(6);
            key.setBorderWidth(0);

            keyFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 15);
            valueFont = FontFactory.getFont(FontFactory.HELVETICA, 15);


            key.setPhrase(new Phrase("", keyFont));
            patientTable.addCell(key);
            key.setPhrase(new Phrase("", keyFont));
            patientTable.addCell(key);
            key.setPhrase(new Phrase("Date: " + prescription.getConsultationDate(), valueFont));
            patientTable.addCell(key);

            key.setPhrase(new Phrase("", keyFont));
            patientTable.addCell(key);
            key.setPhrase(new Phrase("", keyFont));
            patientTable.addCell(key);
            key.setPhrase(new Phrase("", keyFont));
            patientTable.addCell(key);

            key.setPhrase(new Phrase("Patient Id:", keyFont));
            patientTable.addCell(key);
            key.setPhrase(new Phrase("" + prescription.getPatientId(), valueFont));
            patientTable.addCell(key);
            key.setPhrase(new Phrase("", keyFont));
            patientTable.addCell(key);

            key.setPhrase(new Phrase("Patient Name:", keyFont));
            patientTable.addCell(key);
            key.setPhrase(new Phrase(prescription.getPatientName()  , valueFont));
            patientTable.addCell(key);
            key.setPhrase(new Phrase("", keyFont));
            patientTable.addCell(key);

            key.setPhrase(new Phrase("Gender:", keyFont));
            patientTable.addCell(key);
            key.setPhrase(new Phrase(""+patient.getUser().getGender(), valueFont));
            patientTable.addCell(key);
            key.setPhrase(new Phrase("", keyFont));
            patientTable.addCell(key);

            key.setPhrase(new Phrase("Contact Number:", keyFont));
            patientTable.addCell(key);
            key.setPhrase(new Phrase("" + patient.getUser().getPhone(), valueFont));
            patientTable.addCell(key);
            key.setPhrase(new Phrase("", keyFont));
            patientTable.addCell(key);

            document.add(patientTable);

            // ---------------------------------- Adding Prescription ------------------------------------------

            PdfPTable observationTable = new PdfPTable(1);
            observationTable.setWidthPercentage(100);
            observationTable.setSpacingBefore(15);
            observationTable.setHorizontalAlignment(0);

            PdfPCell headerCell = new PdfPCell();

            // Styling header cell
            headerCell.setBackgroundColor(new BaseColor(244, 187, 68));
            headerCell.setBorderWidth(0);
            headerCell.setPadding(5);
            Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            keyFont.setColor(BaseColor.LIGHT_GRAY);

            // Styling paragraph cell
            Font paraFont = FontFactory.getFont(FontFactory.HELVETICA, 14);
            PdfPCell paraCell = new PdfPCell();
            paraCell.setPadding(6);
            paraCell.setBorder(0);
            paraCell.setBorderWidth(0);


            headerCell.setPhrase(new Phrase("Observation", font));
            observationTable.addCell(headerCell);

            paraCell.setPhrase(new Phrase(prescription.getObservation(), paraFont));
            observationTable.addCell(paraCell);

            //paraCell.setPhrase(new Phrase(prescription.getRemark(), paraFont));
            document.add(observationTable);

            PdfPTable medicineTable = new PdfPTable(2);
            medicineTable.setWidthPercentage(100);
            medicineTable.setSpacingBefore(15);
            medicineTable.setHorizontalAlignment(0);
            medicineTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);

            headerCell.setPhrase(new Phrase("Medicine", font));
            headerCell.setBorder(0);
            medicineTable.addCell(headerCell);

            headerCell.setPhrase(new Phrase("Dosage", font));
            medicineTable.addCell(headerCell);

            // ----------------------------- Trying to add multiple medicines in a row -------------------------------
            Set<String> medicines = prescription.getMedicine();
            for(String medicine: medicines) {
                String[] dosage = medicine.split("-->");
                paraCell.setPhrase(new Phrase("" + dosage[0], paraFont));
                medicineTable.addCell(paraCell);
                paraCell.setPhrase(new Phrase("" + dosage[1], paraFont));
                medicineTable.addCell(paraCell);
            }

            document.add(medicineTable);


            PdfPTable remarkTable = new PdfPTable(1);
            remarkTable.setWidthPercentage(100);
            remarkTable.setSpacingBefore(15);
            remarkTable.setHorizontalAlignment(0);

            headerCell.setPhrase(new Phrase("Remark", font));
            remarkTable.addCell(headerCell);

            paraCell.setPhrase(new Phrase(prescription.getRemark(), paraFont));
            remarkTable.addCell(paraCell);

            document.add(remarkTable);


//            // Add copyright text at the bottom of the page
            Paragraph copyright = new Paragraph("\u00A9 All Rights Reserved @ Healthiest", FontFactory.getFont(FontFactory.HELVETICA, 12));
            //copyright.setAlignment(Element.ALIGN_CENTER);
//
// Calculate the y-coordinate for the bottom position
            Rectangle pageSize = document.getPageSize();
            float y = pageSize.getBottom(document.bottomMargin());

            float leftMargin = 272;
//
//// Create a ColumnText instance and position the copyright text at the bottom
            ColumnText.showTextAligned(pdfWriter.getDirectContent(),
                    Element.ALIGN_CENTER,
                    new Phrase(copyright),
                    pageSize.getLeft(document.leftMargin()+ leftMargin),
                    y,
                    0);
//            document.add(copyright);

            document.close();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (Exception e) {
            System.out.println("Error occurred in creating prescription pdf");
            e.printStackTrace();
            return null;
        }
    }
}
