package com.noobzy.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public abstract class Task implements Serializable {


    protected String state;

    protected LocalDateTime finishTime;

    /**
     * 处理结果
     */
    protected String resultMsg;

}
