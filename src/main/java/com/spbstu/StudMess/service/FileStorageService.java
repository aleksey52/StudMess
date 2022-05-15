package com.spbstu.StudMess.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
public class FileStorageService {

    private final Path path = Paths.get("fileStorage");

    public void init() {
        try {
            Files.createDirectory(path);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    public void save(MultipartFile multipartFile) {
        try {
            Files.copy(multipartFile.getInputStream(), this.path.resolve(multipartFile.getOriginalFilename()));
        } catch (IOException e) {
            throw new RuntimeException("Could not store the file. Error:" + e.getMessage());
        }
    }

    public Resource load(String filename) {
        Path file = path.resolve(filename);
        try {
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file.");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error:" + e.getMessage());
        }
    }

    public Stream<Path> load() {
        try {
            return Files.walk(this.path, 1)
                    .filter(path -> !path.equals(this.path))
                    .map(this.path::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files.");
        }
    }

    public void clear() {
        FileSystemUtils.deleteRecursively(path.toFile());
    }
}
