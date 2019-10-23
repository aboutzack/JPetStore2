package org.csu.jpetstore.controller;

import com.alibaba.fastjson.JSONObject;
import org.csu.jpetstore.domain.Item;
import org.csu.jpetstore.domain.Product;
import org.csu.jpetstore.service.CatalogService;
import org.csu.jpetstore.utils.ReturnEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CatalogController {
    @Autowired
    private CatalogService catalogService;

    @GetMapping("/category")
    private ReturnEntity getCategory(@RequestParam String id) {
        List<Product> productList = catalogService.getProductListByCategory(id);
        return ReturnEntity.successResult(productList);
    }

    @GetMapping("/product")
    private ReturnEntity getProduct(@RequestParam String id) {
        JSONObject data = new JSONObject();
        List<Item> itemList = catalogService.getItemListByProduct(id);
        Product product = catalogService.getProduct(id);
        data.put("itemList", itemList);
        data.put("product", product);
        return ReturnEntity.successResult(data);
    }

    @GetMapping("/item")
    private ReturnEntity getItem(@RequestParam String id) {
        JSONObject data = new JSONObject();
        Item item =  catalogService.getItem(id);
        Product product = null;
        if (item != null) {
            product = item.getProduct();
            data.put("item", item);
        } else {
            data.put("item", null);
        }
        data.put("product", product);
        return ReturnEntity.successResult(data);
    }

    private ReturnEntity searchProduct(@RequestParam String keyword) {
        JSONObject data = new JSONObject();
        if (keyword == null || keyword.length() < 1) {
            return ReturnEntity.failedResult("关键字为空");
        }else {
            List<Product> productList = catalogService.searchProductList(keyword.toLowerCase());
            data.put("productList", productList);
            return ReturnEntity.successResult(productList);
        }
    }
}
