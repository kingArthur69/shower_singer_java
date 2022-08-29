package com.amihaliov.shower_singer_java.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@Data
@ConfigurationProperties(prefix = "python")
@ConfigurationPropertiesScan
public class PythonServiceProperties {

    private String baseUrl;
}
