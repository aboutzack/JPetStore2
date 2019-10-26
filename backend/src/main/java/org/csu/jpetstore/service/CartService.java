package org.csu.jpetstore.service;

import org.csu.jpetstore.domain.*;
import org.csu.jpetstore.persistence.CartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartService {
    @Autowired
    CartMapper cartMapper;
    public void insertOrUpdateCartItem(String username, String itemId,int quantity, BigDecimal totalPrice){
        cartMapper.insertOrUpdateCart(username, itemId, quantity, totalPrice);
    }

    public Cart getCartByUsername(String username) {
        Cart cart = new Cart();
        List<MyCart> myCarts = cartMapper.getCartByUsername(username);
        for (MyCart myCart:myCarts) {
            Item item = new Item();
            CartItem cartItem = new CartItem();
            Product product = new Product();
            cartItem.setQuantity(myCart.getQuantity());
            cartItem.setInStock(myCart.getQuantity()>0);
            item.setItemId(myCart.getItemid());
            item.setListPrice(myCart.getListprice());
            item.setUnitCost(myCart.getUnitcost());
            item.setSupplierId(myCart.getSupplierid());
            product.setProductId(myCart.getProductid());
            product.setName(myCart.getName());
            product.setDescription(myCart.getDescription());
            product.setCategoryId(myCart.getCategoryid());
            item.setStatus(myCart.getItemid());
            item.setProductId(myCart.getProductid());
            item.setAttribute1(myCart.getAttribute1());
            item.setAttribute2(myCart.getAttribute2());
            item.setAttribute3(myCart.getAttribute3());
            item.setAttribute4(myCart.getAttribute4());
            item.setAttribute5(myCart.getAttribute5());
            item.setQuantity(myCart.getQuantity());
            item.setProduct(product);
            for (int i = 0; i < cartItem.getQuantity(); i++) {
                cart.addItem(item, cartItem.isInStock());
            }
        }
        return cart;
    }

    public void deleteByUsernameAndItemId(String username, String itemId) {
        cartMapper.deleteByUsernameAndItemId(username, itemId);
    }

    public void deleteByUsername(String username) {
        cartMapper.deleteByUsername(username);
    }
}