package com.anonymous63.crs.services.impl;

import com.anonymous63.crs.services.FileService;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileServiceImpl implements FileService {
    public FileServiceImpl() {
        super();
    }

    public String uploadImage(String path, MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path filePath = Paths.get(path + fileName);
        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException("Could not save image file: " + fileName, e);
        }
        return fileName;
    }

    public InputStream getResource(String path, String FileName) throws FileNotFoundException {
        Path filePath = Paths.get(path + FileName);
        try {
            return Files.newInputStream(filePath);
        } catch (IOException e) {
            throw new FileNotFoundException("Could not load image file: " + FileName);
        }
    }
}
