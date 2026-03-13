package com.taskmanager.storage;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.taskmanager.dto.StoredFileDto;

@Service
public class LocalStorageService implements StorageService {

    private final Path base = Paths.get("upload");

    @Autowired
    private Cloudinary cloudinary;

    public LocalStorageService(){

        try{
            Files.createDirectories(base);
        } catch(Exception e){
            throw new RuntimeException("Local storage cannot be initialized");
        }

    }

    @Override
    public StoredFileDto store(MultipartFile file, String folder) {

        try {

            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file");
            }

            Path dir = base.resolve(folder);
            Files.createDirectories(dir);

            String originalFileName = file.getOriginalFilename();
            String fileName = UUID.randomUUID() + "_" + originalFileName;

            Path targetPath = dir.resolve(fileName);
            Files.copy(file.getInputStream(), targetPath);

            String localPath = folder + "/" + fileName;

            String publicId = folder + "/" + UUID.randomUUID();

            Map<?, ?> uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "public_id", publicId,
                            "resource_type", "auto"
                    )
            );

            String cloudUrl = uploadResult.get("secure_url").toString();

            return new StoredFileDto(localPath, cloudUrl, publicId);

        } catch (Exception e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }


    @Override
    public byte[] read(String storagePath) {

        try {
            
            Path filePath = base.resolve(storagePath);

            if (!Files.exists(filePath)) {
                throw new RuntimeException("File not found: " + storagePath);
            }

            return Files.readAllBytes(filePath);

        } catch (Exception e) {
            throw new RuntimeException("Failed to read file");
        }
    }

    @Override
    public void delete(String publicId, String storagePath) {

        try {
            Path filePath = base.resolve(storagePath);
            if (Files.exists(filePath)) {
                Files.delete(filePath);
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to delete file on local device");
        }

        try {
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete file on cloud");
        }

    }

}
