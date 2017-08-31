package com.zhongqi.service.impl;

import com.zhongqi.dao.CommonFileJpaDao;
import com.zhongqi.dto.FileInfo;
import com.zhongqi.entity.CommonFile;
import com.zhongqi.service.FileService;
import com.zhongqi.util.BaseUtils;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Created by jordan on 16/10/24.
 */
@Service("fileService")
public class FileServiceImpl implements FileService {

    Logger logger = Logger.getLogger(FileServiceImpl.class);

    @Value("${fileBasePath}")
    String sysBasePath;

    @Autowired
    private CommonFileJpaDao commonFileDao;

    @Override
    public FileInfo saveFile(FileInfo fileInfo) {

        // fileMD5
        String result = "";

        String filePath = "";

        // 文件保存路径
        String yearMonth = BaseUtils.formatYearMonth(new Date());
        String day = BaseUtils.formatDay(new Date());

        byte[] fileBytes = Base64.decodeBase64(fileInfo.getBase64String());

        String fileMD5 = DigestUtils.md5DigestAsHex(fileBytes);
        logger.info("fileMD5 = "+fileMD5);

        CommonFile commonFileCheck = commonFileDao.findByFileMD5(fileMD5);

        if(commonFileCheck == null) {

            filePath = File.separator + yearMonth + File.separator + day + File.separator + fileMD5 + fileInfo.getSuffix();
            String fileFullPath = sysBasePath + filePath;

            logger.info("fileFullPath = "+fileFullPath);

            File file = new File(fileFullPath);

            try {
                FileUtils.writeByteArrayToFile(file, fileBytes);
                CommonFile commonFile = new CommonFile();
                commonFile.setFileMD5(fileMD5);
                commonFile.setContentType(fileInfo.getContentType());
                commonFile.setFileName(fileInfo.getFileName());
                commonFile.setSuffix(fileInfo.getSuffix());
                commonFile.setFilePath(filePath);
                commonFile.setCreateTime(new Date());

                commonFile = commonFileDao.save(commonFile);

                if(commonFile!=null){
                    Integer fileId = commonFile.getId();
                    if (fileId != null && fileId != 0) {
                        result = fileMD5;
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            result = fileMD5;
            filePath = commonFileCheck.getFilePath();
        }

        fileInfo.setFileMD5(result);
        fileInfo.setFilePath(filePath);

        return fileInfo;
    }

    @Override
    public FileInfo getFile(String fileMD5) {
        CommonFile commonFile = commonFileDao.findByFileMD5(fileMD5);
        if(commonFile!=null){
            FileInfo fileInfo = new FileInfo();

            String filePath = commonFile.getFilePath();
            String fileFullPath = sysBasePath + filePath;

            File file = new File(fileFullPath);

            try {
                byte[] fileBytes = FileUtils.readFileToByteArray(file);
                String base64String = Base64.encodeBase64String(fileBytes);

                fileInfo.setFileMD5(commonFile.getFileMD5());
                fileInfo.setBase64String(base64String);
                fileInfo.setContentType(commonFile.getContentType());
                fileInfo.setFileName(commonFile.getFileName());
                fileInfo.setSuffix(commonFile.getSuffix());
                fileInfo.setFilePath(commonFile.getFilePath());

            } catch (IOException e) {
                e.printStackTrace();
            }

            return fileInfo;
        }else{
            return null;
        }

    }

    public static void main(String[] args){
        System.out.println(File.separator);
    }

}
