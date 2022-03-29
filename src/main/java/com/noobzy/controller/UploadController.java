package com.noobzy.controller;


import com.noobzy.constant.PathConstant;
import com.noobzy.entity.FileTask;
import com.noobzy.taskHandler.AsyncHandler;
import com.noobzy.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.time.LocalDateTime;
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

        HttpSession session = HttpUtil.getCurrentSession();
        List<FileTask> fileTasks;

        if (files != null && files.length > 0){

            fileTasks = new ArrayList<>(files.length);

            for (MultipartFile file : files) {

                try {
                    //创建文件夹
                    String fileName = file.getOriginalFilename();
                    File tempDir = new File(PathConstant.PATH_BASE + "/" + PathConstant.PATH_TEMP);
                    if (!tempDir.exists()) {
                        tempDir.mkdirs();
                    }

                    //任务信息
                    FileTask fileTask = new FileTask();
                    fileTask.setState("pending");
                    fileTask.setUploadTime(LocalDateTime.now());
                    //[yyyy-MM-dd--HH-mm-ss-SSS]originalFilename.xxx
                    //fileName = "[" + fileTask.getUploadTime().format(formatter) + "]" + fileName;

                    //保存至临时存储文件夹
                    File tempSave = new File(PathConstant.PATH_BASE + "/" + PathConstant.PATH_TEMP + "/" + fileName);
                    file.transferTo(tempSave);

                    fileTask.setFile(tempSave);
                    //添加至任务列表
                    fileTasks.add(fileTask);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            handler.fileClassify(fileTasks, session);
        }

    }

}
