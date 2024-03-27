package com.example.teleconsultationbackend.Repository;

import com.example.teleconsultationbackend.Entity.GlobalAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GlobalAdminRepository extends JpaRepository<GlobalAdmin, Long> {

    @Query("SELECT g FROM GlobalAdmin g WHERE g.globalAdminUsername = ?1")
    GlobalAdmin findGlobalAdminByUserName(String userName);
}
