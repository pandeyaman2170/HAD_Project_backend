package com.example.teleconsultationbackend.Service;

import com.example.teleconsultationbackend.DTO.OnlyPatientDetails;
import com.example.teleconsultationbackend.DTO.PatientDetails;
import com.example.teleconsultationbackend.Entity.*;
import com.example.teleconsultationbackend.Repository.*;
import org.junit.jupiter.api.*;
import org.mockito.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.Date;
import java.util.Collections;

class PatientServiceImplTest {

    @InjectMocks
    private PatientServiceImpl patientService;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private QueuesRepository queuesRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private ConsultationRepository consultationRepository;

    private Patient patient;
    private User user;
    private Department department;
    private Queues queue;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup mock User
        user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPhone("1234567890");
        user.setEmail("john.doe@example.com");

        // Setup mock Patient
        patient = new Patient();
        patient.setId(1L);
        patient.setUser(user);
        patient.setHeight("5'9\"");
        patient.setWeight("70kg");
        patient.setBlood_group("O+");
        patient.setAadhar_number("1234-5678-9101");

        // Setup mock Department and Queue
        department = new Department();
        department.setId(1L);
        department.setName("Cardiology");

        queue = new Queues();
        queue.setQueue_id(1L);
        queue.setDepartment(department);
        queue.setPatients(Collections.singletonList(patient));
    }

    @AfterEach
    void tearDown() {
        reset(patientRepository, userRepository, queuesRepository, departmentRepository, consultationRepository);
    }

    @Test
    void total_patients() {
        // Given
        when(patientRepository.get_count()).thenReturn(10);

        // When
        int result = patientService.total_patients();

        // Then
        assertEquals(10, result);
        verify(patientRepository, times(1)).get_count();
    }

    @Test
    void registerPatient() {
        // Given
        OnlyPatientDetails details = new OnlyPatientDetails();
        details.setHeight("6'0\"");
        details.setWeight("75kg");
        details.setBlood_group("B+");
        details.setAadhar_number("1234-5678-9102");

        // When
        patientService.registerPatient(user, details);

        // Then
        verify(patientRepository, times(1)).save(any(Patient.class));
    }


    @Test
    void deletePatientFromQueue_ShouldPatientNotFound() {
        // Given
        Long patientId = 999L;  // Non-existing patient ID
        when(patientRepository.findPatientById(patientId)).thenReturn(null);

        // When
        patientService.deletePatientFromQueue(patientId);

        // Then
        // No exception should be thrown
        verify(patientRepository, times(0)).save(any(Patient.class));
        verify(queuesRepository, times(0)).save(any(Queues.class));
    }

    @Test
    void getPatientByPatientId() {
        // Given
        Long patientId = 1L;
        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));

        // When
        Patient result = patientService.getPatientByPatientId(patientId);

        // Then
        assertNotNull(result);
        assertEquals(patientId, result.getId());
    }

    @Test
    void getPatientByPatientId_ShouldReturnNull() {
        // Given
        Long patientId = 999L;  // Non-existing patient ID
        when(patientRepository.findById(patientId)).thenReturn(Optional.empty());

        // When
        Patient result = patientService.getPatientByPatientId(patientId);

        // Then
        assertNull(result);
    }

    @Test
    void updatePatient() {
        // Given
        PatientDetails patientDetails = new PatientDetails();
        patientDetails.setFirstName("Jane");
        patientDetails.setLastName("Smith");
        patientDetails.setPhoneNo("9876543210");
        patientDetails.setEmail("jane.smith@example.com");
        patientDetails.setDob(new Date());

        Long patientId = 1L;
        when(patientRepository.findPatientById(patientId)).thenReturn(patient);

        // When
        PatientDetails updatedDetails = patientService.updatePatient(patientDetails, patientId);

        // Then
        assertNotNull(updatedDetails);
        assertEquals(patientDetails.getFirstName(), updatedDetails.getFirstName());
        verify(patientRepository, times(1)).save(patient);
    }

    @Test
    void getPatientByPhoneNumber_ShouldReturnNull() {
        // Given
        String phoneNumber = "9999999999";  // Non-existing phone number
        when(patientRepository.findByPhoneNo(phoneNumber)).thenReturn(null);

        // When
        PatientDetails result = patientService.getPatientByPhoneNumber(phoneNumber);

        // Then
        assertNull(result);
    }

    @Test
    void getPatientDetailsForConsultation() {
        // Given
        Long patientId = 1L;
        when(patientRepository.getPatientDetailsForConsultation(patientId)).thenReturn(patient);

        // When
        PatientDetails result = patientService.getPatientDetailsForConsultation(patientId);

        // Then
        assertNotNull(result);
        assertEquals(patient.getId(), result.getPatientId());
        assertEquals(patient.getUser().getFirstName(), result.getFirstName());
    }
}
