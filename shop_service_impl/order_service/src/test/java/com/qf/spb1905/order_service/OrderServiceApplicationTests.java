package com.qf.spb1905.order_service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Address;
import com.qf.service.IAddressService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceApplicationTests {
    @Reference
    private IAddressService addressService;
    @Test
    public void contextLoads() {
    }
    @Test
    public void tsetAdd() {
        Address address = new Address();
        address.setPhone("456");
        address.setAddress("大学城");
        address.setIsDefault(1);
        address.setUid(16);
        address.setUsername("qf");
        addressService.add(address);
    }

}
