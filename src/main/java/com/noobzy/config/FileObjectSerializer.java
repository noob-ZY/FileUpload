package com.noobzy.config;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;

public class FileObjectSerializer implements ObjectSerializer {
    @Override
    public void write(JSONSerializer jsonSerializer, Object o, Object o1, Type type, int i) throws IOException {
        File file = (File) o;
        String fileName = ((File) o).getName();
        jsonSerializer.write(fileName);
    }
}
