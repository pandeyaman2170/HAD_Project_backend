package com.example.teleconsultationbackend.Service;

import com.example.teleconsultationbackend.DTO.ConsultationDetails;
import com.example.teleconsultationbackend.DTO.DateWiseConsultations;
import com.example.teleconsultationbackend.DTO.MonthWiseConsultation;
import com.example.teleconsultationbackend.Entity.*;
import com.example.teleconsultationbackend.Repository.ConsultationRepository;
import com.example.teleconsultationbackend.Repository.DoctorRepository;
import com.example.teleconsultationbackend.Repository.PatientRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class ConsultationServiceImplemetationTest {

    @Mock
    private ConsultationRepository consultationRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private ConsultationServiceImplemetation consultationService;

    private Doctor doctor;
    private Patient patient;
    private Consultation consultation;
    private User doctorUser;
    private User patientUser;
    private Hospital hospital;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create and set User objects for Doctor and Patient
        doctorUser = new User();
        doctorUser.setId(1L);
        doctorUser.setFirstName("Dr. John");
        doctorUser.setLastName("Doe");
        doctorUser.setRole("Doctor");
        doctorUser.setEmail("doctor.john@example.com");
        doctorUser.setGender('M');
        doctorUser.setPhone("1234567890");
        doctorUser.setAddress("123 Street");
        doctorUser.setCity("City");
        doctorUser.setPincode(123456L);

        patientUser = new User();
        patientUser.setId(2L);
        patientUser.setFirstName("Patient A");
        patientUser.setLastName("Doe");
        patientUser.setRole("Patient");
        patientUser.setEmail("patient.a@example.com");
        patientUser.setGender('F');
        patientUser.setPhone("0987654321");
        patientUser.setAddress("456 Avenue");
        patientUser.setCity("Town");
        patientUser.setPincode(654321L);

        // Create Doctor and set User
        doctor = new Doctor();
        doctor.setUser(doctorUser);

        // Create Hospital and assign it to the Doctor
        hospital = new Hospital();
        hospital.setHospital_id(1L);
        doctor.setHospital(hospital); // Set hospital to doctor

        // Create Patient and set User
        patient = new Patient();
        patient.setUser(patientUser);

        // Create Consultation
        consultation = new Consultation();
        consultation.setConsultationId(1);
        consultation.setDoctor(doctor);
        consultation.setPatient(patient);
        consultation.setStatus("waiting");
    }

    @AfterEach
    void tearDown() {
        // Optionally, clear any mocks or other setup
    }

    @Test
    void total_consultation() {
        // Given
        when(consultationRepository.get_count()).thenReturn(10);

        // When
        int totalConsultations = consultationService.total_consultation();

        // Then
        assertEquals(10, totalConsultations);
        verify(consultationRepository, times(1)).get_count();
    }

    @Test
    void totalConsultationByDoctor() {
        // Given
        when(consultationRepository.findAllByDoctor_DoctorId(anyLong())).thenReturn(5L);

        // When
        Long totalByDoctor = consultationService.totalConsultationByDoctor(1L);

        // Then
        assertEquals(5L, totalByDoctor);
        verify(consultationRepository, times(1)).findAllByDoctor_DoctorId(anyLong());
    }

    @Test
    void totalConsultationByDep() {
        // Given
        List<Consultation> consultations = Collections.singletonList(consultation);
        when(consultationRepository.findConsultationsByDepIdAndStatus(anyLong(), anyString())).thenReturn(consultations);

        // When
        List<ConsultationDetails> result = consultationService.totalConsultationByDep(1L, 1L);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(consultationRepository, times(1)).findConsultationsByDepIdAndStatus(anyLong(), anyString());
    }

    @Test
    void totalConsultationByPatient() {
        // Given
        when(consultationRepository.findAllByPatientId(anyLong())).thenReturn(7L);

        // When
        Long totalByPatient = consultationService.totalConsultationByPatient(1L);

        // Then
        assertEquals(7L, totalByPatient);
        verify(consultationRepository, times(1)).findAllByPatientId(anyLong());
    }

    @Test
    void addConsultationStatusWaitinghelper() {
        // Given
        when(patientRepository.findPatientById(anyLong())).thenReturn(patient);

        // When
        consultationService.addConsultationStatusWaitinghelper(1L, 1L);

        // Then
        verify(consultationRepository, times(1)).save(any(Consultation.class));
    }

    @Test
    void getRepeatStatusHelper() {
        // Given
        when(doctorRepository.findDoctorById(anyLong())).thenReturn(doctor);
        when(patientRepository.findPatientById(anyLong())).thenReturn(patient);
        when(consultationRepository.findAll()).thenReturn(Collections.singletonList(consultation));

        // When
        String result = consultationService.getRepeatStatusHelper(1L, 1L);

        // Then
        assertEquals("Not-Repeat", result);
        verify(consultationRepository, times(1)).findAll();
    }
    @Test
    void totalDateWiseConsultations() {
        // Given
        Date date1 = new Date();
        Date date2 = new Date(date1.getTime() + 1000000);  // Another date for variation

        Consultation consultation1 = new Consultation();
        consultation1.setConsultationId(1);
        consultation1.setConsultationDate(date1);
        consultation1.setStatus("waiting");

        Consultation consultation2 = new Consultation();
        consultation2.setConsultationId(2);
        consultation2.setConsultationDate(date2);
        consultation2.setStatus("accepted");

        List<Consultation> consultationList = List.of(consultation1, consultation2);
        when(consultationRepository.findAll()).thenReturn(consultationList);

        // When
        List<DateWiseConsultations> result = consultationService.totalDateWiseConsultations();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());

        verify(consultationRepository, times(1)).findAll();
    }

    @Test
    void totalMonthWiseConsultations() {
        // Given
        Doctor doctor = new Doctor();
        doctor.setId(1L);

        // Create a few consultations for this doctor
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.JANUARY, 10);  // January
        Date date1 = calendar.getTime();
        calendar.set(2023, Calendar.FEBRUARY, 15);  // February
        Date date2 = calendar.getTime();

        Consultation consultation1 = new Consultation();
        consultation1.setConsultationId(1);
        consultation1.setConsultationDate(date1);
        consultation1.setDoctor(doctor);

        Consultation consultation2 = new Consultation();
        consultation2.setConsultationId(2);
        consultation2.setConsultationDate(date2);
        consultation2.setDoctor(doctor);

        List<Consultation> consultationList = List.of(consultation1, consultation2);
        when(consultationRepository.findConsultationByDoctor_Id(doctor.getId())).thenReturn(consultationList);

        // When
        List<MonthWiseConsultation> result = consultationService.totalMonthWiseConsultations(doctor.getId());

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(consultationRepository, times(1)).findConsultationByDoctor_Id(doctor.getId());
    }

    @Test
    void setStatusToAcceptedHelper() {
        // Given
        Doctor doctor = new Doctor();
        doctor.setId(1L);
        doctor.setDepartment(new Department());  // Assume a department exists

        Patient patient = new Patient();
        patient.setId(2L);

        Consultation consultation = new Consultation();
        consultation.setConsultationId(1);
        consultation.setDoctor(doctor);
        consultation.setPatient(patient);
        consultation.setStatus("waiting");
        consultation.setDepId(1L);

        List<Consultation> consultationList = List.of(consultation);
        when(consultationRepository.findAll()).thenReturn(consultationList);
        when(doctorRepository.findDoctorById(doctor.getId())).thenReturn(doctor);

        // When
        consultationService.setStatusToAcceptedHelper(doctor.getId(), patient.getId());

        // Then
        assertEquals("waiting", consultation.getStatus());}

}
