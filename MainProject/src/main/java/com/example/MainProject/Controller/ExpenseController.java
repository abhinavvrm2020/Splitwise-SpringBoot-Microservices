package com.example.MainProject.Controller;

import com.example.MainProject.Schema.Expense.Expenses;
import com.example.MainProject.Schema.Expense.ExpensesHeader;
import com.example.MainProject.Service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("expenses")
public class ExpenseController {
    @Autowired
    public ExpenseService expenseService;
    @PostMapping("add")
    public ResponseEntity<String> addExpense(
            @RequestBody ExpensesHeader newExpenseHeader,
            @RequestHeader("Authorization") String authHeader

    ) {
        return ResponseEntity.ok(expenseService.addNewExpense(newExpenseHeader,authHeader));
    }
    @GetMapping("allExpenses/{id}")
    public ResponseEntity<List<Expenses>> getExpense(
            @PathVariable Integer id,
            @RequestHeader("Authorization") String authHeader
    ) {
        return ResponseEntity.ok(expenseService.getExpense(id,authHeader));
    }
    @PutMapping("update/{id}")
    public ResponseEntity<String> updateExpense(
            @RequestBody ExpensesHeader newExpense,
            @PathVariable Integer id,
            @RequestHeader("Authorization") String authHeader
    ) {
        return ResponseEntity.ok(expenseService.updateExpense(id,newExpense,authHeader));
    }
}
