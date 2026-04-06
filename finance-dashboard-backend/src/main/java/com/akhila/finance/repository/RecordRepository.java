package com.akhila.finance.repository;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import com.akhila.finance.model.FinancialRecord;
import java.time.LocalDate;

public interface RecordRepository extends JpaRepository<FinancialRecord, Long> {

    // ✅ OVERALL Dashboard queries
    @Query("SELECT SUM(r.amount) FROM FinancialRecord r WHERE r.type = 'income' AND r.deleted = false")
    Double getTotalIncome();

    @Query("SELECT SUM(r.amount) FROM FinancialRecord r WHERE r.type = 'expense' AND r.deleted = false")
    Double getTotalExpense();

    @Query("SELECT r.category, SUM(r.amount) FROM FinancialRecord r WHERE r.deleted = false GROUP BY r.category")
    List<Object[]> getCategoryWiseTotals();

    @Query("SELECT r FROM FinancialRecord r WHERE r.deleted = false ORDER BY r.date DESC")
    List<FinancialRecord> getRecentTransactions(Pageable pageable);

    @Query("SELECT FUNCTION('MONTH', r.date), SUM(r.amount) FROM FinancialRecord r WHERE r.deleted = false GROUP BY FUNCTION('MONTH', r.date)")
    List<Object[]> getMonthlySummary();

    // ✅ PER USER Dashboard queries
    @Query("SELECT SUM(r.amount) FROM FinancialRecord r WHERE r.user.id = :userId AND r.type = 'income' AND r.deleted = false")
    Double getTotalIncomeByUser(@Param("userId") Long userId);

    @Query("SELECT SUM(r.amount) FROM FinancialRecord r WHERE r.user.id = :userId AND r.type = 'expense' AND r.deleted = false")
    Double getTotalExpenseByUser(@Param("userId") Long userId);

    @Query("SELECT r.category, SUM(r.amount) FROM FinancialRecord r WHERE r.user.id = :userId AND r.deleted = false GROUP BY r.category")
    List<Object[]> getCategoryWiseTotalsByUser(@Param("userId") Long userId);

    @Query("SELECT r FROM FinancialRecord r WHERE r.user.id = :userId AND r.deleted = false ORDER BY r.date DESC")
    List<FinancialRecord> getRecentTransactionsByUser(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT FUNCTION('MONTH', r.date), SUM(r.amount) FROM FinancialRecord r WHERE r.user.id = :userId AND r.deleted = false GROUP BY FUNCTION('MONTH', r.date)")
    List<Object[]> getMonthlySummaryByUser(@Param("userId") Long userId);

    // ✅ Filter query
    @Query("SELECT r FROM FinancialRecord r WHERE r.deleted = false " +
           "AND (:type IS NULL OR r.type = :type) " +
           "AND (:category IS NULL OR r.category = :category) " +
           "AND (:date IS NULL OR r.date = :date)")
    Page<FinancialRecord> findWithFilters(
            @Param("type") String type,
            @Param("category") String category,
            @Param("date") LocalDate date,
            Pageable pageable);
}