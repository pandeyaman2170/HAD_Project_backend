package com.example.teleconsultationbackend.Service;

import com.example.teleconsultationbackend.DTO.DoctorFetchDetails;
import com.example.teleconsultationbackend.DTO.HospitalCompleteDetail;
import com.example.teleconsultationbackend.Entity.Department;
import com.example.teleconsultationbackend.Entity.Doctor;
import com.example.teleconsultationbackend.Entity.GlobalAdmin;
import com.example.teleconsultationbackend.Entity.Hospital;
import com.example.teleconsultationbackend.Repository.ConsultationRepository;
import com.example.teleconsultationbackend.Repository.GlobalAdminRepository;
import com.example.teleconsultationbackend.Repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GlobalAdminServiceImplementation implements GlobalAdminService{
    @Autowired
    HospitalRepository hospitalRepository;

    @Autowired
    GlobalAdminRepository globalAdminRepository;

    @Autowired
    ConsultationRepository consultationRepository;

    @Override
    @Transactional
    public void createHospital(Long admin_id, Hospital hospital) {
        Optional<GlobalAdmin> optionalGlobalAdmin = globalAdminRepository.findById(admin_id);
        if (optionalGlobalAdmin.isPresent()) {
            GlobalAdmin globalAdmin = optionalGlobalAdmin.get();
            hospital.setAdmin(globalAdmin);
            hospitalRepository.save(hospital);
        } else {
            // Handle the case where GlobalAdmin with given ID is not found
            throw new IllegalArgumentException("GlobalAdmin with id " + admin_id + " not found.");
        }
    }

    @Override
    @Transactional
    public List<Hospital> viewAllHospital(Long admin_id)
    {
        return hospitalRepository.findByAdminId(admin_id);
    }

    @Override
    @Transactional
    public void updateHospital(Long admin_id, Long hospital_id, Hospital updatedHospital)
    {
        Hospital hospital = hospitalRepository.findByIdAndGlobalAdminId(hospital_id,admin_id);
        hospital.setName(updatedHospital.getName());
        hospital.setLocation(updatedHospital.getLocation());
        hospitalRepository.save(hospital);
    }

    @Override
    @Transactional
    public void deleteHospital(Long admin_id, Long hospital_id)
    {
        hospitalRepository.delete_hospital_by_id(hospital_id,admin_id);

    }

    @Override
    @Transactional
    public int totalHospitals()
    {
        List<Hospital> hospitals=hospitalRepository.findAll();
        return hospitals.size();
    }

    @Override
    @Transactional
    public int totalDoctors()
    {
        int totalDoctors=0;
        List<Hospital> hospitals=hospitalRepository.findAll();

        for (Hospital hospital : hospitals) {
            totalDoctors += hospital.getDoctors().size();
        }
        return totalDoctors;

    }
    @Override
    @Transactional
    public int totalPatients()
    {
        return consultationRepository.distinctPatient();
    }

    @Override
    @Transactional
    public GlobalAdmin getGlobalAdminByUserName(String userName){
        return globalAdminRepository.findGlobalAdminByUserName(userName);
    }

    @Override
    @Transactional
    public List<HospitalCompleteDetail> getAllHospitalDetails(){
        List<Hospital> hospitals = hospitalRepository.getAllHospitalDetails();
        List<HospitalCompleteDetail> allHospitals = new ArrayList<>();
        for(Hospital hospital: hospitals){
            HospitalCompleteDetail hospitalCompleteDetail = new HospitalCompleteDetail();
            List<Department> departmentsList = (hospital.getDepartments());
            hospitalCompleteDetail.setDepartments(departmentsList);
//            for(Department dep:departmentsList){
//
//            }
            hospitalCompleteDetail.setHospital_id(hospital.getHospital_id());
            hospitalCompleteDetail.setLocation(hospital.getLocation());
            hospitalCompleteDetail.setPhone(hospital.getPhone());
            hospitalCompleteDetail.setName(hospital.getName());

            List<Doctor> doctors = hospital.getDoctors();
            List<DoctorFetchDetails> doclist = new ArrayList<>();
            for(Doctor doctor: doctors){
                DoctorFetchDetails doc = new DoctorFetchDetails();
                doc.setDoctorId(doctor.getId());
                doc.setTitle(doctor.getUser().getTitle());
                doc.setFirstName(doctor.getUser().getFirstName());
                doc.setLastName(doctor.getUser().getLastName());
                doc.setEmail(doctor.getUser().getEmail());
                doc.setHospitalID(doctor.getHospital().getHospital_id());
                doc.setPhoneNumber(doctor.getUser().getPhone());
                doc.setRegistration_number(doctor.getRegistrationNumber());
                doc.setDob(doctor.getUser().getDob());
                doc.setGender(doctor.getUser().getGender());
                doc.setAddr(doctor.getUser().getAddress());
                doc.setPincode(doctor.getUser().getPincode());
                doc.setCity(doctor.getUser().getCity());
                doc.setDepartmentName(doctor.getDepartment().getName());
                doc.setRole(doctor.getUser().getRole());
                doclist.add(doc);
//            System.out.println(doctor.getUser().getFirstName());
            }
            hospitalCompleteDetail.setDoctorFetchDetails(doclist);

            allHospitals.add(hospitalCompleteDetail);
        }

        return allHospitals;
    }







}
