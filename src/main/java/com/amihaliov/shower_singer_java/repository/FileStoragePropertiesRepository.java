package com.amihaliov.shower_singer_java.repository;

import com.amihaliov.shower_singer_java.model.FileStorageProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface FileStoragePropertiesRepository extends JpaRepository<FileStorageProperties, UUID> {

    FileStorageProperties findByUserIdAndFileType(UUID userId, String fileType);

    @Query("Select a.fileName from FileStorageProperties a where a.userId = :userId and a.fileType = :fileType")
    String getFileNameByUserIdAndFileType(UUID userId, String fileType);

    @Query("Select a.fileName from FileStorageProperties a where a.userId = :userId")
    List<String> getFileNamesByUserId(UUID userId);
}
