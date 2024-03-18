package com.example.teleconsultationbackend.Repository;

import com.example.teleconsultationbackend.Entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department,Long> {

}
