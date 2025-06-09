package com.moataz.examPlatform.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileServicesImpl implements FileServices {
    @Override
    public String uploadFile(String path, MultipartFile file) throws IOException {
        String filePath = path + File.separator + file.getOriginalFilename();
        File f = new File(path);
        if (!f.exists()) f.mkdirs();
        Files.copy(file.getInputStream() , Paths.get(filePath) , StandardCopyOption.REPLACE_EXISTING);
        return file.getOriginalFilename();
    }

    @Override
    public FileInputStream getResourceFile(String path, String name) throws FileNotFoundException {
        String filePath = path + File.separator + name;
        return new FileInputStream(filePath);
    }
}
