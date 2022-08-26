package com.amihaliov.shower_singer_java.controller;

import com.amihaliov.shower_singer_java.service.AudioService;
import com.amihaliov.shower_singer_java.service.FileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/files")
public class AudioFileController {
    public final AudioService audioService;
    public final FileStorageService fileStorageService;

    public AudioFileController(AudioService audioService, FileStorageService fileStorageService) {
        this.audioService = audioService;
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/")
    public ResponseEntity<Resource> uploadFiles(
            @RequestParam("voiceFile") MultipartFile voiceFile, @RequestParam("songFile") MultipartFile songFile,
            @RequestParam("userId") String userId) {
        try {

            UUID uuid = UUID.fromString(userId);
            fileStorageService.storeFile(voiceFile, uuid, "voice");
            fileStorageService.storeFile(songFile, uuid, "song");

            Path audio = audioService.composeAudio(voiceFile, songFile);

            String combinedSong = fileStorageService.storeFile(audio, uuid, "combinedSong");
            Resource resource = fileStorageService.loadFileAsResource(combinedSong);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .header(HttpHeaders.CONTENT_TYPE, "audio/mpeg")
                    .body(resource);

        } catch (Exception e) {
            log.error("Error Uploading Files", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<String>> getFiles(@PathVariable UUID id) {
        try {
            List<String> files = fileStorageService.findFilesForUser(id);
            return ResponseEntity.ok().body(files);
        } catch (Exception e) {
            log.error("Error Getting Files", e);
            return ResponseEntity.notFound().build();
        }
    }
}
