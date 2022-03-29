package com.noobzy.taskHandler;

import com.noobzy.entity.FileTask;
import com.noobzy.webSocket.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;

@Component
public class AsyncHandler {

    @Autowired
    private FileHandler fileHandler;

    @Async
    public void fileClassify(List<FileTask> fileTasks, HttpSession session) {


        for (FileTask fileTask : fileTasks) {
            try {
                fileHandler.execute(fileTask);
                WebSocket.sendTaskInfo(session, fileTask);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
