package com.example.teleconsultationbackend.Controller;

import com.example.teleconsultationbackend.Service.StorageService;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static java.net.HttpURLConnection.HTTP_OK;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AWSController {
    @Autowired
    private StorageService storageService;

    @PostMapping("/uploadFile/{patientId}")
    public String uploadFile(@RequestParam(value = "file") MultipartFile multipartFile, @PathVariable String patientId) {
        System.out.println("Inside API to upload file");
        if(multipartFile.isEmpty())
            return "File is empty";
        return storageService.uploadFile(multipartFile, Long.parseLong(patientId));
    }

    @GetMapping("/downloadFile/{fileName}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String fileName) {

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.add("Content-Disposition",
                "inline;filename=" + fileName);

        return ResponseEntity
                .status(HTTP_OK)
                .contentType(MediaType.APPLICATION_PDF)
                .headers(httpHeaders)
                .body(storageService.downloadFile(fileName));
    }

    @GetMapping("/getAllFilesPatient/{patientId}")
    public List<String> allFilesPatientS3(@PathVariable String patientId) {
        return storageService.allFilesPatientS3(patientId);
    }

    @GetMapping("/getAllFilesDoctor/{patientId}")
    public List<String> allFilesDoctorS3(@PathVariable String patientId) {
        return storageService.allFilesDoctorS3(patientId);
    }

    @DeleteMapping("/deleteFile/{fileName}")
    public String deleteFile(@PathVariable String fileName) {
        return storageService.deleteFile(fileName);
    }

    @DeleteMapping("/deleteAllFiles/{patientId}")
    public String deleteAllFiles(@PathVariable String patientId) {
        return storageService.deleteAllFiles(patientId);
    }




}
