package com.example.teleconsultationbackend.Repository;

import com.example.teleconsultationbackend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInterface extends JpaRepository<User,Long> {



}
