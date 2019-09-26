package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.qf.entity.Order;
import com.qf.service.IOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class PayController {

    @Reference
    private IOrderService orderService;


    @RequestMapping(value = "/pay")
    public void pay(String oid, HttpServletResponse response){
        Order order = orderService.getOrderById(oid);

        AlipayClient alipayClient = new DefaultAlipayClient(
                "https://openapi.alipaydev.com/gateway.do",
                "2016101200670994",
                "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCw1aPI8K9JmaoJVFfn/T4cr/+/lyDeKL2eAK1lspiqsWEBI/fUBv4d49ZSEDecwT3QBn3po12x8jUmDLcloFjaTOmWWcD5NKRjb7OPvWbRLs2frbx3kZbuXRHwS+7HxxWpKbcY5DDv5Xbz8DmrQRfFsMXvq/KvSoSIG31PNUC/StPAHNhWteckaaqYLe5yqpai9axdUElnmP+mNBF1tUW2ymlrWAtXKvfVUWNdUs/+CxPbnV49X0OsBXq9NpYWT8f4RK4oNR+yzk47TmEbd1KwljwgRAk/z/8iCib1NfRyICgHlt1z1FXmpNUvkrsiIaEYPpW8FRKGZi1xqrKLbbONAgMBAAECggEAQG/I++rAZPhqnnWuhqmgHYIAlobygiGkXtPuW3WF7gUuzOSXZRqbNLcWHNct/J/p70i+TwmplbMlrNTLVBmm5v1JzPCtweFt2ZIVg1ZIBgehBAOh/ov9zKP7am2w9/lSqk66GobkUjZybxdlXEMe73mn1tr4Vum3e4VQ1zEXg9yb0vpvzaF95pRenjDAvQBUFoRICED2yu5oOWlPhFf5W9v0Qs0noFQgcbY/aZjiATs77L0AA8RaUIHBGaNgE4wIik8LMKRI3C8OhbeFVpSD4G0apC9sNO+Tt1qbD1M0jud8nm8X2GXky5l7FhIuuigpbRs/OwhtHWfZcmIpKL4ywQKBgQDm0JmaKg/0cGY9WSwbMxjFe2e687SRoCo5LTlIJoTvTsDmzmjdSvkbZ1pBtiFsElwkCkd8QFrqwh6BpgI8jyz/gJZfdPF4cHnMnkPe4+NLOoaxmTK9ZTjv0pStmhhrJbdIJINuB0g2gnNj/Q89RHzW10uCETQD/JYMuBZaJqwg0QKBgQDEITI8TDcGPqDjVyIq6uoav2sOChg/4QTO5noTAwTs6hjsjISOU+8350uqp2+yu0UkJvSaYehxWHy2Lx2FAh+ItoveuXHL+u0gpPZojYKHjYxMMxD98DmY8WDYPN82aWKF12T3mnPRl6Uyuh3Q+897GH8Vs4XiFcV2k9QXGaY1/QKBgQDb307TOspkEkZynmWTq4MnbD0OLGWQa8CNIvV0M61iweSkeXAPjgHrBvNFsZf4+Sxo8JnUVQHGrBCfdGKhCcrVwcp9OEtmbc65CvKgevnkieYRfFyHastoOXOEREyKlS0ZPh8tSyaRNodxcnl35av+hKzbREQGoDDYAOzaCAjkYQKBgGkQK1v3q+YxeHgwPgJlkwC/F7cOm0zk2nGHL0+t516OTMmywHOJ7PSyd2s0hHZnMTOblp544k6Ni0kA1jxhFcljmGu4RYhyo8I//Cun2ouIvOY42NpfbEtMyocrnJsGOxvddZ3Z5D8bkAgrgTtpGQ0k4zcZ0xd4eC38JLo3KD11AoGBAMZ9DOlTe2wWN49fuRamVYgEN59krsOoCLevfMCtcdnBKsS1a3PJKMooKzqobdLzoDpHOuR5a4jgogk/l0igClBxvyQIllUVOP1zOp54hwPLy73NdM4PqZodpp1FImTqITcOo2GFLb3SLYTWfkfHCH68wehR30wSLmFzAzkk0mD0",
                "JSON",
                "UTF-8",
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsNWjyPCvSZmqCVRX5/0+HK//v5cg3ii9ngCtZbKYqrFhASP31Ab+HePWUhA3nME90AZ96aNdsfI1Jgy3JaBY2kzpllnA+TSkY2+zj71m0S7Nn628d5GW7l0R8Evux8cVqSm3GOQw7+V28/A5q0EXxbDF76vyr0qEiBt9TzVAv0rTwBzYVrXnJGmqmC3ucqqWovWsXVBJZ5j/pjQRdbVFtsppa1gLVyr31VFjXVLP/gsT251ePV9DrAV6vTaWFk/H+ESuKDUfss5OO05hG3dSsJY8IEQJP8//Igom9TX0ciAoB5bdc9RV5qTVL5K7IiGhGD6VvBUShmYtcaqyi22zjQIDAQAB",
                "RSA2");
        // 2.创建API对应的request
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();

        alipayRequest.setReturnUrl("http://localhost:8081"); // 同步地址
        alipayRequest.setNotifyUrl(" http://g5txxi.natappfree.cc/updateOrderState");//在公共参数中设置回跳和通知地址
        // //填充业务参数
        alipayRequest.setBizContent("{" +
                "    \"out_trade_no\":\""+order.getId()+"\"," +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "    \"total_amount\":"+order.getTotalPrice()+"," +
                "    \"subject\":\"Iphone6 16G\"," +
                "    \"body\":\"Iphone6 16G\"," +
                "    \"passback_params\":\"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\"," +
                "    \"extend_params\":{" +
                "    \"sys_service_provider_id\":\"2088102179277710\"" +
                "    }"+
                "  }");

        String form="";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        try {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(form);//直接将完整的表单html输出到页面
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @RequestMapping(value = "/updateOrderState",method = RequestMethod.POST)
    public void updateOrderState(String out_trade_no,String trade_status) {

        if("TRADE_SUCCESS".equals(trade_status)){
            // 修改订单的状态
            orderService.updateOrderState(out_trade_no,1);
        }

        // 修改订单的状态
        System.out.println("PayController.updateOrderState");
    }
}
