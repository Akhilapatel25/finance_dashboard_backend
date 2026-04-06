package com.akhila.finance.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import com.akhila.finance.model.FinancialRecord;
import com.akhila.finance.DTO.*;
import com.akhila.finance.service.DashboardService;

@RestController
@RequestMapping("/dashboard")
@Tag(name = "3. Dashboard APIs", description = "Analytics - ANALYST for overall stats, ADMIN for per user stats")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    //OVERALL — ANALYST only
  
   @GetMapping("/summary")
    @Operation(
        summary = "Get overall summary",
        description = "Only ANALYST role can access\n" +
                      "Returns total income, expense, balance, category wise, recent, monthly"
    )
    public ResponseEntity<ApiResponse<Map<String, Object>>> getOverallSummary(
            @Parameter(description = "Must be ANALYST user ID", example = "3")
            @RequestParam Long analystId) {

        Map<String, Object> response = new HashMap<>();
        response.put("totalIncome", dashboardService.getTotalIncome(analystId));
        response.put("totalExpense", dashboardService.getTotalExpense(analystId));
        response.put("balance", dashboardService.getBalance(analystId));
        response.put("categoryWise", dashboardService.getCategoryWise(analystId));
        response.put("recent", dashboardService.getRecent(analystId));
        response.put("monthlySummary", dashboardService.getMonthly(analystId));
        return ResponseEntity.ok(new ApiResponse<>("Overall summary fetched", response));
    }

    @GetMapping("/total-income")
    @Operation(
        summary = "Get total income of all users",
        description = "Only ANALYST role can access"
    )
    public ResponseEntity<ApiResponse<Double>> getTotalIncome(
            @Parameter(description = " Must be ANALYST user ID", example = "3")
            @RequestParam Long analystId) {
        return ResponseEntity.ok(new ApiResponse<>("Total income fetched",
                dashboardService.getTotalIncome(analystId)));
    }

    @GetMapping("/total-expense")
    @Operation(
        summary = "Get total expense of all users",
        description = "Only ANALYST role can access"
    )
    public ResponseEntity<ApiResponse<Double>> getTotalExpense(
            @Parameter(description = "Must be ANALYST user ID", example = "3")
            @RequestParam Long analystId) {
        return ResponseEntity.ok(new ApiResponse<>("Total expense fetched",
                dashboardService.getTotalExpense(analystId)));
    }

    @GetMapping("/balance")
    @Operation(
        summary = "Get overall balance (Income - Expense)",
        description = "Only ANALYST role can access"
    )
    public ResponseEntity<ApiResponse<Double>> getBalance(
            @Parameter(description = "Must be ANALYST user ID", example = "3")
            @RequestParam Long analystId) {
        return ResponseEntity.ok(new ApiResponse<>("Balance fetched",
                dashboardService.getBalance(analystId)));
    }

    @GetMapping("/category-wise")
    @Operation(
        summary = "Get category wise totals",
        description = "Only ANALYST role can access"
    )
    public ResponseEntity<ApiResponse<List<Object[]>>> getCategoryWise(
            @Parameter(description = "Must be ANALYST user ID", example = "3")
            @RequestParam Long analystId) {
        return ResponseEntity.ok(new ApiResponse<>("Category wise fetched",
                dashboardService.getCategoryWise(analystId)));
    }

    @GetMapping("/monthly-summary")
    @Operation(
        summary = "Get monthly summary",
        description = "Only ANALYST role can access"
    )
    public ResponseEntity<ApiResponse<List<MonthlySummaryDTO>>> getMonthlySummary(
            @Parameter(description = " Must be ANALYST user ID", example = "3")
            @RequestParam Long analystId) {
        return ResponseEntity.ok(new ApiResponse<>("Monthly summary fetched",
                dashboardService.getMonthly(analystId)));
    }

    @GetMapping("/recent")
    @Operation(
        summary = "Get recent 5 transactions",
        description = " Only ANALYST role can access"
    )
    public ResponseEntity<ApiResponse<List<FinancialRecord>>> getRecentTransactions(
            @Parameter(description = "Must be ANALYST user ID", example = "3")
            @RequestParam Long analystId) {
        return ResponseEntity.ok(new ApiResponse<>("Recent transactions fetched",
                dashboardService.getRecent(analystId)));
    }

    
    //PER USER — ADMIN only
   

    @GetMapping("/summary/{targetUserId}")
    @Operation(
        summary = "Get full summary for specific user",
        description = "adminId = ADMIN user ID\n" +
                      "targetUserId = user whose data you want (must exist in user table)"
    )
    public ResponseEntity<ApiResponse<Map<String, Object>>> getUserSummary(
            @Parameter(description = "Target user ID", example = "2")
            @PathVariable Long targetUserId,
            @Parameter(description = "Must be ADMIN user ID", example = "1")
            @RequestParam Long adminId) {
        return ResponseEntity.ok(new ApiResponse<>("User summary fetched",
                dashboardService.getUserSummary(targetUserId, adminId)));
    }

    @GetMapping("/income/{targetUserId}")
    @Operation(
        summary = "Get income of specific user",
        description = "adminId = ADMIN user ID\n" +
                      "targetUserId = user whose income you want"
    )
    public ResponseEntity<ApiResponse<Double>> getUserIncome(
            @Parameter(description = "Target user ID", example = "2")
            @PathVariable Long targetUserId,
            @Parameter(description = " Must be ADMIN user ID", example = "1")
            @RequestParam Long adminId) {
        return ResponseEntity.ok(new ApiResponse<>("User income fetched",
                dashboardService.getTotalIncome(targetUserId, adminId)));
    }

    @GetMapping("/expense/{targetUserId}")
    @Operation(
        summary = "Get expense of specific user",
        description = "adminId = ADMIN user ID\n" +
                      "targetUserId = user whose expense you want"
    )
    public ResponseEntity<ApiResponse<Double>> getUserExpense(
            @Parameter(description = "Target user ID", example = "2")
            @PathVariable Long targetUserId,
            @Parameter(description = "Must be ADMIN user ID", example = "1")
            @RequestParam Long adminId) {
        return ResponseEntity.ok(new ApiResponse<>("User expense fetched",
                dashboardService.getTotalExpense(targetUserId, adminId)));
    }

    @GetMapping("/balance/{targetUserId}")
    @Operation(
        summary = "Get balance of specific user",
        description = "adminId = ADMIN user ID\n" +
                      "targetUserId = user whose balance you want\n" +
                      "Balance = User Income - User Expense"
    )
    public ResponseEntity<ApiResponse<Double>> getUserBalance(
            @Parameter(description = "Target user ID", example = "2")
            @PathVariable Long targetUserId,
            @Parameter(description = " Must be ADMIN user ID", example = "1")
            @RequestParam Long adminId) {
        return ResponseEntity.ok(new ApiResponse<>("User balance fetched",
                dashboardService.getBalance(targetUserId, adminId)));
    }

    @GetMapping("/category/{targetUserId}")
    @Operation(
        summary = "Get category wise totals for specific user",
        description = " adminId = ADMIN user ID\n" +
                      " targetUserId = user whose category data you want"
    )
    public ResponseEntity<ApiResponse<List<Object[]>>> getUserCategoryWise(
            @Parameter(description = "Target user ID", example = "2")
            @PathVariable Long targetUserId,
            @Parameter(description = " Must be ADMIN user ID", example = "1")
            @RequestParam Long adminId) {
        return ResponseEntity.ok(new ApiResponse<>("User category wise fetched",
                dashboardService.getCategoryWise(targetUserId, adminId)));
    }

    @GetMapping("/recent/{targetUserId}")
    @Operation(
        summary = "Get recent transactions of specific user",
        description = "adminId = ADMIN user ID\n" +
                      "targetUserId = user whose recent transactions you want"
    )
    public ResponseEntity<ApiResponse<List<FinancialRecord>>> getUserRecent(
            @Parameter(description = "Target user ID", example = "2")
            @PathVariable Long targetUserId,
            @Parameter(description = " Must be ADMIN user ID", example = "1")
            @RequestParam Long adminId) {
        return ResponseEntity.ok(new ApiResponse<>("User recent fetched",
                dashboardService.getRecent(targetUserId, adminId)));
    }

    @GetMapping("/monthly/{targetUserId}")
    @Operation(
        summary = "Get monthly summary of specific user",
        description = "adminId = ADMIN user ID\n" +
                      "targetUserId = user whose monthly data you want"
    )
    public ResponseEntity<ApiResponse<List<MonthlySummaryDTO>>> getUserMonthly(
            @Parameter(description = "Target user ID", example = "2")
            @PathVariable Long targetUserId,
            @Parameter(description = "Must be ADMIN user ID", example = "1")
            @RequestParam Long adminId) {
        return ResponseEntity.ok(new ApiResponse<>("User monthly fetched",
                dashboardService.getMonthly(targetUserId, adminId)));
    }
}