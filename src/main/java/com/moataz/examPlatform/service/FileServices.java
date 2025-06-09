package com.moataz.examPlatform.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public interface FileServices {

    String uploadFile(String path , MultipartFile file) throws IOException;

    FileInputStream getResourceFile(String path , String name) throws FileNotFoundException;
}