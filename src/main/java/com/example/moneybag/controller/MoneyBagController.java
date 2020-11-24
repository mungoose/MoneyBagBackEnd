package com.example.moneybag.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RequestMapping("MoneyBag")
@RestController
public class MoneyBagController {

        // For testing purposes
    @GetMapping("test")
    public String test() {
        return "Can you see me? But can u see me? I can see u, can u see me now?";
    }
        // localhost:8082/MoneyBag/test
}
