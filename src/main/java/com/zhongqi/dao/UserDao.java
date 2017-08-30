package com.zhongqi.dao;

import com.zhongqi.entity.User;

/**
 * Created by ningcs on 2017/8/30.
 */
public interface UserDao {
    public User login(String name, String password);
}

