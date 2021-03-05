package com.inatlas.challenge.promotion;

import com.inatlas.challenge.products.CoffeeShopMenu;
import com.inatlas.challenge.products.Product;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PromotionTest {

    @Test
    public void testEspressoPromotion() {

        final EspressoPromotion espressoPromotion = new EspressoPromotion("Espresso promo");
        final boolean isPerProduct = espressoPromotion.isPerProduct();
        assertThat(isPerProduct, is(true));

        List<Product> products = Arrays.asList(new Product[]{
                new Product(CoffeeShopMenu.MenuProduct.ESPRESSO, 250),
                new Product(CoffeeShopMenu.MenuProduct.LATTE, 1),
        });
        boolean suitable = espressoPromotion.isSuitable(products);
        assertThat(suitable, is(false));
        Double total = espressoPromotion.calculateTotal(products);
        assertThat(total, is(1005.3)); //$4*250 + $5.3



        //We need a mutable list to test applyPerProduct method
        products = new ArrayList<>();
        products.add(new Product(CoffeeShopMenu.MenuProduct.ESPRESSO, 250));
        products.add(new Product(CoffeeShopMenu.MenuProduct.LATTE, 2));

        suitable = espressoPromotion.isSuitable(products);
        assertThat(suitable, is(true));
        total = espressoPromotion.calculateTotal(products);
        assertThat(total, is(1006.6)); //$4*250 + $5.3*2 - 4(promo)
        espressoPromotion.applyPerProduct(products);
        //Count espressos with discount
        Long espressosWithDiscount = products.stream().filter(p -> p.getMenuProduct() == CoffeeShopMenu.MenuProduct.ESPRESSO && p.isDiscount()).count();
        assertThat(espressosWithDiscount.intValue(), is(1));



        //We need a mutable list to test applyPerProduct method
        products = Arrays.asList(new Product[]{
                new Product(CoffeeShopMenu.MenuProduct.ESPRESSO, 1),
                new Product(CoffeeShopMenu.MenuProduct.LATTE, 4),
                new Product(CoffeeShopMenu.MenuProduct.ESPRESSO, 1)
        });
        total = espressoPromotion.calculateTotal(products);
        assertThat(total, is(21.2)); //$4*2 + $5.3*4 -$8 (promo)
        espressoPromotion.applyPerProduct(products);
        //Count espressos with discount
        espressosWithDiscount = products.stream()
                .filter(p -> p.getMenuProduct() == CoffeeShopMenu.MenuProduct.ESPRESSO && p.isDiscount())
                .map(Product::getQuantity)
                .reduce(0, (a, b) -> a + b).longValue();
        assertThat(espressosWithDiscount.intValue(), is(2));



        //We need a mutable list in this case to test the applyPerProduct method
        products = new ArrayList<>();
        products.add(new Product(CoffeeShopMenu.MenuProduct.ESPRESSO, 250));
        products.add(new Product(CoffeeShopMenu.MenuProduct.LATTE, 4));

        suitable = espressoPromotion.isSuitable(products);
        assertThat(suitable, is(true));
        total = espressoPromotion.calculateTotal(products);
        assertThat(total, is(1013.2)); //$4*250 + $5.3*4 - 8(promo)
        espressoPromotion.applyPerProduct(products);
        //Count espressos with discount
        espressosWithDiscount = products.stream()
                .filter(p -> p.getMenuProduct() == CoffeeShopMenu.MenuProduct.ESPRESSO && p.isDiscount())
                .map(Product::getQuantity)
                .reduce(0, (a, b) -> a + b).longValue();
        assertThat(espressosWithDiscount.intValue(), is(2));

    }


    @Test
    public void testTotalProductsPromotion() {

        final TotalProductsPromotion totalProductsPromotion = new TotalProductsPromotion("8 products promo");
        final boolean isPerProduct = totalProductsPromotion.isPerProduct();
        assertThat(isPerProduct, is(false));

        List<Product> products = Arrays.asList(new Product[]{
                new Product(CoffeeShopMenu.MenuProduct.ESPRESSO, 4),
                new Product(CoffeeShopMenu.MenuProduct.LATTE, 1)
        });
        boolean suitable = totalProductsPromotion.isSuitable(products);
        assertThat(suitable, is(false));
        Double total = totalProductsPromotion.calculateTotal(products);
        assertThat(total, is(21.3)); //$4*4 + $5.3

        products = Arrays.asList(new Product[]{
                new Product(CoffeeShopMenu.MenuProduct.ESPRESSO, 4),
                new Product(CoffeeShopMenu.MenuProduct.LATTE, 4)
        });
        suitable = totalProductsPromotion.isSuitable(products);
        assertThat(suitable, is(false));
        total = totalProductsPromotion.calculateTotal(products);
        assertThat(total, is(37.2)); //$4*4 + $5.3*4

        products = Arrays.asList(new Product[]{
                new Product(CoffeeShopMenu.MenuProduct.ESPRESSO, 4),
                new Product(CoffeeShopMenu.MenuProduct.LATTE, 4),
                new Product(CoffeeShopMenu.MenuProduct.SANDWICH, 1)
        });
        suitable = totalProductsPromotion.isSuitable(products);
        assertThat(suitable, is(true));
        total = totalProductsPromotion.calculateTotal(products);
        assertThat(total, is(44.93)); //($4*4 + $5.3*4 + $10.10) -5%

    }

    @Test
    public void testTotalAmountPromotion() {

        final TotalAmountPromotion totalAmountPromotion = new TotalAmountPromotion("Total amount promo");
        final boolean isPerProduct = totalAmountPromotion.isPerProduct();
        assertThat(isPerProduct, is(true));

        List<Product> products = Arrays.asList(new Product[]{
                new Product(CoffeeShopMenu.MenuProduct.ESPRESSO, 1),
                new Product(CoffeeShopMenu.MenuProduct.LATTE, 1),
        });
        boolean suitable = totalAmountPromotion.isSuitable(products);
        assertThat(suitable, is(false));
        Double total = totalAmountPromotion.calculateTotal(products);
        assertThat(total, is(9.3)); //$4 + $5.3

        products = Arrays.asList(new Product[]{
                new Product(CoffeeShopMenu.MenuProduct.ESPRESSO, 10),
                new Product(CoffeeShopMenu.MenuProduct.LATTE, 5),
        });
        suitable = totalAmountPromotion.isSuitable(products);
        assertThat(suitable, is(true));
        total = totalAmountPromotion.calculateTotal(products);
        assertThat(total, is(55.0)); //$4*10 + $3*5

        products = Arrays.asList(new Product[]{
                new Product(CoffeeShopMenu.MenuProduct.ESPRESSO, 10),
                new Product(CoffeeShopMenu.MenuProduct.LATTE, 500),
        });
        suitable = totalAmountPromotion.isSuitable(products);
        assertThat(suitable, is(true));
        total = totalAmountPromotion.calculateTotal(products);
        assertThat(total, is(1540.0)); //$4*10 + $3*500

    }

}