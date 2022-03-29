package com.noobzy.taskHandler;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.noobzy.entity.Task;
import org.apache.tomcat.util.json.JSONParser;
import org.bson.json.JsonObject;
import org.bson.json.JsonWriter;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class TaskEncoder implements Encoder.Text<Task>{
    @Override
    public String encode(Task task) throws EncodeException {
        try {
            return JSON.toJSONString(task);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
