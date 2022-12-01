package com.nphase.service;

import com.nphase.entity.Product;
import com.nphase.entity.ShoppingCart;
import com.nphase.properties.ShoppingCartProperties;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

public class ShoppingCartService {

    private final BigDecimal discountFactor;
    private final int minimalDiscountQuantity;

    public ShoppingCartService(ShoppingCartProperties shoppingCartProperties) {
        discountFactor = calculateDiscountFactor(shoppingCartProperties);
        minimalDiscountQuantity = shoppingCartProperties.getCategoryDiscountMinimumQuantity();
    }

    public BigDecimal calculateTotalPrice(ShoppingCart shoppingCart) {
        return shoppingCart.getProducts().stream()
                .collect(Collectors.groupingBy(Product::getCategory))
                .values().stream()
                .map(this::calculatePaymentAmountForCategory)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    private BigDecimal calculatePaymentAmountForCategory(List<Product> productList) {
        var totalPaymentForCategory = productList.stream()
                .map(product -> product.getPricePerUnit().multiply(new BigDecimal(product.getQuantity())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);

        if (shouldApplyDiscount(productList)) {
            totalPaymentForCategory = applyDiscount(totalPaymentForCategory);
        }

        return totalPaymentForCategory;
    }

    private BigDecimal applyDiscount(BigDecimal totalPaymentForCategory) {
        totalPaymentForCategory = totalPaymentForCategory.multiply(discountFactor);
        return totalPaymentForCategory.setScale(2, RoundingMode.HALF_UP);
    }

    private boolean shouldApplyDiscount(List<Product> productList) {
        return productList.stream().mapToInt(Product::getQuantity).sum() > minimalDiscountQuantity;
    }

    private static BigDecimal calculateDiscountFactor(ShoppingCartProperties shoppingCartProperties) {
        return new BigDecimal(100)
                .subtract(new BigDecimal(shoppingCartProperties.getCategoryDiscountPercentage()))
                .divide(new BigDecimal(100), MathContext.UNLIMITED);
    }
}
