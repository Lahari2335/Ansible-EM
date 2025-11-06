package com.klef.dev.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.klef.dev.entity.Expense;
import com.klef.dev.service.ExpenseService;

@RestController
@RequestMapping("/expenseapi")
@CrossOrigin(origins = "*")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/")
    public String home() {
        return "Expense API is running!";
    }

    @PostMapping("/add")
    public ResponseEntity<Expense> addExpense(@RequestBody Expense expense) {
        Expense saved = expenseService.addExpense(expense);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Expense>> getAllExpenses() {
        List<Expense> list = expenseService.getAllExpenses();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getExpenseById(@PathVariable int id) {
        Expense exp = expenseService.getExpenseById(id);
        if (exp != null)
            return new ResponseEntity<>(exp, HttpStatus.OK);
        else
            return new ResponseEntity<>("Expense with ID " + id + " not found.", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateExpense(@PathVariable int id, @RequestBody Expense expense) {
        Expense existing = expenseService.getExpenseById(id);
        if (existing != null) {
            expense.setId(id);
            Expense updated = expenseService.updateExpense(expense);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cannot update. Expense with ID " + id + " not found.", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteExpense(@PathVariable int id) {
        Expense existing = expenseService.getExpenseById(id);
        if (existing != null) {
            expenseService.deleteExpenseById(id);
            return new ResponseEntity<>("Expense with ID " + id + " deleted successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cannot delete. Expense with ID " + id + " not found.", HttpStatus.NOT_FOUND);
        }
    }
}
