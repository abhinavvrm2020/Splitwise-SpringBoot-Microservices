package com.example.MainProject.Schema.Expense;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ExpensesWithParticipants {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer expenseId;
    private Integer participantId;
    private Integer amount;
}
