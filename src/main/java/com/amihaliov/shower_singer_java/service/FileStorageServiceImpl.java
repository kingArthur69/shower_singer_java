package com.amihaliov.shower_singer_java.service;

import com.amihaliov.shower_singer_java.model.FileStorageProperties;
import com.amihaliov.shower_singer_java.repository.FileStoragePropertiesRepository;
import org.apache.commons.compress.utils.FileNameUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final Path fileStorageLocation;

    private final FileStoragePropertiesRepository fileStoragePropertiesRepository;

    public FileStorageServiceImpl(FileStorageProperties fileStorageProperties,
                                  FileStoragePropertiesRepository fileStoragePropertiesRepository) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getSaveDir()).toAbsolutePath().normalize();
        this.fileStoragePropertiesRepository = fileStoragePropertiesRepository;

        try {
            if (!Files.exists(this.fileStorageLocation)) {
                Files.createDirectory(this.fileStorageLocation);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Could not create directory for uploaded files", e);
        }
    }

    @Override
    public String storeFile(MultipartFile file, UUID userId, String fileType) {
        try {
            String fileName = createFileName(file.getOriginalFilename(), userId, fileType);

            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return saveFileProperties(userId, fileType, fileName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String storeFile(Path file, UUID userId, String fileType) {
        try {
            String fileName = createFileName(file.getFileName().toString(), userId, fileType);

            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file, targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return saveFileProperties(userId, fileType, fileName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String createFileName(String originalFilename, UUID userId, String fileType) {
        if (originalFilename == null || originalFilename.contains("..")) {
            throw new IllegalArgumentException("Filename contains invalid path sequence: " + originalFilename);
        }

        String fileExtension = FileNameUtils.getExtension(originalFilename);
        return fileType + "_" + userId + "." + fileExtension;
    }

    private String saveFileProperties(UUID userId, String fileType, String fileName) {
        FileStorageProperties fileProperties = fileStoragePropertiesRepository.findByUserIdAndFileType(userId, fileType);

        if (fileProperties != null) {
            fileProperties.setFileName(fileName);
            fileStoragePropertiesRepository.save(fileProperties);
        } else {
            FileStorageProperties newFileProperties = new FileStorageProperties();
            newFileProperties.setUserId(userId);
            newFileProperties.setFileName(fileName);
            newFileProperties.setSaveDir(this.fileStorageLocation.toString());
            newFileProperties.setFileType(fileType);
            fileStoragePropertiesRepository.save(newFileProperties);
        }

        return fileName;
    }

    @Override
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("File not found " + fileName);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getFileName(UUID userId, String fileType) {
        return fileStoragePropertiesRepository.getFileNameByUserIdAndFileType(userId, fileType);
    }

    @Override
    public List<String> findFilesForUser(UUID id) {
        return fileStoragePropertiesRepository.getFileNamesByUserId(id);
    }
}
