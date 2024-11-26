package com.example.teleconsultationbackend.Service;

import com.example.teleconsultationbackend.DTO.DoctorFetchDetails;
import com.example.teleconsultationbackend.DTO.HospitalCompleteDetail;
import com.example.teleconsultationbackend.Entity.*;
import com.example.teleconsultationbackend.Repository.ConsultationRepository;
import com.example.teleconsultationbackend.Repository.GlobalAdminRepository;
import com.example.teleconsultationbackend.Repository.HospitalRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalAdminServiceImplementationTest {

    @Mock
    private HospitalRepository hospitalRepository;

    @Mock
    private GlobalAdminRepository globalAdminRepository;

    @Mock
    private ConsultationRepository consultationRepository;

    @InjectMocks
    private GlobalAdminServiceImplementation globalAdminService;

    private GlobalAdmin globalAdmin;
    private Hospital hospital;
    private Doctor doctor;
    private DoctorFetchDetails doctorFetchDetails;
    private Department department;
    private HospitalCompleteDetail hospitalCompleteDetail;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create and initialize a GlobalAdmin object
        globalAdmin = new GlobalAdmin();
        globalAdmin.setId(1L);
        globalAdmin.setName("Admin Name");
        globalAdmin.setPhone("1234567890");
        globalAdmin.setEmail("admin@example.com");
        globalAdmin.setGlobalAdminUsername("adminuser");
        globalAdmin.setGlobalAdminPassword("password123");

        // Create and initialize a Department object
        department = new Department();
        department.setId(1L);
        department.setName("Cardiology");

        // Create and initialize a Hospital object
        hospital = new Hospital();
        hospital.setHospital_id(1L);
        hospital.setName("City Hospital");
        hospital.setLocation("City");
        hospital.setPhone("123-456-7890");
        hospital.setAdmin(globalAdmin); // Linking global admin to the hospital
        hospital.setDepartments(List.of(department)); // Add departments to hospital

        // Initialize the doctors list to avoid NullPointerException
        hospital.setDoctors(new ArrayList<>());  // Empty list of doctors

        // Create and initialize a User object for the Doctor
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPhone("123-456-7890");
        user.setEmail("johndoe@example.com");
        user.setCity("New York");
        user.setPincode(10001L);
        user.setAddress("123 Main St");
        user.setRole("doctor");
        user.setTitle("Dr.");

        // Create and initialize a Doctor object and link the User
        doctor = new Doctor();
        doctor.setId(1L);
        doctor.setRegistrationNumber("REG123");
        doctor.setDepartment(department);
        doctor.setHospital(hospital);
        doctor.setUser(user);  // Assign the User to the Doctor

        // Add the doctor to the hospital's doctors list
        hospital.getDoctors().add(doctor);

        // Set up DoctorFetchDetails with values
        doctorFetchDetails = new DoctorFetchDetails();
        doctorFetchDetails.setDoctorId(doctor.getId());
        doctorFetchDetails.setFirstName(user.getFirstName());
        doctorFetchDetails.setLastName(user.getLastName());
        doctorFetchDetails.setHospitalID(hospital.getHospital_id());
        doctorFetchDetails.setPhoneNumber(user.getPhone());
        doctorFetchDetails.setRegistration_number(doctor.getRegistrationNumber());
        doctorFetchDetails.setDob(user.getDob());
        doctorFetchDetails.setGender(user.getGender());
        doctorFetchDetails.setAddr(user.getAddress());
        doctorFetchDetails.setPincode(user.getPincode());
        doctorFetchDetails.setCity(user.getCity());
        doctorFetchDetails.setDepartmentName(department.getName());
        doctorFetchDetails.setRole(user.getRole());

        // Set up HospitalCompleteDetail object with linked data
        hospitalCompleteDetail = new HospitalCompleteDetail();
        hospitalCompleteDetail.setHospital_id(hospital.getHospital_id());
        hospitalCompleteDetail.setName(hospital.getName());
        hospitalCompleteDetail.setLocation(hospital.getLocation());
        hospitalCompleteDetail.setPhone(hospital.getPhone());
        hospitalCompleteDetail.setDepartments(hospital.getDepartments());
        hospitalCompleteDetail.setDoctorFetchDetails(List.of(doctorFetchDetails));
    }


    @AfterEach
    void tearDown() {
        // Optionally clean up resources
    }

    @Test
    void createHospital() {
        // Given
        when(globalAdminRepository.findById(globalAdmin.getId())).thenReturn(Optional.of(globalAdmin));
        when(hospitalRepository.save(hospital)).thenReturn(hospital);

        // When
        globalAdminService.createHospital(globalAdmin.getId(), hospital);

        // Then
        verify(globalAdminRepository, times(1)).findById(globalAdmin.getId());
        verify(hospitalRepository, times(1)).save(hospital);
    }

    @Test
    void viewAllHospital() {
        // Given
        when(hospitalRepository.findByAdminId(globalAdmin.getId())).thenReturn(List.of(hospital));

        // When
        List<Hospital> result = globalAdminService.viewAllHospital(globalAdmin.getId());

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("City Hospital", result.get(0).getName());
        verify(hospitalRepository, times(1)).findByAdminId(globalAdmin.getId());
    }

    @Test
    void updateHospital() {
        // Given
        Hospital updatedHospital = new Hospital();
        updatedHospital.setName("New Hospital Name");
        updatedHospital.setLocation("New Location");

        when(hospitalRepository.findByIdAndGlobalAdminId(hospital.getHospital_id(), globalAdmin.getId())).thenReturn(hospital);
        when(hospitalRepository.save(hospital)).thenReturn(hospital);

        // When
        globalAdminService.updateHospital(globalAdmin.getId(), hospital.getHospital_id(), updatedHospital);

        // Then
        assertEquals("New Hospital Name", hospital.getName());
        assertEquals("New Location", hospital.getLocation());
        verify(hospitalRepository, times(1)).findByIdAndGlobalAdminId(hospital.getHospital_id(), globalAdmin.getId());
        verify(hospitalRepository, times(1)).save(hospital);
    }

    @Test
    void deleteHospital() {
        // Given
        doNothing().when(hospitalRepository).delete_hospital_by_id(hospital.getHospital_id(), globalAdmin.getId());

        // When
        globalAdminService.deleteHospital(globalAdmin.getId(), hospital.getHospital_id());

        // Then
        verify(hospitalRepository, times(1)).delete_hospital_by_id(hospital.getHospital_id(), globalAdmin.getId());
    }

    @Test
    void totalHospitals() {
        // Given
        when(hospitalRepository.findAll()).thenReturn(List.of(hospital));

        // When
        int totalHospitals = globalAdminService.totalHospitals();

        // Then
        assertEquals(1, totalHospitals);
        verify(hospitalRepository, times(1)).findAll();
    }

    @Test
    void totalDoctors() {
        // Given
        hospital.setDoctors(List.of(doctor));
        when(hospitalRepository.findAll()).thenReturn(List.of(hospital));

        // When
        int totalDoctors = globalAdminService.totalDoctors();

        // Then
        assertEquals(1, totalDoctors);
        verify(hospitalRepository, times(1)).findAll();
    }

    @Test
    void totalPatients() {
        // Given
        when(consultationRepository.distinctPatient()).thenReturn(100);

        // When
        int totalPatients = globalAdminService.totalPatients();

        // Then
        assertEquals(100, totalPatients);
        verify(consultationRepository, times(1)).distinctPatient();
    }

    @Test
    void getGlobalAdminByUserName() {
        // Given
        when(globalAdminRepository.findGlobalAdminByUserName(globalAdmin.getGlobalAdminUsername())).thenReturn(globalAdmin);

        // When
        GlobalAdmin result = globalAdminService.getGlobalAdminByUserName(globalAdmin.getGlobalAdminUsername());

        // Then
        assertNotNull(result);
        assertEquals("adminuser", result.getGlobalAdminUsername());
        verify(globalAdminRepository, times(1)).findGlobalAdminByUserName(globalAdmin.getGlobalAdminUsername());
    }

    @Test
    void getAllHospitalDetails() {
        // Given
        when(hospitalRepository.getAllHospitalDetails()).thenReturn(List.of(hospital));
        when(hospitalRepository.findAll()).thenReturn(List.of(hospital));

        // When
        List<HospitalCompleteDetail> result = globalAdminService.getAllHospitalDetails();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("City Hospital", result.get(0).getName());
        assertEquals(1, result.get(0).getDoctorFetchDetails().size());
        assertEquals("Dr.", result.get(0).getDoctorFetchDetails().get(0).getTitle());
        verify(hospitalRepository, times(1)).getAllHospitalDetails();
    }

}
