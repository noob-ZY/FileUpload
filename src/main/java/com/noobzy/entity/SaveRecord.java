package com.noobzy.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
public class SaveRecord {

    @Id
    private String id;

    private String fileName;

    private Long fileSize;

    private String fileClass;

    private String MD5;

    private LocalDateTime saveTime;

}
