package com.edu.miu.service;

import com.amazonaws.services.s3.AmazonS3;
import com.edu.miu.service.impl.AwsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

/**
 * @author gasieugru
 */
@ExtendWith(MockitoExtension.class)
public class AwsServiceTest {

    @InjectMocks
    AwsServiceImpl awsService;

    @Mock
    AmazonS3 s3client;

    private MockMultipartFile file;

    @BeforeEach
    void setUp() {
        file = new MockMultipartFile(
                "cx5",
                "cx5.png",
                MediaType.TEXT_PLAIN_VALUE,
                "Mazda cx5".getBytes()
        );
    }

    @DisplayName("JUnit test for uploadFile method.")
    @Test
    void uploadFile_Success() {
        given(s3client.putObject(any()))
                .willReturn(any());

        String result = awsService.uploadFile(file, "/car");
        assertEquals(result != null, true);
    }

    @DisplayName("JUnit test for deleteFileFromS3Bucket method.")
    @Test
    void deleteFileFromS3Bucket_Success() {
//        given(s3client.deleteObject(any()))
//                .getMock();
        doNothing().when(s3client).deleteObject(any());

        String result = awsService.deleteFileFromS3Bucket("https://aws.amazone.com/car");
        assertEquals(result != null, true);
    }
}
