package com.example.moneybag.service;

import com.example.moneybag.classes.*;
import com.example.moneybag.repository.*;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

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
    @Autowired
    private PasswordEncoder passwordEncoder;

    private String tokenGenerator(String userName) {
        Date now = new Date();
        JwtBuilder builder = Jwts.builder()
                .setExpiration(new Date(now.getTime() + 1000 * 60 * 60 * 24))
                .setIssuedAt(now)
                .setIssuer("issuer")
                .signWith(SignatureAlgorithm.HS256, "secret")
                .claim("username", userName);
        String jwt = builder.compact();
        return jwt;
    }

    public String validate(Login login) {
        String encodedPassword = clientsRepository.askPassword(login.getUserName());
        boolean isValidate = passwordEncoder.matches(login.getPassword(), encodedPassword);
        String token;
        if (isValidate) {
            token = tokenGenerator(login.getUserName());
        }
        else {
            throw new RuntimeException("Ei õnnestunud");
        }
        return token;
    }

    public String encodedPassword(String password) {
        String encodedPassword = passwordEncoder.encode(password);
        return encodedPassword;
    }


    // Add client
    public void addClient(Client client) {
        String firstName = client.getFirstName();
        String lastName = client.getLastName();
        BigDecimal balance = client.getBalance().ZERO;
        String userName = client.getUserName();
        String password = encodedPassword(client.getPassword());
        clientsRepository.addClient(firstName, lastName, balance, userName, password);
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
        List<String> combined = new ArrayList<>(plannerRepository.getAllInGroupNamesByClientId(clientId));
        combined.addAll(plannerRepository.getAllOutGroupNamesByClientId(clientId));
        return combined;
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
            BigDecimal moneyIn = transactionAddRequest.getTransactionLines().get(i).getMoneyInLine();
            BigDecimal moneyOut = transactionAddRequest.getTransactionLines().get(i).getMoneyOutLine();

            // Initialize transaction line sum in and sum out
            BigDecimal moneyLineIn = BigDecimal.ZERO;
            BigDecimal moneyLineOut = BigDecimal.ZERO;

            // Decide to add transaction line sum to sum in or sum out
            if (moneyIn != null) {
                moneyLineIn = moneyIn;
                // UPDATE PLANNER: Get planner in actual value
                BigDecimal actual2 = plannerRepository.getPlannerInActualById(clientId, groupName);
                // Update planner in actual value
                actual2 = actual2.add(moneyLineIn);
                // Set new in actual value
                plannerRepository.updatePlannerInActualById(clientId, groupName, actual2);
            } else {
                moneyLineOut = moneyOut;
                // UPDATE PLANNER: Get planner out actual value
                BigDecimal actual = plannerRepository.getPlannerOutActualById(clientId, groupName);
                // Update planner out actual value
                actual = actual.add(moneyLineOut);
                // Set new out actual value
                plannerRepository.updatePlannerOutActualById(clientId, groupName, actual);
            }
            // Add transaction line to transaction_lines table
            transactionLineRepository.addTransactionLine
                    (transactionId, moneyLineIn, moneyLineOut, groupCode, groupName);

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

    // Get transactions and transaction lines by client id
    public List<TransactionCombined> getTransactionsCombined(int clientId) {

        // Create a new TransactionCombined list
        List<TransactionCombined> allTransactions = new ArrayList<>();

        // Get all transactions
        List<Transaction> transactions = transactionRepository.getTransactionsById(clientId);

        // Loop through transactions and add result to allTransactions list
        for (Transaction transaction : transactions) {
            // Create a TransactionCombined object to add read elements to
            TransactionCombined transactionCombined = new TransactionCombined();
            // Read transaction id and save it to a variable
            int id = transaction.getId();
            // Read individual variables and add to TransactionCombined object
            transactionCombined.setId(id);
            transactionCombined.setDate(transaction.getDate());
            transactionCombined.setPayee(transaction.getPayee());
            transactionCombined.setMoneyIn(transaction.getMoneyIn());
            transactionCombined.setMoneyOut(transaction.getMoneyOut());
            transactionCombined.setEndBalance(transaction.getEndBalance());
            transactionCombined.setLocked(transaction.getLocked());

            // Get all transaction lines by transaction id
            List<TransactionLine> transactionLinesGet =
                    transactionLineRepository.getTransactionLinesByTransactionId(id);
            // Create a new list for TransactionLinesList objects
            List<TransactionLinesList> transactionLinesLists = new ArrayList<>();
            // Loop through transactionLines
            for (TransactionLine transactionLine : transactionLinesGet) {
                // Create a new TransactionLinesList object
                TransactionLinesList transactionLines = new TransactionLinesList();
                // Read individual variables and add to TransactionLinesList object
                transactionLines.setId(transactionLine.getId());
                transactionLines.setMoneyInLine(transactionLine.getMoneyInLine());
                transactionLines.setMoneyOutLine(transactionLine.getMoneyOutLine());
                transactionLines.setGroupName(transactionLine.getGroupName());
                // Add TransactionLinesList object to transactionLinesList
                transactionLinesLists.add(transactionLines);
            }
            // Add TransactionLinesList to transactionCombined object
            transactionCombined.setTransactionLinesList(transactionLinesLists);
            // Add transactionCombined object to allTransactions list
            allTransactions.add(transactionCombined);
        }
        return allTransactions;
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


    // Get planner out by id
    public List<PlannerOut> getPlannerOutById(int clientId) {
        return plannerRepository.getPlannerOutById(clientId);
    }

    // Get planner out by id
    public List<PlannerIn> getPlannerInById(int clientId) {
        return plannerRepository.getPlannerInById(clientId);
    }


    // Insert default planner out lines
    public void insertDefaultPlannerOutById(int clientId) {
        // Create correct date format
        Date date = new Date();
        SimpleDateFormat formatDate = new SimpleDateFormat("MMMM yyyy", Locale.forLanguageTag("et-EE"));
        String monthYear = formatDate.format(date);
        monthYear = monthYear.substring(0, 1).toUpperCase() + monthYear.substring(1).toLowerCase();
        int groupCode = 1;
        List<String> defaultNames = Arrays.asList("Toit", "Väljas söömine", "Kodutarbed", "Kodulaen/üür", "Kindlustus",
                "Telekommunikatsioon", "Transport", "Meelelahutus", "Tervis", "Säästmine");
        for (int i = 0; i < defaultNames.size(); i++) {
            groupCode = i + 2;
            String groupName = defaultNames.get(i);
            plannerRepository.insertDefaultPlannerOutById(clientId, monthYear, groupCode, groupName);
        }
    }

    // Insert default planner in line
    public void insertDefaultPlannerInById(int clientId) {
        // Create correct date format
        Date date = new Date();
        SimpleDateFormat formatDate = new SimpleDateFormat("MMMM yyyy", Locale.forLanguageTag("et-EE"));
        String monthYear = formatDate.format(date);
        monthYear = monthYear.substring(0, 1).toUpperCase() + monthYear.substring(1).toLowerCase();
        plannerRepository.insertDefaultPlannerInById(clientId, monthYear, 1, "Sissetulek");

    }

    @Transactional
    // Update planner out by id
    public void updatePlannerOutById(PlannerOutCreator plannerOutCreator, int clientId) {
        plannerRepository.deletePlannerOutByID(clientId);
        List<PlannerOut> plannerOutList = plannerOutCreator.getPlannerOutCreatorList();
        for (int i = 0; i < plannerOutList.size(); i++) {
            PlannerOut plannerOut = plannerOutList.get(i);
            plannerRepository.insertPlannerOutById(plannerOut);
        }
    }

    @Transactional
    // Update planner in by id
    public void updatePlannerInById(PlannerInCreator plannerInCreator, int clientId) {
        plannerRepository.deletePlannerInByID(clientId);
        List<PlannerIn> plannerInList = plannerInCreator.getPlannerInCreatorList();
        for (int i = 0; i < plannerInList.size(); i++) {
            PlannerIn plannerIn = plannerInList.get(i);
            plannerRepository.insertPlannerInById(plannerIn);
        }
    }


}
