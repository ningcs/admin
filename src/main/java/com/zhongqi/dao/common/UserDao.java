package com.zhongqi.dao.common;

import com.zhongqi.entity.common.User;

/**
 * Created by ningcs on 2017/8/30.
 */
public interface UserDao {
    public User login(String name, String password);

    //修改密码
    public void updatePassword(String password,Integer userId);
}

