package com.example.teleconsultationbackend.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StorageServiceImplementationTest {

    @Mock
    private AmazonS3 s3Client;

    @InjectMocks
    private StorageServiceImplementation storageService;

    private final String BUCKET_NAME = "test-bucket";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(storageService, "bucketName", BUCKET_NAME);
    }

    @Test
    void uploadFile_Success() throws IOException {
        // Arrange
        String fileName = "test.txt";
        long patientId = 123L;
        byte[] content = "test content".getBytes();
        MockMultipartFile multipartFile = new MockMultipartFile(
                "file",
                fileName,
                "text/plain",
                content
        );

        // Act
        String result = storageService.uploadFile(multipartFile, patientId);

        // Assert
        verify(s3Client, times(1)).putObject(any(PutObjectRequest.class));
        assertTrue(result.contains("Successfully Uploaded"));
        assertTrue(result.contains(String.valueOf(patientId)));
    }

    @Test
    void downloadFile_Success() throws IOException {
        // Arrange
        String fileName = "test.txt";
        byte[] expectedContent = "test content".getBytes();
        S3Object s3Object = new S3Object();
        s3Object.setObjectContent(new ByteArrayInputStream(expectedContent));

        when(s3Client.getObject(BUCKET_NAME, fileName)).thenReturn(s3Object);

        // Act
        byte[] result = storageService.downloadFile(fileName);

        // Assert
        assertArrayEquals(expectedContent, result);
        verify(s3Client, times(1)).getObject(BUCKET_NAME, fileName);
    }

    @Test
    void allFilesPatientS3_Success() {
        // Arrange
        String patientId = "123";
        List<S3ObjectSummary> summaries = new ArrayList<>();

        S3ObjectSummary summary1 = new S3ObjectSummary();
        summary1.setKey(patientId + "_file1.txt");
        S3ObjectSummary summary2 = new S3ObjectSummary();
        summary2.setKey(patientId + "_file2.txt");
        S3ObjectSummary summary3 = new S3ObjectSummary();
        summary3.setKey("456_file3.txt");

        summaries.add(summary1);
        summaries.add(summary2);
        summaries.add(summary3);

        ListObjectsV2Result listResult = new ListObjectsV2Result();
        listResult.getObjectSummaries().addAll(summaries);

        when(s3Client.listObjectsV2(BUCKET_NAME)).thenReturn(listResult);

        // Act
        List<String> result = storageService.allFilesPatientS3(patientId);

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.contains(patientId + "_file1.txt"));
        assertTrue(result.contains(patientId + "_file2.txt"));
        assertFalse(result.contains("456_file3.txt"));
    }

    @Test
    void deleteFile_Success() {
        // Arrange
        String fileName = "test.txt";

        // Act
        String result = storageService.deleteFile(fileName);

        // Assert
        verify(s3Client, times(1)).deleteObject(BUCKET_NAME, fileName);
        assertEquals("File deleted Successfully", result);
    }

    @Test
    void deleteAllFiles_Success() {
        // Arrange
        String patientId = "123";
        List<S3ObjectSummary> summaries = new ArrayList<>();

        S3ObjectSummary summary1 = new S3ObjectSummary();
        summary1.setKey(patientId + "_file1.txt");
        S3ObjectSummary summary2 = new S3ObjectSummary();
        summary2.setKey(patientId + "_file2.txt");

        summaries.add(summary1);
        summaries.add(summary2);

        ListObjectsV2Result listResult = new ListObjectsV2Result();
        listResult.getObjectSummaries().addAll(summaries);

        when(s3Client.listObjectsV2(BUCKET_NAME)).thenReturn(listResult);

        // Act
        String result = storageService.deleteAllFiles(patientId);

        // Assert
        verify(s3Client, times(2)).deleteObject(eq(BUCKET_NAME), any(String.class));
        assertTrue(result.contains("Successfully flushed"));
        assertTrue(result.contains(patientId));
    }

    @Test
    void downloadFile_ThrowsException() {
        // Arrange
        String fileName = "test.txt";
        when(s3Client.getObject(BUCKET_NAME, fileName)).thenThrow(new RuntimeException("S3 Error"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> storageService.downloadFile(fileName));
    }

    @Test
    void uploadFile_ThrowsException() {
        // Arrange
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.txt",
                "text/plain",
                new byte[0]
        );
        when(s3Client.putObject(any(PutObjectRequest.class))).thenThrow(new RuntimeException("Upload Error"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> storageService.uploadFile(file, 123L));
    }
}