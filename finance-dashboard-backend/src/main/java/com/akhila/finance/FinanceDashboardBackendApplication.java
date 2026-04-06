package com.akhila.finance;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
    title = "Finance Dashboard API",
    version = "1.0",
    description = "Finance Dashboard Backend API Documentation\n\n" +
                  "**Setup Instructions:**\n" +
                  "First insert roles in DB:\n" +
                  "INSERT INTO role VALUES (1,'ADMIN'),(2,'VIEWER'),(3,'ANALYST')\n\n" +
                  "**Roles:**\n" +
                  "- 1 = ADMIN → can create/update/delete records & view per user dashboard\n" +
                  "- 2 = VIEWER → can only view records\n" +
                  "- 3 = ANALYST → can access overall dashboard analytics\n\n" +
                  "**How to Test:**\n" +
                  "1. Create users using User APIs\n" +
                  "2. Create records using Record APIs (need ADMIN userId)\n" +
                  "3. View analytics using Dashboard APIs"
))
public class FinanceDashboardBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(FinanceDashboardBackendApplication.class, args);
    }
}