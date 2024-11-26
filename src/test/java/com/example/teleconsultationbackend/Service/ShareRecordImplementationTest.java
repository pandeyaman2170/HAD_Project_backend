package com.example.teleconsultationbackend.Service;

import com.example.teleconsultationbackend.Entity.*;
import com.example.teleconsultationbackend.Repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ShareRecordImplementationTest {

    @Mock
    private ShareRecordRepository shareRecordRepository;

    @Mock
    private HospitalRepository hospitalRepository;

    @Mock
    private ConsultationRepository consultationRepository;

    @Mock
    private PrescriptionRepository prescriptionRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private ShareRecordImplementation shareRecordService;

    private AutoCloseable closeable;
    private Hospital sendingHospital;
    private Hospital receivingHospital;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);

        // Set up sending hospital
        sendingHospital = new Hospital();
        sendingHospital.setHospital_id(1L);
        sendingHospital.setName("City General Hospital");
        sendingHospital.setLocation("123 Medical Drive");
        sendingHospital.setPhone("1234567890");
        sendingHospital.setHospitalUserName("citygeneral");
        sendingHospital.setHospitalPassword("securepass123");
        sendingHospital.setDoctors(new ArrayList<>());
        sendingHospital.setDepartments(new ArrayList<>());

        // Set up receiving hospital
        receivingHospital = new Hospital();
        receivingHospital.setHospital_id(2L);
        receivingHospital.setName("County Medical Center");
        receivingHospital.setLocation("456 Health Avenue");
        receivingHospital.setPhone("0987654321");
        receivingHospital.setHospitalUserName("countymed");
        receivingHospital.setHospitalPassword("securepass456");
        receivingHospital.setDoctors(new ArrayList<>());
        receivingHospital.setDepartments(new ArrayList<>());
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void createShareRecord_ShouldCreateAndSaveShareRecord() {
        // Arrange
        ShareRecordHospital expectedShareRecord = new ShareRecordHospital();
        expectedShareRecord.setSending_hospital(sendingHospital);
        expectedShareRecord.setReceiving_hospital(receivingHospital);

        when(shareRecordRepository.save(any(ShareRecordHospital.class))).thenReturn(expectedShareRecord);

        // Act
        shareRecordService.createShareRecord(sendingHospital, receivingHospital);

        // Assert
        verify(shareRecordRepository, times(1)).save(argThat(record ->
                record.getSending_hospital().getHospital_id().equals(sendingHospital.getHospital_id()) &&
                        record.getReceiving_hospital().getHospital_id().equals(receivingHospital.getHospital_id())
        ));
    }

    @Test
    void addRecordHelper_WhenDifferentHospitalsAndNoExistingRecord_ShouldCreateNewRecord() {
        // Arrange
        Long patientId = 1L;
        Long doctorId = 1L;

        Doctor receivingDoctor = new Doctor();
        receivingDoctor.setId(doctorId);
        receivingDoctor.setHospital(receivingHospital);

        Doctor prescribingDoctor = new Doctor();
        prescribingDoctor.setId(2L);
        prescribingDoctor.setHospital(sendingHospital);

        Prescription prescription = new Prescription();
        prescription.setDoctor(prescribingDoctor);

        when(doctorRepository.findDoctorById(doctorId)).thenReturn(receivingDoctor);
        when(prescriptionRepository.findPrescriptionsByPatient_PatientId(patientId))
                .thenReturn(Collections.singletonList(prescription));
        when(shareRecordRepository
                .findShareRecordHospitalByPatientIdAndSending_hospitalAndReceiving_hospital(
                        eq(patientId), eq(sendingHospital), eq(receivingHospital)))
                .thenReturn(null);

        // Act
        shareRecordService.addRecordHelper(patientId, doctorId);

        // Assert
        verify(shareRecordRepository, times(1)).save(argThat(record ->
                record.getPatientId().equals(patientId) &&
                        record.getSending_hospital().getHospital_id().equals(sendingHospital.getHospital_id()) &&
                        record.getReceiving_hospital().getHospital_id().equals(receivingHospital.getHospital_id())
        ));
    }

    @Test
    void addRecordHelper_WhenSameHospital_ShouldNotCreateRecord() {
        // Arrange
        Long patientId = 1L;
        Long doctorId = 1L;

        Doctor doctor = new Doctor();
        doctor.setId(doctorId);
        doctor.setHospital(sendingHospital);

        Doctor prescribingDoctor = new Doctor();
        prescribingDoctor.setId(2L);
        prescribingDoctor.setHospital(sendingHospital);

        Prescription prescription = new Prescription();
        prescription.setDoctor(prescribingDoctor);

        when(doctorRepository.findDoctorById(doctorId)).thenReturn(doctor);
        when(prescriptionRepository.findPrescriptionsByPatient_PatientId(patientId))
                .thenReturn(Collections.singletonList(prescription));

        // Act
        shareRecordService.addRecordHelper(patientId, doctorId);

        // Assert
        verify(shareRecordRepository, never()).save(any(ShareRecordHospital.class));
    }

    @Test
    void addRecordHelper_WhenExistingRecord_ShouldNotCreateDuplicate() {
        // Arrange
        Long patientId = 1L;
        Long doctorId = 1L;

        Doctor receivingDoctor = new Doctor();
        receivingDoctor.setId(doctorId);
        receivingDoctor.setHospital(receivingHospital);

        Doctor prescribingDoctor = new Doctor();
        prescribingDoctor.setId(2L);
        prescribingDoctor.setHospital(sendingHospital);

        Prescription prescription = new Prescription();
        prescription.setDoctor(prescribingDoctor);

        ShareRecordHospital existingRecord = new ShareRecordHospital();
        existingRecord.setPatientId(patientId);
        existingRecord.setSending_hospital(sendingHospital);
        existingRecord.setReceiving_hospital(receivingHospital);

        when(doctorRepository.findDoctorById(doctorId)).thenReturn(receivingDoctor);
        when(prescriptionRepository.findPrescriptionsByPatient_PatientId(patientId))
                .thenReturn(Collections.singletonList(prescription));
        when(shareRecordRepository
                .findShareRecordHospitalByPatientIdAndSending_hospitalAndReceiving_hospital(
                        eq(patientId), eq(sendingHospital), eq(receivingHospital)))
                .thenReturn(existingRecord);

        // Act
        shareRecordService.addRecordHelper(patientId, doctorId);

        // Assert
        verify(shareRecordRepository, never()).save(any(ShareRecordHospital.class));
    }

    @Test
    void revokeConsentHelper_ShouldDeleteAllRecordsForPatient() {
        // Arrange
        Long patientId = 1L;

        ShareRecordHospital record1 = new ShareRecordHospital();
        record1.setPatientId(patientId);
        record1.setSending_hospital(sendingHospital);
        record1.setReceiving_hospital(receivingHospital);

        ShareRecordHospital record2 = new ShareRecordHospital();
        record2.setPatientId(patientId);
        record2.setSending_hospital(receivingHospital);
        record2.setReceiving_hospital(sendingHospital);

        ShareRecordHospital record3 = new ShareRecordHospital();
        record3.setPatientId(2L);
        record3.setSending_hospital(sendingHospital);
        record3.setReceiving_hospital(receivingHospital);

        List<ShareRecordHospital> allRecords = Arrays.asList(record1, record2, record3);
        List<ShareRecordHospital> patientRecords = Arrays.asList(record1, record2);

        when(shareRecordRepository.findAll()).thenReturn(allRecords);

        // Act
        shareRecordService.revokeConsentHelper(patientId);

        // Assert
        verify(shareRecordRepository).deleteAll(argThat(iterable -> {
            List<ShareRecordHospital> list = new ArrayList<>();
            iterable.forEach(list::add);
            return list.size() == 2 &&
                    list.stream().allMatch(record ->
                            record.getPatientId().equals(patientId));
        }));
    }

    @Test
    void revokeConsentHelper_WhenNoRecordsExist_ShouldNotDeleteAnything() {
        // Arrange
        Long patientId = 1L;
        when(shareRecordRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        shareRecordService.revokeConsentHelper(patientId);

        // Assert
        verify(shareRecordRepository).deleteAll(Collections.emptyList());
    }
}