package com.inatlas.challenge.controller;

import com.inatlas.challenge.Client;
import com.inatlas.challenge.CoffeeShop;
import com.inatlas.challenge.Order;
import com.inatlas.challenge.products.CoffeeShopMenu;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/coffeeshop/client")
public class ClientController {

    @GetMapping("/{clientId}")
    public ResponseEntity<HashMap> client(@PathVariable Integer clientId) {
        HashMap resultMap = new HashMap();
        resultMap.put("coffeeShopId", CoffeeShop.getInstance().getCoffeeShopId());
        resultMap.put("clientId", clientId);
        resultMap.put("client", CoffeeShop.getInstance().getClient(clientId));

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(resultMap);
    }


    @GetMapping("/{clientId}/orders")
    public ResponseEntity<HashMap> getOrders(@PathVariable Integer clientId) {
        HashMap resultMap = new HashMap();
        resultMap.put("coffeeShopId", CoffeeShop.getInstance().getCoffeeShopId());
        resultMap.put("clientId", clientId);
        Client client = CoffeeShop.getInstance().getClient(clientId);
        resultMap.put("orders", client.getOrders());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(resultMap);
    }

    @GetMapping("/{clientId}/registerNewOrder")
    public ResponseEntity<HashMap> registerNewClient(@PathVariable Integer clientId) {
        HashMap resultMap = new HashMap();
        resultMap.put("coffeeShopId", CoffeeShop.getInstance().getCoffeeShopId());
        resultMap.put("clientId", clientId);
        Client client = CoffeeShop.getInstance().getClient(clientId);
        resultMap.put("orderId", client.registerNewOrder().getOrderId());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(resultMap);
    }

    @GetMapping("/{clientId}/order/{orderId}")
    public ResponseEntity<HashMap> getOrders(@PathVariable Integer clientId,
                                             @PathVariable Integer orderId) {
        HashMap resultMap = new HashMap();
        resultMap.put("coffeeShopId", CoffeeShop.getInstance().getCoffeeShopId());
        resultMap.put("clientId", clientId);
        resultMap.put("orderId", clientId);
        Client client = CoffeeShop.getInstance().getClient(clientId);
        Order order = client.getOrder(orderId);
        resultMap.put("order", order);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(resultMap);
    }

    @GetMapping("/{clientId}/order/{orderId}/takeOrder")
    public ResponseEntity<HashMap> takeOrder(@PathVariable Integer clientId,
                                             @PathVariable Integer orderId,
                                             @RequestParam(name = "productName", required = true) String productName,
                                             @RequestParam(name = "quantity", required = true) Integer quantity) {
        HashMap resultMap = new HashMap();
        resultMap.put("coffeeShopId", CoffeeShop.getInstance().getCoffeeShopId());
        resultMap.put("clientId", clientId);
        resultMap.put("orderId", clientId);
        Client client = CoffeeShop.getInstance().getClient(clientId);
        Order order = client.getOrder(orderId);
        List<CoffeeShopMenu.MenuProduct> menuProductList = Arrays.stream(CoffeeShopMenu.MenuProduct.values()).filter(mp -> mp.getName().equals(productName)).collect(Collectors.toList());
        if (menuProductList != null && !menuProductList.isEmpty()) {
            order.takeOrder(menuProductList.get(0), quantity);
            order.calculateTotal();
        }
        resultMap.put("order", order);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(resultMap);

    }


}
