package com.klef.dev.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "expense_table")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expense_id")
    private int id;

    @Column(name = "expense_title", nullable = false, length = 100)
    private String title;

    @Column(name = "expense_category", nullable = false, length = 50)
    private String category;

    @Column(name = "expense_amount", nullable = false)
    private double amount;

    @Column(name = "expense_notes", length = 255)
    private String notes;

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    @Override
    public String toString() {
        return "Expense [id=" + id + ", title=" + title + ", category=" + category +
                ", amount=" + amount + ", notes=" + notes + "]";
    }
}
