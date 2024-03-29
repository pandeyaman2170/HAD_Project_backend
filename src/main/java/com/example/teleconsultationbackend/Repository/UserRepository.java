package com.example.teleconsultationbackend.Repository;

import com.example.teleconsultationbackend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByPhoneAndRole(String phone,String role);

    @Query("SELECT COUNT(*) FROM User")
    Long getTotalUserCount();

}
