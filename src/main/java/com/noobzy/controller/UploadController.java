package com.noobzy.controller;


import com.noobzy.taskHandler.AsyncHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("file")
@RestController
@EnableAsync
public class UploadController {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd--HH-mm-ss-SSS");

    @Autowired
    private AsyncHandler handler;

    @PostMapping("/upload")
    public void uploadHandle(@RequestParam MultipartFile[] files) {

        String path = System.getProperty("user.dir");
        List<File> tempFiles;

        if (files != null && files.length > 0){

            tempFiles = new ArrayList<>(files.length);

            for (MultipartFile file : files) {

                LocalDateTime localDateTime = LocalDateTime.now();

                try {
                    String fileName = file.getOriginalFilename();
                    File tempDir = new File(path + "/temp");
                    if (!tempDir.exists()) {
                        tempDir.mkdirs();
                    }

                    File tempSave = new File(path + "/temp/[" + localDateTime.format(formatter) + "]" + fileName);
                    file.transferTo(tempSave);
                    tempFiles.add(tempSave);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            handler.fileClassify(tempFiles);
        }

    }

}
