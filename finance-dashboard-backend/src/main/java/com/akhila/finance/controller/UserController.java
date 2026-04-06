package com.akhila.finance.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.akhila.finance.model.User;
import com.akhila.finance.service.UserService;
import com.akhila.finance.DTO.*;

@RestController
@RequestMapping("/users")
@Tag(name = "1. User APIs", description = "Create users and assign roles - Start here first!")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping
    @Operation(
        summary = "Create a new user",
        description = "Creates a new user. roleId must exist in role table.\n" +
                      "- roleId 1 = ADMIN\n" +
                      "- roleId 2 = VIEWER\n" +
                      "- roleId 3 = ANALYST"
    )
    public ResponseEntity<ApiResponse<User>> createUser(
            @Valid @RequestBody UserDTO userDTO) {
        User created = service.createUser(userDTO);
        return ResponseEntity.ok(new ApiResponse<>("User created successfully", created));
    }

    @PutMapping("/assign-role")
    @Operation(
        summary = "Assign role to existing user",
        description = "Assigns a role to an existing user.\n" +
                      "userId must exist in user table\n" +
                      "roleId must exist in role table\n" +
                      "- roleId 1 = ADMIN\n" +
                      "- roleId 2 = VIEWER\n" +
                      "- roleId 3 = ANALYST"
    )
    public ResponseEntity<ApiResponse<User>> assignRole(
            @Valid @RequestBody AssignRoleDTO dto) {
        User updated = service.assignRole(dto.getUserId(), dto.getRoleId());
        return ResponseEntity.ok(new ApiResponse<>("Role assigned successfully", updated));
    }
}