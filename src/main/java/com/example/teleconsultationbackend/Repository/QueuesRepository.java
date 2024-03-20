package com.example.teleconsultationbackend.Repository;

import com.example.teleconsultationbackend.Entity.Department;
import com.example.teleconsultationbackend.Entity.Queues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QueuesRepository extends JpaRepository<Queues, Long> {
    Queues findQueueByDepartment(Department department);
}
