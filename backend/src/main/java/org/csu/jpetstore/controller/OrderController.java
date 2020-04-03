package org.csu.jpetstore.controller;

import com.alibaba.fastjson.JSONObject;
import org.csu.jpetstore.annotation.Authorization;
import org.csu.jpetstore.domain.Order;
import org.csu.jpetstore.domain.UserLog;
import org.csu.jpetstore.service.*;
import org.csu.jpetstore.utils.ReturnEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    @Autowired
    HttpServletRequest request;

    @PostMapping("/user/order")
    @Authorization
    public ReturnEntity postOrder(@RequestBody Order order, @RequestAttribute String username) {
        JSONObject data = new JSONObject();
        order.initOrder(accountService.getAccount(username), cartService.getCartByUsername(username));
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

    @GetMapping("/user/orders")
    @Authorization
    public ReturnEntity getOrders(@RequestAttribute String username) {
        JSONObject data = new JSONObject();
        List<Order> orderList = orderService.getOrdersByUsername(username);
        data.put("orderList", orderList);
        return ReturnEntity.successResult(data);
    }

    @GetMapping("/user/order")
    @Authorization
    public ReturnEntity getOrder(@RequestParam int id, @RequestAttribute String username) {
        JSONObject data = new JSONObject();
        Order order = orderService.getOrder(id);
        //如果请求的是别人的order
        if (!order.getUsername().equals(username)) {
            return ReturnEntity.failedResult("权限不足");
        }
        data.put("order", order);
        return ReturnEntity.successResult(data);
    }


    @PutMapping("/user/order")
    @Authorization
    public ReturnEntity updateOrder(@RequestParam int orderId){
        JSONObject data = new JSONObject();
        Order order = orderService.getOrder(orderId);
        orderService.updateOrderAndInventory(order);

        data.put("orderStatus",order.getStatus());
        UserLog log = new UserLog(new Date(), "用户:" + order.getUsername() + "已付款,修改订单状态为已付款");
        logService.insertUserLog(log);

        return ReturnEntity.successResult(data);
    }
}