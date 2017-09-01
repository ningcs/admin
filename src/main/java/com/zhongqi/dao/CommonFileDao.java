package com.zhongqi.dao;

import com.zhongqi.dto.FileInfo;
import com.zhongqi.entity.CommonFile;

/**
 * Created by ningcs on 2017/9/1.
 */
public interface CommonFileDao {
    public void add(FileInfo fileInfo);

    public CommonFile findByFileMD5(String fileMD5);
}
