package com.amihaliov.shower_singer_java.service;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface PythonService {

    Path mergeAudio(Path vocals, Path song);

    Path mergeAudio(MultipartFile vocals, MultipartFile song);
}
