package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.aop.IsLogin;
import com.qf.service.IAddressService;
import com.qf.service.ICartService;
import com.qf.service.IOrderDetailService;
import com.qf.service.IOrderService;
import com.qf.utils.OrderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import  com.qf.entity.*;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/order")
public class OrderController {

    @Reference
    private IAddressService addressService;

    @Reference
    private ICartService cartService;

    @Autowired
   private OrderUtils orderUtils;

    @Reference
    private IOrderService orderService;

    @Reference
    private IOrderDetailService orderDetailService;

    @RequestMapping(value = "/toOrder")
    @IsLogin(mustUser = true)
    public String toOrder(User user, ModelMap map){

        // 1.先根据用户id查询地址
        List<Address> addressList = addressService.getAddressListByUid(user.getId());

        // 2.查询出购物车的信息
        List<Cart> cartList = cartService.getCartList(null, user);

        // 3.算出总价
        Double totalPrice = 0.0;
        for(Cart cart:cartList){
            totalPrice+=cart.getSubTotal();
        }

        // 4.把数据放入到Map中
        map.put("addressList",addressList);
        map.put("cartList",cartList);
        map.put("totalPrice",totalPrice);

        // 5. 跳转到页面
        return "toOrder";
    }


    @IsLogin(mustUser = true)
    @RequestMapping(value = "/addOrder")
    public String addOrder(Integer aId,User user){

        // 1.添加订单
        String orderId = orderService.addOrder(aId, user);

        // 2.跳转到支付页面

        return "redirect:/pay?oid="+orderId;
    }
    @RequestMapping(value = "/getOrderList")
    @IsLogin(mustUser = true)
    public String getOrderList(User user,ModelMap map){
        List<Order> orderList = orderService.getOrderListByUserId(user.getId());
        map.put("orderList",orderList);

        return "orderList";
    }
}
