package com.example.moneybag.controller;

import com.example.moneybag.classes.Client;
import com.example.moneybag.classes.GoalSetter;
import com.example.moneybag.classes.Group;
import com.example.moneybag.classes.Planner;
import com.example.moneybag.classes.TransactionAddRequest;
import com.example.moneybag.service.MoneyBagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;


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
                                                            // localhost:8083/MoneyBag/getGroupNameByCode?code=1// Get group name by group code

    // Get client all group names by client id
    @CrossOrigin
    @GetMapping("getAllGroupNamesByClientId")
    public List<Group> getAllGroupNamesByClientId(int clientid){
        return moneyBagService.getAllGroupNamesByClientId(clientid);
    }
                                                            // localhost:8083/MoneyBag/getAllGroupNamesByClientId?clientid=1


    // Add transaction and transaction line simultaneously
    @CrossOrigin
    @PostMapping("addTransaction")
    public void addTransactionCombined(@RequestBody TransactionAddRequest transactionAddRequest, int clientid){
        moneyBagService.addTransactionCombined(transactionAddRequest, clientid);
    }
                                                            // localhost:8083/MoneyBag/addTransaction + body

<<<<<<< HEAD
    //Get all income, one client
    @CrossOrigin
    @GetMapping("oneClientAllIncome")
    public BigDecimal oneClientAllIncome(int clientid){
        return moneyBagService.oneClientAllIncome(clientid);
    }

    //Get all expenses, one client
    @CrossOrigin
    @GetMapping("oneClientAllCosts")
    public BigDecimal oneClientAllCosts (int clientid){
        return moneyBagService.oneClientAllCosts(clientid);
    }

    //Get all income, one group, one client
    @CrossOrigin
    @GetMapping("oneClientAllIncomebyGroup")
    public BigDecimal oneClientAllIncome(int groupcode, int clientid){
        return moneyBagService.getGruopSumIN(groupcode, clientid);
    }

    //Get all expenses, one group, one client
    @CrossOrigin
    @GetMapping("oneClientAllCostsbyGroup")
    public BigDecimal oneClientAllCosts (int groupcode, int clientid){
        return moneyBagService.getGruopSumOUT(groupcode, clientid);
    }

    //Create new goal for a group
    @CrossOrigin
    @PostMapping("addMaxGoal")
    public void addGoal(@RequestBody GoalSetter goalSetter){
        moneyBagService.addGoal(goalSetter);
    }

    //Change goal for a group
    @CrossOrigin
    @PostMapping("changeMaxGoal")
    public void changeGoal (@RequestBody GoalSetter goalSetter){
        moneyBagService.changeGoal(goalSetter);
    }




=======
    // Get planner by id
    @CrossOrigin
    @GetMapping("getPlannerById")
    public List<Planner> getPlannerById(int id){
        return moneyBagService.getPlannerById(id);
    }
                                                            // localhost:8083/MoneyBag/getPlannerById?id=1
>>>>>>> working_tree
}
