package com.klef.dev.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.klef.dev.entity.Expense;
import com.klef.dev.service.ExpenseService;

@RestController
@RequestMapping("/expenseapi")
@CrossOrigin(origins = "*") // For production, replace * with frontend URL
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    // Health check
    @GetMapping("/")
    public ResponseEntity<Map<String, String>> home() {
        return ResponseEntity.ok(Map.of("message", "Expense API is running!"));
    }

    // Add Expense
    @PostMapping("/add")
    public ResponseEntity<Expense> addExpense(@RequestBody Expense expense) {
        expense.setId(0); // Ensure DB auto-generates ID
        Expense saved = expenseService.addExpense(expense);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // Get all expenses
    @GetMapping("/all")
    public ResponseEntity<List<Expense>> getAllExpenses() {
        List<Expense> list = expenseService.getAllExpenses();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // Get expense by ID
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getExpenseById(@PathVariable int id) {
        Expense exp = expenseService.getExpenseById(id);
        if (exp != null) {
            return ResponseEntity.ok(exp);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Expense with ID " + id + " not found"));
        }
    }

    // Update expense
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateExpense(@PathVariable int id, @RequestBody Expense expense) {
        Expense existing = expenseService.getExpenseById(id);
        if (existing != null) {
            expense.setId(id);
            Expense updated = expenseService.updateExpense(expense);
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Cannot update. Expense with ID " + id + " not found"));
        }
    }

    // Delete expense
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, String>> deleteExpense(@PathVariable int id) {
        Expense existing = expenseService.getExpenseById(id);
        if (existing != null) {
            expenseService.deleteExpenseById(id);
            return ResponseEntity.ok(Map.of("message", "Expense with ID " + id + " deleted successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Cannot delete. Expense with ID " + id + " not found"));
        }
    }
}
