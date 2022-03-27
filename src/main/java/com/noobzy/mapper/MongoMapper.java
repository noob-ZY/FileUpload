package com.noobzy.mapper;

import java.time.LocalDateTime;

public interface MongoMapper {

    /**
     *
     * @param md5
     * @param fileSize
     * @return true if same file exists
     */
    public Boolean chkFileExists(String md5, long fileSize);

    public void saveNewFile(String fileName, String md5, long fileSize, String fileClass, LocalDateTime saveTime);

}
