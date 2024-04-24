package com.example.teleconsultationbackend.Repository;

import com.example.teleconsultationbackend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByPhoneAndRole(String phone,String role);

    @Query("SELECT COUNT(*) FROM User")
    Long getTotalUserCount();

    @Query(value = "SELECT u FROM User u")
    List<User> getUsersDetails();
}
