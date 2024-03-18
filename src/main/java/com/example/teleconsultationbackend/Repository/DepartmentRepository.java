package com.example.teleconsultationbackend.Repository;

import com.example.teleconsultationbackend.Entity.Department;
import com.example.teleconsultationbackend.Entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department,Long> {
//    Department findByName(String name);
//
//    Optional<Hospital> findById(Department department);
}
