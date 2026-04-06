package com.akhila.finance.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(description = "User creation request")
public class UserDTO {

    @NotBlank(message = "Name is required")
    @Schema(description = "Full name of the user", example = "Akhila", required = true)
    private String name;

    @Schema(description = "User active status - true by default", example = "true")
    private boolean active = true;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}