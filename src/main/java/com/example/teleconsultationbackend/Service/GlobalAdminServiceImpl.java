package com.example.teleconsultationbackend.Service;

import com.example.teleconsultationbackend.Entity.GlobalAdmin;
import com.example.teleconsultationbackend.Repository.GlobalAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GlobalAdminServiceImpl implements GlobalAdminService{
    @Autowired
    private GlobalAdminRepository globalAdminRepository;

    @Override
    public void createHospital(GlobalAdmin globalAdmin){
        globalAdminRepository.save(globalAdmin);
        System.out.println("TGuhsnohdinkd");
    }

}
