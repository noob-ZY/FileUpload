package com.noobzy.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.noobzy.config.FileObjectSerializer;
import lombok.Data;

import java.io.File;
import java.time.LocalDateTime;

@Data
public class FileTask extends Task{

    @JSONField(serializeUsing = FileObjectSerializer.class)
    private File file;


    /**
     * 上传保存时间
     */
    private LocalDateTime uploadTime;

}
