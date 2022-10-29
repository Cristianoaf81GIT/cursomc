package com.cristianoaf81.cursomc.services;

import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.lang.RuntimeException;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest; 
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.AmazonClientException;


@Service
public class S3Service {

  private Logger LOG = LoggerFactory.getLogger(S3Service.class);
  
  @Autowired
  private AmazonS3 s3Client;

  @Value("${s3.bucket}")
  private String bucketName;

  public URI uploadFile(MultipartFile multipartFile) {
    try {
      String fileName = multipartFile.getOriginalFilename();
      InputStream is = multipartFile.getInputStream();
      String contentType = multipartFile.getContentType();
      return uploadFile(is, fileName, contentType);    
    } catch(IOException ie) {
      ie.printStackTrace();
      throw new RuntimeException("Erro de IO: " + ie.getMessage());
    }
  }

  public URI uploadFile(InputStream is, String fileName, String contentType) {            
    try{  
      ObjectMetadata meta = new ObjectMetadata();
      meta.setContentType(contentType);
      LOG.info("Iniciando upload...");
      // PutObjectRequest putObj = new PutObjectRequest(bucketName, file.getName(), file);
      s3Client.putObject(bucketName, fileName, is, meta);
      LOG.info("Upload finalizado...");
      return s3Client.getUrl(bucketName, fileName).toURI();
    } catch(URISyntaxException e) {
      e.printStackTrace();
      throw new RuntimeException("Erro ao converter URL para URI");
    }
  }
}
