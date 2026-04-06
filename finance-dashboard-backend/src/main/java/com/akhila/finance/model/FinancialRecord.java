package com.akhila.finance.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class FinancialRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount;
    private String type;
    private String category;
    private LocalDate date;
    private String notes;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;  // renamed from userId to user (cleaner)

    @Column(name = "is_deleted")
    private boolean deleted = false;  //  renamed from isDeleted to deleted

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public boolean isDeleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }
}