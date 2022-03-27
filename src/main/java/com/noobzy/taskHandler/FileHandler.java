package com.noobzy.taskHandler;


import com.noobzy.mapper.MongoMapper;
import com.noobzy.util.FileTypeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDateTime;

@Component
public class FileHandler {

    @Autowired
    private MongoMapper mongoMapper;

    @Transactional(rollbackFor = Exception.class)
    public void execute(File file) throws Exception {

        String path = System.getProperty("user.dir");

        try (FileInputStream fis = new FileInputStream(file)){
            //文件名
            String fileName = file.getName();
            //文件大小
            Long fileSize = file.length();
            //MD5
            String md5DigestAsHex = DigestUtils.md5DigestAsHex(fis);
            fis.close();
            String fileClass = null;

            //文件类型
            try (FileInputStream nfis = new FileInputStream(file)){
                fileClass = FileTypeUtil.getFileTypeByMagicNumber(nfis, fileName);
            } catch (Exception e) {
                throw e;
            }


            System.out.println(fileName + " class:" + fileClass + " MD5:" + md5DigestAsHex + " size:" + file.length());
            //查重 MD5 FileSize
            fis.close();
            if (mongoMapper.chkFileExists(md5DigestAsHex, file.length())) {
                //delete
                System.out.println(file.getName() + " already exists");
                file.delete();
            } else {
                //classify
                File pathDir = new File(path + "/" + fileClass);
                if (!pathDir.exists()) {
                    pathDir.mkdirs();
                }

                File newPosition = new File(path + "/" + fileClass + "/" + fileName);
                file.renameTo(newPosition);
                try {
                    //写入db
                    mongoMapper.saveNewFile(fileName, md5DigestAsHex, fileSize, fileClass, LocalDateTime.now());
                } catch (Exception e) {
                    newPosition = new File(path + "/error/" + fileName);
                    file.renameTo(newPosition);
                }
            }
        } catch (Exception e) {
            throw e;
        }
    }

}
