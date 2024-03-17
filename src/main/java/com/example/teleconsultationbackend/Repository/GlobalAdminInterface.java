package com.example.teleconsultationbackend.Repository;

import com.example.teleconsultationbackend.Entity.GlobalAdmin;
import com.example.teleconsultationbackend.Entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GlobalAdminInterface extends JpaRepository<GlobalAdmin, Long> {
}
