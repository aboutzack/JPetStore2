package org.csu.jpetstore.persistence;

import org.csu.jpetstore.domain.Item;
import org.csu.jpetstore.domain.LineItem;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ItemMapper {

    void updateInventoryQuantity(Map<String, Object> param);
    void updateInventoryQuantityByLineItem(LineItem lineItem);
    
    int getInventoryQuantity(String itemId);

    List<Item> getItemListByProduct(String productId);

    Item getItem(String itemId);

}