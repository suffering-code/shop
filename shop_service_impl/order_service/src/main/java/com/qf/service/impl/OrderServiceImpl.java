package com.qf.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.qf.config.ThreadLocalDataSource;
import com.qf.dao.IOrderDao;
import com.qf.dao.IOrderDetailDao;
import com.qf.entity.*;
import com.qf.service.IAddressService;
import com.qf.service.ICartService;
import com.qf.service.IOrderService;
import com.qf.utils.OrderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl extends BaseServiecImpl<Order> implements IOrderService {

    @Autowired
    private IOrderDao orderDao;
    @Autowired
    private IOrderDetailDao orderDetailDao;

    @Reference
    private IAddressService addressService;

    @Reference
    private ICartService cartService;

    @Autowired
    private OrderUtils orderUtils;

    @Override
    protected Mapper<Order> getMapper() {
        return orderDao;
    }

    @Override
    public String addOrder(Integer aId, User user) {
        Address address = addressService.getById(aId);

        // 2.查询用户购物车对象
        List<Cart> cartList = cartService.getCartList(null, user);
        // 算出总价
        Double totalPrice = 0.0;
        for(Cart cart:cartList){
            totalPrice+=cart.getSubTotal();
        }

        // 3.生成订单Id
        String orderId = orderUtils.createOrdereId(user.getId());

        // 4.先封装订单对象
        Order order = new Order();
        order.setAddress(address.getAddress());
        order.setPhone(address.getPhone());
        order.setUsername(address.getUsername());
        order.setUid(user.getId());
        order.setState(0);
        order.setCreateTime(new Date());
        order.setTotalPrice(totalPrice);
        order.setId(orderId);

        // 根据用户Id算出数据库
        // 得到userId后四位

        Integer tableNum = setDataSource(user.getId());

        // 5.插入订单
        orderDao.addOrder(order,tableNum); // 插入那个表


        // 6.插入订单详情
        List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
        int i = 0;
        for (Cart cart:cartList) {
            i++;
            Goods goods = cart.getGoods();

            // 封装订单详情对象
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOid(orderId);
            orderDetail.setGid(cart.getGid());
            orderDetail.setGdesc(goods.getGdesc());
            orderDetail.setGname(goods.getGname());
            orderDetail.setGpic(goods.getGpic());
            orderDetail.setGprice(goods.getGprice());
            orderDetail.setNum(cart.getNum());
            orderDetail.setSubTotal(cart.getSubTotal());

            orderDetails.add(orderDetail); // 添加集合中

            if(i % 500 == 0 || orderDetails.size() == 500){ // 分批插入
                orderDetailDao.batchAddOrderDetail(orderDetails,tableNum);
                orderDetails.clear();
            }
        }

        if(!orderDetails.isEmpty()){
            orderDetailDao.batchAddOrderDetail(orderDetails,tableNum);
        }

        // 清空购物车
        cartService.deleteCartByUserId(user.getId());

        return orderId;
    }
    

    @Override
    public Integer setDataSource(Integer userId) {
        int userIdEnd = Integer.parseInt(orderUtils.getUserIdEnd(userId));

        Integer dbNum = (userIdEnd % 2)+1;
        Integer tableNum = (userIdEnd/2 % 2)+1;

        ThreadLocalDataSource.set("order"+dbNum);

        return tableNum;
    }

    @Override
    public List<Order> getOrderListByUserId(Integer userId) {
        // 1.设置数据源
        Integer tableNum = setDataSource(userId);

        // 2.根据用户id和表的编号查询数据
        return orderDao.getOrderListByUserIdAndTableNum(userId,tableNum);
    }

    @Override
    public Order getOrderById(String oid) {
        // 1.先根据订单Id获取用户Id
        Integer userId = orderUtils.getUserIdByOid(oid);

        // 2.设置数据源
        Integer tableNum = setDataSource(userId);

        // 3.查询数据
        Order order = orderDao.getOrderById(oid,tableNum);

        return order;
    }

    @Override
    public void updateOrderState(String orderId, int state) {
        // 1.从订单号中解析出用户Id的后四位
        Integer userId = orderUtils.getUserIdByOid(orderId);

        // 2.设置数据源
        Integer tableNum = setDataSource(userId);

        // 3.更新订单状态
        orderDao.updateOrderState(orderId,state,tableNum);
    }
}
