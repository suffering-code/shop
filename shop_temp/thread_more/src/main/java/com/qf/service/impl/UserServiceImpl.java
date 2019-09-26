package com.qf.service.impl;
import com.qf.dao.IUserDao;
import  com.qf.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl {

    @Autowired
    private IUserDao userDao;

    public List<User> getUserList(){
        return userDao.getUserList();
    };
}
