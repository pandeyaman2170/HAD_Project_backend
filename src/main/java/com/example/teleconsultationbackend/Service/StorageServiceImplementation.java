package com.example.teleconsultationbackend.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class StorageServiceImplementation implements StorageService{

    @Value("${application.bucket.name}")
    private String bucketName;

    private final AmazonS3 s3Client;

    public StorageServiceImplementation(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadFile(MultipartFile multipartFile, long patientId) {
        File file;
        try {
            file = convertMultiPartFileToFile(multipartFile);

            String fileName = patientId + "_" + multipartFile.getOriginalFilename() + System.currentTimeMillis() + "_";

            s3Client.putObject(new PutObjectRequest(bucketName, fileName, file));

            return "Successfully Uploaded file to AWS S3. File Name = " + fileName;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public byte[] downloadFile(String fileName) {

        S3Object s3Object = s3Client.getObject(bucketName, fileName);
        S3ObjectInputStream objectContent = s3Object.getObjectContent();

        try {
            return IOUtils.toByteArray(objectContent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }



    private File convertMultiPartFileToFile(MultipartFile multipartFile) throws IOException{
        File file = new File(multipartFile.getOriginalFilename());

        FileOutputStream fos = new FileOutputStream(file);
        fos.write(multipartFile.getBytes());

        fos.close();

        return file;
    }
    @Override
    public List<String> allFilesPatientS3(String patientId) {

        ListObjectsV2Result listObjectsV2Result = s3Client.listObjectsV2(bucketName);
        List<String> allFiles =  listObjectsV2Result.getObjectSummaries().stream()
                .map(S3ObjectSummary::getKey)
                .collect(Collectors.toList());
        List<String> finalList = new ArrayList<>();
        for(String s: allFiles) {
            if(s.startsWith(patientId))
                finalList.add(s);
        }

        return finalList;
    }

    @Override
    public List<String> allFilesDoctorS3(String patientId) {

        ListObjectsV2Result listObjectsV2Result = s3Client.listObjectsV2(bucketName);
        List<String> allFiles =  listObjectsV2Result.getObjectSummaries().stream()
                .map(S3ObjectSummary::getKey)
                .collect(Collectors.toList());
        List<String> finalList = new ArrayList<>();
        for(String s: allFiles) {
            if(s.startsWith(patientId))
                finalList.add(s);
        }

        return finalList;
    }
    @Override
    public String deleteFile(String fileName) {
        s3Client.deleteObject(bucketName, fileName);
        return "File deleted Successfully";
    }
    @Override
    public String deleteAllFiles(String patientId) {

        List<String> allFileNames = this.allFilesDoctorS3(patientId);

        System.out.println("List of all the files for patient " + patientId + " is " + allFileNames.toString());

        for(String fileName: allFileNames) {
            if(fileName.startsWith(patientId))
                this.deleteFile(fileName);
        }

        return "Successfully flushed S3 for patient " + patientId;
    }




}
