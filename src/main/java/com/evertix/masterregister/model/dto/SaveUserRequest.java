package com.evertix.masterregister.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SaveUserRequest {
    private String username;
    private String email;
    private String role;
    private String name;
    private String lastName;
    private String dni;
    private String gender;
    private String address;
    private String phone;
    private String workArea;
}
