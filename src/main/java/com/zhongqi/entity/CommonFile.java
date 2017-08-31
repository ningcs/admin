package com.zhongqi.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 通用文件
 */
@Entity
@Table(name="CommonFile")
public class CommonFile {


    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", unique = true, nullable = false)
    private Integer id;

    /**
     * 文件MD5值
     */
    @Column(name="fileMD5",nullable=true)
    private String fileMD5;

    /**
     * 文件类型
     */
    @Column(name="contentType",nullable=true)
    private String contentType;

    /**
     * 文件名（原始）
     */
    @Column(name="fileName",nullable=true)
    private String fileName;

    /**
     * 文件后缀名
     */
    @Column(name="suffix",nullable=true)
    private String suffix;

    /**
     * 存储路径
     */
    @Column(name="filePath",nullable=true)
    private String filePath;

    /**
     * 上传日期
     */
    @Column(name="createTime",nullable=true)
    private Date createTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getFileMD5() {
        return fileMD5;
    }

    public void setFileMD5(String fileMD5) {
        this.fileMD5 = fileMD5;
    }



    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }


    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
