package com.nphase.service;


import com.nphase.entity.Product;
import com.nphase.entity.ProductCategory;
import com.nphase.entity.ShoppingCart;
import com.nphase.properties.ShoppingCartProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

public class ShoppingCartServiceTest {
    private ShoppingCartService service;

    @BeforeEach
    public void setup() {
        service = new ShoppingCartService(new ShoppingCartProperties());
    }

    @Test
    public void calculatesPrice() {
        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", BigDecimal.valueOf(5.0), 2, ProductCategory.DRINKS),
                new Product("Bread", BigDecimal.valueOf(6.5), 1, ProductCategory.FOOD)
        ));

        BigDecimal result = service.calculateTotalPrice(cart);

        Assertions.assertEquals(result, BigDecimal.valueOf(16.5));
    }

    @Test
    public void calculatesPriceWithCategoryDiscount() {
        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", BigDecimal.valueOf(5.3), 2, ProductCategory.DRINKS),
                new Product("Coffee", BigDecimal.valueOf(3.5), 2, ProductCategory.DRINKS),
                new Product("Cheese", BigDecimal.valueOf(8), 2, ProductCategory.FOOD)
        ));

        BigDecimal result = service.calculateTotalPrice(cart);

        Assertions.assertEquals(result, BigDecimal.valueOf(31.84));
    }

    @Test
    public void canHandleEmptyShoppingCart() {
        ShoppingCart cart = new ShoppingCart(Collections.emptyList());

        BigDecimal result = service.calculateTotalPrice(cart);

        Assertions.assertEquals(result, BigDecimal.ZERO);
    }

}