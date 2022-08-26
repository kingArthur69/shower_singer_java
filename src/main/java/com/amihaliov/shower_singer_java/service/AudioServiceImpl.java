package com.amihaliov.shower_singer_java.service;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@Service
public class AudioServiceImpl implements AudioService {
    private final PythonService pythonService;

    public AudioServiceImpl(PythonService pythonService) {
        this.pythonService = pythonService;
    }

    @Override
    public Path composeAudio(MultipartFile voice, MultipartFile song) {
        return pythonService.mergeAudio(voice, song);
    }

    @Override
    public Path composeAudio(Path voice, Path song) {
        return pythonService.mergeAudio(voice, song);
    }
}
