package com.example.MainProject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.MainProject.Schema.Expense.ExpensesWithParticipants;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpensesWithParticipantsRepository extends JpaRepository<ExpensesWithParticipants,Integer> {

    @Query(value = "SELECT * FROM expenses_with_participants WHERE participant_id = :participantId AND expense_id = :expenseId", nativeQuery = true)
    ExpensesWithParticipants findByCombinedId(Integer participantId, Integer expenseId);

    @Query(value = "SELECT SUM(amount) FROM expenses_with_participants WHERE participant_id = :participantId", nativeQuery = true)
    Integer findByParticipantId(Integer participantId);
    @Query(value = "SELECT SUM(amount) FROM expenses_with_participants WHERE participant_id = :participantId AND expense_id = :expenseId", nativeQuery = true)
    Integer findSumByCombinedId(Integer participantId, Integer expenseId);
}
