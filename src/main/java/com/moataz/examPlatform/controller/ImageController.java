package com.moataz.examPlatform.controller;


import com.moataz.examPlatform.service.FileServices;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("location")
public class ImageController {

    final private FileServices fileServices;
    @Value("${project.location}")
    String path;


    @GetMapping("/{image_name}")
    public ResponseEntity<byte[]> getImage(@PathVariable("image_name") String img) throws IOException {
        FileInputStream image = fileServices.getResourceFile(path, img);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image.readAllBytes());
    }
}