package com.evertix.masterregister.security.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    // Data of User
    private Long id;
    private String username;
    private String email;
    private String role;
    private String name;
    private String lastName;
    private String dni;
    private String gender;
    private String address;
    private String phone;

    public JwtResponse(String token, Long id, String username, String email, String role,
                       String name, String lastName, String dni, String gender, String address, String phone) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.name = name;
        this.lastName = lastName;
        this.dni = dni;
        this.gender = gender;
        this.address = address;
        this.phone = phone;
    }
}
