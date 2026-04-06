package com.akhila.finance.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Schema(description = "Financial record request body")
public class FinancialRecordDTO {

    @Schema(description = "Record ID - only needed for update, skip for create", example = "1")
    private Long id;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    @Schema(description = "Transaction amount - must be positive", example = "5000.0", required = true)
    private Double amount;

    @NotBlank(message = "Type is required")
    @Schema(description = "Type of transaction: income or expense", example = "income", required = true)
    private String type;

    @NotBlank(message = "Category is required")
    @Schema(description = "Category: salary, food, rent, etc", example = "salary", required = true)
    private String category;

    @NotNull(message = "Date is required")
    @Schema(description = "Transaction date (yyyy-MM-dd)", example = "2026-04-06", required = true)
    private LocalDate date;

    @Schema(description = "Additional notes (optional)", example = "Monthly salary")
    private String notes;

    @NotNull(message = "User ID is required")
    @Schema(description = "Must be existing user ID from user table - record owner", example = "2", required = true)
    private Long userId;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
}