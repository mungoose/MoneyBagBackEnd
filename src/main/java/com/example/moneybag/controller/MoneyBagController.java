package com.example.moneybag.controller;

import com.example.moneybag.service.MoneyBagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("MoneyBag") // localhost:8083/
@RestController
public class MoneyBagController {

    @Autowired
    private MoneyBagService moneyBagService;



}
