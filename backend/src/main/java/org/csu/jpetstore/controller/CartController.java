package org.csu.jpetstore.controller;

import com.alibaba.fastjson.JSONObject;
import org.csu.jpetstore.domain.Cart;
import org.csu.jpetstore.domain.Item;
import org.csu.jpetstore.service.CartService;
import org.csu.jpetstore.service.CatalogService;
import org.csu.jpetstore.service.JwtService;
import org.csu.jpetstore.utils.JwtUtil;
import org.csu.jpetstore.utils.ReturnEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
public class CartController {
    @Autowired
    CartService cartService;
    @Autowired
    CatalogService catalogService;
    @Autowired
    JwtService jwtService;

    @GetMapping("/user/cart")
    /**
     * 请求购物车
     */
    public ReturnEntity getCart(@CookieValue("token") String token) {
        JSONObject data = new JSONObject();
        token = jwtService.validateToken(token, data);
        if (token == null) {
            return ReturnEntity.failedResult("token无效");
        } else {
            String username = JwtUtil.decode(token, JwtUtil.SECRET).get("name").asString();
            Cart cart = cartService.getCartByUsername(username);
            data.put("cart", cart);
            return ReturnEntity.successResult(data);
        }
    }

    /**
     * 添加商品到购物车
     */
    @PostMapping("/user/cart")
    public ReturnEntity PostCart(@CookieValue("token") String token, @RequestBody Map<String, Object> params) {
        JSONObject data = new JSONObject();
        token = jwtService.validateToken(token, data);
        String itemId = params.get("itemId").toString();
        if (token == null) {
            return ReturnEntity.failedResult("token无效");
        } else {
            String username = JwtUtil.decode(token, JwtUtil.SECRET).get("name").asString();
            Cart cart = cartService.getCartByUsername(username);
            if (cart.containsItemId(itemId)) {
                cart.incrementQuantityByItemId(itemId);
            } else {
                boolean isInStock = catalogService.isItemInStock(itemId);
                Item item = catalogService.getItem(itemId);
                cart.addItem(item, isInStock);
            }
            int quantity =  cart.getCartItemQuantityByItemId(itemId);
            Item item = catalogService.getItem(itemId);
            cartService.insertOrUpdateCartItem(username, itemId, quantity,new BigDecimal(quantity).multiply(item.getListPrice()));
            data.put("cart", cart);
            return ReturnEntity.successResult(data);
        }
    }

    /**
     * 更新购物车
     */
    @PutMapping("/user/cart")
    public ReturnEntity PutCart(@CookieValue("token") String token, @RequestBody Map<String, Object> params) {
        JSONObject data = new JSONObject();
        token = jwtService.validateToken(token, data);
        String itemId = params.get("itemId").toString();
        int quantity = (int) params.get("quantity");
        if (token == null) {
            return ReturnEntity.failedResult("token无效");
        } else {
            String username = JwtUtil.decode(token, JwtUtil.SECRET).get("name").asString();
            Cart cart = cartService.getCartByUsername(username);
            cart.setQuantityByItemId(itemId, quantity);
            if (quantity < 1) {
                cart.removeItemById(itemId);
                cartService.deleteByUsernameAndItemId(username, itemId);
            }else {
                Item item = catalogService.getItem(itemId);
                cartService.insertOrUpdateCartItem(username, itemId, quantity, new BigDecimal(quantity).multiply(item.getListPrice()));
            }
            data.put("cart", cart);
            return ReturnEntity.successResult(data);
        }
    }
}
