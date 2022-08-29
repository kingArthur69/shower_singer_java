package com.amihaliov.shower_singer_java;

import com.amihaliov.shower_singer_java.model.FileStorageProperties;
import com.amihaliov.shower_singer_java.model.PythonServiceProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class,
        PythonServiceProperties.class
})
public class ShowerSingerJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShowerSingerJavaApplication.class, args);
    }

}
