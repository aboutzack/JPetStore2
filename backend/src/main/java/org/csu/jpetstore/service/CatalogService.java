package org.csu.jpetstore.service;

import org.csu.jpetstore.domain.Category;
import org.csu.jpetstore.domain.Item;
import org.csu.jpetstore.domain.Product;
import org.csu.jpetstore.persistence.CategoryMapper;
import org.csu.jpetstore.persistence.ItemMapper;
import org.csu.jpetstore.persistence.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogService {

  @Autowired
  private CategoryMapper categoryMapper;
  @Autowired
  private ItemMapper itemMapper;
  @Autowired
  private ProductMapper productMapper;

  public List<Category> getCategoryList() {
    return categoryMapper.getCategoryList();
  }

  public Category getCategory(String categoryId) {
    return categoryMapper.getCategory(categoryId);
  }

  public Product getProduct(String productId) {
    return productMapper.getProduct(productId);
  }

  public List<Product> getProductListByCategory(String categoryId) {
    return productMapper.getProductListByCategory(categoryId);
  }

  public List<Product> searchProductList(String keyword) {
    return productMapper.searchProductList("%" + keyword.toLowerCase() + "%");
  }

  public List<Item> getItemListByProduct(String productId) {
    return itemMapper.getItemListByProduct(productId);
  }

  public Item getItem(String itemId) {
    return itemMapper.getItem(itemId);
  }

  public boolean isItemInStock(String itemId) {
    return itemMapper.getInventoryQuantity(itemId) > 0;
  }

  public List<Product> getAllProducts() {
    return productMapper.getAllProducts();
  }
}