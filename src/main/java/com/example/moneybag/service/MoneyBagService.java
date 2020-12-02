package com.example.moneybag.service;

import com.example.moneybag.classes.*;
import com.example.moneybag.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    @Autowired
    private GoalSetterRepository goalSetterRepository;
    @Autowired
    private PlannerRepository plannerRepository;

    // Add client
    public void addClient(Client client) {
        clientsRepository.addClient(client);
    }

    // Get client by id
    public Client getClientById(int clientId) {
        return clientsRepository.getClientById(clientId);
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

    // Get all client group names by client id
    public List<String> getAllGroupNamesByClientId(int clientId) {
        return plannerRepository.getAllGroupNamesByClientId(clientId);
    }


    // Add transaction and transaction line simultaneously
    @Transactional         // If something brakes in the middle of this function, roll back
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
            // Get group name
            String groupName = transactionAddRequest.getTransactionLines().get(i).getGroupName();
            // Get money amount
            BigDecimal moneyIn = transactionAddRequest.getTransactionLines().get(i).getMoneyIn();
            BigDecimal moneyOut = transactionAddRequest.getTransactionLines().get(i).getMoneyOut();

            // Initialize transaction line sum in and sum out
            BigDecimal moneyLineIn = BigDecimal.ZERO;
            BigDecimal moneyLineOut = BigDecimal.ZERO;

            // Decide to add transaction line sum to sum in or sum out
            if (moneyIn != null) {
                moneyLineIn = moneyIn;
            } else {
                moneyLineOut = moneyOut;
            }
            // Add transaction line to transaction_lines table
            transactionLineRepository.addTransactionLine
                    (transactionId, moneyLineIn, moneyLineOut, groupCode, clientId, groupName);

            // UPDATE PLANNER: Get planner actual value
            BigDecimal actual = plannerRepository.getPlannerActualById(clientId, groupName);
            // Update planner actual value
            actual = actual.add(moneyLineIn);
            actual = actual.add(moneyLineOut);
            // Set new actual value
            plannerRepository.updatePlannerActualById(clientId, groupName, actual);

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

    // Get transactions by id
    public List<Transaction> getTransactionsById(int clientId) {
        return transactionRepository.getTransactionsById(clientId);
    }

    public BigDecimal oneClientAllIncome(int clientid) {
        List<BigDecimal> allMoneyIn = transactionRepository.oneClientAllIncome(clientid);
        BigDecimal sum = new BigDecimal(0);
        for (BigDecimal num : allMoneyIn) {
            sum = sum.add(num);
        }
        return sum;
    }

    public BigDecimal oneClientAllCosts(int clientid) {
        List<BigDecimal> allMoneyOut = transactionRepository.oneClientAllCosts(clientid);
        BigDecimal sum = new BigDecimal(0);
        for (BigDecimal num : allMoneyOut) {
            sum = sum.add(num);
        }
        return sum;
    }

    public BigDecimal getGruopSumOUT(int groupCode, int clientid) {
        List<BigDecimal> allMoneyOut = transactionLineRepository.getGroupSumOUT(groupCode, clientid);
        BigDecimal sum = new BigDecimal(0);
        for (BigDecimal num : allMoneyOut) {
            sum = sum.add(num);
        }
        return sum;
    }

    public BigDecimal getGruopSumIN(int groupCode, int clientid) {
        List<BigDecimal> allMoneyIn = transactionLineRepository.getGroupSumIN(groupCode, clientid);
        BigDecimal sum = new BigDecimal(0);
        BigDecimal num;
        for (int i = 0; i < allMoneyIn.size(); i++) {
            num = allMoneyIn.get(i);
            sum = sum.add(num);
        }
        return sum;
    }

    // Add group goal
    public void addGoal(GoalSetter goalSetter) {
        goalSetterRepository.addGoal(goalSetter);
    }

    // change group goal
    public void changeGoal(GoalSetter goalSetter) {
        goalSetterRepository.changeGoal(goalSetter);
    }

    // Get planner by id
    public List<Planner> getPlannerById(int id) {
        return plannerRepository.getPlannerById(id);
    }

    // Insert default planner lines
    public void insertDefaultPlannerById(int clientId) {
        // Create correct date format
        Date date = new Date();
        SimpleDateFormat formatDate = new SimpleDateFormat("MMMM yyyy", Locale.forLanguageTag("et-EE"));
        String monthYear = formatDate.format(date);
        monthYear = monthYear.substring(0, 1).toUpperCase() + monthYear.substring(1).toLowerCase();
        int groupCode = 1;
        List<String> defaultNames = Arrays.asList("Sissetulek", "Toit", "Väljas söömine", "Kodutarbed", "Kodulaen/üür", "Kindlustus",
                "Telekommunikatsioon", "Transport", "Meelelahutus", "Tervis", "Säästmine");
        for (int i = 0; i < defaultNames.size(); i++) {
            groupCode = i + 1;
            String groupName = defaultNames.get(i);
            plannerRepository.insertDefaultPlannerById(clientId, monthYear, groupCode, groupName);
        }
    }

    // Update planner by id
    @Transactional
    public void updatePlannerById(PlannerCreator plannerCreator) {
        int clientId = plannerCreator.getPlannerCreatorList().get(1).getClientId();
        plannerRepository.deletePlannerByID(clientId);
        List<Planner> plannerList = plannerCreator.getPlannerCreatorList();
        for (int i = 0; i < plannerList.size(); i++) {
            Planner planner = plannerList.get(i);
            plannerRepository.insertPlannerById(planner);
        }
    }

    // Get planned in and planned out difference by id
    public BigDecimal getPlannedDifferenceById(int clientId) {
        BigDecimal plannedIn = plannerRepository.getPlannerById(clientId).get(1).getPlannedIn();
        BigDecimal plannedOut = plannerRepository.getPlannerById(clientId).get(1).getPlannedOut();
        return plannedIn.subtract(plannedOut);
    }

}
