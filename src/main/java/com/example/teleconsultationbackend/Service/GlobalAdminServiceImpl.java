package com.example.teleconsultationbackend.Service;

import com.example.teleconsultationbackend.Entity.GlobalAdmin;
import com.example.teleconsultationbackend.Entity.Hospital;
import com.example.teleconsultationbackend.Repository.GlobalAdminInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GlobalAdminServiceImpl implements GlobalAdminService{
    @Autowired
    private GlobalAdminInterface globalAdminInterface;

    @Override
    public void createHospital(GlobalAdmin globalAdmin){
        globalAdminInterface.save(globalAdmin);
        System.out.println("TGuhsnohdinkd");
    }

}
