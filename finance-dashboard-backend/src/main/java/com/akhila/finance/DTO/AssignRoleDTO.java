package com.akhila.finance.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Assign role to existing user")
public class AssignRoleDTO {

    @NotNull(message = "User ID is required")
    @Schema(description = "Must be existing user ID from user table", example = "2", required = true)
    private Long userId;

    @NotNull(message = "Role ID is required")
    @Schema(description = "Must be existing role ID\n1=ADMIN, 2=VIEWER, 3=ANALYST", example = "1", required = true)
    private Long roleId;

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getRoleId() { return roleId; }
    public void setRoleId(Long roleId) { this.roleId = roleId; }
}