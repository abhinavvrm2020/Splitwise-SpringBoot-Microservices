package com.example.MainProject.Service;

import com.example.MainProject.Repository.*;
import com.example.MainProject.Schema.Expense.Expenses;
import com.example.MainProject.Schema.Group.Groups;
import com.example.MainProject.Schema.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityService {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private ExpensesWithParticipantsRepository expensesWithParticipantsRepository;
    public Integer findLoggedInUserId(String authHeader) {
        String token = authHeader.substring(7);
        String email = jwtService.getUsernameFromToken(token);
        User loggedInUser = userRepository.findByEmail(email)
                .orElseThrow();
        return loggedInUser.getId();
    }
    public Integer getReceiveAmount(String authHeader) {
        Integer user = findLoggedInUserId(authHeader);
        Integer amount = 0;
        List<Groups> userPartOfGroup = groupRepository.findByPayerId(user);
        int grpSize = userPartOfGroup.size();
        for(int i = 0; i < grpSize; ++i) {
            List<Integer> participants = userPartOfGroup.get(i).getMembers();
            int size = participants.size();
            System.out.println(user + " " + userPartOfGroup.get(i).getGroupId());
            Expenses currentExpense = expenseRepository.findByCombinedId(user,userPartOfGroup.get(i).getGroupId());
            System.out.println(currentExpense.getExpenseId());
            System.out.println(user + " " + userPartOfGroup.get(i).getGroupId());
            for(int j = 0; j < size; ++j) {
                System.out.println(participants.get(j) + " " + currentExpense.getExpenseId());
                amount += expensesWithParticipantsRepository.findSumByCombinedId(participants.get(j),currentExpense.getExpenseId());
                System.out.println(participants.get(j) + " " + currentExpense.getExpenseId());
            }
        }
        return amount;
    }

    public Integer getSendAmount(String authHeader) {
        Integer user = findLoggedInUserId(authHeader);
        return expensesWithParticipantsRepository.findByParticipantId(user);
    }
}
