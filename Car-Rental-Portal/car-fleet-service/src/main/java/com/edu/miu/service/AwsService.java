package com.edu.miu.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author gasieugru
 */
public interface AwsService {

    String uploadFile(MultipartFile file, String path);

    String deleteFileFromS3Bucket(String fileUrl);

}
