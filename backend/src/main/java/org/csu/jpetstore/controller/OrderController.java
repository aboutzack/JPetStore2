package org.csu.jpetstore.controller;

import com.alibaba.fastjson.JSONObject;
import org.csu.jpetstore.domain.Order;
import org.csu.jpetstore.domain.UserLog;
import org.csu.jpetstore.service.*;
import org.csu.jpetstore.utils.JwtUtil;
import org.csu.jpetstore.utils.ReturnEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    JwtService jwtService;
    @Autowired
    CartService cartService;
    @Autowired
    AccountService accountService;
    @Autowired
    LogService logService;

    @PostMapping("/user/order")
    public ReturnEntity postOrder(@CookieValue("token") String token, @RequestBody Order order) {
        JSONObject data = new JSONObject();
        token = jwtService.validateToken(token, data);
        if (token == null) {
            return ReturnEntity.failedResult("token无效");
        } else {
            String username = JwtUtil.decode(token, JwtUtil.SECRET).get("name").asString();
            order.initOrder(accountService.getAccount(username),cartService.getCartByUsername(username));
            order.setExpiryDate(order.getExpiryDate().substring(0, 7));
            if (order.getShipToFirstName() == null) {
                order.setShipToFirstName(order.getBillToFirstName());
                order.setShipToLastName(order.getBillToLastName());
                order.setShipAddress1(order.getBillAddress1());
                order.setShipAddress2(order.getBillAddress2());
                order.setShipCity(order.getBillCity());
                order.setShipState(order.getBillCountry());
                order.setShipZip(order.getBillZip());
                order.setShipCountry(order.getBillCountry());
            }
            orderService.insertOrder(order);
            cartService.deleteByUsername(username);
            data.put("order", order);
            UserLog log = new UserLog(new Date(), "用户:" + username + "提交订单,总价值:" + order.getTotalPrice());
            logService.insertUserLog(log);
            return ReturnEntity.successResult(data);
        }
    }

    @GetMapping("/user/orders")
    public ReturnEntity getOrders(@CookieValue("token") String token) {
        JSONObject data = new JSONObject();
        token = jwtService.validateToken(token, data);
        if (token == null) {
            return ReturnEntity.failedResult("token无效");
        } else {
            String username = JwtUtil.decode(token, JwtUtil.SECRET).get("name").asString();
            List<Order> orderList = orderService.getOrdersByUsername(username);
            data.put("orderList", orderList);
            return ReturnEntity.successResult(data);
        }
    }

    @GetMapping("/user/order")
    public ReturnEntity getOrder(@CookieValue("token")String token,@RequestParam int id){
        JSONObject data = new JSONObject();
        token = jwtService.validateToken(token, data);
        if (token == null) {
            return ReturnEntity.failedResult("token无效");
        } else {
            String username = JwtUtil.decode(token, JwtUtil.SECRET).get("name").asString();
            List<Order> orderList = orderService.getOrdersByUsername(username);
            Order order = orderService.getOrder(id);
            if(!order.getUsername().equals(username)){
                return ReturnEntity.failedResult("权限不足");
            }
            data.put("order", order);
            return ReturnEntity.successResult(data);
        }
    }
}