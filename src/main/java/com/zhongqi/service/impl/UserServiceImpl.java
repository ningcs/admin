package com.zhongqi.service.impl;

import com.zhongqi.dao.UserDao;
import com.zhongqi.entity.User;
import com.zhongqi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ningcs on 2017/8/30.
 */
@Service
public class UserServiceImpl  implements UserService{

    @Autowired
    private UserDao userDao;
    @Override
    public User login(String name, String password) {
        return userDao.login(name,password);
    }
}
