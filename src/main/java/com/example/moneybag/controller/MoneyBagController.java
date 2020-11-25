package com.example.moneybag.controller;

import com.example.moneybag.classes.Client;
import com.example.moneybag.classes.Group;
import com.example.moneybag.classes.Transaction;
import com.example.moneybag.service.MoneyBagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;


@RequestMapping("MoneyBag")                                 // localhost:8083/
@RestController
public class MoneyBagController {

    @Autowired
    private MoneyBagService moneyBagService;

    // Add client
    @CrossOrigin
    @PostMapping("addClient")
    public void addClient(@RequestBody Client client){
        moneyBagService.addClient(client);
    }
                                                            // localhost:8083/MoneyBag/addClient + body

    // Get client by id
    @CrossOrigin
    @GetMapping("getClientById")
    public Client getClientById(int id){
        return moneyBagService.getClientById(id);
    }
                                                            // localhost:8083/MoneyBag/getClientById?id=1

    // Get balance by id
    @CrossOrigin
    @GetMapping("getBalanceById")
    public BigDecimal getBalanceById(int id) {return moneyBagService.getBalanceById(id);}
                                                            // localhost:8083/MoneyBag/getBalanceById?id=1

    // Add group by providing client id, code nr and name for the group
    @CrossOrigin
    @PostMapping("createGroup")
    public void createGroup(@RequestBody Group group){
        moneyBagService.addGroup(group);
    }
                                                            // localhost:8083/MoneyBag/createGroup + body

    // Get group name by group code
    @CrossOrigin
    @GetMapping("getGroupNameByCode")
    public String getGroupNameByCode(int code){
        return moneyBagService.getGroupNameByCode(code);
    }
                                                            // localhost:8083/MoneyBag/getGroupNameByCode?code=1

    // Add transaction money in
    @CrossOrigin
    @PutMapping("addTransactionMoneyIn")
    public void addTransactionMoneyIn(@RequestBody Transaction transaction, int id){
        moneyBagService.addTransactionMoneyIn(transaction, id);
    }
                                                            // localhost:8083/MoneyBag/addTransactionMoneyIn?id=1 + body

    // Add transaction money out
    @CrossOrigin
    @PutMapping("addTransactionMoneyOut")
    public void addTransactionMoneyOut(@RequestBody Transaction transaction, int id){
        moneyBagService.addTransactionMoneyOut(transaction, id);
    }
                                                            // localhost:8083/MoneyBag/addTransactionMoneyOut?id=1 + body

    // Add transaction money out with auto-key
    @CrossOrigin
    @PutMapping("addTransactionMoneyOutKey")
    public Integer addTransactionMoneyOutKey(@RequestBody Transaction transaction, int id){
        return moneyBagService.addTransactionMoneyOutKeyHolder(transaction, id);
    }
                                                            // localhost:8083/MoneyBag/addTransactionMoneyOutKey?id=1 + body

    // Add
}
