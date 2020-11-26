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

    // Add group by providing client id, code nr and name for the group
    public void addGroup(Group group) {
        groupsRepository.addGroup(group);
    }

    // Get group name by group code
    public String getGroupNameByCode(int code) {
        return groupsRepository.getGroupNameByCode(code);
    }


    // Add transaction and transaction line simultaneously
    public void addTransactionCombined(TransactionAddRequest transactionAddRequest, int clientId) {

        // Create an empty transaction to create a transaction id
        int transactionId = transactionRepository.createInitialTransaction(clientId);

        // Initialize the balance of transaction lines
        BigDecimal groupBalance = BigDecimal.ZERO;

        // Get current balance
        BigDecimal currentBalance = getBalanceById(clientId);

        // Get transaction lines from Json body
        List<TransactionLineAddRequest> thisList = transactionAddRequest.getTransactionLines();
        // Loop through lines
        for (int i = 0; i < thisList.size(); i++) {
            // Get group code
            int groupCode = transactionAddRequest.getTransactionLines().get(i).getGroupCode();
            // Get money amount
            BigDecimal moneyIn = transactionAddRequest.getTransactionLines().get(i).getMoneyIn();
            BigDecimal moneyOut = transactionAddRequest.getTransactionLines().get(i).getMoneyOut();

            // Initialize transaction line sum in and sum out
            BigDecimal moneyLineIn = BigDecimal.ZERO;
            BigDecimal moneyLineOut = BigDecimal.ZERO;

            // Decide to add transaction line sum to sum in or sum out
            if (moneyIn != null){
                moneyLineIn = moneyIn;
            } else {
                moneyLineOut = moneyOut;
            }
            // Add transaction line to transaction_lines table
            transactionLineRepository.addTransactionLine(transactionId, moneyLineIn, moneyLineOut, groupCode);

            if (moneyIn != null) {
                groupBalance = groupBalance.add(moneyIn);
            } else {
                groupBalance = groupBalance.subtract(moneyOut);

            }
        }
        // Calculate new balance
        BigDecimal newBalance = currentBalance.add(groupBalance);

        // Update balance in clients table
        clientsRepository.updateBalanceById(clientId, newBalance);

        // Initialize sum in and sum out
        BigDecimal transactionSumIn = BigDecimal.ZERO;
        BigDecimal transactionSumOut = BigDecimal.ZERO;

        // Decide to add transaction sum to sum in or sum out
        if (groupBalance.compareTo(BigDecimal.ZERO) > 0) {
            transactionSumIn = groupBalance;
        } else {
            transactionSumOut = groupBalance;
        }
        // Update created transaction line
        transactionRepository.addTransaction
                (clientId, transactionAddRequest.getDate(), transactionAddRequest.getPayee(),
                        transactionSumIn, transactionSumOut.abs(), newBalance, transactionId);
    }
}
