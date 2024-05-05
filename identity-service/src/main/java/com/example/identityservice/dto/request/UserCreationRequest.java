package com.example.identityservice.dto.request;

import com.example.identityservice.exception.ErrorCode;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreationRequest {
    @Size(min = 3, message = "USER_NAME_INVALID")
    private String userName;
    @Size(min = 8, message = "INVALID_ASSWORD")
    private String password;
    private String firstName;
    private String lastName;

    private LocalDate dateOfBirth;
}
