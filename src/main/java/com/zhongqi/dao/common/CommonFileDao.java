package com.zhongqi.dao.common;

import com.zhongqi.dto.common.FileInfo;
import com.zhongqi.entity.common.CommonFile;

/**
 * Created by ningcs on 2017/9/1.
 */
public interface CommonFileDao {
    public void add(FileInfo fileInfo);

    public CommonFile findByFileMD5(String fileMD5);
}
