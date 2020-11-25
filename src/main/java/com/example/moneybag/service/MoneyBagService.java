package com.example.moneybag.service;

import com.example.moneybag.classes.*;
import com.example.moneybag.repository.ClientsRepository;
import com.example.moneybag.repository.GroupsRepository;
import com.example.moneybag.repository.TransactionLineRepository;
import com.example.moneybag.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class MoneyBagService {

    @Autowired
    private ClientsRepository clientsRepository;
    @Autowired
    private GroupsRepository groupsRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private TransactionLineRepository transactionLineRepository;

    // Add client
    public void addClient(Client client) {
        clientsRepository.addClient(client);
    }

    // Get client by id
    public Client getClientById(int id) {
        return clientsRepository.getClientById(id);
    }

    // Get balance by id
    public BigDecimal getBalanceById(int id) {
        return clientsRepository.getBalanceById(id);
    }

    // Update balance by id
    public void updateBalanceById(int id, BigDecimal moneyIn) {
        BigDecimal balance = clientsRepository.getBalanceById(id);
        balance = balance.add(moneyIn);
        clientsRepository.updateBalanceById(id, balance);
    }

    // Add group by providing client id, code nr and name for the group
    public void addGroup(Group group) {
        groupsRepository.addGroup(group);
    }

    // Get group name by group code
    public String getGroupNameByCode(int code) {
        return groupsRepository.getGroupNameByCode(code);
    }

    // Add transaction money in, update balance in clients table
/*    public Integer addTransactionMoneyIn(Transaction transaction, int id) {
        BigDecimal currentBalance = getBalanceById(id);
        BigDecimal newBalance = transaction.getMoneyIn().add(currentBalance);
        clientsRepository.updateBalanceById(id, newBalance);
        return transactionRepository.addTransactionMoneyIn(transaction, newBalance, id);

    }*/

    // Add transaction money out, update balance in clients table
    public Integer addTransactionMoneyOut(Transaction transaction, int id) {
        BigDecimal currentBalance = getBalanceById(id);
        BigDecimal newBalance = currentBalance.subtract(transaction.getMoneyOut());
        clientsRepository.updateBalanceById(id, newBalance);
        return transactionRepository.addTransactionMoneyOut(transaction, newBalance, id);

    }

    // Add transaction and transaction line simultaneously
    public void addTransactionCombined(TransactionAddRequest transactionAddRequest, int clientId) {
        int transactionId = transactionRepository.createInitialTransaction(clientId);

//        int transactionId = 1;      // TODO: should come from addTransactionMoneyIn's return
        BigDecimal groupBalance = BigDecimal.ZERO;

        BigDecimal currentBalance = getBalanceById(clientId);

        List<TransactionLineAddRequest> thisList = transactionAddRequest.getTransactionLines();
        for (int i = 0; i < thisList.size(); i++) {

            int groupCode = transactionAddRequest.getTransactionLines().get(i).getGroupCode();
            BigDecimal moneyIn = transactionAddRequest.getTransactionLines().get(i).getMoneyIn();
            BigDecimal moneyOut = transactionAddRequest.getTransactionLines().get(i).getMoneyOut();

            if (moneyIn != null) {
                groupBalance = groupBalance.add(moneyIn);
            } else {
                groupBalance = groupBalance.subtract(moneyOut);

            }
            transactionLineRepository.addTransactionLineMoneyIn(transactionId, moneyIn, groupCode);
        }
        BigDecimal transactionSum = currentBalance.add(groupBalance);
        transactionRepository.addTransactionMoneyIn2
                (transactionAddRequest.getDate(), transactionAddRequest.getPayee(), groupBalance, clientId, transactionSum, transactionId);

    }
}
