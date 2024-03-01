package com.example.MainProject.Schema.Expense;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ExpensesHeader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer expenseId;
    private Integer payerId;
    private Integer groupId;
    private Double amount;
    private List<Map<String,Integer>> participant;
    private String description;
}
