package com.nphase.properties;

import com.nphase.exceptions.ShoppingCartException;

import java.io.IOException;
import java.util.Properties;

public class ShoppingCartProperties {
    private static final String CATEGORY_DISCOUNT_PERCENTAGE_PROP_KEY = "discount.category.percentage";
    private static final String CATEGORY_DISCOUNT_MINIMUM_QUANTITY_PROP_KEY = "discount.category.minimum_quantity";

    private final int categoryDiscountPercentage;
    private final int categoryDiscountMinimumQuantity;

    public ShoppingCartProperties() {
        try {
            Properties properties = new Properties();
            properties.load(ShoppingCartProperties.class.getClassLoader().getResourceAsStream("application.properties"));
            categoryDiscountPercentage = Integer.parseInt((String) properties.get(CATEGORY_DISCOUNT_PERCENTAGE_PROP_KEY));
            categoryDiscountMinimumQuantity = Integer.parseInt((String) properties.get(CATEGORY_DISCOUNT_MINIMUM_QUANTITY_PROP_KEY));
        } catch (IOException e) {
            throw new ShoppingCartException("Failed to load application properties", e);
        }
    }

    public int getCategoryDiscountPercentage() {
        return categoryDiscountPercentage;
    }

    public int getCategoryDiscountMinimumQuantity() {
        return categoryDiscountMinimumQuantity;
    }

}
