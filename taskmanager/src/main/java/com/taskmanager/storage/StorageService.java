package com.taskmanager.storage;

import org.springframework.web.multipart.MultipartFile;

import com.taskmanager.dto.StoredFileDto;

public interface StorageService {
    
    StoredFileDto store(MultipartFile file, String folder);

    byte[] read(String storagePath);

    void delete(String publicId, String storagePath);

}
