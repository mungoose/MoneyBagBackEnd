package com.example.moneybag.service;

import com.example.moneybag.classes.Client;
import com.example.moneybag.classes.Group;
import com.example.moneybag.classes.Transaction;
import com.example.moneybag.repository.ClientsRepository;
import com.example.moneybag.repository.GroupsRepository;
import com.example.moneybag.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class MoneyBagService {

    @Autowired
    private ClientsRepository clientsRepository;
    @Autowired
    private GroupsRepository groupsRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    // Add client
    public void addClient(Client client){
        clientsRepository.addClient(client);
    }

    // Get client by id
    public Client getClientById(int id){
        return clientsRepository.getClientById(id);
    }

    // Get balance by id
    public BigDecimal getBalanceById(int id) {return clientsRepository.getBalanceById(id);}

    // Update balance by id
    public void updateBalanceById(int id, BigDecimal moneyIn) {
        BigDecimal balance = clientsRepository.getBalanceById(id);
        balance = balance.add(moneyIn);
        clientsRepository.updateBalanceById(id, balance);}

    // Add group by providing client id, code nr and name for the group
    public void addGroup(Group group){
        groupsRepository.addGroup(group);
    }

    // Get group name by group code
    public String getGroupNameByCode(int code){
        return groupsRepository.getGroupNameByCode(code);
    }

    // Add transaction money in, update balance in clients table
    public void addTransactionMoneyIn(Transaction transaction, int id){
        BigDecimal currentBalance = getBalanceById(id);
        BigDecimal newBalance = transaction.getMoneyIn().add(currentBalance);
        transactionRepository.addTransactionMoneyIn(transaction, newBalance, id);
        clientsRepository.updateBalanceById(id, newBalance);
    }

    // Add transaction money out, update balance in clients table
    public void addTransactionMoneyOut(Transaction transaction, int id){
        BigDecimal currentBalance = getBalanceById(id);
        BigDecimal newBalance = currentBalance.subtract(transaction.getMoneyOut());
        transactionRepository.addTransactionMoneyOut(transaction, newBalance, id);
        clientsRepository.updateBalanceById(id, newBalance);
    }

    // Add transaction money out, update balance in clients table
    public Integer addTransactionMoneyOutKeyHolder(Transaction transaction, int id){
        BigDecimal currentBalance = getBalanceById(id);
        BigDecimal newBalance = currentBalance.subtract(transaction.getMoneyOut());
        clientsRepository.updateBalanceById(id, newBalance);
        return transactionRepository.addTransactionMoneyOutKeyHolder(transaction, newBalance, id);

    }


}
