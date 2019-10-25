package org.csu.jpetstore.persistence;

import org.apache.ibatis.annotations.Param;
import org.csu.jpetstore.domain.MyCart;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CartMapper {
    void insertOrUpdateCart(@Param("username")String username, @Param("itemid") String itemId, @Param("quantity") int quantity,
                            @Param("totalprice")BigDecimal totalPrice);
    List<MyCart> getCartByUsername(String username);
    void deleteByUsernameAndItemId(@Param("username") String username,@Param("itemid") String itemId);
    void deleteByUsername(String username);
}
