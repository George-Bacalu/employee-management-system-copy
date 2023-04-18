package com.project.ems.user;

import com.project.ems.person.PersonDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto extends PersonDto {

    @Positive(message = "User ID must be positive")
    private Long id;

    @NotNull(message = "Role ID must not be null")
    @Positive(message = "Role ID must be positive")
    private Long roleId;
}
