package com.example.moneybag.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RequestMapping("MoneyBag")
@Controller
public class MoneyBagController {

    @PostMapping("test")
    public String test() {
        return "Can you see me? But can u see me?";
    }

}
