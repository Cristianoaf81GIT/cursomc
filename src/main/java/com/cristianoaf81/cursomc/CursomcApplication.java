package com.cristianoaf81.cursomc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cristianoaf81.cursomc.services.S3Service;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

  @Autowired
  private S3Service s3Service;

  private String UserPictureDir = System.getProperty("user.home") + "/Pictures";
  private String fileName = "203575.jpg";

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
    String fileToUpload = UserPictureDir + "/" + fileName;
    s3Service.uploadFile(fileToUpload);
	}

}
