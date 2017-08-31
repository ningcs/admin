package com.zhongqi.dao.impl;

import com.zhongqi.dao.UserDao;
import com.zhongqi.entity.User;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by ningcs on 2017/8/30.
 */
@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public User login(String name, String password) {
        Map<String,Object> params =new HashedMap();
        String sql ="select * from User where name=:name and password=:password";
        params.put("name",name);
        params.put("password",password);
        User user =null;
        try {
            user =jdbcTemplate.queryForObject(sql,params,new BeanPropertyRowMapper<User>(User.class));
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }
}
