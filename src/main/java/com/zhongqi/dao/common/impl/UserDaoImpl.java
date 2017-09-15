package com.zhongqi.dao.common.impl;

import com.zhongqi.dao.common.UserDao;
import com.zhongqi.entity.common.User;
import com.zhongqi.util.SHA256;
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

    @Override
    public void updatePassword(String password,Integer userId) {
        Map<String,Object> params =new HashedMap();
        String sql="update User set password=:password where id=:userId";
        params.put("userId",userId);
        params.put("password", SHA256.encrypt(password));
        jdbcTemplate.update(sql,params);
    }
}
