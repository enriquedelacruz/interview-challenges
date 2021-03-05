package com.inatlas.challenge.promotion;

import com.inatlas.challenge.products.CoffeeShopMenu;
import com.inatlas.challenge.products.Product;
import com.inatlas.challenge.utils.CoffeeShopUtils;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class EspressoPromotion extends AbstractPromotion {

    private static final int MINIMUM_LATTES = 2;
    private Integer maxFreeEspressos = null;

    public EspressoPromotion(final String name) {
        super(name, true);
    }

    @Override
    public Double calculateTotal(final List<Product> products) {

        //Count total lattes in the order
        final int totalLattes = products.stream()
                .filter(p -> p.getName() == CoffeeShopMenu.MenuProduct.LATTE)
                .map(Product::getQuantity)
                .reduce(0, (a, b) -> a + b);
        //Count total espressos in the order
        final int totalEspressos = products.stream()
                .filter(p -> p.getName() == CoffeeShopMenu.MenuProduct.ESPRESSO)
                .map(Product::getQuantity)
                .reduce(0, (a, b) -> a + b);
        //Calculates the maximum free espressos = minimum value between total espressos and the half of lattes
        this.maxFreeEspressos = ((totalLattes/MINIMUM_LATTES) < totalEspressos)?(totalLattes/MINIMUM_LATTES):totalEspressos;

        Double total = calculateOriginalTotal(products);
        if (maxFreeEspressos > 0) {
            total -= maxFreeEspressos * CoffeeShopMenu.MenuProduct.ESPRESSO.getPrice();
        }

        return CoffeeShopUtils.formatDouble(total);
    }

    @Override
    public void applyPerProduct(final List<Product> products) {
        if (this.maxFreeEspressos == null) {
            calculateOriginalTotal(products);
        }
        if (this.maxFreeEspressos > 0) {
            final AtomicInteger auxMaxFreeEspressos = new AtomicInteger(this.maxFreeEspressos);
            //Find espressos in products with quantity = 1 and set discount
            products.stream()
                    .filter(p -> p.getName().equals(CoffeeShopMenu.MenuProduct.ESPRESSO) && p.getQuantity() == 1)
                    .forEach(p -> {
                        if (auxMaxFreeEspressos.get() > 0) {
                            p.setDiscount(true);
                            p.setPromoPrice(0.0);
                            auxMaxFreeEspressos.getAndDecrement();
                        }
                    });
            //Count espressos with discount
            final Long espressosWithDiscount = products.stream()
                    .filter(p -> p.getName().equals(CoffeeShopMenu.MenuProduct.ESPRESSO) && p.isDiscount())
                    .count();
            //And removes from aux variable (pending espressos)
            auxMaxFreeEspressos.set((int) (auxMaxFreeEspressos.get() - espressosWithDiscount));
            if (auxMaxFreeEspressos.get() > 0) {
                final int finalAuxMaxFreeEspressos = auxMaxFreeEspressos.get();
                //Removes from the other espressos quantity
                products.stream()
                        .filter(p -> p.getName().equals(CoffeeShopMenu.MenuProduct.ESPRESSO) && p.getQuantity() > 1)
                        .forEach(p -> p.setQuantity(p.getQuantity() - finalAuxMaxFreeEspressos));
                //And adds the new item of espressos with discount
                products.add(new Product(CoffeeShopMenu.MenuProduct.ESPRESSO, finalAuxMaxFreeEspressos, true, 0.0));
            }

        }
    }

    @Override
    public boolean isSuitable(final List<Product> products) {
        final int totalLattes = products.stream()
                .filter(p -> p.getName() == CoffeeShopMenu.MenuProduct.LATTE)
                .map(Product::getQuantity)
                .reduce(0, (a, b) -> a + b);
        final int totalEspressos = products.stream()
                .filter(p -> p.getName() == CoffeeShopMenu.MenuProduct.ESPRESSO)
                .map(Product::getQuantity)
                .reduce(0, (a, b) -> a + b);
        return (totalLattes >= MINIMUM_LATTES && totalEspressos > 0);
    }

}
