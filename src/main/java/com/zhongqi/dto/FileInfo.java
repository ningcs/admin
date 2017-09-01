package com.zhongqi.dto;

/**
 * Created by ningcs on 2017/8/31.
 */
public class FileInfo {
    private String fileMD5;
    private String base64String;
    private String contentType;
    private String fileName;
    private String suffix;
    private String filePath;
    private String suffixName;

    public String getSuffixName() {
        return suffixName;
    }

    public void setSuffixName(String suffixName) {
        this.suffixName = suffixName;
    }

    public FileInfo() {
    }

    public String getFileMD5() {
        return this.fileMD5;
    }

    public void setFileMD5(String fileMD5) {
        this.fileMD5 = fileMD5;
    }

    public String getBase64String() {
        return this.base64String;
    }

    public void setBase64String(String base64String) {
        this.base64String = base64String;
    }

    public String getContentType() {
        return this.contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
