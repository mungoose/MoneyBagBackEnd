package com.example.moneybag.service;

import com.example.moneybag.repository.MoneyBagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoneyBagService {

    @Autowired
    private MoneyBagRepository moneyBagRepository;


}
