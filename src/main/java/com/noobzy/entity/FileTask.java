package com.noobzy.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FileTask extends Task{

    /**
     * 文件名，因为打算向页面暂时信息，懒得处理，直接使用String
     */
    private String fileName;


    /**
     * 上传保存时间
     */
    private LocalDateTime uploadTime;

}
