package com.example.MainProject.Service;

import com.example.MainProject.Repository.*;
import com.example.MainProject.Schema.Expense.Expenses;
import com.example.MainProject.Schema.Expense.ExpensesWithParticipants;
import com.example.MainProject.Schema.Group.Groups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SettlementService {
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public GroupRepository groupRepository;
    @Autowired
    public ExpenseRepository expenseRepository;
    @Autowired
    public JwtService jwtService;
    @Autowired
    ExpensesWithParticipantsRepository expensesWithParticipantsRepository;
    public String doSettlement(Integer group_id,Integer payer_id,Integer receiver_id,String authHeader) {
        try {
            Groups currentGroup = groupRepository.findById(group_id)
                    .orElseThrow();
            List<Integer> participants = currentGroup.getMembers();
            if(!participants.contains(payer_id)) {
                return "Payer doesn't owe to receiver";
            } else {
                Expenses currentExpense = expenseRepository.findByCombinedId(payer_id,group_id);
                ExpensesWithParticipants currentExpenseWithParticipant = expensesWithParticipantsRepository.findByCombinedId(currentExpense.getExpenseId(),payer_id);
                expensesWithParticipantsRepository.delete(currentExpenseWithParticipant);
                return "Settlement Done";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Settlement not performed";
        }
    }
}