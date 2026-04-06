package com.akhila.finance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import org.springframework.data.domain.PageRequest;
import com.akhila.finance.DTO.MonthlySummaryDTO;
import com.akhila.finance.model.FinancialRecord;
import com.akhila.finance.model.User;
import com.akhila.finance.exception.UnauthorizedException;
import com.akhila.finance.repository.RecordRepository;
import com.akhila.finance.repository.UserRepository;

@Service
public class DashboardService {

    @Autowired
    private RecordRepository repo;

    @Autowired
    private UserRepository userRepo;

    // Only ANALYST can access overall dashboard
    private void validateAnalyst(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getRole() == null) {
            throw new RuntimeException("User has no role assigned");
        }
        if (!"ANALYST".equals(user.getRole().getRoleName())) {
            throw new UnauthorizedException("Only ANALYST can access overall dashboard");
        }
    }

    // Only ADMIN can access per user dashboard
    private void validateAdmin(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getRole() == null) {
            throw new RuntimeException("User has no role assigned");
        }
        if (!"ADMIN".equals(user.getRole().getRoleName())) {
            throw new UnauthorizedException("Only ADMIN can access per user dashboard");
        }
    }

  
    // OVERALL — ANALYST only
  

    public Double getTotalIncome(Long analystId) {
        validateAnalyst(analystId);
        return Optional.ofNullable(repo.getTotalIncome()).orElse(0.0);
    }

    public Double getTotalExpense(Long analystId) {
        validateAnalyst(analystId);
        return Optional.ofNullable(repo.getTotalExpense()).orElse(0.0);
    }

    public Double getBalance(Long analystId) {
        validateAnalyst(analystId);
        Double income = Optional.ofNullable(repo.getTotalIncome()).orElse(0.0);
        Double expense = Optional.ofNullable(repo.getTotalExpense()).orElse(0.0);
        return income - expense;
    }

    public List<Object[]> getCategoryWise(Long analystId) {
        validateAnalyst(analystId);
        return repo.getCategoryWiseTotals();
    }

    public List<FinancialRecord> getRecent(Long analystId) {
        validateAnalyst(analystId);
        return repo.getRecentTransactions(PageRequest.of(0, 5));
    }

    public List<MonthlySummaryDTO> getMonthly(Long analystId) {
        validateAnalyst(analystId);
        List<Object[]> data = repo.getMonthlySummary();
        List<MonthlySummaryDTO> result = new ArrayList<>();
        for (Object[] row : data) {
            int month = (int) row[0];
            Double total = (Double) row[1];
            result.add(new MonthlySummaryDTO(month, total));
        }
        return result;
    }

   
    //  PER USER — ADMIN only
   

    public Map<String, Object> getUserSummary(Long targetUserId, Long adminId) {
        validateAdmin(adminId);

        // Check target user exists
        userRepo.findById(targetUserId)
                .orElseThrow(() -> new RuntimeException("Target user not found"));

        Map<String, Object> result = new HashMap<>();
        Double income = Optional.ofNullable(repo.getTotalIncomeByUser(targetUserId)).orElse(0.0);
        Double expense = Optional.ofNullable(repo.getTotalExpenseByUser(targetUserId)).orElse(0.0);

        result.put("totalIncome", income);
        result.put("totalExpense", expense);
        result.put("balance", income - expense);
        result.put("categoryWise", repo.getCategoryWiseTotalsByUser(targetUserId));
        result.put("recent", repo.getRecentTransactionsByUser(targetUserId, PageRequest.of(0, 5)));

        List<Object[]> monthlyData = repo.getMonthlySummaryByUser(targetUserId);
        List<MonthlySummaryDTO> monthlySummary = new ArrayList<>();
        for (Object[] row : monthlyData) {
            int month = (int) row[0];
            Double total = (Double) row[1];
            monthlySummary.add(new MonthlySummaryDTO(month, total));
        }
        result.put("monthlySummary", monthlySummary);
        return result;
    }

    public Double getTotalIncome(Long targetUserId, Long adminId) {
        validateAdmin(adminId);
        userRepo.findById(targetUserId)
                .orElseThrow(() -> new RuntimeException("Target user not found"));
        return Optional.ofNullable(repo.getTotalIncomeByUser(targetUserId)).orElse(0.0);
    }

    public Double getTotalExpense(Long targetUserId, Long adminId) {
        validateAdmin(adminId);
        userRepo.findById(targetUserId)
                .orElseThrow(() -> new RuntimeException("Target user not found"));
        return Optional.ofNullable(repo.getTotalExpenseByUser(targetUserId)).orElse(0.0);
    }

    public Double getBalance(Long targetUserId, Long adminId) {
        validateAdmin(adminId);
        userRepo.findById(targetUserId)
                .orElseThrow(() -> new RuntimeException("Target user not found"));
        Double income = Optional.ofNullable(repo.getTotalIncomeByUser(targetUserId)).orElse(0.0);
        Double expense = Optional.ofNullable(repo.getTotalExpenseByUser(targetUserId)).orElse(0.0);
        return income - expense;
    }

    public List<Object[]> getCategoryWise(Long targetUserId, Long adminId) {
        validateAdmin(adminId);
        userRepo.findById(targetUserId)
                .orElseThrow(() -> new RuntimeException("Target user not found"));
        return repo.getCategoryWiseTotalsByUser(targetUserId);
    }

    public List<FinancialRecord> getRecent(Long targetUserId, Long adminId) {
        validateAdmin(adminId);
        userRepo.findById(targetUserId)
                .orElseThrow(() -> new RuntimeException("Target user not found"));
        return repo.getRecentTransactionsByUser(targetUserId, PageRequest.of(0, 5));
    }

    public List<MonthlySummaryDTO> getMonthly(Long targetUserId, Long adminId) {
        validateAdmin(adminId);
        userRepo.findById(targetUserId)
                .orElseThrow(() -> new RuntimeException("Target user not found"));
        List<Object[]> data = repo.getMonthlySummaryByUser(targetUserId);
        List<MonthlySummaryDTO> result = new ArrayList<>();
        for (Object[] row : data) {
            int month = (int) row[0];
            Double total = (Double) row[1];
            result.add(new MonthlySummaryDTO(month, total));
        }
        
        return result;
    }
}