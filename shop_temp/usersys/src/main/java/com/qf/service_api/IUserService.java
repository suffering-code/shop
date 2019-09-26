package com.qf.service_api;

import com.qf.entity.User;

import java.util.List;

public interface IUserService {

    public List<User> getUserList();

    public User addUser(User user);

    public User getUserById(Integer id);

    public int updateUser(User user);

    public int deleteUser(Integer id);
}
