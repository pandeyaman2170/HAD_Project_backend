package com.example.teleconsultationbackend.Service;

import com.example.teleconsultationbackend.Entity.Department;
import com.example.teleconsultationbackend.Entity.Doctor;
import com.example.teleconsultationbackend.Entity.Patient;
import com.example.teleconsultationbackend.Entity.Queues;
import com.example.teleconsultationbackend.Repository.DepartmentRepository;
import com.example.teleconsultationbackend.Repository.DoctorRepository;
import com.example.teleconsultationbackend.Repository.PatientRepository;
import com.example.teleconsultationbackend.Repository.QueuesRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QueueServiceImplTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private QueuesRepository queuesRepository;

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private QueueServiceImpl queueService;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void getAllDoctorsFromDepartment_WithOnlineAndOfflineDoctors_ReturnsOnlyOnlineDoctors() {
        // Arrange
        Long depId = 1L;
        Department department = new Department();

        Doctor onlineDoctor1 = new Doctor();
        onlineDoctor1.setOnline_status(true);
        onlineDoctor1.setId(1L);

        Doctor offlineDoctor = new Doctor();
        offlineDoctor.setOnline_status(false);
        offlineDoctor.setId(2L);

        Doctor onlineDoctor2 = new Doctor();
        onlineDoctor2.setOnline_status(true);
        onlineDoctor2.setId(3L);

        List<Doctor> allDoctors = Arrays.asList(onlineDoctor1, offlineDoctor, onlineDoctor2);
        department.setDoctors(allDoctors);

        when(departmentRepository.findDepartmentById(depId)).thenReturn(department);

        // Act
        List<Doctor> result = queueService.getAllDoctorsFromDepartment(depId);

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.contains(onlineDoctor1));
        assertTrue(result.contains(onlineDoctor2));
        assertFalse(result.contains(offlineDoctor));
    }

    @Test
    void getAllWaitingPatientByInQueueByDepartmentId_WithValidQueue_ReturnsCorrectPosition() {
        // Arrange
        Long patientId = 1L;
        Patient patient = new Patient();
        Queues queue = new Queues();

        Patient patient1 = new Patient();
        Patient patient2 = new Patient();
        Patient patient3 = new Patient();

        List<Patient> patients = Arrays.asList(patient1, patient2, patient3);
        queue.setPatients(patients);
        patient.setQueues(queue);

        when(patientRepository.findPatientById(patientId)).thenReturn(patient);

        // Act
        int position = queueService.getAllWaitingPatientByInQueueByDepartmentId(patientId);

        // Assert
        assertEquals(0, position);
    }

    @Test
    void getAllPatientByDepNameQueue_WithValidDepartment_ReturnsPatientList() {
        // Arrange
        String depName = "Cardiology";
        Department department = new Department();
        Queues queue = new Queues();
        List<Patient> patients = new ArrayList<>();
        patients.add(new Patient());
        patients.add(new Patient());
        queue.setPatients(patients);

        when(departmentRepository.findDepartmentByName(depName)).thenReturn(department);
        when(queuesRepository.findQueueByDepartment(department)).thenReturn(queue);

        // Act
        List<Patient> result = queueService.getAllPatientByDepNameQueue(depName);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void getQueuesTop_WithNonEmptyQueue_ReturnsFirstPatientId() {
        // Arrange
        String depName = "Cardiology";
        Department department = new Department();
        Queues queue = new Queues();
        Patient firstPatient = new Patient();
        firstPatient.setId(1L);

        List<Patient> patients = new ArrayList<>();
        patients.add(firstPatient);
        queue.setPatients(patients);

        when(departmentRepository.findDepartmentByName(depName)).thenReturn(department);
        when(queuesRepository.findQueueByDepartment(department)).thenReturn(queue);

        // Act
        Long result = queueService.getQueuesTop(depName);

        // Assert
        assertEquals(1L, result);
    }

    @Test
    void getQueuesTop_WithEmptyQueue_ReturnsNull() {
        // Arrange
        String depName = "Cardiology";
        Department department = new Department();
        Queues queue = new Queues();
        queue.setPatients(new ArrayList<>());

        when(departmentRepository.findDepartmentByName(depName)).thenReturn(department);
        when(queuesRepository.findQueueByDepartment(department)).thenReturn(queue);

        // Act
        Long result = queueService.getQueuesTop(depName);

        // Assert
        assertNull(result);
    }

    @Test
    void getQueueSizeHelper_WithValidQueue_ReturnsCorrectSize() {
        // Arrange
        String depName = "Cardiology";
        Department department = new Department();
        Queues queue = new Queues();
        List<Patient> patients = Arrays.asList(new Patient(), new Patient(), new Patient());
        queue.setPatients(patients);

        when(departmentRepository.findDepartmentByName(depName)).thenReturn(department);
        when(queuesRepository.findQueueByDepartment(department)).thenReturn(queue);

        // Act
        int result = queueService.getQueueSizeHelper(depName);

        // Assert
        assertEquals(3, result);
    }

    @Test
    void getQueueSizeHelper_WithNullQueue_ReturnsNegativeOne() {
        // Arrange
        String depName = "Cardiology";
        Department department = new Department();

        when(departmentRepository.findDepartmentByName(depName)).thenReturn(department);
        when(queuesRepository.findQueueByDepartment(department)).thenReturn(null);

        // Act
        int result = queueService.getQueueSizeHelper(depName);

        // Assert
        assertEquals(-1, result);
    }
}