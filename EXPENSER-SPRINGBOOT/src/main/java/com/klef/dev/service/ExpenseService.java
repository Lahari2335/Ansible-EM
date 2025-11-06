package com.klef.dev.service;

import java.util.List;
import com.klef.dev.entity.Expense;

public interface ExpenseService {
    Expense addExpense(Expense expense);
    List<Expense> getAllExpenses();
    Expense getExpenseById(int id);
    Expense updateExpense(Expense expense);
    void deleteExpenseById(int id);
}
