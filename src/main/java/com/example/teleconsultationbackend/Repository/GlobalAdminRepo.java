package com.example.teleconsultationbackend.Repository;

import com.example.teleconsultationbackend.Entity.GlobalAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GlobalAdminRepo extends JpaRepository<GlobalAdmin,Long> {
}
