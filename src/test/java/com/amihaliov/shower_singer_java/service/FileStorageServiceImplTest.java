package com.amihaliov.shower_singer_java.service;

import com.amihaliov.shower_singer_java.model.FileStorageProperties;
import com.amihaliov.shower_singer_java.repository.FileStoragePropertiesRepository;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FileStorageServiceImplTest {

//    @Test
//    void save() throws IOException {
//        File file = new File("src/test/resources/audio1.mp3");
//        FileInputStream input = new FileInputStream(file);
//
//        MultipartFile multipartFile = new MockMultipartFile("file",
//                file.getName(), "audio/mpeg", IOUtils.toByteArray(input));
//
//        FileStorageProperties fileStorageProperties = new FileStorageProperties();
//        fileStorageProperties.setSaveDir(Path.of("").toAbsolutePath() + "\\src\\test\\resources\\");
//        FileStorageServiceImpl fileStorageService = new FileStorageServiceImpl(fileStorageProperties,null);
//        String save = fileStorageService.storeFile(multipartFile, UUID.randomUUID(), "test");
//
//    }
}