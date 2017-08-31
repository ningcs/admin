package com.zhongqi.dao;

import com.zhongqi.entity.CommonFile;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by songrenfei on 2017/2/17.
 */
public interface CommonFileJpaDao extends JpaRepository<CommonFile,Integer> {

    public CommonFile findByFileMD5(String fileMD5);

}
