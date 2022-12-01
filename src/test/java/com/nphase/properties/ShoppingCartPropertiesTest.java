package com.nphase.properties;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ShoppingCartPropertiesTest {

    private ShoppingCartProperties shoppingCartProperties = new ShoppingCartProperties();

    @Test
    public void canGetCategoryDiscountPercentage() {
        Assertions.assertEquals(shoppingCartProperties.getCategoryDiscountPercentage(), 10);
    }

    @Test
    public void canGetCategoryDiscountMinimumQuantity() {
        Assertions.assertEquals(shoppingCartProperties.getCategoryDiscountMinimumQuantity(), 3);
    }

}