package com.inatlas.challenge.products;


import com.inatlas.challenge.utils.CoffeeShopUtils;

/**
 * Class to represents a product into the order
 */
public class Product {

    /**
     * Menu product property than is included in the menu
     */
    private final CoffeeShopMenu.MenuProduct menuProduct;
    /**
     * Quantity of the product sold in the order
     */
    private Integer quantity;
    /**
     * Boolean value to indicate if a product is promotion applied
     */
    private boolean discount;
    /**
     * Product new price after the promotion applied
     */
    private Double promoPrice;

    //Constrcutors

    /**
     * Constructor class with parameters
     * @param menuProduct menu product type
     * @param qtt quantity of the product
     */
    public Product(final CoffeeShopMenu.MenuProduct menuProduct, final Integer qtt) {
        this.menuProduct = menuProduct;
        this.quantity = qtt;
        this.promoPrice = menuProduct.getPrice();
    }

    /**
     * Constructor class with parameters
     * @param menuProduct menu product type
     * @param qtt quantity of the product
     * @param discount boolean value that indicates that the product is discount applied
     */
    public Product(final CoffeeShopMenu.MenuProduct menuProduct, final Integer qtt, final boolean discount) {
        this.menuProduct = menuProduct;
        this.quantity = qtt;
        this.discount = discount;
        this.promoPrice = menuProduct.getPrice();
    }

    /**
     * Constructor class with parameters
     * @param menuProduct menu product type
     * @param qtt quantity of the product
     * @param discount boolean value that indicates that the product is discount applied
     * @param promoPrice price of the new price after promotion applied
     */
    public Product(final CoffeeShopMenu.MenuProduct menuProduct, final Integer qtt, final boolean discount, final Double promoPrice) {
        this.menuProduct = menuProduct;
        this.quantity = qtt;
        this.discount = discount;
        this.promoPrice = promoPrice;
    }

    /**
     * Overridden method to returns a string with th product representation
     * @return string with th product representation
     */
    @Override
    public String toString() {
        return menuProduct.getName() + " " + getPrice() + (discount?" - promotion":"");
    }

    //Getter and Setters

    /**
     * Getter method to get the quantity of the product into an order
     * @return the quantity of the product sold
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * Setter method to set the quantity of the product into an order
     * @param quantity the quantity of the product sold
     */
    public void setQuantity(final Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * Getter method to get the product type sold
     * @return the product type sold
     */
    public CoffeeShopMenu.MenuProduct getMenuProduct() {
        return menuProduct;
    }

    /**
     * Getter method to get the price of the product sold
     * @return the price of the product solf
     */
    public Double getPrice() {
        Double price;

        if (discount) {
            //if product has a discount, return the promoPrice property
            price = CoffeeShopUtils.formatDouble(this.promoPrice) * this.quantity;
        } else {
            //if product has a discount, return the original price of the menu product
            price = CoffeeShopUtils.formatDouble(this.menuProduct.getPrice()) * this.quantity;
        }

        return price;
    }

    /**
     * Getter method to check if the product has a discount
     * @return true if the product has a discount, false otherwise
     */
    public boolean isDiscount() {
        return discount;
    }

    /**
     * Setter method to set the discount property of the product
     * @param discount boolean value to set
     */
    public void setDiscount(final boolean discount) {
        this.discount = discount;
    }

    /**
     * Getter method to get the new price with discount
     * @return the new price with discount
     */
    public Double getPromoPrice() {
        return promoPrice;
    }

    /**
     * Setter method to set the new price with discount
     * @param promoPrice the new price with discount
     */
    public void setPromoPrice(final Double promoPrice) {
        this.promoPrice = promoPrice;
    }
}
