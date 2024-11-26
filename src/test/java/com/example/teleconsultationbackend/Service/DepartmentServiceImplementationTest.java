package com.example.teleconsultationbackend.Service;

import com.example.teleconsultationbackend.Entity.Department;
import com.example.teleconsultationbackend.Entity.Doctor;
import com.example.teleconsultationbackend.Entity.Hospital;
import com.example.teleconsultationbackend.Repository.DepartmentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DepartmentServiceImplementationTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentServiceImplementation departmentService;

    private Department department;
    private Hospital hospital;
    private Doctor doctor;

    @BeforeEach
    void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);  // Initialize mock objects

        // Initialize mock data
        department = new Department();
        department.setId(1L);
        department.setName("Cardiology");

        hospital = new Hospital();
        hospital.setHospital_id(1L);
        hospital.setName("City Hospital");

        doctor = new Doctor();
        doctor.setId(1L);
        doctor.setOnline_status(true);
        department.setDoctors(Collections.singletonList(doctor));
    }

    @AfterEach
    void tearDown() {
        // Optionally clean up resources
    }

    @Test
    void getAllHospitals() {
        // Given
        List<Hospital> hospitals = Collections.singletonList(hospital);
        when(departmentRepository.findHospitalsByDepartmentID(department.getId())).thenReturn(hospitals);

        // When
        List<Hospital> result = departmentService.getAllHospitals(department.getId());

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("City Hospital", result.get(0).getName());
        verify(departmentRepository, times(1)).findHospitalsByDepartmentID(department.getId());
    }

    @Test
    void getDepartmentIdByDepartmentName() {
        // Given
        when(departmentRepository.findDepartmentByName("Cardiology")).thenReturn(department);

        // When
        Long departmentId = departmentService.getDepartmentIdByDepartmentName("Cardiology");

        // Then
        assertNotNull(departmentId);
        assertEquals(department.getId(), departmentId);
        verify(departmentRepository, times(1)).findDepartmentByName("Cardiology");
    }

    @Test
    void getDepartmentIdByDepartmentName_DepartmentNotFound() {
        // Given
        when(departmentRepository.findDepartmentByName("Neurology")).thenReturn(null);

        // When
        Long departmentId = departmentService.getDepartmentIdByDepartmentName("Neurology");

        // Then
        assertNull(departmentId);
        verify(departmentRepository, times(1)).findDepartmentByName("Neurology");
    }

    @Test
    void getOnlineDoctorHelper() {
        // Given
        when(departmentRepository.findDepartmentById(department.getId())).thenReturn(department);

        // When
        Long onlineDoctorId = departmentService.getOnlineDoctorHelper(department.getId());

        // Then
        assertNotNull(onlineDoctorId);
        assertEquals(doctor.getId(), onlineDoctorId);
        verify(departmentRepository, times(1)).findDepartmentById(department.getId());
    }

    @Test
    void getOnlineDoctorHelper_NoOnlineDoctor() {
        // Given
        doctor.setOnline_status(false);  // Set doctor as offline
        department.setDoctors(Collections.singletonList(doctor));  // Reset department with offline doctor
        when(departmentRepository.findDepartmentById(department.getId())).thenReturn(department);

        // When
        Long onlineDoctorId = departmentService.getOnlineDoctorHelper(department.getId());

        // Then
        assertNull(onlineDoctorId);
        verify(departmentRepository, times(1)).findDepartmentById(department.getId());
    }
}
