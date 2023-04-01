package com.project.ems.role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleDto {

    @Positive(message = "Role ID must be positive")
    private Long id;

    @NotBlank(message = "Name must not be blank")
    private String name;
}
