
# Finance Dashboard Backend API

## Tech Stack
- Java 17
- Spring Boot 4.0.5
- MySQL 8.0
- Spring Data JPA
- Swagger (OpenAPI 3.1)
- Maven

## Project Structure
src/main/java/com/akhila/finance
│
├── controller
│   ├── UserController.java
│   ├── RecordController.java
│   └── DashboardController.java
│
├── service
│   ├── UserService.java
│   ├── RecordService.java
│   └── DashboardService.java
│
├── repository
│   ├── UserRepository.java
│   ├── RecordRepository.java
│   └── RoleRepository.java
│
├── model
│   ├── User.java
│   ├── Role.java
│   └── FinancialRecord.java
│
├── dto
│   ├── UserDTO.java
│   ├── AssignRoleDTO.java
│   ├── FinancialRecordDTO.java
│   ├── MonthlySummaryDTO.java
│   └── ApiResponse.java
│
└── exception
    ├── UnauthorizedException.java
    └── GlobalExceptionHandler.java

## Database Setup
1. Create MySQL database:
```sql
CREATE DATABASE finance_db;
```

2. Insert roles (mandatory before testing):
```sql
INSERT INTO role (role_name) VALUES ('ADMIN');
INSERT INTO role (role_name) VALUES ('VIEWER');
INSERT INTO role (role_name) VALUES ('ANALYST');
```

## Application Properties
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/finance_db
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
```

## How to Run
```bash
cd finance-dashboard-backend
mvn spring-boot:run
```

## Swagger Documentation
http://localhost:8080/swagger-ui/index.html

## Roles & Permissions

| Role | Permissions |
|------|------------|
| ADMIN (roleId=1) | Create/Update/Delete records + Per user analytics |
| VIEWER (roleId=2) | View records only |
| ANALYST (roleId=3) | Overall dashboard analytics |

## API Endpoints

### User APIs
| Method | URL | Description |
|--------|-----|-------------|
| POST | /users | Create user |
| PUT | /users/assign-role | Assign role to user |

### Record APIs
| Method | URL | Description |
|--------|-----|-------------|
| POST | /records?userId={adminId} | Create record (ADMIN only) |
| GET | /records | Get all records with filters |
| GET | /records/{id}?userId={userId} | Get record by ID |
| PUT | /records/{id}?userId={adminId} | Update record (ADMIN only) |
| DELETE | /records/{id}?userId={adminId} | Soft delete (ADMIN only) |

### Dashboard APIs — Overall (ANALYST only)
| Method | URL | Description |
|--------|-----|-------------|
| GET | /dashboard/summary?analystId={id} | Overall summary |
| GET | /dashboard/total-income?analystId={id} | Total income |
| GET | /dashboard/total-expense?analystId={id} | Total expense |
| GET | /dashboard/balance?analystId={id} | Balance |
| GET | /dashboard/category-wise?analystId={id} | Category wise |
| GET | /dashboard/monthly-summary?analystId={id} | Monthly summary |
| GET | /dashboard/recent?analystId={id} | Recent transactions |

### Dashboard APIs — Per User (ADMIN only)
| Method | URL | Description |
|--------|-----|-------------|
| GET | /dashboard/summary/{userId}?adminId={id} | User summary |
| GET | /dashboard/income/{userId}?adminId={id} | User income |
| GET | /dashboard/expense/{userId}?adminId={id} | User expense |
| GET | /dashboard/balance/{userId}?adminId={id} | User balance |
| GET | /dashboard/category/{userId}?adminId={id} | User category wise |
| GET | /dashboard/recent/{userId}?adminId={id} | User recent |
| GET | /dashboard/monthly/{userId}?adminId={id} | User monthly |

## Features Implemented
- Role based access control (ADMIN, VIEWER, ANALYST)
- ADMIN validation on every create/update/delete
-  User existence check in DB
-  User active status check
-  Soft delete (records not permanently deleted)
-  Multi-filter support (type, category, date)
-  Pagination support
-  Global exception handling
- Input validation with proper error messages
-  ApiResponse wrapper for consistent responses
-  Swagger API documentation
-  Dashboard analytics (overall + per user)

## Testing Order in Swagger
1. POST /users → Create ADMIN user (roleId will be assigned next)
2. PUT /users/assign-role → Assign ADMIN role (roleId=1)
3. POST /users → Create normal user
4. PUT /users/assign-role → Assign VIEWER role (roleId=2)
5. POST /records?userId=1 → Create records for user
6. GET /records → View all records
7. GET /dashboard/total-income?analystId=3 → View analytics

## Error Responses
All errors return:
```json
{
    "message": "Error description",
    "data": null
}
```

## Success Responses
All success returns:
```json
{
    "message": "Success description",
    "data": { }
}
```
