package com.example.moneybag.controller;

import com.example.moneybag.classes.Test2;
import com.example.moneybag.service.MoneyBagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("MoneyBag")
@RestController
public class MoneyBagController {

    @Autowired
    private MoneyBagService moneyBagService;

        // For testing purposes
    @GetMapping("test")
    public List<Test2> test(int i) {

        return moneyBagService.getName(i);
    }
        // localhost:8082/MoneyBag/test?i=1
}
