package com.amihaliov.shower_singer_java.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

public interface FileStorageService {

    String storeFile(MultipartFile file, UUID userId, String fileType);

    String storeFile(Path file, UUID userId, String fileType);

    Resource loadFileAsResource(String fileName);

    String getFileName(UUID userId, String fileType);

    List<String> findFilesForUser(UUID id);
}
