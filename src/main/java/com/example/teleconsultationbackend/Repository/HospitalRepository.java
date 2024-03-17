package com.example.teleconsultationbackend.Repository;

import com.example.teleconsultationbackend.Entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface HospitalRepository extends JpaRepository<Hospital,Long> {
    List<Hospital> findByAdminId(Long global_admin_id);

    @Query("SELECT h FROM Hospital h WHERE h.hospital_id = ?1 AND h.admin.id= ?2")
    Hospital findByIdAndGlobalAdminId(Long hospital_id, Long global_admin_id);

    @Transactional
    @Modifying
    @Query("DELETE FROM Hospital h WHERE h.hospital_id = ?1 AND h.admin.id = ?2")
    void delete_hospital_by_id(Long hospital_id, Long admin_id);


}