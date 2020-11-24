package com.example.moneybag.service;

import com.example.moneybag.classes.Test2;
import com.example.moneybag.repository.MoneyBagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoneyBagService {

    @Autowired
    private MoneyBagRepository moneyBagRepository;

    public List<Test2> getName(int i){
        return moneyBagRepository.getName(i);
    }
}
