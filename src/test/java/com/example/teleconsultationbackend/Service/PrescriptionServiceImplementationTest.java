package com.example.teleconsultationbackend.Service;

import com.example.teleconsultationbackend.DTO.DailyLogDetails;
import com.example.teleconsultationbackend.DTO.FollowUpDetails;
import com.example.teleconsultationbackend.DTO.PrescriptionDetails;
import com.example.teleconsultationbackend.Entity.*;
import com.example.teleconsultationbackend.Repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PrescriptionServiceImplementationTest {

    @Mock
    private PrescriptionRepository prescriptionRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private ConsultationRepository consultationRepository;

    @InjectMocks
    private PrescriptionServiceImplementation prescriptionService;

    private Doctor doctor;
    private Patient patient;
    private User doctorUser;
    private User patientUser;
    private Hospital hospital;
    private Department department;
    private Prescription prescription;
    private PrescriptionDetails prescriptionDetails;
    private Date currentDate;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        currentDate = new Date();

        // Setup Users
        doctorUser = new User();
        doctorUser.setFirstName("John");
        doctorUser.setLastName("Smith");
        doctorUser.setPhone("1234567890");

        patientUser = new User();
        patientUser.setFirstName("Jane");
        patientUser.setLastName("Doe");
        patientUser.setPhone("9876543210");

        // Setup Hospital and Department
        hospital = new Hospital();
        hospital.setName("Test Hospital");

        department = new Department();
        department.setName("Cardiology");

        // Setup Doctor
        doctor = new Doctor();
        doctor.setId(1L);
        doctor.setUser(doctorUser);
        doctor.setHospital(hospital);
        doctor.setDepartment(department);
        doctor.setOnline_status(true);

        // Setup Patient
        patient = new Patient();
        patient.setId(1L);
        patient.setUser(patientUser);

        // Setup Prescription
        Set<String> medicines = new HashSet<>();
        medicines.add("Medicine1-->1-0-1");
        medicines.add("Medicine2-->0-1-0");

        prescription = new Prescription();
        prescription.setPrescriptionId(1);
        prescription.setConsultationDate(currentDate);
        prescription.setMedical_findings("Test findings");
        prescription.setMedicine(medicines);
        prescription.setRemark("Test remark");
        prescription.setFollowUpDate(new Date(currentDate.getTime() + 86400000)); // Next day
        prescription.setDoctor(doctor);
        prescription.setPatient(patient);

        // Setup PrescriptionDetails
        prescriptionDetails = new PrescriptionDetails();
        prescriptionDetails.setPrescriptionId(1);
        prescriptionDetails.setDoctorId(1L);
        prescriptionDetails.setPatientId(1L);
        prescriptionDetails.setConsultationDate(currentDate);
        prescriptionDetails.setObservation("Test findings");
        prescriptionDetails.setMedicine(medicines);
        prescriptionDetails.setRemark("Test remark");
        prescriptionDetails.setFollowUpDate(new Date(currentDate.getTime() + 86400000));
    }

    @Test
    void addPrescription_Success() {
        // Arrange
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(prescriptionRepository.save(any(Prescription.class))).thenReturn(prescription);

        Consultation consultation = new Consultation();
        when(consultationRepository.findConsultationByDoctorAndPatientAndStatus(doctor, patient, "accepted"))
                .thenReturn(consultation);

        // Act
        PrescriptionDetails result = prescriptionService.addPrescription(prescriptionDetails);

        // Assert
        assertNotNull(result);
        assertEquals(prescriptionDetails.getDoctorId(), result.getDoctorId());
        verify(prescriptionRepository).save(any(Prescription.class));
        verify(consultationRepository).save(any(Consultation.class));
    }

    @Test
    void addPrescription_DoctorNotFound() {
        // Arrange
        when(doctorRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        PrescriptionDetails result = prescriptionService.addPrescription(prescriptionDetails);

        // Assert
        assertNull(result);
        verify(prescriptionRepository, never()).save(any(Prescription.class));
    }

    @Test
    void getPrescriptionsPatient_Success() {
        // Arrange
        List<Prescription> prescriptions = Collections.singletonList(prescription);
        when(prescriptionRepository.findPrescriptionsByPatient_PatientId(1L)).thenReturn(prescriptions);

        // Act
        List<PrescriptionDetails> result = prescriptionService.getPrescriptionsPatient(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(prescription.getPrescriptionId(), result.get(0).getPrescriptionId());
    }

    @Test
    void getPrescriptionById_Success() {
        // Arrange
        when(prescriptionRepository.findById(1)).thenReturn(Optional.of(prescription));

        // Act
        PrescriptionDetails result = prescriptionService.getPrescriptionById(1);

        // Assert
        assertNotNull(result);
        assertEquals(prescription.getPrescriptionId(), result.getPrescriptionId());
    }

    @Test
    void getPrescriptionById_NotFound() {
        // Arrange
        when(prescriptionRepository.findById(1)).thenReturn(Optional.empty());

        // Act
        PrescriptionDetails result = prescriptionService.getPrescriptionById(1);

        // Assert
        assertNull(result);
    }

    @Test
    void doctorDailyLog_Success() {
        // Arrange
        List<Prescription> prescriptions = Collections.singletonList(prescription);
        when(prescriptionRepository.findPrescriptionsByDoctor_DoctorId(1L)).thenReturn(prescriptions);

        // Act
        List<DailyLogDetails> result = prescriptionService.doctorDailyLog(1L);

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(prescription.getDoctor().getId(), result.get(0).getDoctorId());
    }

    @Test
    void doctorDailyLog_NoPrescriptions() {
        // Arrange
        when(prescriptionRepository.findPrescriptionsByDoctor_DoctorId(1L)).thenReturn(null);

        // Act
        List<DailyLogDetails> result = prescriptionService.doctorDailyLog(1L);

        // Assert
        assertNull(result);
    }

    @Test
    void getFollowUpDetails_Success() throws Exception {
        // Arrange
        List<Prescription> prescriptions = Collections.singletonList(prescription);
        when(prescriptionRepository.findPrescriptionsByPatient_PatientId(1L)).thenReturn(prescriptions);

        // Act
        List<FollowUpDetails> result = prescriptionService.getFollowUpDetails(1L);

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(prescription.getDoctor().getDepartment().getName(), result.get(0).getDepartmentName());
    }

    @Test
    void getFollowUpDetails_NoFutureFollowUps() throws Exception {
        // Arrange
        prescription.setFollowUpDate(new Date(currentDate.getTime() - 86400000)); // Previous day
        List<Prescription> prescriptions = Collections.singletonList(prescription);
        when(prescriptionRepository.findPrescriptionsByPatient_PatientId(1L)).thenReturn(prescriptions);

        // Act
        List<FollowUpDetails> result = prescriptionService.getFollowUpDetails(1L);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getFollowUpDetails_NullFollowUpDate() {
        // Arrange
        prescription.setFollowUpDate(null);
        List<Prescription> prescriptions = Collections.singletonList(prescription);
        when(prescriptionRepository.findPrescriptionsByPatient_PatientId(1L)).thenReturn(prescriptions);

        // Act
        List<FollowUpDetails> result = prescriptionService.getFollowUpDetails(1L);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}