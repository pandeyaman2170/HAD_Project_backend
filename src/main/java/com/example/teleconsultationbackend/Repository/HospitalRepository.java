package com.example.teleconsultationbackend.Repository;

import com.example.teleconsultationbackend.Entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

public interface HospitalRepository extends JpaRepository<Hospital,Long> {
    List<Hospital> findByAdminId(Long global_admin_id);

    @Query("SELECT h FROM Hospital h WHERE h.hospital_id = ?1 AND h.admin.id= ?2")
    Hospital findByIdAndGlobalAdminId(Long hospital_id, Long global_admin_id);

    @Transactional
    @Modifying
    @Query("DELETE FROM Hospital h WHERE h.hospital_id = ?1 AND h.admin.id = ?2")
    void delete_hospital_by_id(Long hospital_id, Long admin_id);

    Hospital findByPhone(String phone);

    @Query("SELECT h from Hospital h WHERE h.hospital_id = ?1")
    Hospital getHospitalsByHospital_id(Long hospitalID);

    @Query("SELECT h from Hospital h WHERE h.hospitalUserName = ?1")
    Hospital findHospitalsByHospitalUserName(String hospitalUserName);
}
