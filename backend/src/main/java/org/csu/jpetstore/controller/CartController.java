package org.csu.jpetstore.controller;

import com.alibaba.fastjson.JSONObject;
import org.csu.jpetstore.annotation.Authorization;
import org.csu.jpetstore.domain.Cart;
import org.csu.jpetstore.domain.Item;
import org.csu.jpetstore.domain.UserLog;
import org.csu.jpetstore.service.CartService;
import org.csu.jpetstore.service.CatalogService;
import org.csu.jpetstore.service.JwtService;
import org.csu.jpetstore.service.LogService;
import org.csu.jpetstore.utils.ReturnEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@RestController
public class CartController {
    @Autowired
    CartService cartService;
    @Autowired
    CatalogService catalogService;
    @Autowired
    JwtService jwtService;
    @Autowired
    LogService logService;

    /**
     * 请求购物车
     */
    @GetMapping("/user/cart")
    @Authorization
    public ReturnEntity getCart(@RequestAttribute String username) {
        JSONObject data = new JSONObject();
        Cart cart = cartService.getCartByUsername(username);
        data.put("cart", cart);
        return ReturnEntity.successResult(data);
    }

    /**
     * 添加商品到购物车
     */
    @PostMapping("/user/cart")
    @Authorization
    public ReturnEntity PostCart(@RequestBody Map<String, Object> params, @RequestAttribute String username) {
        JSONObject data = new JSONObject();
        String itemId = params.get("itemId").toString();
        Cart cart = cartService.getCartByUsername(username);
        if (cart.containsItemId(itemId)) {
            cart.incrementQuantityByItemId(itemId);
        } else {
            boolean isInStock = catalogService.isItemInStock(itemId);
            Item item = catalogService.getItem(itemId);
            cart.addItem(item, isInStock);
        }
        int quantity = cart.getCartItemQuantityByItemId(itemId);
        Item item = catalogService.getItem(itemId);
        cartService.insertOrUpdateCartItem(username, itemId, quantity, new BigDecimal(quantity).multiply(item.getListPrice()));
        data.put("cart", cart);
        UserLog log = new UserLog(new Date(), "用户:" + username + "添加item:" + itemId + "到购物车");
        logService.insertUserLog(log);
        return ReturnEntity.successResult(data);
    }

    /**
     * 更新购物车
     */
    @PutMapping("/user/cart")
    @Authorization
    public ReturnEntity PutCart(@RequestBody Map<String, Object> params, @RequestAttribute String username) {
        JSONObject data = new JSONObject();
        String itemId = params.get("itemId").toString();
        int quantity = (int) params.get("quantity");
        Cart cart = cartService.getCartByUsername(username);
        cart.setQuantityByItemId(itemId, quantity);
        if (quantity < 1) {
            cart.removeItemById(itemId);
            cartService.deleteByUsernameAndItemId(username, itemId);
        } else {
            Item item = catalogService.getItem(itemId);
            cartService.insertOrUpdateCartItem(username, itemId, quantity, new BigDecimal(quantity).multiply(item.getListPrice()));
        }
        data.put("cart", cart);
        UserLog log = new UserLog(new Date(), "用户:" + username + "更新item:" + itemId + "目前数量为" + quantity);
        logService.insertUserLog(log);
        return ReturnEntity.successResult(data);
    }
}
