package com.iarasantos.services;

import com.iarasantos.config.FileStorageConfig;
import com.iarasantos.exceptions.FileStorageException;
import com.iarasantos.exceptions.MyFileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {
    private final Path fileStorageLocation;

    //injection through constructor
    @Autowired
    public FileStorageService(FileStorageConfig fileStorageConfig) {
        //normalize used to scape spaces
        Path path = Paths.get(fileStorageConfig.getUploadDir())
                .toAbsolutePath().normalize();
        this.fileStorageLocation = path;

        // if directory doesn't exist, create
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception e) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be saved", e);
        }
    }

    public String storeFile(MultipartFile file){
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            //validate filename
            if(fileName.contains("..")){
                throw new FileStorageException("Sorry " + fileName + " contains invalid path sequence");
            }
            //define file recording location
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            //save overwriting
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (Exception e) {
            throw new FileStorageException("Could not store file " + fileName + ". Please, try again!", e);
        }
    }

    public Resource loadFileAsResource(String filename) {
        try {
            Path filePath = this.fileStorageLocation.resolve(filename).normalize();
            Resource resource = new UrlResource((filePath.toUri()));
            if(resource.exists()) return resource;
            else throw new MyFileNotFoundException("File " + filename + " not found");
        }catch (Exception e) {
            throw new MyFileNotFoundException("File " + filename + " not found", e);
        }
    }
}
