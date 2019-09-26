package com.qf.service_api.impl;

import com.qf.dao.IUserDao;
import com.qf.entity.User;
import com.qf.service_api.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDao userDao;
    
    @Cacheable(value = "user",key = "'List'")
    @Override
    public List<User> getUserList() {
        System.out.println("查询数据库");
        List<User> userList = userDao.select(null);
        return userList;
    }
    @CacheEvict(value = "user",key = "'List'")
    @CachePut(value ="user" ,key = "'id_'+#user.id")
    @Override
    public User addUser(User user) {
        userDao.insertSelective(user);
        return user;
    }
    @Cacheable(value = "user",key = "'id_'+#id")
    @Override
    public User getUserById(Integer id) {
        return userDao.selectByPrimaryKey(id);
    }
    @Caching(evict = {
            @CacheEvict(value = "user",key = "'List'"),
            @CacheEvict(value = "user",key = "'id_'+#user.id")
    })
    @Override
    public int updateUser(User user) {
        return userDao.updateByPrimaryKey(user);
    }
    @CacheEvict(value = "user",key = "'List'")
    @Override
    public int deleteUser(Integer id) {
        return userDao.deleteByPrimaryKey(id);
    }
}
