package com.example.MainProject.Repository;

import com.example.MainProject.Schema.Expense.Expenses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expenses,Integer> {
    @Query(value = "SELECT * FROM expenses WHERE payer_id = :id", nativeQuery = true)
    List<Expenses> findAllExpensesByUserId (Integer id);

    @Query(value = "SELECT SUM(split_weightage) FROM expenses WHERE payer_id = :id", nativeQuery = true)
    Integer findByPayerId (Integer id);
    @Query(value = "SELECT FROM expenses WHERE payer_id = :payerId AND group_id = :groupId", nativeQuery = true)
    Expenses findByCombinedId(Integer payerId, Integer groupId);
}
