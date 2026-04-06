package com.akhila.finance.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;
import com.akhila.finance.model.FinancialRecord;
import com.akhila.finance.service.RecordService;
import com.akhila.finance.DTO.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/records")
@Tag(name = "2. Record APIs", description = "Manage financial records - Only ADMIN can create/update/delete")
public class RecordController {

    @Autowired
    private RecordService service;

    @PostMapping
    @Operation(
        summary = "Create a financial record",
        description = "userId param = ADMIN user ID (must be ADMIN role)\n" +
                      "userId in body = record owner (must exist in user table & be active)\n" +
                      "Only ADMIN can create records for other users."
    )
    public ResponseEntity<ApiResponse<FinancialRecord>> create(
            @Parameter(description = "⚠️ Must be ADMIN user ID", example = "1")
            @RequestParam Long userId,
            @Valid @RequestBody FinancialRecordDTO dto) {

        FinancialRecord created = service.create(dto, userId);
        return ResponseEntity.ok(new ApiResponse<>("Record created successfully", created));
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Get record by transaction ID",
        description = "id = transaction ID (from financial_record table)\n" +
                      "userId = owner of the record (must exist in user table)\n" +
                      "Record must belong to the given userId."
    )
    public ResponseEntity<ApiResponse<FinancialRecord>> getById(
            @Parameter(description = "Transaction ID", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Must be existing user ID (record owner)", example = "2")
            @RequestParam Long userId) {

        FinancialRecord record = service.getById(id, userId);
        return ResponseEntity.ok(new ApiResponse<>("Record fetched successfully", record));
    }

    @GetMapping
    @Operation(
        summary = "Get all records with optional filters + pagination",
        description = "Filter by one or more: type, category, date\n" +
                      "type values: income or expense\n" +
                      "Supports pagination: page=0, size=5"
    )
    public ResponseEntity<ApiResponse<Page<FinancialRecord>>> getAll(
            @Parameter(description = "Filter by type: income or expense", example = "income")
            @RequestParam(required = false) String type,
            @Parameter(description = "Filter by category: salary, food, rent etc", example = "salary")
            @RequestParam(required = false) String category,
            @Parameter(description = "Filter by date (yyyy-MM-dd)", example = "2026-04-06")
            @RequestParam(required = false) java.time.LocalDate date,
            @Parameter(description = "Page number (starts from 0)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of records per page", example = "5")
            @RequestParam(defaultValue = "5") int size) {

        Page<FinancialRecord> records = service.getAll(type, category, date, PageRequest.of(page, size));
        return ResponseEntity.ok(new ApiResponse<>("Records fetched successfully", records));
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Update a financial record",
        description = " id = transaction ID to update\n" +
                      " userId param = ADMIN user ID (must be ADMIN role)\n" +
                      " userId in body = record owner (must exist in user table & be active)"
    )
    public ResponseEntity<ApiResponse<FinancialRecord>> update(
            @Parameter(description = "Transaction ID to update", example = "1")
            @PathVariable Long id,
            @Parameter(description = " Must be ADMIN user ID", example = "1")
            @RequestParam Long userId,
            @Valid @RequestBody FinancialRecordDTO dto) {

        FinancialRecord updated = service.update(id, dto, userId);
        return ResponseEntity.ok(new ApiResponse<>("Record updated successfully", updated));
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Soft delete a record",
        description = "userId param = ADMIN user ID (must be ADMIN role)\n" +
                      "Record is NOT permanently deleted - just marked as deleted.\n" +
                      "Deleted records won't appear in GET requests."
    )
    public ResponseEntity<ApiResponse<Void>> delete(
            @Parameter(description = "Transaction ID to delete", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Must be ADMIN user ID", example = "1")
            @RequestParam Long userId) {

        service.delete(id, userId);
        return ResponseEntity.ok(new ApiResponse<>("Record deleted successfully", null));
    }
}