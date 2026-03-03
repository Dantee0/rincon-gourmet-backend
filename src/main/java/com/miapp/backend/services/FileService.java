package com.miapp.backend.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileService {

    // La carpeta donde se van a guardar las fotos
    private final String uploadDir = "uploads/";

    public String saveFile(MultipartFile file) throws Exception {
        // 1. Verificamos si la carpeta "uploads" existe, sino la creamos
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // 2. Le ponemos un código único (UUID) al nombre por si dos personas suben "foto.jpg"
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);

        // 3. Copiamos el archivo a la carpeta
        Files.copy(file.getInputStream(), filePath);

        // 4. Devolvemos el nuevo nombre para guardarlo en la base de datos
        return fileName;
    }
}