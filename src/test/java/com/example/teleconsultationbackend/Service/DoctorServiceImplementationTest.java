package com.example.teleconsultationbackend.Service;

import com.example.teleconsultationbackend.DTO.DoctorDetails;
import com.example.teleconsultationbackend.DTO.DoctorFetchDetails;
import com.example.teleconsultationbackend.Entity.Department;
import com.example.teleconsultationbackend.Entity.Doctor;
import com.example.teleconsultationbackend.Entity.Hospital;
import com.example.teleconsultationbackend.Entity.User;
import com.example.teleconsultationbackend.Repository.DepartmentRepository;
import com.example.teleconsultationbackend.Repository.DoctorRepository;
import com.example.teleconsultationbackend.Repository.HospitalRepository;
import com.example.teleconsultationbackend.Repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DoctorServiceImplementationTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private HospitalRepository hospitalRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DoctorServiceImplementation doctorService;

    private DoctorDetails doctorDetails;
    private Doctor doctor;
    private DoctorFetchDetails doctorFetchDetails;
    private Department department;
    private Hospital hospital;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize mock data
        department = new Department();
        department.setId(1L);
        department.setName("Cardiology");

        hospital = new Hospital();
        hospital.setHospital_id(1L);
        hospital.setName("City Hospital");

        user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPhone("1234567890");
        user.setCity("City");
        user.setAddress("123 Main St");

        doctor = new Doctor();
        doctor.setId(1L);
        doctor.setDepartment(department);
        doctor.setHospital(hospital);
        doctor.setUser(user);
        doctor.setRegistrationNumber("REG123");
        doctor.setRole("Doctor");
        doctor.setOnline_status(false);

        doctorDetails = new DoctorDetails();
        doctorDetails.setFirstName("John");
        doctorDetails.setLastName("Doe");
        doctorDetails.setEmail("john.doe@example.com");
        doctorDetails.setPhoneNumber("1234567890");
        doctorDetails.setCity("City");
        doctorDetails.setAddr("123 Main St");
        doctorDetails.setPincode(1234L);
        doctorDetails.setHospitalID(1L);
        doctorDetails.setDepartmentName("Cardiology");
        doctorDetails.setTitle("Dr.");
        doctorDetails.setRole("Doctor");
        doctorDetails.setRegistration_number("REG123");
        doctorDetails.setAadhar_number("AADHAR123");
    }

    @AfterEach
    void tearDown() {
        // Optionally clean up resources
    }

    @Test
    void total_doctors() {
        // Given
        when(doctorRepository.get_count()).thenReturn(10);

        // When
        int totalDoctors = doctorService.total_doctors();

        // Then
        assertEquals(10, totalDoctors);
        verify(doctorRepository, times(1)).get_count();
    }

    @Test
    void addDoctor() {
        // Given
        when(departmentRepository.findDepartmentByName(doctorDetails.getDepartmentName())).thenReturn(department);
        when(hospitalRepository.getHospitalsByHospital_id(doctorDetails.getHospitalID())).thenReturn(hospital);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(doctorRepository.save(any(Doctor.class))).thenReturn(doctor);

        // When
        DoctorDetails result = doctorService.addDoctor(doctorDetails);

        // Then
        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        verify(departmentRepository, times(1)).findDepartmentByName(doctorDetails.getDepartmentName());
        verify(hospitalRepository, times(1)).getHospitalsByHospital_id(doctorDetails.getHospitalID());
        verify(userRepository, times(1)).save(any(User.class));
        verify(doctorRepository, times(1)).save(any(Doctor.class));
    }

    @Test
    void getDoctorByPhoneNumber() {
        // Given
        when(doctorRepository.findByPhoneNumber(doctorDetails.getPhoneNumber())).thenReturn(doctor);

        // When
        DoctorFetchDetails result = doctorService.getDoctorByPhoneNumber(doctorDetails.getPhoneNumber());

        // Then
        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("john.doe@example.com", result.getEmail());
        verify(doctorRepository, times(1)).findByPhoneNumber(doctorDetails.getPhoneNumber());
    }

    @Test
    void updateDoctorDetails() {
        // Given
        DoctorFetchDetails updatedDetails = new DoctorFetchDetails();
        updatedDetails.setPhoneNumber("1234567890");
        updatedDetails.setFirstName("Jane");
        updatedDetails.setLastName("Doe");
        updatedDetails.setPincode(54321L);
        updatedDetails.setCity("New City");
        updatedDetails.setAddr("456 Main St");
        updatedDetails.setTitle("Dr.");
        updatedDetails.setEmail("jane.doe@example.com");

        when(doctorRepository.findByPhoneNumber(updatedDetails.getPhoneNumber())).thenReturn(doctor);

        // When
        DoctorFetchDetails result = doctorService.updateDoctorDetails(updatedDetails);

        // Then
        assertNotNull(result);
        assertEquals("Jane", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals(54321L, result.getPincode());
        verify(doctorRepository, times(1)).findByPhoneNumber(updatedDetails.getPhoneNumber());
        verify(doctorRepository, times(1)).save(any(Doctor.class));
    }

    @Test
    void setDoctorOnlineStatusHelper() {
        // Given
        when(doctorRepository.findDoctorById(doctor.getId())).thenReturn(doctor);

        // When
        doctorService.setDoctorOnlineStatusHelper(doctor.getId());

        // Then
        assertTrue(doctor.isOnline_status());
        verify(doctorRepository, times(1)).findDoctorById(doctor.getId());
        verify(doctorRepository, times(1)).save(any(Doctor.class));
    }

    @Test
    void setDoctorOfflineStatusHelper() {
        // Given
        when(doctorRepository.findDoctorById(doctor.getId())).thenReturn(doctor);

        // When
        doctorService.setDoctorOfflineStatusHelper(doctor.getId());

        // Then
        assertFalse(doctor.isOnline_status());
        verify(doctorRepository, times(1)).findDoctorById(doctor.getId());
        verify(doctorRepository, times(1)).save(any(Doctor.class));
    }
}
