package com.qf.spb1905.thread_more;

import com.qf.config.ThreadLocalDataSource;
import com.qf.dao.IUserDao;
import com.qf.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ThreadMoreApplicationTests {
    @Autowired
    private UserServiceImpl userService;

    //@Autowired
    private DataSource dataSource;

    @Test
    public void contextLoads() {
//		System.out.println(dataSource.getClass());
        ThreadLocalDataSource.add("d1");
        System.out.println(userService.getUserList());
    }

}
