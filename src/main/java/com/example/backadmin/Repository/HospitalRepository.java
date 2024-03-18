package com.example.backadmin.Repository;

//import com.example.backadmin.Entity.Doctor;
import com.example.backadmin.Entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Long> {
}

//@Repository
//public interface HospitalRepository extends JpaRepository<Hospital, Long> {
//}