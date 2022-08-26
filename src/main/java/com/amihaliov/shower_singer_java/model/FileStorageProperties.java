package com.amihaliov.shower_singer_java.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "user_files")
@ConfigurationProperties(prefix = "file", ignoreUnknownFields = false)
@ConfigurationPropertiesScan
public class FileStorageProperties {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Type(type="org.hibernate.type.UUIDCharType")
    @Column(name = "file_id", columnDefinition = "char(36)")
    private UUID fileId;

    @Type(type="org.hibernate.type.UUIDCharType")
    @Column(name = "user_id", columnDefinition = "char(36)")
    private UUID userId;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "save_dir")
    private String saveDir;

}
