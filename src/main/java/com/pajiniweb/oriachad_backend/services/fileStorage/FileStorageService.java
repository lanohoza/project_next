package com.pajiniweb.oriachad_backend.services.fileStorage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    public String saveFile(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Generate a unique filename
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return fileName; // Return the unique filename
    }

    public void deleteFile(String filePath) throws IOException {
        if (filePath == null || filePath.isEmpty()) {
            return;
        }

        Path path = Paths.get(uploadDir, filePath);
        if (Files.exists(path)) {
            Files.delete(path);
        } else {
            throw new IOException("File not found: " + filePath);
        }
    }

    public Path getFilePath(String relativePath) {
        return Paths.get(uploadDir).resolve(relativePath);
    }

    public byte[] loadFile(String relativePath) throws IOException {
        Path filePath = getFilePath(relativePath);
        if (Files.exists(filePath)) {
            return Files.readAllBytes(filePath);
        } else {
            throw new IOException("File not found: " + relativePath);
        }
    }
}
