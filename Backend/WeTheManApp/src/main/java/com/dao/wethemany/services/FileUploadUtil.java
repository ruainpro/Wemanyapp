package com.dao.wethemany.services;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {
    
    public static String saveFile(String uploadDir, String fileName,
            MultipartFile multipartFile) throws IOException {
    	
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    	String fullFilename=timestamp.getTime()+fileName;
        Path uploadPath = Paths.get(uploadDir);
         
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
         
        try (InputStream inputStream = multipartFile.getInputStream()) {
            
            Path filePath = uploadPath.resolve(fullFilename);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {        
            throw new IOException("Could not save image file: " + fileName, ioe);
        }    
        
        return fullFilename;
    }
}