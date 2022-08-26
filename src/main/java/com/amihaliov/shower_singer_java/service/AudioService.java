package com.amihaliov.shower_singer_java.service;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface AudioService {

    Path composeAudio(MultipartFile voice, MultipartFile music);
    Path composeAudio(Path voice, Path music);
}
