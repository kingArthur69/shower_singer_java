package com.amihaliov.shower_singer_java.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

@Slf4j
@Service
public class PythonServiceImpl implements PythonService {

    private final WebClient client;

    public PythonServiceImpl() {
        client = WebClient.builder()
                .baseUrl("http://localhost:5000")
                .build();
    }
    @Override
    public Path mergeAudio(Path vocals, Path song) {
        try {
            MultipartBodyBuilder builder = new MultipartBodyBuilder();
            builder.part("vocals", new FileSystemResource(vocals));
            builder.part("song", new FileSystemResource(song));

            return makePost(BodyInserters.fromMultipartData(builder.build()));
        } catch (Exception e) {
            log.error("Error merging Audio", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Path mergeAudio(MultipartFile vocals, MultipartFile song) {
        try {
            LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            map.add("vocals", vocals.getResource());
            map.add("song", song.getResource());

            return makePost(BodyInserters.fromMultipartData(map));
        } catch (Exception e) {
            log.error("Error merging Audio", e);
            throw new RuntimeException(e);
        }
    }

    private Path makePost(BodyInserters.MultipartInserter inserter) throws IOException {
        Flux<DataBuffer> flux = client.post()
                .uri("/audio")
                .body(inserter)
                .accept(MediaType.ALL)
                .retrieve()
                .bodyToFlux(DataBuffer.class);

        Path tmp = Files.createTempFile("temp_" + UUID.randomUUID(), ".mp3");

        DataBufferUtils.write(flux, tmp, StandardOpenOption.WRITE).block();
        return tmp;
    }
}
