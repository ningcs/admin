package com.zhongqi.service.common;


import com.zhongqi.dto.common.FileInfo;

public interface FileService {

    //存储文件
    public FileInfo saveFile(FileInfo fileInfo);

    //获取文件
    public FileInfo getFile(String fileMD5);

    //添加文件
    public void add(FileInfo fileInfo);


}
