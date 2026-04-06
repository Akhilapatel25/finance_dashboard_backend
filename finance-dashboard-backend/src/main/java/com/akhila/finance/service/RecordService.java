package com.akhila.finance.service;

import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDate;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import com.akhila.finance.model.FinancialRecord;
import com.akhila.finance.model.User;
import com.akhila.finance.repository.RecordRepository;
import com.akhila.finance.repository.UserRepository;
import com.akhila.finance.DTO.FinancialRecordDTO;
import com.akhila.finance.exception.UnauthorizedException;

@Service
public class RecordService {

    @Autowired
    private RecordRepository repo;

    @Autowired
    private UserRepository userRepo;

    // CREATE
    public FinancialRecord create(FinancialRecordDTO dto, Long adminUserId) {

        // 1. Check param userId is ADMIN
        User admin = userRepo.findById(adminUserId)
                .orElseThrow(() -> new RuntimeException("Admin user not found"));

        if (!admin.getRole().getRoleName().equals("ADMIN")) {
            throw new UnauthorizedException("Only ADMIN can create records");
        }

        // 2. Check body userId exists in DB
        User owner = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found in DB"));

        // 3. Check owner is active
        if (!owner.isActive()) {
            throw new RuntimeException("Cannot create record - User account is inactive");
        }

        // 4. Create and save record
        FinancialRecord record = new FinancialRecord();
        record.setAmount(dto.getAmount());
        record.setType(dto.getType());
        record.setCategory(dto.getCategory());
        record.setDate(dto.getDate());
        record.setNotes(dto.getNotes());
        record.setDeleted(false);
        record.setUser(owner);

        return repo.save(record);
    }

    //GET BY ID
    public FinancialRecord getById(Long id, Long userId) {

        // 1. Check user exists in DB
        User owner = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Check user is active
        if (!owner.isActive()) {
            throw new RuntimeException("User account is inactive");
        }

        // 3. Get record and verify belongs to user
        FinancialRecord record = repo.findById(id)
                .filter(r -> !r.isDeleted())
                .orElseThrow(() -> new RuntimeException("Record not found or deleted"));

        if (!record.getUser().getId().equals(owner.getId())) {
            throw new RuntimeException("This record does not belong to user: " + userId);
        }

        return record;
    }

    //GET ALL WITH FILTERS
    public Page<FinancialRecord> getAll(String type, String category, LocalDate date, Pageable pageable) {
        return repo.findWithFilters(type, category, date, pageable);
    }

    //UPDATE
    public FinancialRecord update(Long recordId, FinancialRecordDTO dto, Long adminUserId) {

        // 1. Check param userId is ADMIN
        User admin = userRepo.findById(adminUserId)
                .orElseThrow(() -> new RuntimeException("Admin user not found"));

        if (!admin.getRole().getRoleName().equals("ADMIN")) {
            throw new UnauthorizedException("Only ADMIN can update records");
        }

        // 2. Check body userId exists in DB
        User owner = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found in DB"));

        // 3. Check owner is active
        if (!owner.isActive()) {
            throw new RuntimeException("Cannot update record - User account is inactive");
        }

        // 4. Find the record
        FinancialRecord record = repo.findById(recordId)
                .filter(r -> !r.isDeleted())
                .orElseThrow(() -> new RuntimeException("Record not found or deleted"));
     // 5. Validate amount
        if (dto.getAmount() == null || dto.getAmount() < 0) {
            throw new IllegalArgumentException("Amount must be greater than or equal to 0");
        }
        // 5. Update and save
        record.setAmount(dto.getAmount());
        record.setType(dto.getType());
        record.setCategory(dto.getCategory());
        record.setDate(dto.getDate());
        record.setNotes(dto.getNotes());
        record.setUser(owner);

        return repo.save(record);
    }

    // SOFT DELETE
    public void delete(Long id, Long adminUserId) {

        // 1. Check param userId is ADMIN
        User admin = userRepo.findById(adminUserId)
                .orElseThrow(() -> new RuntimeException("Admin user not found"));

        if (!admin.getRole().getRoleName().equals("ADMIN")) {
            throw new UnauthorizedException("Only ADMIN can delete");
        }

        // 2. Find the record
        FinancialRecord record = repo.findById(id)
                .filter(r -> !r.isDeleted())
                .orElseThrow(() -> new RuntimeException("Record not found or already deleted"));

        // 3. Soft delete
        record.setDeleted(true);
        repo.save(record);
    }
}                                