package com.qf.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.dao.ICartDao;
import com.qf.dao.IGoodsDao;
import com.qf.entity.Cart;
import com.qf.entity.Goods;
import com.qf.entity.User;
import com.qf.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;

@Service
public class CartServiceImpl extends BaseServiecImpl<Cart> implements ICartService {

    @Autowired
    private ICartDao cartDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IGoodsDao goodsDao;

    @Override
    protected Mapper<Cart> getMapper() {
        return cartDao;
    }

    @Override
    public void addCart(String cartToken, User user, Cart cart) {
        System.out.println(cart);
        System.out.println(user);
        System.out.println("3"+cartToken);
        // 判断用用户是否登录
        if(user != null){
            // 如果用户登录了就添加到数据库
            cart.setCreateTime(new Date());
            cart.setUid(user.getId());
            cartDao.insert(cart); // ?
        }else{
            // 用户没有登录添加到缓存中
//            redisTemplate.opsForValue().set(cartToken,cart); // ? lpush key 1,2,3,4 3,4,5,6
            redisTemplate.opsForList().rightPush(cartToken,cart); // 添加到缓存中
        }

    }
    @Override
    public int findCartCount(String cartToken, User user) {

        int count = 0;
        if (user != null) {
            Cart cart = new Cart();
            cart.setUid(user.getId());
            count = cartDao.selectCount(cart);
        }else{
            count = redisTemplate.opsForList().size(cartToken).intValue();
        }
        return count;
    }

    @Override
    public int mergeCart(String cartToken, User user) {
        List<Cart> cartList= (List<Cart>)redisTemplate.opsForList().range(cartToken, 0, -1);
        if(cartList != null){
            for(Cart cart:cartList){
                cart.setUid(user.getId()); // 给购物车分配用户
                cart.setCreateTime(new Date());
                cartDao.insert(cart); // 添加数据库
            }
            // 删除Reids中的key
            redisTemplate.delete(cartToken);
            return 1;
        }
        return 0;
    }

    @Override
    public void deleteCartByUserId(Integer id) {
        Cart cart = new Cart();
        cart.setUid(id);
        cartDao.delete(cart);

    }

    @Override
    public List<Cart> getCartList(String cartToken, User user) {
        List<Cart> cartList = null;

        // 擦讯购物车对象
        if(user != null) {
            Cart cart = new Cart();
            cart.setUid(user.getId());
            cartList = cartDao.select(cart);
        }else{
            cartList = (List<Cart>)redisTemplate.opsForList().range(cartToken,0,-1);
        }

        // 查询具体商品信息
        for(Cart cart :cartList){
            Goods goods = goodsDao.selectByPrimaryKey(cart.getGid());
            cart.setGoods(goods);
            cart.setSubTotal(cart.getNum()*goods.getGprice());
        }

        return cartList;
    }
}
