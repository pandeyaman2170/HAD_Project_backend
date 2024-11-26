package com.example.teleconsultationbackend.Service;

import com.example.teleconsultationbackend.DTO.DoctorFetchDetails;
import com.example.teleconsultationbackend.DTO.PrescriptionDetails;
import com.example.teleconsultationbackend.Entity.*;
import com.example.teleconsultationbackend.Repository.*;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HospitalServiceImplementationTest {

    @InjectMocks
    private HospitalServiceImplementation hospitalService;

    @Mock
    private HospitalRepository hospitalRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private PrescriptionRepository prescriptionRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private GlobalAdminRepository globalAdminRepository;

    @Mock
    private QueuesRepository queuesRepository; // Add this mock

    private Hospital hospital;
    private Doctor doctor;
    private Patient patient;
    private Prescription prescription;
    private User doctorUser;
    private User patientUser;

    @BeforeEach
    void setUp() {
        // Initialize the mock entities
        MockitoAnnotations.openMocks(this);

        // Create and initialize Patient's User
        patientUser = new User();
        patientUser.setId(1L);
        patientUser.setFirstName("Jane");
        patientUser.setLastName("Doe");
        patientUser.setRole("Patient");
        patientUser.setEmail("jane.doe@example.com");
        patientUser.setPhone("123-456-7890");
        patientUser.setGender('F');
        patientUser.setAddress("123 Main St");
        patientUser.setCity("New York");
        patientUser.setPincode(10001L);
        patientUser.setDob(new Date());

        // Create and initialize Doctor's User
        doctorUser = new User();
        doctorUser.setId(2L);
        doctorUser.setFirstName("Dr. John");
        doctorUser.setLastName("Doe");
        doctorUser.setRole("Doctor");
        doctorUser.setEmail("john.doe@example.com");
        doctorUser.setPhone("987-654-3210");
        doctorUser.setGender('M');
        doctorUser.setAddress("456 Doctor Rd");
        doctorUser.setCity("New York");
        doctorUser.setPincode(10002L);
        doctorUser.setDob(new Date());

        // Create and initialize Patient
        patient = new Patient();
        patient.setId(1L);
        patient.setUser(patientUser);
        patient.setBlood_group("O+");
        patient.setHeight("5'6\"");
        patient.setWeight("60kg");
        patient.setAadhar_number("1234-5678-9101");

        // Create and initialize Doctor with a Hospital
        doctor = new Doctor();
        doctor.setId(1L);
        doctor.setRegistrationNumber("DOC123");
        doctor.setUser(doctorUser);
        doctor.setDepartment(new Department());

        // Set hospital for the doctor (avoid NPE)
        hospital = new Hospital();
        hospital.setHospital_id(1L);
        hospital.setName("City Hospital");
        hospital.setLocation("New York");
        hospital.setPhone("123-456-7890");
        hospital.setDoctors(Collections.singletonList(doctor));
        doctor.setHospital(hospital); // Set the hospital for the doctor to avoid NPE

        // Create and initialize Prescription
        Set<String> medicines = new HashSet<>();
        medicines.add("Paracetamol");
        medicines.add("Aspirin");

        prescription = new Prescription(
                new Date(), // consultationDate
                "Patient is recovering well", // medical_findings
                medicines, // medicine
                "Follow up in 1 week", // remark
                new Date(), // followUpDate
                doctor, // doctor
                patient // patient
        );
    }

    @AfterEach
    void tearDown() {
        // Reset mock behavior
        reset(hospitalRepository, doctorRepository, prescriptionRepository, patientRepository, departmentRepository, globalAdminRepository, queuesRepository);
    }

    @Test
    void totalHospitals_ShouldReturnCorrectCount() {
        // Given
        when(hospitalRepository.get_count()).thenReturn(5);

        // When
        int result = hospitalService.total_hospitals();

        // Then
        assertEquals(5, result);
        verify(hospitalRepository, times(1)).get_count();
    }

    @Test
    void createHospital_ShouldCreateHospitalWhenAdminExists() {
        // Given
        Long adminId = 1L;
        Hospital hospital = new Hospital();
        GlobalAdmin globalAdmin = new GlobalAdmin();
        when(globalAdminRepository.findById(adminId)).thenReturn(Optional.of(globalAdmin)); // Mock admin found
        when(hospitalRepository.save(hospital)).thenReturn(hospital);

        // When
        hospitalService.createHospital(adminId, hospital);

        // Then
        verify(hospitalRepository, times(1)).save(hospital);
    }

    @Test
    void createHospital_ShouldThrowExceptionWhenAdminNotFound() {
        // Given
        Long adminId = 1L;
        Hospital hospital = new Hospital();
        when(globalAdminRepository.findById(adminId)).thenReturn(Optional.empty()); // Mock admin not found

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> hospitalService.createHospital(adminId, hospital));
    }

    @Test
    void addDepartments_ShouldAddExistingDepartment() {
        // Given
        Long hospitalId = 1L;
        String departmentName = "Cardiology";
        Department department = new Department();
        department.setName(departmentName);
        hospital.setDepartments(new ArrayList<>());
        when(hospitalRepository.findById(hospitalId)).thenReturn(Optional.of(hospital));
        when(departmentRepository.findDepartmentByName(departmentName)).thenReturn(department);
        when(queuesRepository.findQueueByDepartment(department)).thenReturn(null); // Mock this call to return null

        // When
        hospitalService.addDepartments(hospitalId, departmentName);

        // Then
        assertTrue(hospital.getDepartments().contains(department));
    }

    @Test
    void addDepartments_ShouldCreateNewDepartmentIfNotExists() {
        // Given
        Long hospitalId = 1L;
        String departmentName = "Neurology";
        hospital.setDepartments(new ArrayList<>());
        when(hospitalRepository.findById(hospitalId)).thenReturn(Optional.of(hospital));
        when(departmentRepository.findDepartmentByName(departmentName)).thenReturn(null); // Return null when department does not exist
        Department newDepartment = new Department();
        when(departmentRepository.save(any(Department.class))).thenReturn(newDepartment);
        when(queuesRepository.findQueueByDepartment(any(Department.class))).thenReturn(null); // Mock return value for queue

        // When
        hospitalService.addDepartments(hospitalId, departmentName);

        // Then
        assertEquals(1, hospital.getDepartments().size());
        verify(departmentRepository, times(1)).save(any(Department.class));
        verify(queuesRepository, times(1)).findQueueByDepartment(any(Department.class)); // Verify this call
    }

    @Test
    void getDoctorsListOfAHospital_ShouldReturnDoctorDetails() {
        // Given
        Long hospitalId = 1L;
        when(doctorRepository.getDoctorsByHospitalID(hospitalId)).thenReturn(Collections.singletonList(doctor));

        // When
        List<DoctorFetchDetails> result = hospitalService.getDoctorsListOfAHospital(hospitalId);

        // Then
        assertEquals(1, result.size());
        assertEquals("Dr. John", result.get(0).getFirstName());
        assertEquals("Doe", result.get(0).getLastName());
    }

    @Test
    void getHospitalTotalConsultations_ShouldReturnPrescriptionDetails() {
        // Given
        Long hospitalId = 1L;
        List<Doctor> doctors = Collections.singletonList(doctor);
        when(doctorRepository.getDoctorsByHospitalID(hospitalId)).thenReturn(doctors);
        when(prescriptionRepository.getAllPrescription(doctors)).thenReturn(Collections.singletonList(prescription));

        // When
        List<PrescriptionDetails> result = hospitalService.getHospitalTotalConsultations(hospitalId);

        // Then
        assertEquals(1, result.size());
        assertEquals("Dr. John Doe", result.get(0).getDoctorName());
        assertEquals("Jane Doe", result.get(0).getPatientName());
        assertTrue(result.get(0).getMedicine().contains("Paracetamol"));
    }
}
