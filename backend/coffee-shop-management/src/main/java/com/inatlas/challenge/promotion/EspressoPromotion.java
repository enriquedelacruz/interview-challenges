package com.inatlas.challenge.promotion;

import com.inatlas.challenge.Menu;
import com.inatlas.challenge.Product;
import com.inatlas.challenge.utils.Utils;

import java.util.List;

public class EspressoPromotion extends AbstractPromotion {

    private static final int MINIMUM_LATTES = 2;
    private Integer maxFreeEspressos = null;

    public EspressoPromotion(String name) {
        super(name, true);
    }

    @Override
    public Double calculateTotal(List<Product> products) {

        //Count total lattes in the order
        int totalLattes = products.stream()
                .filter(p -> {
                    return p.getName() == Menu.LATTE;
                })
                .map(Product::getQuantity)
                .reduce(0, (a, b) -> a + b);
        //Count total espressos in the order
        int totalEspressos = products.stream()
                .filter(p -> {
                    return p.getName() == Menu.ESPRESSO;
                })
                .map(Product::getQuantity)
                .reduce(0, (a, b) -> a + b);
        //Calculates the maximum free espressos = minimum value between total espressos and the half of lattes
        this.maxFreeEspressos = ((totalLattes/MINIMUM_LATTES) < totalEspressos)?(totalLattes/MINIMUM_LATTES):totalEspressos;

        Double total = calculateOriginalTotal(products);
        if (maxFreeEspressos > 0) {
            total -= maxFreeEspressos * Menu.ESPRESSO.getPrice();
        }

        return Utils.formatDouble(total);
    }

    @Override
    public void applyPerProduct(List<Product> products) {
        if (this.maxFreeEspressos == null) {
            calculateOriginalTotal(products);
        }
        if (this.maxFreeEspressos > 0) {
            int auxMaxFreeEspressos = this.maxFreeEspressos;
            //Find espressos in products with quantity = 1 and set discount
            products.stream()
                    .filter(p -> p.getName().equals(Menu.ESPRESSO) && p.getQuantity() == 1)
                    .forEach(p -> p.setDiscount(true));
            //Count expressos with discount
            Long espressosWithDiscount = products.stream()
                    .filter(p -> p.getName().equals(Menu.ESPRESSO) && p.isDiscount())
                    .count();
            //And removes from aux variable (pending espressos)
            auxMaxFreeEspressos -= espressosWithDiscount;
            if (auxMaxFreeEspressos > 0) {
                int finalAuxMaxFreeEspressos = auxMaxFreeEspressos;
                //Removes from the other espressos quantity
                products.stream()
                        .filter(p -> p.getName().equals(Menu.ESPRESSO) && p.getQuantity() > 1)
                        .forEach(p -> {
                            p.setQuantity(p.getQuantity() - finalAuxMaxFreeEspressos);
                        });
                //And adds the new item of espressos with discount
                products.add(new Product(Menu.ESPRESSO, finalAuxMaxFreeEspressos, true));
            }

        }
    }

    @Override
    public boolean isSuitable(List<Product> products) {
        int totalLattes = products.stream()
                .filter(p -> {
                    return p.getName() == Menu.LATTE;
                })
                .map(Product::getQuantity)
                .reduce(0, (a, b) -> a + b);
        int totalEspressos = products.stream()
                .filter(p -> {
                    return p.getName() == Menu.ESPRESSO;
                })
                .map(Product::getQuantity)
                .reduce(0, (a, b) -> a + b);
        return (totalLattes >= MINIMUM_LATTES && totalEspressos > 0);
    }

}
