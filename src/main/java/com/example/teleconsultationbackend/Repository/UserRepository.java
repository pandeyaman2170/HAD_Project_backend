package com.example.teleconsultationbackend.Repository;

import com.example.teleconsultationbackend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByPhoneAndRole(String phone,String role);

}
