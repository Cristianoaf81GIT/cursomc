package com.cristianoaf81.cursomc.services;

import java.io.File;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest; 
import com.amazonaws.AmazonServiceException;
import com.amazonaws.AmazonClientException;

@Service
public class S3Service {

  private Logger LOG = LoggerFactory.getLogger(S3Service.class);
  
  @Autowired
  private AmazonS3 s3Client;

  @Value("${s3.bucket}")
  private String bucketName;

  public void uploadFile(String localFilePath) {
    try {    
      File file = new File(localFilePath);
      LOG.info("Iniciando upload...");
      PutObjectRequest putObj = new PutObjectRequest(bucketName, "teste.jpg", file);
      s3Client.putObject(putObj);
      LOG.info("Upload finalizado...");
    } catch(AmazonServiceException ase) {
      LOG.error("AmazonServiceException: " + ase.getErrorMessage());
      LOG.error("Status code: " + ase.getErrorCode());
    } catch(AmazonClientException ace) {
      LOG.error("AmazonClientException: " + ace.getMessage());
    }
  }
}
