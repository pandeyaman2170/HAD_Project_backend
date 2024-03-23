package com.example.teleconsultationbackend.Service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StorageService {

    String uploadFile(MultipartFile multipartFile, long patientId);

    byte[] downloadFile(String fileName);

    List<String> allFilesPatientS3(String patientId);

    List<String> allFilesDoctorS3(String patientId);

    String deleteFile(String fileName);

    String deleteAllFiles(String patientId);
}
