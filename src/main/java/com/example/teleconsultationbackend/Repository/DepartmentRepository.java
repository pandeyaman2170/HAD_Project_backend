package com.example.teleconsultationbackend.Repository;

import com.example.teleconsultationbackend.Entity.Department;
import com.example.teleconsultationbackend.Entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department,Long> {

    @Query("SELECT distinct d.hospitals FROM Department d WHERE d.id=: departmentID")
    List<Hospital> findHospitalsByDepartmentID(Long departmentID);

    @Query("SELECT d FROM Department d WHERE d.name=:deptName")
    Department findDepartmentByName(String deptName);

    @Query("SELECT d FROM Department d WHERE d.id=:departmentID")
    Department findDepartmentById(Long departmentID);
}
