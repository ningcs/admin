package com.zhongqi.dao.impl;

import com.zhongqi.dao.CommonFileDao;
import com.zhongqi.dto.FileInfo;
import com.zhongqi.entity.CommonFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ningcs on 2017/9/1.
 */
@Repository
public class CommonFileDaoImpl implements CommonFileDao{

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public void add(FileInfo fileInfo) {
        Map<String,Object> params=new HashMap<>();

        String sql ="insert into CommonFile(fileMD5,contentType,fileName,suffix,filePath,createTime) " +
                "values(:fileMD5,:contentType,:fileName,:suffix,:filePath,:createTime)";
        params.put("fileMD5",fileInfo.getFileMD5());
        params.put("createTime",new Date());
        params.put("contentType",fileInfo.getContentType());
        params.put("fileName",fileInfo.getFileName());
        params.put("suffix",fileInfo.getSuffix());
        params.put("filePath",fileInfo.getFilePath());
        jdbcTemplate.update(sql,params);
    }

    @Override
    public CommonFile findByFileMD5(String fileMD5) {
        Map<String,Object> params=new HashMap<>();
        String sql ="select * from CommonFile where fileMD5=:fileMD5";
        params.put("fileMD5",fileMD5);
        CommonFile commonFile =null;
        try {
            commonFile =jdbcTemplate.queryForObject(sql,params,new BeanPropertyRowMapper<CommonFile>(CommonFile.class));
        }catch (Exception e){
            e.printStackTrace();
        }
        return commonFile;
    }
}
