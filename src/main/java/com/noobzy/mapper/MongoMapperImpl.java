package com.noobzy.mapper;

import com.noobzy.entity.SaveRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


//TODO change to @Mapper
@Component
public class MongoMapperImpl implements MongoMapper {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     *
     * @param md5
     * @param fileSize
     * @return true if same file exists
     */
    public Boolean chkFileExists(String md5, long fileSize) {
        return mongoTemplate.exists(
                Query.query(Criteria
                        .where("MD5").is(md5)
                        .and("fileSize").is(fileSize))
                ,"savedFiles");
    }

    public void saveNewFile(String fileName, String md5, long fileSize, String fileClass, LocalDateTime saveTime) {
        SaveRecord saveRecord = new SaveRecord();
        saveRecord.setFileName(fileName);
        saveRecord.setFileSize(fileSize);
        saveRecord.setMD5(md5);
        saveRecord.setSaveTime(saveTime);
        saveRecord.setFileClass(fileClass);
        mongoTemplate.insert(saveRecord, "savedFiles");
    }

}
