package com.inatlas.challenge.controller;

import com.inatlas.challenge.CoffeeShop;
import com.inatlas.challenge.products.CoffeeShopMenu;
import com.inatlas.challenge.utils.CoffeeShopUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/coffeeshop")
public class CoffeeShopController {

    @GetMapping("/welcome")
    public ResponseEntity<HashMap> welcome() {
        HashMap resultMap = new HashMap();
        resultMap.put("coffeeShopId", CoffeeShop.getInstance().getCoffeeShopId());
        resultMap.put("message", "Welcome to CoffeShop " + CoffeeShop.getInstance().getCoffeeShopId());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(resultMap);
    }

    @GetMapping("/menu")
    public ResponseEntity<HashMap> menu() {
        HashMap resultMap = new HashMap();
        resultMap.put("coffeeShopId", CoffeeShop.getInstance().getCoffeeShopId());
        resultMap.put("menu", CoffeeShopMenu.toJson());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(resultMap);
    }

    @GetMapping("/printMenu")
    public ResponseEntity<HashMap> printMenu() {
        CoffeeShop.getInstance().printMenu();

        return menu();
    }

    @GetMapping("/clients")
    public ResponseEntity<HashMap> getClients() {
        HashMap resultMap = new HashMap();
        resultMap.put("coffeeShopId", CoffeeShop.getInstance().getCoffeeShopId());
        resultMap.put("clients", CoffeeShop.getInstance().getClients());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(resultMap);
    }

    @GetMapping("/registerNewClient")
    public ResponseEntity<HashMap> registerNewClient() {
        HashMap resultMap = new HashMap();
        resultMap.put("coffeeShopId", CoffeeShop.getInstance().getCoffeeShopId());
        resultMap.put("clientId", CoffeeShop.getInstance().registerNewClient().getClientId());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(resultMap);
    }

    @GetMapping("/countClientsByDateRange")
    public ResponseEntity<HashMap> countClientsByDateRange(@RequestParam(name = "beginDate", required = true) String beginDate,
                                                           @RequestParam(name = "endDate", required = true) String endDate) {
        HashMap resultMap = new HashMap();
        resultMap.put("coffeeShopId", CoffeeShop.getInstance().getCoffeeShopId());
        resultMap.put("countClientsByDateRange", CoffeeShop.getInstance().countClientsByDateRange(CoffeeShopUtils.parseDate(beginDate, CoffeeShopUtils.DATE_FORMAT),
                CoffeeShopUtils.parseDate(endDate, CoffeeShopUtils.DATE_FORMAT)));

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(resultMap);
    }

    @GetMapping("/dailyProductsSold")
    public ResponseEntity<HashMap> dailyProductsSold(@RequestParam(name = "day", required = true) String day) {
        HashMap resultMap = new HashMap();
        resultMap.put("coffeeShopId", CoffeeShop.getInstance().getCoffeeShopId());
        resultMap.put("dailyProductsSold", CoffeeShop.getInstance().listDailyProductsSold(CoffeeShopUtils.parseDate(day, CoffeeShopUtils.DATE_FORMAT)));

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(resultMap);
    }

    @GetMapping("/printDailyProductsSold")
    public ResponseEntity<HashMap> printDailyProductsSold(@RequestParam(name = "day", required = true) String day) {
        CoffeeShop.getInstance().printDailyProductsSold(CoffeeShopUtils.parseDate(day, CoffeeShopUtils.DATE_FORMAT));

        return dailyProductsSold(day);
    }

    @GetMapping("/dailyClientAverageExpense")
    public ResponseEntity<HashMap> dailyClientAverageExpense(@RequestParam(name = "day", required = true) String day) {
        HashMap resultMap = new HashMap();
        resultMap.put("coffeeShopId", CoffeeShop.getInstance().getCoffeeShopId());
        resultMap.put("dailyClientAverageExpense", CoffeeShop.getInstance().calculateDailyClientAverageExpense(CoffeeShopUtils.parseDate(day, CoffeeShopUtils.DATE_FORMAT)));

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(resultMap);
    }

}
