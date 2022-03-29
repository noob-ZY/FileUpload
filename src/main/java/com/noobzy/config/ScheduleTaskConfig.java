package com.noobzy.config;

import com.noobzy.constant.PathConstant;
import com.noobzy.entity.FileTask;
import com.noobzy.taskHandler.FileHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
@EnableScheduling
public class ScheduleTaskConfig {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd--HH-mm-ss-SSS");

    private static File autoDir = new File(PathConstant.PATH_BASE + "/" + PathConstant.PATH_AUTO_MANAGE);

    static {
        if (!autoDir.exists()) {
            try {
                autoDir.mkdirs();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Autowired
    private FileHandler fileHandler;

    @Scheduled(cron = "0/5 * * * * ?")
    public void autoFileClassify() {

        File[] files = autoDir.listFiles();

        if (files != null) {
            for (File file : files) {
                String fileName = file.getName();

                if (fileName.matches("\\[\\d\\d\\d\\d-\\d\\d-\\d\\d--\\d\\d-\\d\\d-\\d\\d-\\d\\d\\d\\].*")) {
                    fileName = fileName.replaceFirst("\\[\\d\\d\\d\\d-\\d\\d-\\d\\d--\\d\\d-\\d\\d-\\d\\d-\\d\\d\\d\\]" , "[" + LocalDateTime.now().format(formatter) + "]");
                } else {
                    fileName = "[" + LocalDateTime.now().format(formatter) + "]" + fileName;
                }

                try {
                    File newFile = new File(file.getParentFile().getCanonicalPath()+ "/" + fileName);
                    file.renameTo(newFile);
                    FileTask fileTask = new FileTask();

                    fileTask.setFile(newFile);
                    fileTask.setUploadTime(LocalDateTime.now());
                    fileTask.setState("pending");
                    fileHandler.execute(fileTask);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }


}
