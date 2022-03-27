package com.noobzy.taskHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
public class AsyncHandler {

    @Autowired
    private FileHandler fileHandler;

    @Async
    public void fileClassify(List<File> files) {


        for (File file : files) {
            try {
                fileHandler.execute(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
