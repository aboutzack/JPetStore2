package org.csu.jpetstore.persistence;

import org.csu.jpetstore.domain.Category;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryMapper {

    List<Category> getCategoryList();

    Category getCategory(String categoryId);

}
