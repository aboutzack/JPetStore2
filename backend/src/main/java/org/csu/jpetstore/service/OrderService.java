package org.csu.jpetstore.service;

import org.csu.jpetstore.domain.Item;
import org.csu.jpetstore.domain.LineItem;
import org.csu.jpetstore.domain.Order;
import org.csu.jpetstore.domain.Sequence;
import org.csu.jpetstore.persistence.ItemMapper;
import org.csu.jpetstore.persistence.LineItemMapper;
import org.csu.jpetstore.persistence.OrderMapper;
import org.csu.jpetstore.persistence.SequenceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

  @Autowired
  private ItemMapper itemMapper;
  @Autowired
  private OrderMapper orderMapper;
  @Autowired
  private SequenceMapper sequenceMapper;
  @Autowired
  private LineItemMapper lineItemMapper;

  @Transactional
  public void insertOrder(Order order) {
    order.setOrderId(getNextId("ordernum"));
    for (int i = 0; i < order.getLineItems().size(); i++) {
      LineItem lineItem =  order.getLineItems().get(i);
//      String itemId = lineItem.getItemId();
//      Integer increment = new Integer(lineItem.getQuantity());
//      Map<String, Object> param = new HashMap<String, Object>(2);
//      param.put("itemId", itemId);
//      param.put("increment", increment);
//      itemMapper.updateInventoryQuantity(param);
      itemMapper.updateInventoryQuantityByLineItem(lineItem);
    }

    orderMapper.insertOrder(order);
    orderMapper.insertOrderStatus(order);
    for (int i = 0; i < order.getLineItems().size(); i++) {
      LineItem lineItem = (LineItem) order.getLineItems().get(i);
      lineItem.setOrderId(order.getOrderId());
      lineItemMapper.insertLineItem(lineItem);
    }
  }

  @Transactional
  public Order getOrder(int orderId) {
    Order order = orderMapper.getOrder(orderId);
    if (order != null) {
      order.setLineItems(lineItemMapper.getLineItemsByOrderId(orderId));

      for (int i = 0; i < order.getLineItems().size(); i++) {
        LineItem lineItem = (LineItem) order.getLineItems().get(i);
        Item item = itemMapper.getItem(lineItem.getItemId());
        item.setQuantity(itemMapper.getInventoryQuantity(lineItem.getItemId()));
        lineItem.setItem(item);
      }
    }
    return order;
  }

  public List<Order> getOrdersByUsername(String username) {
    return orderMapper.getOrdersByUsername(username);
  }

  public int getNextId(String name) {
    Sequence sequence = new Sequence(name, -1);
    sequence = (Sequence) sequenceMapper.getSequence(sequence);
    if (sequence == null) {
      throw new RuntimeException("Error: A null sequence was returned from the database (could not get next " + name
          + " sequence).");
    }
    Sequence parameterObject = new Sequence(name, sequence.getNextId() + 1);
    sequenceMapper.updateSequence(parameterObject);
    return sequence.getNextId();
  }

  //更改order和lineItem的状态--已付款
  //更改库存
  @Transactional
  public void updateOrderAndInventory(Order order){
    if (order != null) {
      orderMapper.updateOrderStatus(order);

      for (int i = 0; i < order.getLineItems().size(); i++) {
        LineItem lineItem = (LineItem)order.getLineItems().get(i);
        itemMapper.updateInventoryQuantityByLineItem(lineItem);
      }
    }
  }

}
