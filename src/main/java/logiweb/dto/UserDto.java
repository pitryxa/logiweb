package logiweb.dto;

import logiweb.entity.enums.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    private Integer id;

    @NotNull
    @Size(min = 3, max = 30, message = "Field must be between 3 and 30 characters.")
    private String firstName;

    @NotNull
    @Size(min = 3, max = 30, message = "Field must be between 3 and 30 characters.")
    private String lastName;

    private Role role;

    @Email
    @Size(min = 6, max = 64, message = "Field must be between 6 and 64 characters.")
    private String email;

    @NotNull
    @Size(min = 8, max = 32, message = "Field must be between 8 and 32 characters.")
    private String password;

    @NotNull
    @Size(min = 8, max = 32, message = "Field must be between 8 and 32 characters.")
    @Transient
    private String confirmPassword;
}
