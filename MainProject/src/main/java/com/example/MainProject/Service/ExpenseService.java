package com.example.MainProject.Service;

import com.example.MainProject.Repository.ExpenseRepository;
import com.example.MainProject.Repository.ExpensesWithParticipantsRepository;
import com.example.MainProject.Repository.UserRepository;
import com.example.MainProject.Schema.Expense.Expenses;
import com.example.MainProject.Schema.Expense.ExpensesHeader;
import com.example.MainProject.Schema.Expense.ExpensesWithParticipants;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.MainProject.Schema.User.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ExpenseService {
    @Autowired
    public ExpenseRepository expenseRepository;
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public JwtService jwtService;
    @Autowired
    public ExpensesWithParticipantsRepository expensesWithParticipantsRepository;

    public Integer findLoggedInUserId(String authHeader) {
        String token = authHeader.substring(7);
        String email = jwtService.getUsernameFromToken(token);
        User loggedInUser = userRepository.findByEmail(email)
                .orElseThrow();
        return loggedInUser.getId();
    }

    public boolean ValidateUserToMakeUpdate(Integer payerId,String authHeader) {
        if(authHeader == null) {
            return false;
        }
        String token = authHeader.substring(7);
        String email = jwtService.getUsernameFromToken(token);
        User userFromToken = userRepository.findByEmail(email)
                .orElseThrow();
        return (payerId == userFromToken.getId());
    }

    public boolean ValidateUser(Integer id, String authHeader) {
        if(authHeader == null) {
            return false;
        }
        String token = authHeader.substring(7);
        String email = jwtService.getUsernameFromToken(token);
        User userFromToken = userRepository.findByEmail(email)
                .orElseThrow();
        User userFromId = userRepository.findById(id)
                .orElseThrow();
        if(userFromId.equals(userFromToken)) {
            return true;
        } else {
            return false;
        }
    }

    public String addNewExpense(ExpensesHeader newExpense, String authHeader) {
        Integer loggedInUserId = findLoggedInUserId(authHeader);
        if(loggedInUserId == newExpense.getPayerId()) {
            Expenses expense = Expenses.builder()
                    .payerId(newExpense.getPayerId())
                    .groupId(newExpense.getGroupId())
                    .amount(newExpense.getAmount())
                    .description(newExpense.getDescription()).build();
            expenseRepository.save(expense);
            List<Map<String,Integer>> participants = newExpense.getParticipant();
            for (Map<String, Integer> map : participants) {
                for (Map.Entry<String, Integer> entry : map.entrySet()) {
                    Integer participantId = Integer.parseInt(entry.getKey());
                    ExpensesWithParticipants addParticipant = ExpensesWithParticipants.builder()
                                    .expenseId(expense.getExpenseId())
                                    .participantId(participantId)
                                    .amount(entry.getValue()).build();
                    expensesWithParticipantsRepository.save(addParticipant);
                }
            }
            return "Expense added Successfully";
        } else {
            throw new RuntimeException("You'r not an Admin of this group");
        }
    }

    public List<Expenses> getExpense(Integer id, String authHeader) {
        boolean check = ValidateUser(id,authHeader);
        if(check) {
            try {
                return expenseRepository.findAllExpensesByUserId(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new ArrayList<>();
        } else {
            return new ArrayList<>();
        }
    }
    public String updateExpense(Integer id, ExpensesHeader newExpense, String authHeader) {
        System.out.println(newExpense.getDescription());
        Expenses currentExpense = expenseRepository.findById(id)
                .orElseThrow();
        boolean check = ValidateUserToMakeUpdate(currentExpense.getPayerId(), authHeader);
        if(check) {
            try {
                Integer logInUserId = findLoggedInUserId(authHeader);
                if(newExpense.getAmount() != null) {
                    currentExpense.setAmount(newExpense.getAmount());
                    expenseRepository.save(currentExpense);
                }
                if(newExpense.getDescription() != null) {
                    currentExpense.setDescription(newExpense.getDescription());
                    expenseRepository.save(currentExpense);
                }
                if(newExpense.getParticipant() != null) {
                    List<Map<String,Integer>> participants = newExpense.getParticipant();
                    for (Map<String, Integer> map : participants) {
                        for (Map.Entry<String, Integer> entry : map.entrySet()) {
                            Integer participantId = Integer.parseInt(entry.getKey());
                            ExpensesWithParticipants participantToBeUpdated = expensesWithParticipantsRepository.findByCombinedId(participantId,id);
                            participantToBeUpdated.setParticipantId(participantId);
                            participantToBeUpdated.setAmount(entry.getValue());
                            expensesWithParticipantsRepository.save(participantToBeUpdated);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "Update Unsuccessful";
            }
            return "Update Successful";
        } else {
            return "Log in First";
        }
    }
}