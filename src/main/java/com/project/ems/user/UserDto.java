package com.project.ems.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    @Positive(message = "User ID must be positive")
    private Long id;

    @NotBlank(message = "Name must not be blank")
    private String name;

    @Email
    private String email;

    @NotBlank(message = "Password must not be blank")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+-=()])(?=\\S+$).{8,25}$")
    private String password;

    @NotBlank(message = "Mobile must not be blank")
    @Pattern(regexp = "^(00|\\+?40|0)(7\\d{2}|\\d{2}[13]|[2-37]\\d|8[02-9]|9[0-2])\\s?\\d{3}\\s?\\d{3}$")
    private String mobile;

    @NotBlank(message = "Address must not be blank")
    private String address;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @Positive(message = "Role ID must be positive")
    private Long roleId;
}
