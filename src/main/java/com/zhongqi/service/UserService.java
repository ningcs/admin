package com.zhongqi.service;

import com.zhongqi.entity.User;

/**
 * Created by ningcs on 2017/8/30.
 */
public interface UserService {
    public User login(String name, String password);
}
