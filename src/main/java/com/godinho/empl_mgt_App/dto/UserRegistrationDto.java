package com.godinho.empl_mgt_App.dto;

import com.godinho.empl_mgt_App.constraint.FieldMatch;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldMatch.List({
        @FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match"),
        @FieldMatch(first = "email", second = "confirmEmail", message = "The email fields must match")
})
public class UserRegistrationDto {
    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotEmpty
    private String password;

    @NotEmpty
    private String confirmPassword;

    @Email
    @NotEmpty
    private String email;

    @Email
    @NotEmpty
    private String confirmEmail;

    @AssertTrue
    private Boolean terms;
}
